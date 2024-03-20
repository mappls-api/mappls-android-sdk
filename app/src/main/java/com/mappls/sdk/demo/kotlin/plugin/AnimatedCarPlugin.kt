package com.mappls.sdk.demo.kotlin.plugin


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.gson.JsonObject
import com.mappls.sdk.demo.R
import com.mappls.sdk.geojson.Feature
import com.mappls.sdk.geojson.FeatureCollection
import com.mappls.sdk.geojson.Point
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.Style
import com.mappls.sdk.maps.annotations.BubbleLayout
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.maps.style.expressions.Expression.*
import com.mappls.sdk.maps.style.layers.Property
import com.mappls.sdk.maps.style.layers.PropertyFactory.*
import com.mappls.sdk.maps.style.layers.SymbolLayer
import com.mappls.sdk.maps.style.sources.GeoJsonSource
import com.mappls.sdk.turf.TurfMeasurement
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class AnimatedCarPlugin(private val context: Context, mapView: MapView, private val mapplsMap: MapplsMap?) {
    private var car: Car? = null
    private var carSource: GeoJsonSource? = null
    private var nextPoint: LatLng? = null
    private var onUpdatePoint: OnUpdatePoint? = null
    private var isClearAllCallBacks: Boolean = false
    private var featureCollection: FeatureCollection? = null
    private var latLng: LatLng? = null

    private var layerIds: MutableList<String>? = null

    fun setOnUpdateNextPoint(onUpdatePoint: OnUpdatePoint) {
        this.onUpdatePoint = onUpdatePoint
    }



    interface OnUpdatePoint {
        fun updateNextPoint()
    }

    /**
     * To remove all callbacks
     */
    fun clearAllCallBacks() {
        isClearAllCallBacks = true
    }

    /**
     * Add all callbacks
     */
    fun addAllCallBacks() {
        isClearAllCallBacks = false
    }

    init {
        updateState()
        mapView.addOnDidFinishLoadingStyleListener {
            updateState()
            addMainCar(latLng, false)
        }
        this.mapplsMap?.addOnMapClickListener {
            this.handleClickIcon(it)
            return@addOnMapClickListener false
        }
    }

    /**
     * Animate Marker from current point to next point
     */
    fun animateCar() {
        isClearAllCallBacks = false
        val valueAnimator = ValueAnimator.ofObject(LatLngEvaluator(), car!!.current, car!!.next)
        valueAnimator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {

            private var latLng: LatLng? = null

            override fun onAnimationUpdate(animation: ValueAnimator) {
                if (!isClearAllCallBacks) {
                    latLng = animation.animatedValue as LatLng
                    car!!.current = latLng!!
                    updateCarSource()
                    car!!.feature!!.properties()!!.addProperty(PROPERTY_BEARING, Car.getBearing(car!!.current, car!!.next!!))
                }
            }
        })

        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (!isClearAllCallBacks) {
                    onUpdatePoint!!.updateNextPoint()
                }
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                if (!isClearAllCallBacks) {
                    car!!.feature!!.properties()!!.addProperty(PROPERTY_BEARING, Car.getBearing(car!!.current, car!!.next!!))
                }
            }
        })
        //        (long) (7 * car.current.distanceTo(car.next))
        valueAnimator.duration = (100 * car!!.current.distanceTo(car!!.next!!)).toLong()
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.start()
    }

    /**
     * For updating the next point
     *
     * @param nextPoint next point where the marker move
     */
    fun updateNextPoint(nextPoint: LatLng) {
        this.nextPoint = nextPoint
        car!!.next = nextPoint
    }

    /**
     * Update the maker feature
     */
    private fun updateCarSource() {
        car?.updateFeature()
        carSource?.setGeoJson(car?.feature!!)
    }

    /**
     * Add marker & Source
     *
     * @param latLng   position of the marker
     * @param selected if it true than it shows info window
     */
    fun addMainCar(latLng: LatLng?, selected: Boolean) {
        this.latLng = latLng
        this.nextPoint = latLng
        mapplsMap?.getStyle {
            setVisibility(true, it)
            val properties = JsonObject()
            properties.addProperty(PROPERTY_BEARING, Car.getBearing(latLng!!, nextPoint!!))
            properties.addProperty(FILTER_TEXT, "filter")
            properties.addProperty(TITLE, "Tittle")
            properties.addProperty(PROPERTY_NAME, "name")
            properties.addProperty(PROPERTY_ADDRESS, "Address")
            if (selected)
                properties.addProperty(PROPERTY_SELECTED, false)

            val feature = Feature.fromGeometry(
                Point.fromLngLat(
                    latLng.longitude,
                    latLng.latitude), properties)

            featureCollection = FeatureCollection.fromFeatures(arrayOf(feature))

            car = Car(feature, nextPoint)
            it.addImage(CAR,
                (ContextCompat.getDrawable(context, R.drawable.placeholder) as BitmapDrawable).bitmap)

            if(it.getSource(CAR_SOURCE) == null) {

                carSource = GeoJsonSource(CAR_SOURCE, featureCollection)
                it.addSource(carSource!!)
            }

        }

        featureCollection?.let { GenerateViewIconTask(WeakReference(this).get()!!).doBackground(it) }

    }

    /**
     * Generate Info window Icon
     */
    private class GenerateViewIconTask(private val activity: AnimatedCarPlugin, private val refreshSource: Boolean = true) {
        private val viewMap = HashMap<String, View>()
        private val activityRef: WeakReference<AnimatedCarPlugin> = WeakReference(activity)

        fun doBackground(vararg featureCollection: FeatureCollection) {
            CoroutineScope(Dispatchers.IO).launch {
                backgroundProcess(featureCollection[0])

                withContext(Dispatchers.Main) {
                    // UI Thread work here
                    val bitmapHashMap = HashMap<String, Bitmap>()
                    val activity = activityRef.get()
                    if (activity != null && bitmapHashMap != null) {
                        activity.setImageGenResults(bitmapHashMap)
                        if (refreshSource) {
                            activity.refreshSource()
                        }
                        Toast.makeText(activity.context, "Marker Instructions", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        private fun backgroundProcess(vararg params: FeatureCollection): AbstractMap<String, Bitmap>? {
            val activity = activityRef.get() ?: return null

            val imagesMap = HashMap<String, Bitmap>()
            val inflater = LayoutInflater.from(activity.context)
            val featureCollection = params[0]

            for (feature in featureCollection.features()!!) {
                val bubbleLayout = inflater.inflate(R.layout.symbol_layer_info_window_layout_callout, null) as BubbleLayout
                if (feature.hasProperty(PROPERTY_NAME)) {
                    val name = feature.getStringProperty(PROPERTY_NAME)
                    val titleTextView = bubbleLayout.findViewById<TextView>(R.id.info_window_title)
                    titleTextView.text = name

                    val address = feature.getStringProperty(PROPERTY_ADDRESS)
                    val addressTextView = bubbleLayout.findViewById<TextView>(R.id.info_window_description)
                    addressTextView.text = address

                    val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    bubbleLayout.measure(measureSpec, measureSpec)

                    val measuredWidth = bubbleLayout.measuredWidth.toFloat()
                    bubbleLayout.setArrowPosition(measuredWidth / 2 - 5)

                    val bitmap = SymbolGenerator.generate(bubbleLayout)
                    imagesMap[name] = bitmap
                    viewMap[name] = bubbleLayout
                }
            }

            return imagesMap
        }
    }

    /**
     * This method handles click events for SymbolLayer symbols.
     *
     *
     * When a SymbolLayer icon is clicked, we moved that feature to the selected state.
     *
     *
     * @param screenPoint the point on screen clicked
     */
    private fun handleClickIcon(screenPoint: LatLng) {
        val features = mapplsMap!!.queryRenderedFeatures(this.mapplsMap.projection.toScreenLocation(screenPoint), CAR_LAYER, SOURCE_LAYER_INFO_WINDOW)
        if (!features.isEmpty()) {
            val name = features[0].getStringProperty(PROPERTY_NAME)
            val featureList = featureCollection!!.features()
            for (i in 0 until Objects.requireNonNull(featureList)!!.size) {
                if (featureList!![i].hasProperty(PROPERTY_NAME)) {
                    if (featureList[i].getStringProperty(PROPERTY_NAME) == name) {
                        Timber.tag("TAG").d(featureList[i].toJson())
                        car!!.setSelected(!car!!.selected)
                    }
                }
            }
        }
    }


    /**
     * Add images on the map
     *
     * @param imageMap Hashmap of images
     */
    private fun setImageGenResults(imageMap: HashMap<String, Bitmap>) {
        mapplsMap?.getStyle {
            it.addImages(imageMap)
        }

    }


    /**
     * Refresh the Source to update state of the marker
     */
    private fun refreshSource() {
        //        updateCarSource();
        updateState()
    }


    /**
     * Update the state of the Marker.
     */
    private fun updateState() {
        mapplsMap?.getStyle {
            val source = it.getSource(CAR_SOURCE) as GeoJsonSource?
            if (source == null) {
                initialise(it)
                return@getStyle
            }
            if (featureCollection != null) {
                source.setGeoJson(featureCollection)
            }

            setVisibility(true, it)
        }

    }


    /**
     * Set the visibility of all layers
     *
     * @param visible if it is true than layers will be visible
     */
    private fun setVisibility(visible: Boolean, style: Style) {
        if (layerIds == null)
            return
        val layers = style.layers
        if (layers.size > 0) {
            for (layer in layers) {
                if (layerIds!!.contains(layer.id)) {

                    layer.setProperties(visibility(if (visible) Property.VISIBLE else Property.NONE))
                }
            }
        }
    }

    /**
     * Initialise the marker
     */
    private fun initialise(style: Style) {
        layerIds = ArrayList()
        if(style.getLayer(CAR_LAYER) == null) {
            val symbolLayer = SymbolLayer(CAR_LAYER, CAR_SOURCE)
            symbolLayer.withProperties(
                iconImage(CAR),
                iconRotate(get(PROPERTY_BEARING)),
                iconAllowOverlap(true),
                iconIgnorePlacement(true),
                iconRotationAlignment(Property.ICON_ROTATION_ALIGNMENT_MAP)

            )
            style.addLayer(symbolLayer)
            layerIds!!.add(symbolLayer.id)
        }

        if(style.getLayer(SOURCE_LAYER_INFO_WINDOW) == null) {
            val symbolLayerInfoWindow = SymbolLayer(SOURCE_LAYER_INFO_WINDOW, CAR_SOURCE)
                .withProperties(
                    /* show image with id title based on the value of the name feature property */
                    iconImage("{name}"),

                    /* set anchor of icon to bottom-left */
                    iconAnchor(Property.ICON_ANCHOR_BOTTOM_LEFT),

                    /* all info window and marker image to appear at the same time*/
                    iconAllowOverlap(true),

                    /* offset the info window to be above the marker */
                    iconOffset(arrayOf(-2f, -25f))
                )
                /* setData a filter to show only when selected feature property is true */
                .withFilter(eq(get(PROPERTY_SELECTED), literal(true)))

            style.addLayer(symbolLayerInfoWindow)
            layerIds?.add(symbolLayerInfoWindow.id)
        }
    }


    /**
     * Utility class to generate Bitmaps for Symbol.
     *
     *
     * Bitmaps can be added to the map with
     *
     */
    private object SymbolGenerator {

        /**
         * Generate a Bitmap from an Android SDK View.
         *
         * @param view the View to be drawn to a Bitmap
         * @return the generated bitmap
         */
        fun generate(view: View): Bitmap {


            val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view.measure(measureSpec, measureSpec)

            val measuredWidth = view.measuredWidth
            val measuredHeight = view.measuredHeight

            view.layout(0, 0, measuredWidth, measuredHeight)
            val bitmap1 = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)


            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            options.inBitmap = bitmap1
            //            BitmapFactory.decodeByteArray(bitmap1.getNinePatchChunk(), 0, view.getHeight(), options);
            val sampleSize = calculateInSampleSize(options, measuredWidth, measuredHeight)
            options.inSampleSize = sampleSize
            //            options.inBitmap = bitmap1;
            val bitmap = options.inBitmap
            bitmap.eraseColor(Color.TRANSPARENT)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }
    }

    /**
     * Evaluator for LatLng pairs
     */
    private class LatLngEvaluator : TypeEvaluator<LatLng> {

        private val latLng = LatLng()

        override fun evaluate(fraction: Float, startValue: LatLng, endValue: LatLng): LatLng {
            latLng.latitude = startValue.latitude + (endValue.latitude - startValue.latitude) * fraction
            latLng.longitude = startValue.longitude + (endValue.longitude - startValue.longitude) * fraction
            return latLng
        }
    }


    private class Car internal constructor(var feature: Feature?, internal var next: LatLng?) {
        internal var current: LatLng
        var selected: Boolean = false

        init {
            val point = feature!!.geometry() as Point?
            this.current = LatLng(point!!.latitude(), point.longitude())
        }

        /**
         * Set the value that marker is selected or not
         *
         * @param selected if it is true than Info window is visible
         */
        fun setSelected(selected: Boolean?) {
            this.selected = selected!!
        }

        /**
         * Update the feature of the marker
         */
        internal fun updateFeature() {

            feature = Feature.fromGeometry(Point.fromLngLat(
                current.longitude,
                current.latitude)
            )
            feature!!.properties()!!.addProperty(PROPERTY_BEARING, getBearing(current, next!!))
            feature!!.properties()!!.addProperty(FILTER_TEXT, "filter")
            feature!!.properties()!!.addProperty(TITLE, "Tittle")
            feature!!.properties()!!.addProperty(PROPERTY_NAME, "name")
            feature!!.properties()!!.addProperty(PROPERTY_ADDRESS, "Address")
            feature!!.properties()!!.addProperty(PROPERTY_SELECTED, selected)
        }

        companion object {
            /**
             * Get the bearing between two points
             *
             * @param from Current point
             * @param to   Next Point
             * @return bearing value in degree
             */
            fun getBearing(from: LatLng, to: LatLng): Float {
                return TurfMeasurement.bearing(
                    Point.fromLngLat(from.longitude, from.latitude),
                    Point.fromLngLat(to.longitude, to.latitude)
                ).toFloat()
            }
        }
    }

    companion object {

        private const val PROPERTY_BEARING = "bearing"
        private const val CAR = "car"
        private const val CAR_LAYER = "car-layer"
        private const val CAR_SOURCE = "car-source"

        private const val SOURCE_LAYER_INFO_WINDOW = "info-window-poi-marker-layer"
        private const val PROPERTY_SELECTED = "property-selected"
        private const val TITLE = "title"
        private const val PROPERTY_NAME = "name"
        private const val FILTER_TEXT = "filter_text"
        private const val PROPERTY_ADDRESS = "address"

        /**
         * Calculate the sample size of the image
         *
         * @param options   BitmapFactory options
         * @param reqWidth  required width
         * @param reqHeight required height
         * @return calculated sample size
         */
        private fun calculateInSampleSize(
            options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {

                val halfHeight = height / 2
                val halfWidth = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }

            return inSampleSize
        }
    }
}

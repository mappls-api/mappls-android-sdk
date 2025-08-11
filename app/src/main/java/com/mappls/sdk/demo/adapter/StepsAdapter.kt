package com.mappls.sdk.demo.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mappls.sdk.demo.databinding.AdpterStepsBinding
import com.mappls.sdk.plugin.directions.DirectionsUtils
import com.mappls.sdk.services.api.directions.models.LegStep
import java.text.DecimalFormat


class StepsAdapter(private val legSteps: List<LegStep>) : RecyclerView.Adapter<StepsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdpterStepsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.stepsText.text = DirectionsUtils.getTextInstructions((legSteps[position]))
        holder.binding.maneuverView.setManeuverTypeAndModifier(
            legSteps[position].maneuver().type(),
            legSteps[position].maneuver().modifier()
        )
        val type = legSteps[position].maneuver().type()
        if (type != null) {
            if (type.equals("roundabout", ignoreCase = true) || type.equals(
                    "rotary",
                    ignoreCase = true
                )
            ) {
                if (legSteps[position].maneuver().degree() != null) {
                    holder.binding.maneuverView.setRoundaboutAngle(
                        legSteps[position].maneuver().degree()!!.toFloat()
                    )
                } else {
                    holder.binding.maneuverView.setRoundaboutAngle(180f)
                }
            }
        }

        holder.binding.distanceText.text = String.format(
            "GO  %s",
            convertMetersToText(legSteps[position].distance())
        )
    }

    override fun getItemCount(): Int = legSteps.size


    class ViewHolder(val binding: AdpterStepsBinding): RecyclerView.ViewHolder(binding.root)

    private fun convertMetersToText(dist: Double): String {
        if ((dist).toInt() <= 1000) {
            val distt = dist.toString() + ""
            if (distt.indexOf(".") > -1) {
                val distance = distt.substring(0, distt.indexOf("."))
                return "$distance mt"
            } else {
                return "$distt mt"
            }
        } else {
            var distance = (dist / 1000)
            val df = DecimalFormat("#.#")
            distance = df.format(distance).toDouble()
            return "$distance km"
        }
    }
}



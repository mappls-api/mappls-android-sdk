package com.mappls.sdk.demo.java.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.activity.AutoSuggestActivity;
import com.mappls.sdk.demo.java.activity.DirectionActivity;
import com.mappls.sdk.demo.java.activity.DistanceActivity;
import com.mappls.sdk.demo.java.activity.GeoCodeActivity;
import com.mappls.sdk.demo.java.activity.HateOsNearbyActivity;
import com.mappls.sdk.demo.java.activity.NearByActivity;
import com.mappls.sdk.demo.java.activity.NearbyReportActivity;
import com.mappls.sdk.demo.java.activity.PlaceDetailActivity;
import com.mappls.sdk.demo.java.activity.PoiAlongRouteActivity;
import com.mappls.sdk.demo.java.activity.PredictiveDirectionActivity;
import com.mappls.sdk.demo.java.activity.PredictiveDistanceActivity;
import com.mappls.sdk.demo.java.activity.ReverseGeocodeActivity;
import com.mappls.sdk.demo.java.adapter.MapFeatureListAdapter;
import com.mappls.sdk.demo.java.model.FeaturesList;

import java.util.ArrayList;

public class ApiCallsFragment extends Fragment {
    RecyclerView featuresRecycleView;
    MapFeatureListAdapter.OnClickListener onClickListener;
    ArrayList<FeaturesList> featuresArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_mapfeatures_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        featuresRecycleView = view.findViewById(R.id.addfeaturesRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        featuresRecycleView.setLayoutManager(mLayoutManager);
        setList();
        MapFeatureListAdapter mapFeatureListAdapter = new MapFeatureListAdapter(featuresArrayList);
        featuresRecycleView.setAdapter(mapFeatureListAdapter);
        mapFeatureListAdapter.setOnClickListener(new MapFeatureListAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                if(position == 0) {
                    Intent autosuggestIntent = new Intent(getContext(), AutoSuggestActivity.class);
                    startActivity(autosuggestIntent);
                } else if (position == 1) {
                    Intent geoCodeApiIntent = new Intent(getContext(), GeoCodeActivity.class);
                    startActivity(geoCodeApiIntent);
                } else if (position == 2) {
                    Intent revreseGeoCodeIntent = new Intent(getContext(), ReverseGeocodeActivity.class);
                    startActivity(revreseGeoCodeIntent);
                } else if (position == 3) {
                    Intent nearByIntent = new Intent(getContext(), NearByActivity.class);
                    startActivity(nearByIntent);
                } else if (position == 4) {
                    Intent getDirectionIntent = new Intent(getContext(), DirectionActivity.class);
                    startActivity(getDirectionIntent);
                }  else if (position == 5) {
                    Intent getDirectionIntent = new Intent(getContext(), PredictiveDirectionActivity.class);
                    startActivity(getDirectionIntent);
                }else if (position == 6) {
                    Intent getDistanceIntent = new Intent(getContext(), DistanceActivity.class);
                    startActivity(getDistanceIntent);
                }else if (position == 7) {
                    Intent getDistanceIntent = new Intent(getContext(), PredictiveDistanceActivity.class);
                    startActivity(getDistanceIntent);
                }else if (position == 8) {
                    Intent getDistanceIntent = new Intent(getContext(), HateOsNearbyActivity .class);
                    startActivity(getDistanceIntent);
                }else if (position == 9) {
                    Intent getDistanceIntent = new Intent(getContext(), PoiAlongRouteActivity.class);
                    startActivity(getDistanceIntent);
                }else if (position == 10) {
                    Intent getDistanceIntent = new Intent(getContext(), PlaceDetailActivity.class);
                    startActivity(getDistanceIntent);
                }else if (position == 11) {
                    Intent getDistanceIntent = new Intent(getContext(), NearbyReportActivity.class);
                    startActivity(getDistanceIntent);
                }
            }
        });
    }
    private void setList() {
        featuresArrayList.add(new FeaturesList("Autosuggest","Auto suggest places on the map"));
        featuresArrayList.add(new FeaturesList("Geo Code", "Geocode rest API call"));
        featuresArrayList.add(new FeaturesList("Reverse Geocode", "Reverse Geocode rest API call"));
        featuresArrayList.add(new FeaturesList("Nearby", "Show nearby results on the map"));
        featuresArrayList.add(new FeaturesList("Get Direction", "Get directions between two points and show on the map"));
        featuresArrayList.add(new FeaturesList("Get Predictive Direction", "Get predictive directions between two points and show on the map"));
        featuresArrayList.add(new FeaturesList("Get Distance", "Get distance between points"));
        featuresArrayList.add(new FeaturesList("Get Predictive Distance", "Get predictive distance between points"));
        featuresArrayList.add(new FeaturesList("Hateos Nearby Api", "Nearby places using hateos api"));
        featuresArrayList.add(new FeaturesList("POI Along Route Api", "user will be able to get the details of POIs of a particular category along his set route"));
        featuresArrayList.add(new FeaturesList("Place Detail", "To get the place details from mappls pin"));
        featuresArrayList.add(new FeaturesList("Nearby Report", "To get the nearby reports on the map"));


    }
}

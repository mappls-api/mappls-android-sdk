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
import com.mappls.sdk.demo.java.activity.DirectionStepActivity;
import com.mappls.sdk.demo.java.activity.DirectionUiActivity;
import com.mappls.sdk.demo.java.activity.FeedbackUiActivity;
import com.mappls.sdk.demo.java.activity.GeoFenceActivity;
import com.mappls.sdk.demo.java.activity.NearbyUiActivity;
import com.mappls.sdk.demo.java.activity.PickerActivity;
import com.mappls.sdk.demo.java.activity.PlaceAutoCompleteActivity;
import com.mappls.sdk.demo.java.adapter.MapFeatureListAdapter;
import com.mappls.sdk.demo.java.model.FeaturesList;

import java.util.ArrayList;

public class CustomWidgetsFragment extends Fragment {
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
                if (position == 0) {
                    Intent placeAutoCompleteWidget = new Intent(getContext(), PlaceAutoCompleteActivity.class);
                    startActivity(placeAutoCompleteWidget);
                } else if (position == 1) {
                    Intent placePickerWidgetIntent = new Intent(getContext(), PickerActivity.class);
                    startActivity(placePickerWidgetIntent);
                } else if (position == 2) {
                    Intent geofenceWidgetIntent = new Intent(getContext(), GeoFenceActivity.class);
                    startActivity(geofenceWidgetIntent);
                } else if (position == 3) {
                    Intent directionStepIntent = new Intent(getContext(), DirectionStepActivity.class);
                    startActivity(directionStepIntent);
                } else if (position == 4) {
                    Intent directionUiIntent = new Intent(getContext(), DirectionUiActivity.class);
                    startActivity(directionUiIntent);
                } else if (position == 5) {
                    Intent nearbyUiIntent = new Intent(getContext(), NearbyUiActivity.class);
                    startActivity(nearbyUiIntent);
                } else if (position == 6) {
                    Intent feedbackIntent = new Intent(getContext(), FeedbackUiActivity.class);
                    startActivity(feedbackIntent);
                }
            }
        });
    }

    private void setList() {
        featuresArrayList.add(new FeaturesList("Place Autocomplete Widget", "Location search functionality and UI to search a place"));
        featuresArrayList.add(new FeaturesList("Place Picker", "Place Picker to search and choose a specific location"));
        featuresArrayList.add(new FeaturesList("GeoFence", "Highly customizable UI widget to create/edit geofence widget"));
        featuresArrayList.add(new FeaturesList("Direction Step", "How to show textual instructions and maneuver icon to the user"));
        featuresArrayList.add(new FeaturesList("Direction Widget", "Mappls Direction Widget to show Route on map"));
        featuresArrayList.add(new FeaturesList("Nearby Widget", "Mappls Nearby Widget to search nearby result on map"));
        featuresArrayList.add(new FeaturesList("Feedback Widget", "Mappls Feedback Widget to give Feedback"));


    }
}

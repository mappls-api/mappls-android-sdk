package com.mappls.sdk.demo.java.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.demo.java.adapter.StepsAdapter;
import com.mappls.sdk.geojson.Point;
import com.mappls.sdk.services.api.OnResponseCallback;
import com.mappls.sdk.services.api.directions.DirectionsCriteria;
import com.mappls.sdk.services.api.directions.MapplsDirectionManager;
import com.mappls.sdk.services.api.directions.MapplsDirections;
import com.mappls.sdk.services.api.directions.models.DirectionsResponse;
import com.mappls.sdk.services.api.directions.models.DirectionsRoute;
import com.mappls.sdk.services.api.directions.models.LegStep;
import com.mappls.sdk.services.api.directions.models.RouteLeg;

import java.util.ArrayList;
import java.util.List;

public class DirectionStepActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_step);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MapplsDirections mapplsDirections = MapplsDirections.builder()
                .origin(Point.fromLngLat(73.041932, 19.018686))
                .destination(Point.fromLngLat(73.040028, 19.019499))
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .resource(DirectionsCriteria.RESOURCE_ROUTE)
                .steps(true)
                .alternatives(false)
                .overview(DirectionsCriteria.OVERVIEW_FULL).build();
        MapplsDirectionManager.newInstance(mapplsDirections).call(new OnResponseCallback<DirectionsResponse>() {
            @Override
            public void onSuccess(DirectionsResponse directionsResponse) {
                if (directionsResponse != null) {
                    List<DirectionsRoute> results = directionsResponse.routes();

                    if (results.size() > 0) {
                        List<RouteLeg> routeLegList = results.get(0).legs();
                        List<LegStep> legSteps = new ArrayList<>();
                        for (RouteLeg routeLeg : routeLegList) {
                            legSteps.addAll(routeLeg.steps());
                        }
                        if (legSteps != null && legSteps.size() > 0) {
                            recyclerView.setAdapter(new StepsAdapter(legSteps));
                        }
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }
}

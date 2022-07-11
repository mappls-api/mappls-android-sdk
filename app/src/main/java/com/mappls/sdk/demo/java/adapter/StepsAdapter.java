package com.mappls.sdk.demo.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mappls.sdk.demo.R;
import com.mappls.sdk.plugin.directions.DirectionsUtils;
import com.mappls.sdk.plugin.directions.view.ManeuverView;
import com.mappls.sdk.services.api.directions.models.LegStep;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Saksham on 17/1/20.
 */
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private List<LegStep> legSteps;


    public StepsAdapter(List<LegStep> legSteps) {
        this.legSteps = legSteps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpter_steps, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.stepsText.setText(DirectionsUtils.getTextInstructions((legSteps.get(position))));
        holder.maneuverView.setManeuverTypeAndModifier(legSteps.get(position).maneuver().type(), legSteps.get(position).maneuver().modifier());


        String type = legSteps.get(position).maneuver().type();
        if (type != null) {
            if (type.equalsIgnoreCase("roundabout") || type.equalsIgnoreCase("rotary")) {
                if(legSteps.get(position).maneuver().degree() != null) {
                    holder.maneuverView.setRoundaboutAngle(legSteps.get(position).maneuver().degree().floatValue());
                } else {
                    holder.maneuverView.setRoundaboutAngle(180f);
                }
            }
        }

        holder.distanceText.setText(String.format("GO  %s", convertMetersToText(legSteps.get(position).distance())));
    }

    private String convertMetersToText(double dist) {
        if ((int) (dist) <= 1000) {
            String distt = dist + "";
            if (distt.indexOf(".") > -1) {
                String distance = distt.substring(0, distt.indexOf("."));
                return distance + " mt";
            } else {
                return distt + " mt";
            }

        } else {
            double distance = (dist / 1000);
            DecimalFormat df = new DecimalFormat("#.#");
            distance = Double.valueOf(df.format(distance));
            return distance + " km";
        }
    }

    @Override
    public int getItemCount() {
        if (legSteps == null) {
            return 0;
        }
        return legSteps.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView stepsText;
        private TextView distanceText;
        private ManeuverView maneuverView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            stepsText = itemView.findViewById(R.id.steps_text);
            distanceText = itemView.findViewById(R.id.distance_text);
            maneuverView = itemView.findViewById(R.id.navigate_icon);
        }
    }
}

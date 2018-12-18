package com.a6.projectgroep.bestofbreda.ViewModel.Adapters;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.View.Activities.DetailedActivity;
import com.a6.projectgroep.bestofbreda.ViewModel.SightsListViewModel;

import java.io.Serializable;
import java.util.List;

public class SightsRecyclerviewAdapter extends RecyclerView.Adapter<SightsRecyclerviewAdapter.ViewHolder> {
    private List<WaypointModel> sightList;
    private LayoutInflater inflater;
    private Context context;

    public SightsRecyclerviewAdapter(Context context, List<WaypointModel> sightList) {
        this.sightList = sightList;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.sightlistfragment_recyclerview_item2, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //WaypointModel wayPoint = sightsListViewModel.getWayPoint(i);
        //viewHolder.nameTextView.setText(wayPoint.getName());
        //Picasso.get().load(wayPoint.getMultimediaID().getPictureUrls().get(0)).into(viewHolder.backgroundImage);
        // TODO: get multimediaID from waypoint and search for it in multimediaDAO.
        WaypointModel waypointModel = sightList.get(i);
        viewHolder.nameTextView.setText(waypointModel.getName());
    }

    @Override
    public int getItemCount() {
        //return sightsListViewModel.getValue().size(); TODO:aanpassen
        return sightList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView backgroundImage;
        TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroundImage = itemView.findViewById(R.id.sightlistfragment_recyclerview_item_background);
            nameTextView = itemView.findViewById(R.id.sightlistfragment_recyclerview_item_name2);

            itemView.setOnClickListener(view ->
            {
//                Intent intent = new Intent(context, DetailedActivity.class);
//                intent.putExtra("ROUTE_ADAPTERPOS", getAdapterPosition());
//                context.startActivity(intent);
            });
        }
    }

    public void setSightList(List<WaypointModel> sightList) {
        this.sightList = sightList;
    }
}

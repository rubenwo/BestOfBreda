package com.a6.projectgroep.bestofbreda.ViewModel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.View.Activities.DetailedActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SightsRecyclerviewAdapter extends RecyclerView.Adapter<SightsRecyclerviewAdapter.ViewHolder> {
    private List<WaypointModel> sightList;
    private List<WaypointModel> defaultSightList;
    private LayoutInflater inflater;
    private Context context;

    public SightsRecyclerviewAdapter(Context context, List<WaypointModel> sightList) {
        this.sightList = sightList;
        this.defaultSightList = sightList;
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
        viewHolder.nameTextView.setTextColor(Color.WHITE);
        WaypointModel waypointModel = sightList.get(i);
        String url = waypointModel.getMultiMediaModel().getPictureUrls().get(0);
        if(url != null && !url.equals("")) {
            if (url.startsWith("api"))
                url = "https://" + url;
            Picasso.get().load(url).into(viewHolder.backgroundImage);
        }
        else
            viewHolder.nameTextView.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        viewHolder.nameTextView.setText(waypointModel.getName());
    }

    @Override
    public int getItemCount() {
        //return sightsListViewModel.getValue().size(); TODO:aanpassen
        return sightList.size();
    }

    public void setDataset(String filter){
        if(filter.equals("")){
            sightList = defaultSightList;
        }
        else {
            List<WaypointModel> filteredList = new ArrayList<>();
            for (WaypointModel m : defaultSightList) {
                if (m.getName().toLowerCase().contains(filter.toLowerCase()))
                    filteredList.add(m);
            }
            sightList = filteredList;
            notifyDataSetChanged();
        }
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
                Intent intent = new Intent(context, DetailedActivity.class);
                WaypointModel waypointModel = sightList.get(getAdapterPosition());
                intent.putExtra("SightName", waypointModel.getName());
                context.startActivity(intent);
            });
        }
    }

    public void setSightList(List<WaypointModel> s) {
        this.sightList = s;
        defaultSightList = sightList;
    }
}

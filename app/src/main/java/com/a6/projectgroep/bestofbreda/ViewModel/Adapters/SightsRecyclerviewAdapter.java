package com.a6.projectgroep.bestofbreda.ViewModel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.View.Activities.DetailedActivity;
import com.a6.projectgroep.bestofbreda.ViewModel.SightsListViewModel;
import com.squareup.picasso.Picasso;

public class SightsRecyclerviewAdapter  extends RecyclerView.Adapter<SightsRecyclerviewAdapter.ViewHolder>
{

    private SightsListViewModel sightsListViewModel;
    private LayoutInflater inflater;
    private Context context;

    public SightsRecyclerviewAdapter(Context context, SightsListViewModel viewModel)
    {
        sightsListViewModel = viewModel;
        inflater = LayoutInflater.from(context);
        sightsListViewModel = viewModel;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = inflater.inflate(R.layout.sightlistfragment_recyclerview_item2, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        WayPointModel wayPoint = sightsListViewModel.getWayPoint(i);

        viewHolder.nameTextView.setText(wayPoint.getName());
        Picasso.get().load(wayPoint.getMultimedia().getPictureUrls().get(0)).into(viewHolder.backgroundImage);
    }

    @Override
    public int getItemCount()
    {
        return sightsListViewModel.getAllWaypointModels().getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView backgroundImage;
        TextView nameTextView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            backgroundImage = itemView.findViewById(R.id.sightlistfragment_recyclerview_item_background);
            nameTextView = itemView.findViewById(R.id.sightlistfragment_recyclerview_item_name2);

            itemView.setOnClickListener(view ->
            {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("ROUTE_ADAPTERPOS", getAdapterPosition());
            });
        }
    }
}

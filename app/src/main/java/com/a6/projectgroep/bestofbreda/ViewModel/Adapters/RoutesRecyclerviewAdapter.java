package com.a6.projectgroep.bestofbreda.ViewModel.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.R;
import com.a6.projectgroep.bestofbreda.ViewModel.RouteListViewModel;

public class RoutesRecyclerviewAdapter extends RecyclerView.Adapter<RoutesRecyclerviewAdapter.ViewHolder>
{
    private RouteListViewModel routeListViewModel;
    private LayoutInflater inflater;
    private Context context;

    public RoutesRecyclerviewAdapter(Context context, RouteListViewModel viewModel)
    {
        routeListViewModel = viewModel;
        inflater = LayoutInflater.from(context);
        routeListViewModel = viewModel;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = inflater.inflate(R.layout.routeactivity_recyclerview_item2, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        RouteModel routeModel = routeListViewModel.getAllRouteModels().getValue().get(i);

        viewHolder.nameTextView.setText(routeModel.getName());
        viewHolder.distanceTextView.setText("TODO");
        //TODO add image to RouteModel and load here
    }

    @Override
    public int getItemCount()
    {
        return routeListViewModel.getAllRouteModels().getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView backgroundImage;
        TextView nameTextView;
        TextView distanceTextView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            backgroundImage = itemView.findViewById(R.id.routeactivity_recyclerview_item_background);
            nameTextView = itemView.findViewById(R.id.routeactivity_recyclerview_item_name2);
            distanceTextView = itemView.findViewById(R.id.routeactivity_recyclerview_item_distance2);

            itemView.setOnClickListener(view ->
            {
                //TODO load route
            });
        }
    }
}

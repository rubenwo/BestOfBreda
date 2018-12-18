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

import java.util.ArrayList;
import java.util.List;

public class RoutesRecyclerviewAdapter extends RecyclerView.Adapter<RoutesRecyclerviewAdapter.ViewHolder> {
    private List<RouteModel> currentList;
    private List<RouteModel> routesList;
    private LayoutInflater inflater;
    private Context context;
    private OnSelectRouteListener listener;

    public RoutesRecyclerviewAdapter(Context context, List<RouteModel> viewModel, OnSelectRouteListener listener) {
        routesList = viewModel;
        currentList = viewModel;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.routeactivity_recyclerview_item2, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RouteModel routeModel = currentList.get(i);

        viewHolder.nameTextView.setText(routeModel.getName());
        viewHolder.backgroundImage.setImageResource(
                context.getResources().getIdentifier(routeModel.getResourceID(),
                        "drawable",
                        context.getPackageName()));
        viewHolder.backgroundImage.setOnClickListener(view -> listener.onSelectRoute(routeModel));
    }

    public void setRoutes(List<RouteModel> routes) {
        this.routesList = routes;
        currentList = routesList;
    }

    @Override
    public int getItemCount() {
        return currentList.size();
    }

    public void setDataset(String filter) {
        List<RouteModel> filteredList = new ArrayList<>();
        for (RouteModel m : routesList) {
            if (m.getName().toLowerCase().contains(filter.toLowerCase()))
                filteredList.add(m);
        }
        currentList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView backgroundImage;
        TextView nameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroundImage = itemView.findViewById(R.id.routeactivity_recyclerview_item_background);
            nameTextView = itemView.findViewById(R.id.routeactivity_recyclerview_item_name2);
        }
    }

    public interface OnSelectRouteListener {
        void onSelectRoute(RouteModel routeModel);
    }
}

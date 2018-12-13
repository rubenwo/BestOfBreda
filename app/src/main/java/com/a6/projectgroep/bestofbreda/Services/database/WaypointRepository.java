package com.a6.projectgroep.bestofbreda.Services.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;

import java.util.List;

public class WaypointRepository {
    private WaypointDAO waypointDAO;
    private LiveData<List<WaypointModel>> mData;

    public WaypointRepository(Application application) {
        NavigationDatabase database = NavigationDatabase.getInstance(application);
        waypointDAO = database.waypointDAO();
        mData = waypointDAO.getAllWayPoints();
    }

    public LiveData<List<WaypointModel>> getAllWaypoints() {
        return mData;
    }

    public void insertWaypoint(WaypointModel model) {
        new InsertWaypointAsyncTask(waypointDAO).doInBackground(model);
    }

    public void updateWaypoint(WaypointModel model) {
        new UpdateWaypointAsyncTask(waypointDAO).doInBackground(model);
    }

    public void deleteWaypoint(WaypointModel model) {
        new DeleteWaypointAsyncTask(waypointDAO).doInBackground(model);
    }

    private static class InsertWaypointAsyncTask extends AsyncTask<WaypointModel, Void, Void> {
        private WaypointDAO waypointDAO;

        private InsertWaypointAsyncTask(WaypointDAO dao) {
            this.waypointDAO = dao;
        }

        @Override
        protected Void doInBackground(WaypointModel... waypointModels) {
            waypointDAO.insertWaypoint(waypointModels[0]);
            return null;
        }
    }

    private static class UpdateWaypointAsyncTask extends AsyncTask<WaypointModel, Void, Void> {
        private WaypointDAO waypointDAO;

        private UpdateWaypointAsyncTask(WaypointDAO dao) {
            this.waypointDAO = dao;
        }

        @Override
        protected Void doInBackground(WaypointModel... waypointModels) {
            waypointDAO.updateWaypoint(waypointModels[0]);
            return null;
        }
    }

    private static class DeleteWaypointAsyncTask extends AsyncTask<WaypointModel, Void, Void> {
        private WaypointDAO waypointDAO;

        private DeleteWaypointAsyncTask(WaypointDAO dao) {
            this.waypointDAO = dao;
        }

        @Override
        protected Void doInBackground(WaypointModel... waypointModels) {
            waypointDAO.deleteWaypoint(waypointModels[0]);
            return null;
        }
    }

}


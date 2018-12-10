package com.a6.projectgroep.bestofbreda.Services.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.a6.projectgroep.bestofbreda.Model.WayPointModel;

import java.util.List;

public class WaypointRepository {
    private WaypointDAO wayPointDAO;
    private LiveData<List<WayPointModel>> mWaypoint;

    public WaypointRepository(Application application) {
        NavigationDatabase navigationDatabase = NavigationDatabase.getInstance(application);
        wayPointDAO = navigationDatabase.waypointDAO();
        mWaypoint = wayPointDAO.getAllWaypoints();
    }

    public void insertMultiMedia(WayPointModel model) {
        new WaypointRepository.InsertMultiMediaAsyncTask(wayPointDAO).execute(model);
    }

    public void updateTestData(WayPointModel model) {
        new WaypointRepository.UpdateTestDataAsyncTask(wayPointDAO).execute(model);
    }

    public void deleteTestData(WayPointModel model) {
        new WaypointRepository.DeleteTestDataAsyncTask(wayPointDAO).execute(model);
    }

    public LiveData<List<WayPointModel>> getAllTestData() {
        return mWaypoint;
    }

    private static class InsertMultiMediaAsyncTask extends AsyncTask<WayPointModel, Void, Void> {
        private WaypointDAO waypointDAO;

        private InsertMultiMediaAsyncTask(WaypointDAO waypointDAO) {
            this.waypointDAO = waypointDAO;
        }

        @Override
        protected Void doInBackground(WayPointModel... model) {
            waypointDAO.insertWaypoint(model[0]);
            return null;
        }
    }

    private static class UpdateTestDataAsyncTask extends AsyncTask<WayPointModel, Void, Void> {
        private WaypointDAO waypointDAO;

        private UpdateTestDataAsyncTask(WaypointDAO waypointDAO) {
            this.waypointDAO = waypointDAO;
        }

        @Override
        protected Void doInBackground(WayPointModel... models) {
            waypointDAO.updateWaypoint(models[0]);
            return null;
        }
    }

    private static class DeleteTestDataAsyncTask extends AsyncTask<WayPointModel, Void, Void> {
        private WaypointDAO waypointDAO;

        private DeleteTestDataAsyncTask(WaypointDAO waypointDAO) {
            this.waypointDAO = waypointDAO;
        }

        @Override
        protected Void doInBackground(WayPointModel... models) {
            waypointDAO.deleteWayPoint(models[0]);
            return null;
        }
    }
}

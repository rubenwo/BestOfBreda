package com.a6.projectgroep.bestofbreda.Services.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.a6.projectgroep.bestofbreda.Model.RouteModel;

import java.util.List;

public class RouteRepository {
    private RouteDAO routeDAO;
    private LiveData<List<RouteModel>> mRoute;

    public RouteRepository(Application application) {
        NavigationDatabase navigationDatabase = NavigationDatabase.getInstance(application);
        routeDAO = navigationDatabase.routeDAO();
        mRoute = routeDAO.getAllRoutes();
    }

    public void insertMultiMedia(RouteModel model) {
        new RouteRepository.InsertMultiMediaAsyncTask(routeDAO).execute(model);
    }

    public void updateTestData(RouteModel model) {
        new RouteRepository.UpdateTestDataAsyncTask(routeDAO).execute(model);
    }

    public void deleteTestData(RouteModel model) {
        new RouteRepository.DeleteTestDataAsyncTask(routeDAO).execute(model);
    }

    public LiveData<List<RouteModel>> getAllTestData() {
        return mRoute;
    }

    private static class InsertMultiMediaAsyncTask extends AsyncTask<RouteModel, Void, Void> {
        private RouteDAO routeDAO;

        private InsertMultiMediaAsyncTask(RouteDAO routeDAO) {
            this.routeDAO = routeDAO;
        }

        @Override
        protected Void doInBackground(RouteModel... model) {
            routeDAO.insertRoute(model[0]);
            return null;
        }
    }

    private static class UpdateTestDataAsyncTask extends AsyncTask<RouteModel, Void, Void> {
        private RouteDAO routeDAO;

        private UpdateTestDataAsyncTask(RouteDAO routeDAO) {
            this.routeDAO = routeDAO;
        }

        @Override
        protected Void doInBackground(RouteModel... models) {
            routeDAO.updateRoute(models[0]);
            return null;
        }
    }

    private static class DeleteTestDataAsyncTask extends AsyncTask<RouteModel, Void, Void> {
        private RouteDAO routeDAO;

        private DeleteTestDataAsyncTask(RouteDAO routeDAO) {
            this.routeDAO = routeDAO;
        }

        @Override
        protected Void doInBackground(RouteModel... models) {
            routeDAO.deleteRoute(models[0]);
            return null;
        }
    }
}

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

    public void insertRouteModel(RouteModel model) {
        new InsertRouteModelAsyncTask(routeDAO).execute(model);
    }

    public void updateRouteModel(RouteModel model) {
        new UpdateRouteModelAsyncTask(routeDAO).execute(model);
    }

    public void deleteRouteModel(RouteModel model) {
        new DeleteRouteModelAsycTask(routeDAO).execute(model);
    }

    public LiveData<List<RouteModel>> getAllRouteModels() {
        return mRoute;
    }

    public LiveData<RouteModel> getRouteModel(String routeString) {
        return routeDAO.getLiveRoute(routeString);
    }

    private static class InsertRouteModelAsyncTask extends AsyncTask<RouteModel, Void, Void> {
        private RouteDAO routeDAO;

        private InsertRouteModelAsyncTask(RouteDAO routeDAO) {
            this.routeDAO = routeDAO;
        }

        @Override
        protected Void doInBackground(RouteModel... model) {
            routeDAO.insertRoute(model[0]);
            return null;
        }
    }

    private static class UpdateRouteModelAsyncTask extends AsyncTask<RouteModel, Void, Void> {
        private RouteDAO routeDAO;

        private UpdateRouteModelAsyncTask(RouteDAO routeDAO) {
            this.routeDAO = routeDAO;
        }

        @Override
        protected Void doInBackground(RouteModel... models) {
            routeDAO.updateRoute(models[0]);
            return null;
        }
    }

    private static class DeleteRouteModelAsycTask extends AsyncTask<RouteModel, Void, Void> {
        private RouteDAO routeDAO;

        private DeleteRouteModelAsycTask(RouteDAO routeDAO) {
            this.routeDAO = routeDAO;
        }

        @Override
        protected Void doInBackground(RouteModel... models) {
            routeDAO.deleteRoute(models[0]);
            return null;
        }
    }

//    private static class GetRouteAsyncTask extends AsyncTask<String, Void, Void> {
//        private RouteDAO routeDAO;
//
//        private GetRouteAsyncTask(RouteDAO routeDAO) {
//            this.routeDAO = routeDAO;
//        }
//
//        @Override
//        protected Void doInBackground(String... strings) {
//            routeDAO.getLiveRoute(strings[0]);
//            return null;
//        }
//    }
}

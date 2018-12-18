package com.a6.projectgroep.bestofbreda.Services.database;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.Services.JsonDecoder;

@Database(entities = {MultimediaModel.class, RouteModel.class, WaypointModel.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class NavigationDatabase extends RoomDatabase {

    private volatile static NavigationDatabase instance;
    private static Application mApplication;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateWithTestDataAsyncTask(instance).execute();
        }
    };

    public static synchronized NavigationDatabase getInstance(Application application) {
        if (instance == null) {
            mApplication = application;
            instance = Room.databaseBuilder(application, NavigationDatabase.class, "navigation-database")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract MultimediaDAO multiMediaDAO();

    public abstract RouteDAO routeDAO();

    public abstract WaypointDAO waypointDAO();

    private static class PopulateWithTestDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private MultimediaDAO multiMediaDAO;
        private RouteDAO routeDAO;
        private WaypointDAO waypointDAO;

        private PopulateWithTestDataAsyncTask(NavigationDatabase database) {
            multiMediaDAO = database.multiMediaDAO();
            routeDAO = database.routeDAO();
            waypointDAO = database.waypointDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            JsonDecoder.decodeJsonRouteFile(mApplication, "BlindwallsRoute.json", routeDAO);
            JsonDecoder.decodeJsonRouteFile(mApplication, "HistorischeRoute.json", routeDAO);
            JsonDecoder.decodeJsonWaypointsFile(mApplication, "Blindwalls.json", waypointDAO);
            JsonDecoder.decodeJsonWaypointsFile(mApplication, "HistorischeRouteWaypoints.json", waypointDAO);

            return null;
        }
    }
}

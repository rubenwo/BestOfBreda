package com.a6.projectgroep.bestofbreda.Services.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WaypointModel;
import com.a6.projectgroep.bestofbreda.Services.JsonDecoder;
import com.a6.projectgroep.bestofbreda.Services.UserPreferences;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;

@Database(entities = {MultimediaModel.class, RouteModel.class, WaypointModel.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class NavigationDatabase extends RoomDatabase {

    private volatile static NavigationDatabase instance;
    private static Context mContext;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateWithTestDataAsyncTask(instance).execute();
        }
    };

    public static synchronized NavigationDatabase getInstance(Context context) {
        if (instance == null) {
            mContext = context;
            instance = Room.databaseBuilder(context.getApplicationContext(), NavigationDatabase.class, "navigation-database")
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
            List<String> strings = Arrays.asList("Test", "Test2");
            String string = "testString";
            MultimediaModel multiMedia = new MultimediaModel(strings, string);
            multiMediaDAO.insertMultiMedia(multiMedia);
            routeDAO.insertRoute(new RouteModel(Arrays.asList("1", "2", "3", "4"), "nameOfRoute", false, "resource"));
            waypointDAO.insertWaypoint(new WaypointModel("name", "desc", new LatLng(1.23, 4.56), false, false, multiMedia));

            UserPreferences preferences = UserPreferences.getInstance(mContext);
            if (preferences.getFirstRun()) {
                if (!JsonDecoder.decodeJsonRouteFile(mContext, "BlindwallsRoute.json", routeDAO)) {
                    return null;
                }
                JsonDecoder.decodeJsonRouteFile(mContext, "HistorischeRoute.json", routeDAO);
                JsonDecoder.decodeJsonWaypointsFile(mContext, "Blindwalls.json", waypointDAO);
                JsonDecoder.decodeJsonWaypointsFile(mContext, "HistorsicheRouteWaypoints.json", waypointDAO);
                preferences.setFirstRun(false);
            }
            return null;
        }
    }
}

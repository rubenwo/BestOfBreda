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
import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {MultimediaModel.class, RouteModel.class, WayPointModel.class}, version = 1)
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

        //TODO: This is only called when running for the first time after installing for some weird reason..... It needs to run every time the app launches.
        @Override
        protected Void doInBackground(Void... voids) {
            List<String> testList = new ArrayList<>();
            multiMediaDAO.insertMultiMedia(new MultimediaModel(new ArrayList<String>(), new ArrayList<String>()));
            routeDAO.insertRoute(new RouteModel(new ArrayList<Integer>(), "nameOfRoute", false));
            waypointDAO.insertWaypoint(new WayPointModel("nameOfWaypoint", new LatLng(1.2d, 3.4d), "waypointDescription", 100,
                    false, false, new MultimediaModel(testList, testList)));
            return null;
        }
    }
}

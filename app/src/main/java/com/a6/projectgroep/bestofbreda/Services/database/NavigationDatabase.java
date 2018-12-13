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
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;

@Database(entities = {MultimediaModel.class, RouteModel.class, WaypointModel.class}, version = 1)
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
            multiMediaDAO.insertMultiMedia(new MultimediaModel(1, Arrays.asList("test", "test2"), "url"));
            routeDAO.insertRoute(new RouteModel(Arrays.asList(1, 2, 3, 4), "nameOfRoute", false));
            waypointDAO.insertWaypoint(new WaypointModel(1, "name", "desc", new LatLng(1.23, 4.56), false, false, 1));
            return null;
        }
    }
}

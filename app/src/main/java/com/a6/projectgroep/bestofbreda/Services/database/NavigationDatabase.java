package com.a6.projectgroep.bestofbreda.Services.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;
import com.a6.projectgroep.bestofbreda.Model.RouteModel;
import com.a6.projectgroep.bestofbreda.Model.WayPointModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

@Database(entities = {MultimediaModel.class, RouteModel.class, WayPointModel.class}, version = 1)
public abstract class NavigationDatabase extends RoomDatabase{

    private volatile static NavigationDatabase instance;

    public abstract MultimediaDAO multiMediaDAO();
    public abstract RouteDAO routeDAO();
    public abstract WaypointDAO waypointDAO();

    public static synchronized NavigationDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NavigationDatabase.class, "navigation-database")
                    .addCallback(roomCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateWithTestDataAsyncTask(instance).execute();
        }
    };
    private static class PopulateWithTestDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private MultimediaDAO multiMediaDAO;
        private RouteDAO routeDAO;
        private WaypointDAO waypointDAO;

        private PopulateWithTestDataAsyncTask(NavigationDatabase database){
            multiMediaDAO = database.multiMediaDAO();
            routeDAO = database.routeDAO();
            waypointDAO = database.waypointDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            multiMediaDAO.insertMultiMedia(new MultimediaModel(new ArrayList<String>(), new ArrayList<String>()));
            routeDAO.insertRoute(new RouteModel(new ArrayList<Integer>(), "nameOfRoute", false));
            waypointDAO.insertWaypoint(new WayPointModel("nameOfWaypoint", new LatLng(1.2d,3.4d), "waypointDescription", 100,
                    false, false, new MultimediaModel(new ArrayList<String>(), new ArrayList<String>())));
            return null;
        }
    }
}

package com.a6.projectgroep.bestofbreda.Services.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;

import java.util.List;

public class MultimediaRepository {
    private RouteDAO multimediaDAO;
    private LiveData<List<MultimediaModel>> mMultiMedia;

    public MultimediaRepository(Application application){
        NavigationDatabase navigationDatabase = NavigationDatabase.getInstance(application);
        multimediaDAO = navigationDatabase.multiMediaDAO();
        mMultiMedia = multimediaDAO.getAllMultimedia();
    }
    public void insertMultiMedia(MultimediaModel model){
        new InsertMultiMediaAsyncTask(multimediaDAO).execute(model);
    }
    public void updateTestData(MultimediaModel model){
        new UpdateTestDataAsyncTask(multimediaDAO).execute(model);
    }
    public void deleteTestData(MultimediaModel model){
        new DeleteTestDataAsyncTask(multimediaDAO).execute(model);
    }
    public LiveData<List<MultimediaModel>> getAllTestData(){
        return mMultiMedia;
    }

    private static class InsertMultiMediaAsyncTask extends AsyncTask<MultimediaModel, Void, Void> {
        private MultimediaDAO multimediaDAO;

        private InsertMultiMediaAsyncTask(MultimediaDAO multimediaDAO){
            this.multimediaDAO = multimediaDAO;
        }

        @Override
        protected Void doInBackground(MultimediaModel... model) {
            multimediaDAO.insertMultiMedia(model[0]);
            return null;
        }
    }
    private static class UpdateTestDataAsyncTask extends AsyncTask<MultimediaModel, Void, Void>{
        private MultimediaDAO multimediaDAO;

        private UpdateTestDataAsyncTask(MultimediaDAO multimediaDAO){
            this.multimediaDAO = multimediaDAO;
        }

        @Override
        protected Void doInBackground(MultimediaModel... multimediaModels) {
            multimediaDAO.updateMultiMedia(multimediaModels[0]);
            return null;
        }
    }
    private static class DeleteTestDataAsyncTask extends AsyncTask<MultimediaModel, Void, Void>{
        private MultimediaDAO multimediaDAO;

        private DeleteTestDataAsyncTask(MultimediaDAO multimediaDAO){
            this.multimediaDAO = multimediaDAO;
        }

        @Override
        protected Void doInBackground(MultimediaModel... models) {
            multimediaDAO.deleteMultiMedia(models[0]);
            return null;
        }
    }
}

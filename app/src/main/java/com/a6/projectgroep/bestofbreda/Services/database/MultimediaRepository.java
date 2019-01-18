package com.a6.projectgroep.bestofbreda.Services.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;

import java.util.List;

public class MultimediaRepository {
    private MultimediaDAO multimediaDAO;
    private LiveData<List<MultimediaModel>> mMultiMedia;

    public MultimediaRepository(Application application) {
        NavigationDatabase navigationDatabase = NavigationDatabase.getInstance(application);
        multimediaDAO = navigationDatabase.multiMediaDAO();
        mMultiMedia = multimediaDAO.getAllMultimedia();
    }

    public void insertMultiMedia(MultimediaModel model) {
        new InsertMultiMediaAsyncTask(multimediaDAO).execute(model);
    }

    public void updateMultiMedia(MultimediaModel model) {
        new UpdateMultiMediaAsyncTask(multimediaDAO).execute(model);
    }

    public void deleteMultiMedia(MultimediaModel model) {
        new DeleteMultiMediaAsyncTask(multimediaDAO).execute(model);
    }

    public LiveData<List<MultimediaModel>> getAllMultiMedia() {
        return mMultiMedia;
    }

    private static class InsertMultiMediaAsyncTask extends AsyncTask<MultimediaModel, Void, Void> {
        private MultimediaDAO multimediaDAO;

        private InsertMultiMediaAsyncTask(MultimediaDAO multimediaDAO) {
            this.multimediaDAO = multimediaDAO;
        }

        @Override
        protected Void doInBackground(MultimediaModel... model) {
            multimediaDAO.insertMultiMedia(model[0]);
            return null;
        }
    }

    private static class UpdateMultiMediaAsyncTask extends AsyncTask<MultimediaModel, Void, Void> {
        private MultimediaDAO multimediaDAO;

        private UpdateMultiMediaAsyncTask(MultimediaDAO multimediaDAO) {
            this.multimediaDAO = multimediaDAO;
        }

        @Override
        protected Void doInBackground(MultimediaModel... multimediaModels) {
            multimediaDAO.updateMultiMedia(multimediaModels[0]);
            return null;
        }
    }

    private static class DeleteMultiMediaAsyncTask extends AsyncTask<MultimediaModel, Void, Void> {
        private MultimediaDAO multimediaDAO;

        private DeleteMultiMediaAsyncTask(MultimediaDAO multimediaDAO) {
            this.multimediaDAO = multimediaDAO;
        }

        @Override
        protected Void doInBackground(MultimediaModel... models) {
            multimediaDAO.deleteMultiMedia(models[0]);
            return null;
        }
    }
}
package com.a6.projectgroep.bestofbreda.Services.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.a6.projectgroep.bestofbreda.Model.MultimediaModel;

import java.util.List;

@Dao
public interface MultimediaDAO {
    @Query("SELECT * FROM MULTIMEDIA_MODEL")
    LiveData<List<MultimediaModel>> getAllMultimedia();

    @Query("SELECT * FROM MULTIMEDIA_MODEL WHERE id = :multimediaID")
    LiveData<MultimediaModel> getLiveMultimedia(int multimediaID);

    @Insert
    void insertMultiMedia(MultimediaModel multimediaModel);

    @Update
    void updateMultiMedia(MultimediaModel multimediaMode);

    @Delete
    void deleteMultiMedia(MultimediaModel multimediaMode);
}
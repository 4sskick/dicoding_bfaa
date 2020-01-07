package com.niteroomcreation.basemade.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.niteroomcreation.basemade.data.local.dao.MovieDao;
import com.niteroomcreation.basemade.data.local.dao.TvDao;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;

/**
 * Created by Septian Adi Wijaya on 07/01/20
 */

@Database(entities = {MovieEntity.class, TvEntity.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    public abstract TvDao tvDao();

    private static volatile LocalDatabase instance;

    public static LocalDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalDatabase.class) {
                if (instance == null) {
                    instance = buildDatabase(context);
                }
            }
        }

        return instance;
    }

    private static LocalDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context
                , LocalDatabase.class
                , "made-catalogue-movie.db").allowMainThreadQueries().build();
    }

}

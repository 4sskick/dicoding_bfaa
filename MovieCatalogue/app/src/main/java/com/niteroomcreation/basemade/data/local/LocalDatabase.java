package com.niteroomcreation.basemade.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.niteroomcreation.basemade.data.local.dao.MovieDao;
import com.niteroomcreation.basemade.data.local.dao.TvDao;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.local.entity.TvEntity;

/**
 * Created by Septian Adi Wijaya on 07/01/20
 */

@Database(entities = {MovieEntity.class, TvEntity.class}, version = 5, exportSchema = false)
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
                , "made-catalogue-movie.db").allowMainThreadQueries()/*.addMigrations(migration_1_2)*/.fallbackToDestructiveMigration().build();
    }

    /**
     * migration DB section
     * see: https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
     */
    //1 to 2
    static final Migration migration_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table MovieEntity add column isFavorite INTEGER default 0 not" +
                    " null");

        }
    };

}

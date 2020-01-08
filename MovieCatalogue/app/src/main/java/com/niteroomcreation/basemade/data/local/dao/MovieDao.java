package com.niteroomcreation.basemade.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.niteroomcreation.basemade.data.local.entity.MovieEntity;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 07/01/20
 */

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertMovies(List<MovieEntity> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertMovie(MovieEntity movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateMovie(MovieEntity movie);

    @Query("select * from `MovieEntity` where page = :page")
    List<MovieEntity> getMoviesByPage(Long page);

    @Query("select * from `MovieEntity` where id = :id")
    MovieEntity getMovieById(Long id);
}

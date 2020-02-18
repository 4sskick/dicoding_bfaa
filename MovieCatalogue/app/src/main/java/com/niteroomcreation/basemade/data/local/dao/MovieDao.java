package com.niteroomcreation.basemade.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.niteroomcreation.basemade.data.local.entity.MovieEntity;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 07/01/20
 */

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertMovies(List<MovieEntity> movies);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMovie(MovieEntity movie);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    int updateMovie(MovieEntity movie);

    @Query("select * from `MovieEntity` where page = :page")
    MovieEntity getMoviesByPage(Long page);

    @Query("select * from `MovieEntity` where _id = :id")
    MovieEntity getMovieById(Long id);

    @Query("select * from `MovieEntity` where _id = :id and languageType = :lang")
    MovieEntity getMovieByIdLang(Long id, String lang);

    @Query("select * from `MovieEntity` where languageType = :lang")
    List<MovieEntity> getMoviesByLang(String lang);

    @Query("select * from `MovieEntity` where isFavorite = 1")
    List<MovieEntity> getFavMovies();

    @Query("select * from `MovieEntity` where title like '%' || :query || '%'")
    List<MovieEntity> getMoviesOnQuery(String query);

    //content values section queries
    @Query("select * from `MovieEntity` where isFavorite = 1")
    Cursor cursorSelectAll();

    @Query("select * from `MovieEntity` where _id = :id")
    Cursor cursorSelectById(long id);
}

package com.niteroomcreation.basemade.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.niteroomcreation.basemade.data.local.entity.MovieEntity;
import com.niteroomcreation.basemade.data.models.BaseResponse;

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
    MovieEntity getMoviesByPage(Long page);

    @Query("select * from `MovieEntity` where id = :id")
    MovieEntity getMovieById(Long id);

    @Query("select * from `MovieEntity` where id = :id and languageType = :lang")
    MovieEntity getMovieByIdLang(Long id, String lang);

    @Query("select * from `MovieEntity` where languageType = :lang")
    List<MovieEntity> getMoviesByLang(String lang);

    @Query("select * from `MovieEntity` where isFavorite = 1")
    List<MovieEntity> getFavMovies();

    @Query("select * from `MovieEntity` where title like '%' || :query || '%'")
    List<MovieEntity> getMoviesOnQuery(String query);

}

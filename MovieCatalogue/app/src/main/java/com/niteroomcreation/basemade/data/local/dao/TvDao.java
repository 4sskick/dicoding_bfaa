package com.niteroomcreation.basemade.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.niteroomcreation.basemade.data.local.entity.TvEntity;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 07/01/20
 */

@Dao
public interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertTvs(List<TvEntity> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTv(TvEntity movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateTv(TvEntity movie);

    @Query("select * from `TvEntity` where page = :page")
    TvEntity getTvsByPage(Long page);

    @Query("select * from `TvEntity` where id = :id")
    TvEntity getTvById(Long id);

    @Query("select * from `TvEntity` where id = :id and languageType = :lang")
    TvEntity getTvByIdLang(Long id, String lang);

    @Query("select * from `TvEntity` where languageType = :lang")
    List<TvEntity> getTvsByLang(String lang);

    @Query("select * from `TvEntity` where isFavorite = 1")
    List<TvEntity> getFavsTv();

    @Query("select * from `TvEntity` where name like '%'|| :query || '%'")
    List<TvEntity> gettvShowsOnQuery(String query);
}

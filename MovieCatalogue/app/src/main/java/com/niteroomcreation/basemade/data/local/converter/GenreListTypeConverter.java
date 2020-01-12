package com.niteroomcreation.basemade.data.local.converter;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.niteroomcreation.basemade.models.details.Genre;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Septian Adi Wijaya on 12/01/2020.
 * please be sure to add credential if you use people's code
 */
public class GenreListTypeConverter {

    @TypeConverter
    public List<Genre> fromString(String value) {
        Type listType = new TypeToken<List<Genre>>() {
        }.getType();
        List<Genre> genres = new Gson().fromJson(value, listType);
        return genres;
    }

    @TypeConverter
    public String fromList(List<Genre> genres) {
        return new Gson().toJson(genres);
    }
}

package com.niteroomcreation.basemade.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.R;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 26/01/2020.
 * please be sure to add credential if you use people's code
 */
public class FavsObjectItem implements Parcelable {

    public FavsObjectItem() {
    }

    @Expose
    private Long id;

    /**
     * type 1: MovieEntity
     * type 2: TvEntity
     */
    @Expose
    private int typeObject;

    @Expose
    private String overview;

    @Expose
    private String originalTitle;

    @Expose
    private String title;

    @Expose
    private String posterPath;

    @Expose
    private String backdropPath;

    @Expose
    private boolean isFavorite;

    @Expose
    private List<String> genres;

    protected FavsObjectItem(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        typeObject = in.readInt();
        overview = in.readString();
        originalTitle = in.readString();
        title = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        isFavorite = in.readByte() != 0;
        genres = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeInt(typeObject);
        dest.writeString(overview);
        dest.writeString(originalTitle);
        dest.writeString(title);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeStringList(genres);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FavsObjectItem> CREATOR = new Creator<FavsObjectItem>() {
        @Override
        public FavsObjectItem createFromParcel(Parcel in) {
            return new FavsObjectItem(in);
        }

        @Override
        public FavsObjectItem[] newArray(int size) {
            return new FavsObjectItem[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTypeObject() {
        return typeObject;
    }

    public void setTypeObject(int typeObject) {
        this.typeObject = typeObject;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getFullPosterPath(boolean isHalf) {
        return String.format(isHalf ? "%s%sw500%s" : /*"%s%sw780%s"*/"%s%sw500%s"
                , BuildConfig.BASE_URL_IMG
                , BuildConfig.BASE_PATH_IMG
                , posterPath);
    }

    public String getTypeObjectStr(Context mContext, int typeObject) {
        switch (typeObject) {
            case 1:
                return mContext.getResources().getString(R.string.nav_bot_movies);

            case 2:
                return mContext.getResources().getString(R.string.nav_bot_saved_fav);
        }

        return mContext.getResources().getString(R.string.nav_bot_saved_fav);
    }

    @Override
    public String toString() {
        return "FavsObjectItem{" +
                "id=" + id +
                ", typeObject=" + typeObject +
                ", overview='" + overview + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", isFavorite=" + isFavorite +
                ", genres=" + genres +
                '}';
    }
}

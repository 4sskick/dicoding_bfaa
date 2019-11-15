package com.niteroomcreation.basemade.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Septian Adi Wijaya on 15/11/19
 */
public class TvShowModel implements Parcelable {

    private String name;
    private String desc;
    private String posterPath;
    private int year;
    private int poster;
    private int ratePercentage;

    private FeaturedMovieModel featuredMovieModel;

    public TvShowModel(String name
            , String desc
            , int poster
            , int year
            , int ratePercentage
            , FeaturedMovieModel featuredMovieModel) {
        this.name = name;
        this.desc = desc;
        this.poster = poster;
        this.year = year;
        this.ratePercentage = ratePercentage;
        this.featuredMovieModel = featuredMovieModel;
    }

    protected TvShowModel(Parcel in) {
        name = in.readString();
        desc = in.readString();
        posterPath = in.readString();
        year = in.readInt();
        ratePercentage = in.readInt();
        featuredMovieModel = in.readParcelable(FeaturedMovieModel.class.getClassLoader());
    }

    public static final Creator<TvShowModel> CREATOR = new Creator<TvShowModel>() {
        @Override
        public TvShowModel createFromParcel(Parcel in) {
            return new TvShowModel(in);
        }

        @Override
        public TvShowModel[] newArray(int size) {
            return new TvShowModel[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(posterPath);
        dest.writeInt(year);
        dest.writeInt(ratePercentage);
        dest.writeParcelable(featuredMovieModel, flags);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public int getRatePercentage() {
        return ratePercentage;
    }

    public void setRatePercentage(int ratePercentage) {
        this.ratePercentage = ratePercentage;
    }

    public FeaturedMovieModel getFeaturedMovieModel() {
        return featuredMovieModel;
    }

    public void setFeaturedMovieModel(FeaturedMovieModel featuredMovieModel) {
        this.featuredMovieModel = featuredMovieModel;
    }
}

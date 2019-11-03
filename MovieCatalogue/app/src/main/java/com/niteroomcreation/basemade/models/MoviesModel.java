package com.niteroomcreation.basemade.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class MoviesModel implements Parcelable {

    private static final String EXTRA_FEATURE = "feature_movie";

    private String name;
    private int poster;
    private String desc;
    private int ratePercentage;
    private FeaturedMovieModel featuredMovieModel;

    public MoviesModel(String name
            , String desc
            , int poster
            , int ratePercentage
            , FeaturedMovieModel featuredMovieModel) {
        this.name = name;
        this.desc = desc;
        this.poster = poster;
        this.ratePercentage = ratePercentage;
        this.featuredMovieModel = featuredMovieModel;
    }

    protected MoviesModel(Parcel in) {
        name = in.readString();
        poster = in.readInt();
        desc = in.readString();
        ratePercentage = in.readInt();
        featuredMovieModel = in.readParcelable(FeaturedMovieModel.class.getClassLoader());
    }

    public static final Creator<MoviesModel> CREATOR = new Creator<MoviesModel>() {
        @Override
        public MoviesModel createFromParcel(Parcel in) {
            return new MoviesModel(in);
        }

        @Override
        public MoviesModel[] newArray(int size) {
            return new MoviesModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeInt(poster);
        dest.writeInt(ratePercentage);

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_FEATURE, featuredMovieModel);

        dest.writeBundle(bundle);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

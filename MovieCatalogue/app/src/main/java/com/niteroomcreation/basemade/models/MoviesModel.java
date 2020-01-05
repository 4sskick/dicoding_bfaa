package com.niteroomcreation.basemade.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.niteroomcreation.basemade.BuildConfig;

public class MoviesModel implements Parcelable {

    /**
     * https://stackoverflow.com/a/33182408
     * think about mechanism to passing the bitmap
     * into parcelable:
     * - stored into local storage & saved the URI of absolute path
     * - after in another act querying the URI to load the bitmap
     */

    private String name;
    private String desc;
    private String posterPath;
    private int year;
    private int poster;
    private int ratePercentage;

    private FeaturedMovieModel featuredMovieModel;

    public MoviesModel(String name
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

    /**
     * order of read & write does matter!
     *
     * @param in
     */
    protected MoviesModel(Parcel in) {
        name = in.readString();
        desc = in.readString();
        posterPath = in.readString();
        year = in.readInt();
        ratePercentage = in.readInt();
        featuredMovieModel = in.readParcelable(FeaturedMovieModel.class.getClassLoader());
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

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getFullPosterPath() {
        return String.format("%s%sw780%s"
                , BuildConfig.BASE_URL_IMG
                , BuildConfig.BASE_PATH_IMG
                , posterPath);
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
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

    public String getPercentage() {
        return getRatePercentage() + "%";
    }

    public void setRatePercentage(int ratePercentage) {
        this.ratePercentage = ratePercentage;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public FeaturedMovieModel getFeaturedMovieModel() {
        return featuredMovieModel;
    }

    public void setFeaturedMovieModel(FeaturedMovieModel featuredMovieModel) {
        this.featuredMovieModel = featuredMovieModel;
    }

    @Override
    public String toString() {
        return "MoviesModel{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", year=" + year +
                ", poster=" + poster +
                ", ratePercentage=" + ratePercentage +
                ", featuredMovieModel=" + featuredMovieModel +
                '}';
    }

}

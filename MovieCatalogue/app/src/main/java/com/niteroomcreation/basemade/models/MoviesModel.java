package com.niteroomcreation.basemade.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class MoviesModel implements Parcelable {

    /**
     * https://stackoverflow.com/a/33182408
     * think about mechanism to passing the bitmap
     * into parcelable:
     * - stored into local storage & saved the URI of absolute path
     * - after in another act querying the URI to load the bitmap
     */

    private static final String EXTRA_FEATURE = "feature_movie";

    private String name;
    private String desc;
    //    private int poster;
    private Bitmap posterBitmap;
    private int ratePercentage;
    private FeaturedMovieModel featuredMovieModel;

    public MoviesModel(String name
            , String desc
//            , int poster
            , Bitmap posterBitmap
            , int ratePercentage
            , FeaturedMovieModel featuredMovieModel) {
        this.name = name;
        this.desc = desc;
//        this.poster = poster;
        this.posterBitmap = posterBitmap;
        this.ratePercentage = ratePercentage;
        this.featuredMovieModel = featuredMovieModel;
    }

    protected MoviesModel(Parcel in) {
        name = in.readString();
//        poster = in.readInt();
        posterBitmap = ((Bitmap) in.readParcelable(Bitmap.class.getClassLoader()));
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
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
//        dest.writeInt(poster);
        dest.writeParcelable(posterBitmap, PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeInt(ratePercentage);

//        Bundle bundle = new Bundle();
//        bundle.putParcelable(EXTRA_FEATURE, featuredMovieModel);
//
//        dest.writeBundle(bundle);
        dest.writeParcelable(featuredMovieModel, PARCELABLE_WRITE_RETURN_VALUE);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public int getPoster() {
//        return poster;
//    }
//
//    public void setPoster(int poster) {
//        this.poster = poster;
//    }


    public Bitmap getPosterBitmap() {
        return posterBitmap;
    }

    public void setPosterBitmap(Bitmap posterBitmap) {
        this.posterBitmap = posterBitmap;
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

    @Override
    public String toString() {
        return "MoviesModel{" +
                "name='" + name + '\'' +
                ", poster=" + (posterBitmap != null ? "ga null" : "null looo!") +
                ", desc='" + desc + '\'' +
                ", ratePercentage=" + ratePercentage +
                ", featuredMovieModel=" + featuredMovieModel +
                '}';
    }
}

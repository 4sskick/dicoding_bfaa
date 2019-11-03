package com.niteroomcreation.basemade.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FeaturedMovieModel implements Parcelable {

    private String name;
    private String role;

    protected FeaturedMovieModel(Parcel in) {
        name = in.readString();
        role = in.readString();
    }

    public static final Creator<FeaturedMovieModel> CREATOR = new Creator<FeaturedMovieModel>() {
        @Override
        public FeaturedMovieModel createFromParcel(Parcel in) {
            return new FeaturedMovieModel(in);
        }

        @Override
        public FeaturedMovieModel[] newArray(int size) {
            return new FeaturedMovieModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

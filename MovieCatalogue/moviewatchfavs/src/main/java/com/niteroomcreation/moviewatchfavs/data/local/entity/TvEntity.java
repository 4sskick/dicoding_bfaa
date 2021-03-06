package com.niteroomcreation.moviewatchfavs.data.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.niteroomcreation.moviewatchfavs.BuildConfig;

import java.util.List;

@Entity(primaryKeys = (BaseColumns._ID))
public class TvEntity implements Parcelable {

    private static final String C_NAME = "name";

    public TvEntity() {
    }

    protected TvEntity(Parcel in) {
        this.firstAirDate = in.readString();
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.posterPath = in.readString();
        this.originCountry = in.createStringArrayList();
        this.backdropPath = in.readString();
        this.originalName = in.readString();
        this.popularity = in.readDouble();
        this.voteAverage = in.readDouble();
        this.name = in.readString();
        this.id = in.readLong();
        this.voteCount = in.readInt();
        this.languageType = in.readString();
        this.isFavorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstAirDate);
        dest.writeString(overview);
        dest.writeString(originalLanguage);
        dest.writeString(posterPath);
        dest.writeStringList(originCountry);
        dest.writeString(backdropPath);
        dest.writeString(originalName);
        dest.writeDouble(popularity);
        dest.writeDouble(voteAverage);
        dest.writeString(name);
        dest.writeLong(id);
        dest.writeInt(voteCount);
        dest.writeString(this.languageType);
        dest.writeByte((byte) (this.isFavorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TvEntity> CREATOR = new Creator<TvEntity>() {
        @Override
        public TvEntity createFromParcel(Parcel in) {
            return new TvEntity(in);
        }

        @Override
        public TvEntity[] newArray(int size) {
            return new TvEntity[size];
        }
    };

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("genre_ids")
    @Ignore
    private List<Integer> genreIds;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("origin_country")
    @Ignore
    private List<String> originCountry;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("vote_average")
    private double voteAverage;

    @ColumnInfo(name = C_NAME)
    @SerializedName("name")
    private String name;

    @ColumnInfo(index = true, name = BaseColumns._ID)
    @SerializedName("id")
    private long id;

    @SerializedName("vote_count")
    private int voteCount;

    @Expose
    private String languageType;

    @Expose
    private boolean isFavorite;

    @Expose
    private Long page;

    @SerializedName("number_of_episodes")
    @Expose
    private int numberOfEpisodes;

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath != null ? posterPath : String.format("/%s.jpg", name.replaceAll("\\s+",
                "_"));
    }

    public String getFullPosterPath(boolean isHalf) {
        return String.format(isHalf ? "%s%sw500%s" : /*"%s%sw780%s"*/"%s%sw500%s"
                , BuildConfig.BASE_URL_IMG
                , BuildConfig.BASE_PATH_IMG
                , posterPath);
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public static TvEntity fromContentValues(@Nullable ContentValues cv) {
        final TvEntity e = new TvEntity();

        if (cv != null && cv.containsKey(BaseColumns._ID)) {
            e.id = cv.getAsLong(BaseColumns._ID);
        }

        if (cv != null && cv.containsKey(C_NAME)) {
            e.name = cv.getAsString(C_NAME);
        }

        return e;
    }

    @Override
    public String toString() {
        return "TvEntity{" +
                "firstAirDate='" + firstAirDate + '\'' +
                ", overview='" + overview + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", genreIds=" + genreIds +
                ", posterPath='" + posterPath + '\'' +
                ", originCountry=" + originCountry +
                ", backdropPath='" + backdropPath + '\'' +
                ", originalName='" + originalName + '\'' +
                ", popularity=" + popularity +
                ", voteAverage=" + voteAverage +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", voteCount=" + voteCount +
                ", languageType='" + languageType + '\'' +
                ", isFavorite=" + isFavorite +
                ", page=" + page +
                ", numberOfEpisodes=" + numberOfEpisodes +
                '}';
    }
}
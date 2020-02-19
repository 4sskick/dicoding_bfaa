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

@Entity(primaryKeys = (BaseColumns._ID), tableName = MovieEntity.T_NAME)
public class MovieEntity implements Parcelable {

    public static final String C_TITLE = "title";
    public static final String C_POSTER_PATH = "posterPath";

    public static final String T_NAME = "MovieEntity";

    public MovieEntity() {

    }

    public MovieEntity(long id, String title, String posterPath, String overview) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
    }

    @ColumnInfo(index = true, name = BaseColumns._ID)
    @SerializedName("id")
    @Expose
    private Long id;

    @Expose
    private Long page;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("original_language")
    @Ignore
    @Expose
    private String originalLanguage;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("video")
    @Expose
    private boolean video;

    @ColumnInfo(name = C_TITLE)
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("genre_ids")
    @Ignore
    private List<Integer> genreIds;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("popularity")
    @Expose
    private double popularity;

    @SerializedName("vote_average")
    @Expose
    private double voteAverage;

    @SerializedName("adult")
    @Expose
    private boolean adult;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    @Expose
    private String languageType;

    @Expose
    private boolean isFavorite;

    @SerializedName("number_of_episodes")
    @Expose
    private int numberOfEpisodes;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
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

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isVideo() {
        return video;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
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
        return posterPath != null ? posterPath : String.format("/%s.jpg", title.replaceAll("\\s+"
                , "_"));
    }

    public String getFullPosterPath(boolean isHalf) {
        return String.format(isHalf ? "%s%sw500%s" : /*"%s%sw780%s"*/"%s%sw500%s"
                , BuildConfig.BASE_URL_IMG
                , BuildConfig.BASE_PATH_IMG
                , posterPath);
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isAdult() {
        return adult;
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

    /**
     * an extra from detail data requested in by ID
     */
    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    protected MovieEntity(Parcel in) {
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.video = in.readByte() != 0;
        this.title = in.readString();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.releaseDate = in.readString();
        this.popularity = in.readDouble();
        this.voteAverage = in.readDouble();
        this.id = in.readLong();
        this.adult = in.readByte() != 0;
        this.voteCount = in.readInt();
        this.languageType = in.readString();
        this.isFavorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeByte((byte) (this.video ? 1 : 0));
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.popularity);
        dest.writeDouble(this.voteAverage);
        dest.writeLong(this.id);
        dest.writeByte((byte) (this.adult ? 1 : 0));
        dest.writeInt(this.voteCount);
        dest.writeString(this.languageType);
        dest.writeByte((byte) (this.isFavorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel in) {
            return new MovieEntity(in);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };


    public static MovieEntity fromContentValues(@Nullable ContentValues cv) {

        final MovieEntity m = new MovieEntity();
        if (cv != null && cv.containsKey(BaseColumns._ID)) {
            m.id = cv.getAsLong(BaseColumns._ID);
        }

        if (cv != null && cv.containsKey(C_TITLE)) {
            m.title = cv.getAsString(C_TITLE);
        }

        return m;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id=" + id +
                ", page=" + page +
                ", overview='" + overview + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", video=" + video +
                ", title='" + title + '\'' +
                ", genreIds=" + genreIds +
                ", posterPath='" + posterPath + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", popularity=" + popularity +
                ", voteAverage=" + voteAverage +
                ", adult=" + adult +
                ", voteCount=" + voteCount +
                ", languageType='" + languageType + '\'' +
                ", isFavorite=" + isFavorite +
                ", numberOfEpisodes=" + numberOfEpisodes +
                '}';
    }
}
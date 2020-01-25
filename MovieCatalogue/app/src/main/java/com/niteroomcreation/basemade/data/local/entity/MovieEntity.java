package com.niteroomcreation.basemade.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.niteroomcreation.basemade.BuildConfig;
import com.niteroomcreation.basemade.data.local.converter.GenreListTypeConverter;
import com.niteroomcreation.basemade.models.details.Genre;
import com.niteroomcreation.basemade.models.details.tvshow.LastEpisodeToAir;

import java.util.List;

@Entity(primaryKeys = ("id"))
public class MovieEntity implements Parcelable {

    public MovieEntity() {

    }

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

    /**
     * an extra from detail data requested in by ID
     */
    @SerializedName("number_of_episodes")
    @Expose
    private int numberOfEpisodes;

    //need a type converters
    @SerializedName("genres")
    @Expose
    @TypeConverters(GenreListTypeConverter.class)
    private List<Genre> genres;

    @SerializedName("number_of_seasons")
    @Expose
    private int numberOfSeasons;

    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;

    //need a type converters
//    @SerializedName("seasons")
//    @Expose
//    private List<Season> seasons;

    //need a type converters
//    @SerializedName("created_by")
//    @Expose
//    private List<CreatedByItem> createdBy;

//    @SerializedName("last_episode_to_air")
//    @Expose
//    private LastEpisodeToAir lastEpisodeToAir;

    @SerializedName("last_air_date")
    @Expose
    private String lastAirDate;

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("status")
    @Expose
    private String status;

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
        return posterPath != null ? posterPath : String.format("/%s.jpg", title.replaceAll("\\s+", "_"));
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

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                ", genres=" + genres +
                ", numberOfSeasons=" + numberOfSeasons +
                ", firstAirDate='" + firstAirDate + '\'' +
                ", lastAirDate='" + lastAirDate + '\'' +
                ", homepage='" + homepage + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
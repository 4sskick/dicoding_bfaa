package com.niteroomcreation.basemade.models.details.movie;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.niteroomcreation.basemade.models.details.Genre;
import com.niteroomcreation.basemade.models.details.ProductionCompany;


public class MoviesDetail {

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("imdb_id")
    private String imdbId;

    @SerializedName("video")
    private boolean video;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("revenue")
    private int revenue;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("production_countries")
    private List<ProductionCountry> productionCountries;

    @SerializedName("id")
    private long id;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("budget")
    private int budget;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("runtime")
    private int runtime;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("spoken_languages")
    private List<SpokenLanguage> spokenLanguages;

    @SerializedName("production_companies")
    private List<ProductionCompany> productionCompanies;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("belongs_to_collection")
    private BelongsToCollection belongsToCollection;

    @SerializedName("tagline")
    private String tagline;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("status")
    private String status;

    private boolean isFavorite;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getImdbId() {
        return imdbId;
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

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
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

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getBudget() {
        return budget;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setBelongsToCollection(BelongsToCollection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public BelongsToCollection getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTagline() {
        return tagline;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "MoviesDetail{" +
                "originalLanguage='" + originalLanguage + '\'' +
                ", imdbId='" + imdbId + '\'' +
                ", video=" + video +
                ", title='" + title + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", revenue=" + revenue +
                ", genres=" + genres +
                ", popularity=" + popularity +
                ", productionCountries=" + productionCountries +
                ", id=" + id +
                ", voteCount=" + voteCount +
                ", budget=" + budget +
                ", overview='" + overview + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", runtime=" + runtime +
                ", posterPath='" + posterPath + '\'' +
                ", spokenLanguages=" + spokenLanguages +
                ", productionCompanies=" + productionCompanies +
                ", releaseDate='" + releaseDate + '\'' +
                ", voteAverage=" + voteAverage +
                ", belongsToCollection=" + belongsToCollection +
                ", tagline='" + tagline + '\'' +
                ", adult=" + adult +
                ", homepage='" + homepage + '\'' +
                ", status='" + status + '\'' +
                ", isFavorite=" + isFavorite +
                '}';
    }

}
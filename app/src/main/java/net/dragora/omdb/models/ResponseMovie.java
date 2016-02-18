package net.dragora.omdb.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.google.gson.annotations.SerializedName;


@JsonObject
public class ResponseMovie implements Parcelable{

    private static final String FIELD_RUNTIME = "Runtime";
    private static final String FIELD_YEAR = "Year";
    private static final String FIELD_RATED = "Rated";
    private static final String FIELD_POSTER = "Poster";
    private static final String FIELD_PLOT = "Plot";
    private static final String FIELD_ACTORS = "Actors";
    private static final String FIELD_RELEASED = "Released";
    private static final String FIELD_IMDB_RATING = "imdbRating";
    private static final String FIELD_IMDB_VOTES = "imdbVotes";
    private static final String FIELD_METASCORE = "Metascore";
    private static final String FIELD_IMDB_ID = "imdbID";
    private static final String FIELD_GENRE = "Genre";
    private static final String FIELD_TYPE = "Type";
    private static final String FIELD_WRITER = "Writer";
    private static final String FIELD_LANGUAGE = "Language";
    private static final String FIELD_ERROR = "Error";
    private static final String FIELD_COUNTRY = "Country";
    private static final String FIELD_TITLE = "Title";
    private static final String FIELD_RESPONSE = "Response";
    private static final String FIELD_AWARDS = "Awards";
    private static final String FIELD_DIRECTOR = "Director";


    @SerializedName(FIELD_RUNTIME)
    @JsonField(name = FIELD_RUNTIME)
    private String mRuntime;
    @SerializedName(FIELD_YEAR)
    @JsonField(name = FIELD_YEAR)
    private int mYear;
    @SerializedName(FIELD_RATED)
    @JsonField(name = FIELD_RATED)
    private String mRated;
    @SerializedName(FIELD_POSTER)
    @JsonField(name = FIELD_POSTER)
    private String mPoster;
    @SerializedName(FIELD_PLOT)
    @JsonField(name = FIELD_PLOT)
    private String mPlot;
    @SerializedName(FIELD_ACTORS)
    @JsonField(name = FIELD_ACTORS)
    private String mActor;
    @SerializedName(FIELD_RELEASED)
    @JsonField(name = FIELD_RELEASED)
    private String mReleased;
    @SerializedName(FIELD_IMDB_RATING)
    @JsonField(name = FIELD_IMDB_RATING)
    private double mImdbRating;
    @SerializedName(FIELD_IMDB_VOTES)
    @JsonField(name = FIELD_IMDB_VOTES)
    private String mImdbVote;
    @SerializedName(FIELD_METASCORE)
    @JsonField(name = FIELD_METASCORE)
    private String mMetascore;
    @SerializedName(FIELD_IMDB_ID)
    @JsonField(name = FIELD_IMDB_ID)
    private String mImdbID;
    @SerializedName(FIELD_GENRE)
    @JsonField(name = FIELD_GENRE)
    private String mGenre;
    @SerializedName(FIELD_TYPE)
    @JsonField(name = FIELD_TYPE)
    private String mType;
    @SerializedName(FIELD_WRITER)
    @JsonField(name = FIELD_WRITER)
    private String mWriter;
    @SerializedName(FIELD_LANGUAGE)
    @JsonField(name = FIELD_LANGUAGE)
    private String mLanguage;
    @SerializedName(FIELD_ERROR)
    @JsonField(name = FIELD_ERROR)
    private String mError;
    @SerializedName(FIELD_COUNTRY)
    @JsonField(name = FIELD_COUNTRY)
    private String mCountry;
    @SerializedName(FIELD_TITLE)
    @JsonField(name = FIELD_TITLE)
    private String mTitle;
    @SerializedName(FIELD_RESPONSE)
    @JsonField(name = FIELD_RESPONSE)
    private String mResponse;
    @SerializedName(FIELD_AWARDS)
    @JsonField(name = FIELD_AWARDS)
    private String mAward;
    @SerializedName(FIELD_DIRECTOR)
    @JsonField(name = FIELD_DIRECTOR)
    private String mDirector;


    public ResponseMovie(){

    }

    public void setRuntime(String runtime) {
        mRuntime = runtime;
    }

    public String getRuntime() {
        return mRuntime;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public int getYear() {
        return mYear;
    }

    public void setRated(String rated) {
        mRated = rated;
    }

    public String getRated() {
        return mRated;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPlot(String plot) {
        mPlot = plot;
    }

    public String getPlot() {
        return mPlot;
    }

    public void setActor(String actor) {
        mActor = actor;
    }

    public String getActor() {
        return mActor;
    }

    public void setReleased(String released) {
        mReleased = released;
    }

    public String getReleased() {
        return mReleased;
    }

    public void setImdbRating(double imdbRating) {
        mImdbRating = imdbRating;
    }

    public double getImdbRating() {
        return mImdbRating;
    }

    public void setImdbVote(String imdbVote) {
        mImdbVote = imdbVote;
    }

    public String getImdbVote() {
        return mImdbVote;
    }

    public void setMetascore(String metascore) {
        mMetascore = metascore;
    }

    public String getMetascore() {
        return mMetascore;
    }

    public void setImdbID(String imdbID) {
        mImdbID = imdbID;
    }

    public String getImdbID() {
        return mImdbID;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

    public void setWriter(String writer) {
        mWriter = writer;
    }

    public String getWriter() {
        return mWriter;
    }

    public void setLanguage(String language) {
        mLanguage = language;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setError(String error) {
        mError = error;
    }

    public String getError() {
        return mError;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setResponse(String response) {
        mResponse = response;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setAward(String award) {
        mAward = award;
    }

    public String getAward() {
        return mAward;
    }

    public void setDirector(String director) {
        mDirector = director;
    }

    public String getDirector() {
        return mDirector;
    }

    public ResponseMovie(Parcel in) {
        mRuntime = in.readString();
        mYear = in.readInt();
        mRated = in.readString();
        mPoster = in.readString();
        mPlot = in.readString();
        mActor = in.readString();
        mReleased = in.readString();
        mImdbRating = in.readDouble();
        mImdbVote = in.readString();
        mMetascore = in.readString();
        mImdbID = in.readString();
        mGenre = in.readString();
        mType = in.readString();
        mWriter = in.readString();
        mLanguage = in.readString();
        mError = in.readString();
        mCountry = in.readString();
        mTitle = in.readString();
        mResponse = in.readString();
        mAward = in.readString();
        mDirector = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResponseMovie> CREATOR = new Creator<ResponseMovie>() {
        public ResponseMovie createFromParcel(Parcel in) {
            return new ResponseMovie(in);
        }

        public ResponseMovie[] newArray(int size) {
        return new ResponseMovie[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRuntime);
        dest.writeInt(mYear);
        dest.writeString(mRated);
        dest.writeString(mPoster);
        dest.writeString(mPlot);
        dest.writeString(mActor);
        dest.writeString(mReleased);
        dest.writeDouble(mImdbRating);
        dest.writeString(mImdbVote);
        dest.writeString(mMetascore);
        dest.writeString(mImdbID);
        dest.writeString(mGenre);
        dest.writeString(mType);
        dest.writeString(mWriter);
        dest.writeString(mLanguage);
        dest.writeString(mError);
        dest.writeString(mCountry);
        dest.writeString(mTitle);
        dest.writeString(mResponse);
        dest.writeString(mAward);
        dest.writeString(mDirector);
    }

    @Override
    public String toString(){
        return "runtime = " + mRuntime + ", year = " + mYear + ", rated = " + mRated + ", poster = " + mPoster + ", plot = " + mPlot + ", actor = " + mActor + ", released = " + mReleased + ", imdbRating = " + mImdbRating + ", imdbVote = " + mImdbVote + ", metascore = " + mMetascore + ", imdbID = " + mImdbID + ", genre = " + mGenre + ", type = " + mType + ", writer = " + mWriter + ", language = " + mLanguage + ", error = " + mError + ", country = " + mCountry + ", title = " + mTitle + ", response = " + mResponse + ", award = " + mAward + ", director = " + mDirector;
    }

}
package id.radikz.movielistwithsql.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FavMovie implements Parcelable {
    private int id;
    private String title;
    private float rating;
    private String releaseDate;
    private String posterPath;

    public FavMovie(int id, String title, float rating, String releaseDate, String posterPath) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
    }

    public FavMovie(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeFloat(this.rating);
    }

    protected FavMovie(Parcel in) {
        this.title = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.rating = in.readFloat();
    }

    public static final Parcelable.Creator<FavMovie> CREATOR = new Parcelable.Creator<FavMovie>() {
        @Override
        public FavMovie createFromParcel(Parcel source) {
            return new FavMovie(source);
        }

        @Override
        public FavMovie[] newArray(int size) {
            return new FavMovie[size];
        }
    };
}

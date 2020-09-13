package id.radikz.movielistwithsql.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.radikz.movielistwithsql.models.FavMovie;
import id.radikz.movielistwithsql.R;

public class FavMoviesAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<FavMovie> movies;


    public FavMoviesAdapter(Context context, ArrayList<FavMovie> movies) {
        this.context = context;
//        movies = new ArrayList<>();
        this.movies = movies;
    }

    public void setMovies(ArrayList<FavMovie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_fav_movie, viewGroup, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        FavMovie movie = (FavMovie) getItem(i);
        viewHolder.bind(movie);
        return view;
    }

    private class ViewHolder {
        private TextView textMovieName;
        private TextView textMovieDate;
        private TextView textMovieRating;
        private ImageView imageMovie;
        ViewHolder(View view) {
            textMovieName = view.findViewById(R.id.item_movie_title);
            textMovieDate = view.findViewById(R.id.item_movie_release_date);
            textMovieRating = view.findViewById(R.id.item_movie_rating);
            imageMovie = view.findViewById(R.id.item_movie_poster);
        }
        void bind(FavMovie movie) {
            textMovieName.setText(movie.getTitle());
            textMovieDate.setText(movie.getReleaseDate());
            textMovieRating.setText(String.valueOf(movie.getRating()) );
            Glide.with(context).load("http://image.tmdb.org/t/p/w154" + movie.getPosterPath()).into(imageMovie);
        }
    }
}

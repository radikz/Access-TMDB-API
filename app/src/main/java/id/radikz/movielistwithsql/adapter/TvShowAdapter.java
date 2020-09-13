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

import id.radikz.movielistwithsql.R;
import id.radikz.movielistwithsql.models.TvShow;

public class TvShowAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<TvShow> tvShow;


    public TvShowAdapter(Context context, ArrayList<TvShow> tvShow) {
        this.context = context;
//        tvShow = new ArrayList<>();
        this.tvShow = tvShow;
    }

    public void setTvShow(ArrayList<TvShow> tvShow) {
        this.tvShow = tvShow;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return tvShow.size();
    }

    @Override
    public Object getItem(int i) {
        return tvShow.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        TvShow tvShow = (TvShow) getItem(i);
        viewHolder.bind(tvShow);
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
        void bind(TvShow tvShow) {
            textMovieName.setText(tvShow.getTitle());
            textMovieDate.setText(tvShow.getReleaseDate());
            textMovieRating.setText(String.valueOf(tvShow.getRating()) );
            Glide.with(context).load("http://image.tmdb.org/t/p/w154" + tvShow.getPosterPath()).into(imageMovie);
        }
    }
}

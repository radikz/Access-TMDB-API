package id.radikz.movielistwithsql.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.radikz.movielistwithsql.DetailMovieActivity;
import id.radikz.movielistwithsql.models.Movie;
import id.radikz.movielistwithsql.R;
import id.radikz.movielistwithsql.adapter.MoviesAdapter;
import id.radikz.movielistwithsql.loader.MyAsyncTaskLoader;


public class MovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {
    ListView listView;
    private ArrayList<Movie> tvList = new ArrayList<>();
    MoviesAdapter adapter;
    //    private RecyclerView mrecyclerView;
    ProgressBar progressBar;
    TextView mode;
    Button fav;
    private Parcelable mListState = null;
//    String stringMode;

    private ArrayList<Movie> movies = new ArrayList<>();

    static final String EXTRAS_LANG = "EXTRAS_LANG";
    private static final String STATE_RESULT = "state_result";


    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(STATE_RESULT, movies);
        //    mListState = listView.onSaveInstanceState();
        //    savedInstanceState.putParcelable(STATE_RESULT, mListState);
        Log.d("MovieFrag", "save");
        Log.d("MovieFrag", STATE_RESULT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);


        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = new ProgressBar(getActivity());
        progressBar = view.findViewById(R.id.item_movie_progress);

        adapter = new MoviesAdapter(getActivity(), movies);
        adapter.notifyDataSetChanged();

        mode = view.findViewById(R.id.mode_language);

        mode.setText(getResources().getString(R.string.mode_language));

        fav = view.findViewById(R.id.button_movie);

        listView = view.findViewById(R.id.lv_list);

        listView.setAdapter(adapter);



        Bundle bundle = new Bundle();
        progressBar.setVisibility(View.VISIBLE);
        bundle.putString(EXTRAS_LANG, getResources().getString(R.string.api_lang));
        Log.d("MovieFrag", "viewCreated");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Movie movie = new Movie();
                Toast.makeText(getActivity(), movies.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                Intent moveWithObjectIntent = new Intent(getActivity(), DetailMovieActivity.class);
                moveWithObjectIntent.putExtra(DetailMovieActivity.NAME_MOVIE, movies.get(i));
                startActivity(moveWithObjectIntent);
            }
        });



        if (savedInstanceState != null) {
//            initViews();
            movies = savedInstanceState.getParcelableArrayList(STATE_RESULT);
            Log.d("MovieFrag", "nggak kosong");
            Log.d("MovieFrag", STATE_RESULT);
//            listView.onRestoreInstanceState(mListState);
            adapter = new MoviesAdapter(getActivity(), movies);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
        else{
            getLoaderManager().restartLoader(0, bundle, this);
        }

    }

    public MovieFragment(){

    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new MyAsyncTaskLoader(getActivity(), getResources().getString(R.string.api_lang));

    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        adapter.setMovies(movies);
        this.movies = movies;
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        adapter.setMovies(null);
    }
}

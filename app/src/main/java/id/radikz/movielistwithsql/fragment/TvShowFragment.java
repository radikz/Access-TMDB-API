package id.radikz.movielistwithsql.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.radikz.movielistwithsql.DetailTvShowActivity;
import id.radikz.movielistwithsql.R;
import id.radikz.movielistwithsql.models.TvShow;
import id.radikz.movielistwithsql.adapter.TvShowAdapter;
import id.radikz.movielistwithsql.loader.MyAsyncTaskLoaderTv;


public class TvShowFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<TvShow>> {
    ListView listView;
    TvShowAdapter adapter;
    //    private RecyclerView mrecyclerView;
    ProgressBar progressBar;
    String lang;
    TextView mode;
//    String stringMode;

    private ArrayList<TvShow> tvShow = new ArrayList<>();

    static final String EXTRAS_LANG = "EXTRAS_LANG";
    private static final String STATE_RESULT1 = "state_result";

    @Override
    public void onSaveInstanceState(@NonNull Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelableArrayList(STATE_RESULT1, tvShow);
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

        adapter = new TvShowAdapter(getActivity(), tvShow);
        adapter.notifyDataSetChanged();

        mode = view.findViewById(R.id.mode_language);

        mode.setText(getResources().getString(R.string.mode_language));

        listView = view.findViewById(R.id.lv_list);
        listView.setAdapter(adapter);



        Bundle bundle = new Bundle();
        progressBar.setVisibility(View.VISIBLE);
        bundle.putString(EXTRAS_LANG, getResources().getString(R.string.api_lang));

//        getLoaderManager().restartLoader(0, bundle, this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TvShow movie = new TvShow();
                Toast.makeText(getActivity(), tvShow.get(i).getTitle(), Toast.LENGTH_SHORT).show();
                Intent moveWithObjectIntent = new Intent(getActivity(), DetailTvShowActivity.class);
                moveWithObjectIntent.putExtra(DetailTvShowActivity.NAME_MOVIE, tvShow.get(i));
                startActivity(moveWithObjectIntent);
            }
        });

        if (savedInstanceState != null) {
//            initViews();
            tvShow = savedInstanceState.getParcelableArrayList(STATE_RESULT1);
            Log.d("tvShowFrag", "nggak kosong");
//            Log.d("MovieFrag", STATE_RESULT1);
//            listView.onRestoreInstanceState(mListState);
            adapter = new TvShowAdapter(getActivity(), tvShow);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        }
        else{
            Log.d("tvShowFrag", "kosong");
            getLoaderManager().restartLoader(0, bundle, this);
        }

    }

    public TvShowFragment(){

    }

    @NonNull
    @Override
    public Loader<ArrayList<TvShow>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new MyAsyncTaskLoaderTv(getActivity(), getResources().getString(R.string.api_lang));

    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<TvShow>> loader, ArrayList<TvShow> tvShow) {
        adapter.setTvShow(tvShow);
        this.tvShow = tvShow;
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<TvShow>> loader) {
        adapter.setTvShow(null);
    }

}

package id.radikz.movielistwithsql.fragment;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import id.radikz.movielistwithsql.models.FavMovie;
import id.radikz.movielistwithsql.R;
import id.radikz.movielistwithsql.adapter.FavMoviesAdapter;
import id.radikz.movielistwithsql.database.DatabaseController;


public class FavMovieFragment extends Fragment{
    ListView listView;
    ArrayList<FavMovie> mList;

    FavMoviesAdapter adapter;
    //    private RecyclerView mrecyclerView;
    ProgressBar progressBar;
    TextView mode;
    Button fav;
    private Parcelable mListState = null;
    DatabaseController dbController;
    FavMovie favMovie;
//    String stringMode;

    private ArrayList<FavMovie> movies = new ArrayList<>();

    static final String EXTRAS_LANG = "EXTRAS_LANG";
    private static final String STATE_RESULT = "state_result";


    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(STATE_RESULT, movies);
        //    mListState = listView.onSaveInstanceState();
        //    savedInstanceState.putParcelable(STATE_RESULT, mListState);
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



        mList = new ArrayList<>();
        adapter = new FavMoviesAdapter(getActivity(), mList);
        listView = view.findViewById(R.id.lv_list);
        adapter.notifyDataSetChanged();

        dbController = new DatabaseController(getActivity());


        Bundle bundle = new Bundle();
        progressBar.setVisibility(View.INVISIBLE);
        bundle.putString(EXTRAS_LANG, getResources().getString(R.string.api_lang));

        FillList();

//        if (savedInstanceState != null) {
////            initViews();
//            movies = savedInstanceState.getParcelableArrayList(STATE_RESULT);
//            Log.d("MovieFrag", "nggak kosong");
//            Log.d("MovieFrag", STATE_RESULT);
////            listView.onRestoreInstanceState(mListState);
//            adapter = new MoviesAdapter(getActivity(), movies);
////            adapter.notifyDataSetChanged();
//            listView.setAdapter(adapter);
        }

    private void FillList() {
        try {

            Cursor c = dbController.getCompanies();

            while (c.moveToNext()){
                int id = c.getInt(0);
                String title = c.getString(1);
                float rating = Float.valueOf(c.getString(2)) ;
                String date = c.getString(3);
                String photo = c.getString(4);


                mList.add(new FavMovie(id, title, rating, date, photo));
            }

            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), String.valueOf(mList.get(position).getId()) , Toast.LENGTH_SHORT).show();
                    showDialogDelete(mList.get(position).getId(), position);
                }
            });

        } catch (Exception ex) {
//            Log.d("favmovie", ex.getMessage());
//            Toast.makeText(getActivity(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogDelete(final int idRecord, final int position) {

        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());
        dialogDelete.setTitle(R.string.warning);
        dialogDelete.setMessage(R.string.delete);
        dialogDelete.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbController.deleteData(idRecord);
                mList.remove(position);
//                Toast.makeText(getActivity(), R.string.hapus, Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });

        dialogDelete.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    public FavMovieFragment(){

    }

}

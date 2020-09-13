package id.radikz.movielistwithsql;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.radikz.movielistwithsql.database.DatabaseController;
import id.radikz.movielistwithsql.models.Movie;

public class DetailMovieActivity extends AppCompatActivity {
    public static final String NAME_MOVIE = "extra_person";

    TextView movieName, movieDesc, movieScore, movieDate;
    ImageView imgPreview;
    Movie movie;
    Button fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        movieName = findViewById(R.id.name_movie);
        movieScore = findViewById(R.id.score_movie);
        movieDesc = findViewById(R.id.desc_movie);
        movieDate = findViewById(R.id.date_movie);
        imgPreview = findViewById(R.id.img_preview);
        fav = findViewById(R.id.button_movie);

        movie = getIntent().getParcelableExtra(NAME_MOVIE);
        Glide.with(this).load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath()).into(imgPreview);

        String text = getResources().getString(R.string.nama_film) + movie.getTitle();
//                movie.getScore();
        movieName.setText(text);
        text = getResources().getString(R.string.nilai)  + movie.getRating();
        movieScore.setText(text);
        movieScore.setTextColor(Color.GREEN);
        movieDesc.setText(movie.getOverview());
        movieDate.setText(movie.getReleaseDate());

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    DatabaseController dbHandler = new DatabaseController(DetailMovieActivity.this);
                    String s = dbHandler.InsertData(movie.getTitle(), String.valueOf(movie.getRating()) , movie.getReleaseDate(), movie.getPosterPath());
//
                    Toast.makeText(DetailMovieActivity.this, R.string.tambah, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}

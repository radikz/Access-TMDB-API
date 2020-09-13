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

import id.radikz.movielistwithsql.database.DatabaseControllerTv;
import id.radikz.movielistwithsql.models.TvShow;

public class DetailTvShowActivity extends AppCompatActivity {
    public static final String NAME_MOVIE = "extra_person";

    TextView tvShowName, tvShowDesc, tvShowScore, tvShowDate;
    ImageView imgPreview;
    Button fav;
    TvShow tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        tvShowName = findViewById(R.id.name_tv_show);
        tvShowScore = findViewById(R.id.score_tv_show);
        tvShowDesc = findViewById(R.id.desc_tv_show);
        tvShowDate = findViewById(R.id.date_tv_show);
        imgPreview = findViewById(R.id.img_preview);
        fav = findViewById(R.id.button_tv_show);

        tvShow = getIntent().getParcelableExtra(NAME_MOVIE);
        Glide.with(this).load("https://image.tmdb.org/t/p/w185/" + tvShow.getPosterPath()).into(imgPreview);

        String text = getResources().getString(R.string.nama_film) + tvShow.getTitle();
//                tvShow.getScore();
        tvShowName.setText(text);
        text = getResources().getString(R.string.nilai) + tvShow.getRating();
        tvShowScore.setText(text);
        tvShowScore.setTextColor(Color.GREEN);
        tvShowDesc.setText(tvShow.getOverview());
        tvShowDate.setText(tvShow.getReleaseDate());

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    DatabaseControllerTv dbHandler = new DatabaseControllerTv(DetailTvShowActivity.this);
                    String s = dbHandler.InsertData(tvShow.getTitle(), String.valueOf(tvShow.getRating()) , tvShow.getReleaseDate(), tvShow.getPosterPath());
//
                    Toast.makeText(DetailTvShowActivity.this, R.string.tambah, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}


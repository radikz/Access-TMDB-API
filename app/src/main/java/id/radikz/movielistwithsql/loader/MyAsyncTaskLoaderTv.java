package id.radikz.movielistwithsql.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


import cz.msebera.android.httpclient.Header;
import id.radikz.movielistwithsql.models.TvShow;


public class MyAsyncTaskLoaderTv extends AsyncTaskLoader<ArrayList<TvShow>> {
    private ArrayList<TvShow> mData;
    private Context context;
    private String lang;
    private boolean mHasResult = false;
    //    private String cities;
    public MyAsyncTaskLoaderTv(final Context context, String lang) {
        super(context);
        this.context = context;
        this.lang = lang;
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<TvShow> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            mData = null;
            mHasResult = false;
        }
    }

    private static final String API_KEY = "ef89e61c8a5eab5624af0b6b8802021e";

    // Format search kota url JAKARTA = 1642911 ,BANDUNG = 1650357, SEMARANG = 1627896
    // http://api.openweathermap.org/data/2.5/group?id=1642911,1650357,1627896&units=metric&appid=API_KEY
    //en-US

    @Override
    public ArrayList<TvShow> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<TvShow> movies = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY +"&language=" + lang;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
                Log.d("MyActivity","sync mulai");
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        TvShow movie = new TvShow(weather);
                        movies.add(movie);
                    }
                    Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    //Jika terjadi error pada saat parsing maka akan masuk ke catch()
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
                Log.d("MyActivity","error");
//                Toast.makeText(MyAsyncTaskLoader.this.getClass(), "gagal terhubung", Toast.LENGTH_SHORT).show();
            }
        });
        return movies;
    }
}


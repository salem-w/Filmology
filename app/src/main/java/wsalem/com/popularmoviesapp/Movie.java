package wsalem.com.popularmoviesapp;

import android.net.Uri;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Acer on 2/21/2016.
 */
public class Movie {
    public static final String POSTER_PATH = "poster_path";
    public static final String MOVIE_ID = "id";

    public final String poster_path;
    public final long id;

    public Movie(long id ,String poster_path)
    {
        this.id = id;
        this.poster_path = poster_path;
    }
    public Movie(Bundle bundle){

        this(
                bundle.getLong(MOVIE_ID),
                bundle.getString(POSTER_PATH));
    }
    public Bundle toBundle(){
        Bundle bundle = new Bundle();

        bundle.putLong(MOVIE_ID, id);
        bundle.putString(POSTER_PATH, poster_path);

        return bundle;
    }

    public static Movie getFromJson(JSONObject movieObject) throws JSONException {
        return  new Movie(
                movieObject.getLong(MOVIE_ID),
                movieObject.getString(POSTER_PATH));
    }

    public Uri getMovieCover(){
        final String BASE = "http://image.tmdb.org/t/p/w185";

        Uri builtUri = Uri.parse(BASE).buildUpon()
                .appendEncodedPath(poster_path)
                .build();

        return builtUri;
    }
}


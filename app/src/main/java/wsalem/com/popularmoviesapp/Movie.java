package wsalem.com.popularmoviesapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.FileNameMap;

/**
 * Created by salem-w on 2/21/2016.
 */
public class Movie {
    public static final String POSTER_PATH = "poster_path";
    public static final String MOVIE_ID = "id";
    public static final String MOVIE_TITLE = "title";
    public static final String MOVIE_OVERVIEW = "overview";
    public static final String MOVIE_RATING = "vote_average";


    public final String poster_path;
    public final long id;
    public final String movie_title;
    public final String overview;
    public final String rating;

    public Movie(long id ,String poster_path, String movie_title, String overview, String rating)
    {
        this.id = id;
        this.poster_path = poster_path;
        this.movie_title = movie_title;
        this.overview = overview;
        this.rating = rating;
    }
    public Movie(Bundle bundle){

        this(
                bundle.getLong(MOVIE_ID),
                bundle.getString(POSTER_PATH),
                bundle.getString(MOVIE_TITLE),
                bundle.getString(MOVIE_OVERVIEW),
                bundle.getString(MOVIE_RATING));
    }
    public Bundle toBundle(){
        Bundle bundle = new Bundle();

        bundle.putLong(MOVIE_ID, id);
        bundle.putString(POSTER_PATH, poster_path);
        bundle.putString(MOVIE_TITLE, movie_title);
        bundle.putString(MOVIE_OVERVIEW, overview );
        bundle.putString(MOVIE_RATING, rating);



        return bundle;
    }

    public static Movie getFromJson(JSONObject movieObject) throws JSONException {
        return  new Movie(
                movieObject.getLong(MOVIE_ID),
                movieObject.getString(POSTER_PATH),
                movieObject.getString(MOVIE_TITLE),
                movieObject.getString(MOVIE_OVERVIEW),
                movieObject.getString(MOVIE_RATING));
    }

    public Uri getMovieCover(){
        final String BASE = "http://image.tmdb.org/t/p/w185";

        Uri builtUri = Uri.parse(BASE).buildUpon()
                .appendEncodedPath(poster_path)
                .build();

        return builtUri;
    }
}


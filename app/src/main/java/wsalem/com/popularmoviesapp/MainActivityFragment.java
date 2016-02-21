package wsalem.com.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    String[] movieId, movieTitle, movieReleaseDate, movieOverview, moviePosterPath;
    public MainActivityFragment() {
    }

    MovieAdapter movieAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(this.getActivity());
        GridView grid = (GridView) rootView.findViewById(
                R.id.gridview);
        if(grid == null) {
            Log.d(LOG_TAG,"gridview is null");
        }
        grid.setAdapter(movieAdapter);
      //  updateCatalog();
        return rootView;
    }
   // public void updateCatalog()
    //{
      //  FetchMovieTask movieTask = new FetchMovieTask();
       // movieTask.execute();

    //}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_refresh){
        //    updateCatalog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        // updateCatalog();
    }

 /**   public class FetchMovieTask extends AsyncTask<Void, Void, List<String>> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected List<String> doInBackground(Void... params) {

            //nothing to do so return null
            if(params.length == 0)
            {
                return null;
            }

            //initialize objects
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // will contain the raw JSON response
            String jsonResponse = null;
            URL url = null;
            try {


                 url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&" +
                        "api_key=" + BuildConfig.MOVIE_DB_API_KEY);

                try {
                    //try connecting to url
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    //place url in inputstream
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();

                    if(inputStream == null) {
                        //there is nothing to do
                        return null;
                    }

                    //place inputstream into a buffered reader
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;

                    while((line = reader.readLine()) != null)
                    {
                        buffer.append(line + "\n");
                    }

                    if(buffer.length() == 0)
                    {
                        //buffer was empty
                        return null;
                    }
                    jsonResponse = buffer.toString();

                }catch (IOException e) {
                    Log.e(LOG_TAG, "IOExcpetion has been caught!");
                    return null;
                }
                finally {
                    if (urlConnection != null)
                    {
                        urlConnection.disconnect();
                    }

                }
                try {
                    if (reader != null)
                        reader.close();

                }catch (IOException e)
                {
                    Log.e(LOG_TAG, "Error closing buffered Reader");
                    e.printStackTrace();
                }
                }catch (MalformedURLException m) {
                Log.e(LOG_TAG, "URL IS NOT VALID!");
                return null;

            }
            if(url != null) {
                Log.v(LOG_TAG, "SOMETHING IS WRONG WITH THE URL!");
            }

            try{
                return getMovieThroughJson(jsonResponse);
            }
            catch (JSONException j){
                Log.e( LOG_TAG,"Json error", j);

            }
            return null;
        }
        private List<String> getMovieThroughJson(String movieJsonString) throws JSONException {

            JSONObject movieJson = new JSONObject(movieJsonString);
            JSONArray movieArray = movieJson.getJSONArray("results");
            List<String> urls = new ArrayList<String>();

            for(int i = 0; i < movieArray.length(); i++) {
                JSONObject movies = movieArray.getJSONObject(i);
                urls.add("http://image.tmdb.org/t/p/w185" + movies.getString("poster_path"));

            }


            return urls;

        }

        @Override
        protected void onPostExecute(List<String> strings) {
            movieAdapter.replace(strings);
        }
    }
        */

    class MovieAdapter extends BaseAdapter {
        private final String LOG_TAG = MovieAdapter.class.getSimpleName();
        private final Context context;
        private final List<String> urls = new ArrayList<String>();
        private final int height;
        private final int width;

        /**
         * This method adds all movie poster paths into the urls arraylist
         * @param context
         *     - global context equals paramter context
         */
        public MovieAdapter(Context context) {
            this.context = context;
            height = Math.round(this.context.getResources().getDimension(R.dimen.poster_height));
            width = Math.round(this.context.getResources().getDimension((R.dimen.poster_width)));
          //  Collections.addAll(urls, moviePosterPath);

        }

        /**
         * This method gets the image form the url and sets it into the imageview
         * @param position
         * @param convertView
         * @param parent
         * @return
         *     - returns the imageView imageview
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView;
            /**
             * if convertView is empty, set it equal to ImageView
             */
            if(convertView == null) {
                imageView = new ImageView(context);


                imageView.setLayoutParams(new GridView.LayoutParams(width, height));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            else{
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(dummyImages[position]);

  //          String url = getItem(position);

//            Log.e(LOG_TAG, "URL " + url);

           // Picasso.with(context).load(url).into(imageView);

            return imageView;
        }



        public int getCount() {
            return dummyImages.length;
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

       /** public void replace(List<String> urls) {
        *    this.urls.clear();
        *    this.urls.addAll(urls);
        *    notifyDataSetChanged();
        */

        }
        private Integer[] dummyImages = {
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7

        };

}

package wsalem.com.popularmoviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import wsalem.com.popularmoviesapp.Movie;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    private boolean isLoading = false;
    private int pages = 1;
    private TextView loadingTextView;
    private MovieAdapter movieAdapter;

    private class FetchCatalogTask extends AsyncTask<Integer, Void, Collection<Movie>> {

        public  final String LOG_TAG = FetchCatalogTask.class.getSimpleName();

        @Override
        protected Collection<Movie> doInBackground(Integer... params) {
            if (params.length == 0) {
                return null;
            }

            int page = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String responseJsonStr = null;

            try {
                final String API_BASE_URL = "http://api.themoviedb.org/3/movie/";
                final String API_PATH_TOP_RATED = "top_rated";
                final String API_PATH_POPULAR = "popular";
                final String API_PARAM_PAGE = "page";
                final String API_PARAM_KEY = "api_key";

                Uri builtUri = Uri.parse(API_BASE_URL).buildUpon()
                        .appendPath(API_PATH_POPULAR)
                        .appendQueryParameter(API_PARAM_PAGE, String.valueOf(page))
                        .appendQueryParameter(API_PARAM_KEY, BuildConfig.MOVIE_DB_API_KEY)
                        .build();


                Log.d(LOG_TAG, "QUERY URI: " + builtUri.toString());
                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                responseJsonStr = buffer.toString();

            } catch (Exception ex) {
                Log.e(LOG_TAG, "Error", ex);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return fetchMoviesFromJson(responseJsonStr);
            } catch (JSONException ex) {
                Log.d(LOG_TAG, "Can't parse JSON: " + responseJsonStr, ex);
                return null;
            }
        }

        private Collection<Movie> fetchMoviesFromJson(String jsonStr) throws JSONException {
            final String KEY_MOVIES = "results";

            JSONObject json  = new JSONObject(jsonStr);
            JSONArray movies = json.getJSONArray(KEY_MOVIES);
            ArrayList result = new ArrayList<>();

            for (int i = 0; i < movies.length(); i++) {
                result.add(Movie.getFromJson(movies.getJSONObject(i)));
            }

            return result;
        }

        @Override
        protected void onPostExecute(Collection<Movie> xs) {
            if (xs == null) {
                Toast.makeText(
                        getActivity(),
                        getString(R.string.error_message),
                        Toast.LENGTH_SHORT
                ).show();

                quitLoading();
                return;
            }

            pages++;

            quitLoading();

            movieAdapter.addAll(xs);
        }

    }

    private void begin() {
        if (isLoading) {
            return;
        }

        isLoading = true;

        if (loadingTextView != null) {
            loadingTextView.setVisibility(View.VISIBLE);
        }

        new FetchCatalogTask().execute(pages + 1);
    }

    private void quitLoading() {
        if (!isLoading) {
            return;
        }

        isLoading = false;

        if (loadingTextView != null) {
            loadingTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity());
        loadingTextView = (TextView) rootView.findViewById(R.id.loading);

        prepareGrid(rootView);

        quitLoading();

        return rootView;
    }

    private void prepareGrid(View view) {
        GridView grid = (GridView) view.findViewById(R.id.gridview);

        if (grid == null) {
            return;
        }

        grid.setAdapter(movieAdapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v,
                                    int position,
                                    long id) {

                MovieAdapter movieAdapter = (MovieAdapter) parent.getAdapter();
                Movie movie = movieAdapter.getItem(position);

                if (movie == null) {
                    return;
                }

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, movie.toBundle());
                getActivity().startActivity(intent);
            }
        });


        grid.setOnScrollListener(
                new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        int last = firstVisibleItem + visibleItemCount;
                        if (last == totalItemCount) {
                            begin();
                        }
                    }
                }

        );
    }

}
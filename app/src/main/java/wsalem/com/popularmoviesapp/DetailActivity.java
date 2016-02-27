package wsalem.com.popularmoviesapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by salem-w on 2/25/2016.
 */
public class DetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_detail);
        if(savedInstanceState == null) {
           getSupportFragmentManager().beginTransaction()
                   .add(R.id.container, new DetailFragment())
                   .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailfragment, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings) {
            //startActivity(new Intent(this,SettingsActivity.class));
        return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends Fragment {

        private String movieString;

        public void DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                Movie movie = new Movie(intent.getBundleExtra(Intent.EXTRA_TEXT));
                ((TextView) rootView.findViewById(R.id.summaryTextView))
                        .setText(movie.overview);
                ((TextView) rootView.findViewById(R.id.titleTextView))
                        .setText(" " + movie.movie_title);
            }
            return rootView;
        }
    }


}



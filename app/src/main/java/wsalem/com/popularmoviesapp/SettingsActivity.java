package wsalem.com.popularmoviesapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by salem-w on 2/28/2016.
 */
public class SettingsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}


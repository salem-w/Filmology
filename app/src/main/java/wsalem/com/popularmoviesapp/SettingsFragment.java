package wsalem.com.popularmoviesapp;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Acer on 2/28/2016.
 */
public class SettingsFragment extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    public void SettingsFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //add general preferences defined in the XML file
        addPreferencesFromResource(R.xml.pref_general);

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sort_key)));
    //    bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_sort_default)));
    }

    private void bindPreferenceSummaryToValue(Preference pref){
        //set the listener to watch for value changes.
        pref.setOnPreferenceChangeListener(this);

        //trigger the listener immediately with the preference's current value
        onPreferenceChange(pref, PreferenceManager
                .getDefaultSharedPreferences(pref.getContext())
                .getString(pref.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String value = newValue.toString();

        if(preference instanceof ListPreference) {
            //For list preferences, look up correct display value in  the preferences
            //entries list
            ListPreference list = (ListPreference) preference;
            int prefIndex = list.findIndexOfValue(value);
            if (prefIndex >= 0)
                preference.setSummary(list.getEntries()[prefIndex]);
        }else
            preference.setSummary(value);

        return true;

    }
}

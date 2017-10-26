package com.bouami.danecreteil2017_cloud;

import android.app.Activity;
import android.os.Bundle;

import com.bouami.danecreteil2017_cloud.Fragments.PrefsDepartementFragment;

/**
 * Created by Mohammed on 26/10/2017.
 */

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefsDepartementFragment())
                .commit();
    }
}

package com.bouami.danecreteil2017_cloud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bouami.danecreteil2017_cloud.Adapter.MyFragmentPagerAdapter;
import com.bouami.danecreteil2017_cloud.Fragments.PersonnelListFragment;
import com.bouami.danecreteil2017_cloud.Fragments.AnimateurListFragment;
import com.bouami.danecreteil2017_cloud.Fragments.EtablissementListFragment;
import com.bouami.danecreteil2017_cloud.Fragments.ReferentListFragment;
import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "danecreteil2017_cloud";
    RequestQueue mRequestQueue;
    private MyFragmentPagerAdapter mPagerAdapter = null;
    private ViewPager mViewPager;
    private TabLayout mtabLayout;
    public static String departementencours = "";
    public static Fragment[] mFragments;
    public static String[] mFragmentNames;
    private RadioGroup choixdepartement;

    private void SelectDepart() {
        departementencours = getPreferredDepart(this);
        switch (departementencours) {
            case "77" :
                RadioButton selection77 = (RadioButton) findViewById(R.id.sem);
                selection77.setChecked(true);
                break;
            case "93" :
                RadioButton selection93 = (RadioButton) findViewById(R.id.ssd);
                selection93.setChecked(true);
                break;
            case "94" :
                RadioButton selection94 = (RadioButton) findViewById(R.id.vdm);
                selection94.setChecked(true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRequestQueue = Volley.newRequestQueue(this);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mtabLayout = (TabLayout) findViewById(R.id.tabs);
        SelectDepart();
        choixdepartement = (RadioGroup) this.findViewById(R.id.choixdepartement);
//        String departementpardefault = getPreferredDepart(this);
        departementencours = getPreferredDepart(this);
        mFragments = new Fragment[] {
                AnimateurListFragment.newInstance(departementencours) ,
                EtablissementListFragment.newInstance(departementencours,Long.valueOf(0)),
                PersonnelListFragment.newInstance(departementencours, Long.valueOf(0)),
                ReferentListFragment.newInstance(departementencours, Long.valueOf(0)),
        };
        mFragmentNames = new String[] {
                "Animateurs",
                "Etablissements",
                "Personnel",
                "Référents Numériques"
        };
        try {
            mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),mFragments,mFragmentNames);
            mViewPager.setAdapter(mPagerAdapter);
            mtabLayout.setupWithViewPager(mViewPager);
            mViewPager.setCurrentItem(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        choixdepartement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            private RadioButton departement;
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                final int ongletencours = mViewPager.getCurrentItem();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                departement = (RadioButton) findViewById(selectedId);
                departementencours = departement.getText().toString();
                try {
                    mFragments[0] = AnimateurListFragment.newInstance(departementencours);
                    mFragments[1] = EtablissementListFragment.newInstance(departementencours,Long.valueOf(0));
                    mFragments[2] = PersonnelListFragment.newInstance(departementencours,Long.valueOf(0));
                    mFragments[3] = ReferentListFragment.newInstance(departementencours,Long.valueOf(0));
                    mFragmentNames[0]="Animateurs du "+departementencours;
                    mFragmentNames[1]="Etablissements du "+departementencours;
                    mFragmentNames[2]="Personnel du "+departementencours;
                    mFragmentNames[3]="Référents Numériques du "+departementencours;
                    mViewPager.setAdapter(mPagerAdapter);
                    mViewPager.setCurrentItem(ongletencours);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        Cursor NombreetablissementCursor = getBaseContext().getContentResolver().query(
                DaneContract.EtablissementEntry.CONTENT_URI,
                new String[]{DaneContract.EtablissementEntry._ID},
                null,
                null,
                null);
        if (!(NombreetablissementCursor.getCount() > 0)) {
            Log.d(TAG, "Base de données non présente : ");
            DaneContract.ImporterDonneesFromUrlToDatabase(this,DaneContract.BASE_URL_EXPORT);
        } else {
            Log.d(TAG, NombreetablissementCursor.getCount()+" établissements sont déjà enregistrés dans la Base de données.");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop () {
        super.onStop();
//        if (mRequestQueue != null) {
//            mRequestQueue.cancelAll(TAG);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accueil, menu);
        return true;
    }

    public static String getPreferredDepart(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_depart_key),
                context.getString(R.string.pref_depart_default));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout :
                Log.d(TAG, "AccueilActivity: action_logout activé");
                return true;
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
//                Intent i = new Intent(this, ParametresActivity.class);
//                startActivity(i);
                return true;
            case R.id.action_reinitialisation :

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
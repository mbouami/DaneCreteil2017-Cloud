package com.bouami.danecreteil2017_cloud;

import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    StringRequest stringRequest; // Assume this exists.
    RequestQueue mRequestQueue;  // Assume this exists.
    List<Animateur> listedesanimateurspardepart = new ArrayList<Animateur>();
    private MyFragmentPagerAdapter mPagerAdapter = null;
    private ViewPager mViewPager;
    private TabLayout mtabLayout;
    public static JSONObject mesdonneesjson = new JSONObject();
    public static String departementencours = "";
    public static Fragment[] mFragments;
    public static String[] mFragmentNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRequestQueue = Volley.newRequestQueue(this);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mtabLayout = (TabLayout) findViewById(R.id.tabs);
        RadioGroup choixdepartement = (RadioGroup) this.findViewById(R.id.choixdepartement);
        departementencours = null;
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
//                    if(mPagerAdapter!=null){
////                                        Log.d(TAG, "onCheckedChanged : "+departementencours+"---"+mesdonneesjson);
//                        mPagerAdapter.notifyDataSetChanged();;
//                    }
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
        getMenuInflater().inflate(R.menu.menu_animateurs, menu);
        return true;
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
            case R.id.rechercher:
                return true;
            case R.id.action_reinitialisation :

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

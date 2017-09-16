package com.bouami.danecreteil2017_cloud;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bouami.danecreteil2017_cloud.Fragments.AnimateurListFragment;
import com.bouami.danecreteil2017_cloud.Fragments.EtablissementListFragment;
import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Parametres.mesparametres;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "danecreteil2017_cloud";
    StringRequest stringRequest; // Assume this exists.
    RequestQueue mRequestQueue;  // Assume this exists.
    List<Animateur> listedesanimateurspardepart = new ArrayList<Animateur>();
    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private mesparametres mesdonnees = new mesparametres();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, mesdonnees.BASE_URL_DEPART, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(final JSONObject response) {
                        listedesanimateurspardepart = mesdonnees.getListeAnimateurs(response,"93");
                        // Create the adapter that will return a fragment for each section
                        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                            private final Fragment[] mFragments = new Fragment[] {
//                                    new AnimateurListFragment(listedesanimateurspardepart),
//                                    new AnimateurListFragment(mesdonnees.getListeAnimateurs(response,"94")),
//                                    new AnimateurListFragment(mesdonnees.getListeAnimateurs(response,"77"))
                                    new AnimateurListFragment(mesdonnees.getListeAnimateurs(response,"")),
                                    new EtablissementListFragment(mesdonnees.getListeEtablissements(response,""))
                            };
                            private final String[] mFragmentNames = new String[] {
//                                    getString(R.string.heading_my_animateurs)+ " du 93",
//                                    getString(R.string.heading_my_animateurs)+ " du 94",
//                                    getString(R.string.heading_my_animateurs)+ " du 77"
                                    getString(R.string.heading_my_animateurs),
                                    getString(R.string.heading_my_etablissements)
                            };
                            @Override
                            public Fragment getItem(int position) {
                                return mFragments[position];
                            }
                            @Override
                            public int getCount() {
                                return mFragments.length;
                            }
                            @Override
                            public CharSequence getPageTitle(int position) {
                                return mFragmentNames[position];
                            }
                        };
                        mViewPager = (ViewPager) findViewById(R.id.container);
                        mViewPager.setAdapter(mPagerAdapter);
                        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                        tabLayout.setupWithViewPager(mViewPager);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(TAG, "That didn't work!");
                    }
                });
//        stringRequest.setTag(TAG);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        mRequestQueue.add(jsObjRequest);
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
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
            case R.id.animateurrechercher :
                return true;
            case R.id.action_reinitialisation :

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

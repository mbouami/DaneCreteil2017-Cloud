package com.bouami.danecreteil2017_cloud;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bouami.danecreteil2017_cloud.Adapter.EtablissementRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Adapter.MyFragmentPagerAdapter;
import com.bouami.danecreteil2017_cloud.Fragments.EtablissementListFragment;
import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailAnimateurActivity extends AppCompatActivity {

    private static final String TAG = "DetailAnimateurActivity";
    public static final String EXTRA_ANIMATEUR_ID = "animateur_id";
    private Long mAnimateurId;
    private RecyclerView mRecycler;
    private EtablissementRecyclerViewAdapter mAdapter;
    private TextView nomanimateur;
    private TextView telanimateur;
    private TextView mailanimateur;
    public static MyFragmentPagerAdapter mPagerAdapter = null;
    private ViewPager mViewPager;
    private TabLayout mtabLayout;
    private Uri mUri;
    private static final int ETABLISSEMENTS_PAR_ANIMATEUR_LOADER = 0;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etablissements_par_animateur);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        mAnimateurId = getIntent().getLongExtra(EXTRA_ANIMATEUR_ID,0);
        if (mAnimateurId == null) {
            throw new IllegalArgumentException("Vous devez fournir EXTRA_ANIMATEUR_KEY");
        }
        nomanimateur = (TextView) this.findViewById(R.id.nom);
        telanimateur = (TextView) this.findViewById(R.id.tel);
        mailanimateur = (TextView) this.findViewById(R.id.email);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Cursor mcursor = DaneContract.getAnimateurFromId(this,mAnimateurId);
        if (mcursor!=null) {
            String civiliteanim  = DaneContract.getValueFromCursor(mcursor,"CIVILITE");
            String nomanim  = DaneContract.getValueFromCursor(mcursor,DaneContract.AnimateurEntry.COLUMN_NOM);
            String prenomanim  = DaneContract.getValueFromCursor(mcursor,DaneContract.AnimateurEntry.COLUMN_PRENOM);
            nomanimateur.setText(civiliteanim + " "+nomanim+" "+prenomanim);
            telanimateur.setText("Tél : "+DaneContract.getValueFromCursor(mcursor,DaneContract.AnimateurEntry.COLUMN_TEL));
            mailanimateur.setText(DaneContract.getValueFromCursor(mcursor,DaneContract.AnimateurEntry.COLUMN_EMAIL));
        }
        mUri = DaneContract.AnimateurEntry.buildEtabParIdAnimateur(String.valueOf(mAnimateurId),"etab");
        mViewPager = (ViewPager) findViewById(R.id.container);
        mtabLayout = (TabLayout) findViewById(R.id.tabs);
        try {
//            Long idetab = Long.valueOf(DaneContract.getValueFromCursor(mcursor,DaneContract.EtablissementEntry._ID));
            Fragment[] mFragments = new Fragment[] {
                    EtablissementListFragment.newInstance(null,mAnimateurId ),
            };
            String[] mFragmentNames = new String[] {
                    "Etablissements"
            };
            mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),mFragments,mFragmentNames);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mViewPager.setAdapter(mPagerAdapter);
        mtabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animateurs, menu);
        // Do something that differs the Activity's menu here
        MenuItem menusearch = menu.findItem(R.id.rechercher);
        SearchView searchview = (SearchView) menusearch.getActionView();
//        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
////                Log.d(TAG, "setOnQueryTextListener: onQueryTextSubmit " + query);
//                return false;
//            }
//
//            @SuppressLint("LongLogTag")
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                List<Etablissement> listedesetablissementsfiltre = new ArrayList<Etablissement>();
//                for (Etablissement etab : mlistedesetabsparanimateur) {
//                    if (etab.getNom().toLowerCase().contains(newText.toLowerCase())) {
//                        listedesetablissementsfiltre.add(etab);
//                    }
//                }
////                mAdapter = new EtablissementRecyclerViewAdapter(Etablissement.class, R.layout.item_etablissement,
////                        EtablissementViewHolder.class, listedesetablissementsfiltre);
//                recyclerView.setAdapter(mAdapter);
//                return true;
//            }
//        });
        return true;
    }
}

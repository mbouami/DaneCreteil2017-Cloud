package com.bouami.danecreteil2017_cloud;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bouami.danecreteil2017_cloud.Adapter.MyFragmentPagerAdapter;
import com.bouami.danecreteil2017_cloud.Adapter.PersonnelsRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Fragments.PersonnelListFragment;
import com.bouami.danecreteil2017_cloud.Fragments.ReferentListFragment;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;

import org.json.JSONException;

public class DetailEtablissementActivity extends AppCompatActivity {
    private static final String TAG = "DetailEtablissementActivity";
    public static final String EXTRA_ETABLISSEMENT_ID = "etablissement_id";
    public static Long mEtablissementId;
    private RecyclerView recyclerView;
    private PersonnelsRecyclerViewAdapter mAdapter;
    private TextView nometab;
    private TextView teletab;
    private TextView faxetab;
    private TextView mailetab;
    private TextView adresseetab;
    private TextView villeetab;
    private TextView animateureetab;
    public static MyFragmentPagerAdapter mPagerAdapter = null;
    private ViewPager mViewPager;
    private TabLayout mtabLayout;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate : ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_par_etablissement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        mEtablissementId = getIntent().getLongExtra(EXTRA_ETABLISSEMENT_ID,0);
        if (mEtablissementId == 0) {
            throw new IllegalArgumentException("Vous devez fournir EXTRA_ETABLISSEMENT_ID");
        }
        nometab = (TextView) this.findViewById(R.id.nom);
        teletab = (TextView) this.findViewById(R.id.tel);
        faxetab = (TextView) this.findViewById(R.id.fax);
        mailetab = (TextView) this.findViewById(R.id.email);
        adresseetab = (TextView) this.findViewById(R.id.adresse);
        villeetab = (TextView) this.findViewById(R.id.ville);
        animateureetab = (TextView) this.findViewById(R.id.animateur);
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onStart() {
        super.onStart();
        Cursor mcursor = DaneContract.getEtablissementFromId(this,mEtablissementId);
        if (mcursor!=null) {
            String etab = DaneContract.getValueFromCursor(mcursor,DaneContract.EtablissementEntry.COLUMN_TYPE)
                    + " "+DaneContract.getValueFromCursor(mcursor,DaneContract.EtablissementEntry.COLUMN_NOM);
            Cursor manimateurcursor = DaneContract.getAnimateurFromId(this, Long.valueOf(DaneContract.getValueFromCursor(mcursor,DaneContract.EtablissementEntry.COLUMN_ANIMATEUR_ID)));
            String nomanimateur = "Animateur DANE : "+(manimateurcursor==null?"":DaneContract.getValueFromCursor(manimateurcursor,DaneContract.AnimateurEntry.COLUMN_NOM));
            nometab.setText(etab);
            teletab.setText("Tél : "+DaneContract.getValueFromCursor(mcursor,DaneContract.EtablissementEntry.COLUMN_TEL));
            faxetab.setText("Fax : "+DaneContract.getValueFromCursor(mcursor,DaneContract.EtablissementEntry.COLUMN_FAX));
            mailetab.setText(DaneContract.getValueFromCursor(mcursor,DaneContract.EtablissementEntry.COLUMN_EMAIL));
            adresseetab.setText(DaneContract.getValueFromCursor(mcursor,DaneContract.EtablissementEntry.COLUMN_ADRESSE));
            villeetab.setText(DaneContract.getValueFromCursor(mcursor,"CPVILLE")+ " "+DaneContract.getValueFromCursor(mcursor,"NOMVILLE"));
            animateureetab.setText(nomanimateur);
        }
        mViewPager = (ViewPager) findViewById(R.id.container);
        mtabLayout = (TabLayout) findViewById(R.id.tabs);
        try {
//            Long idetab = Long.valueOf(DaneContract.getValueFromCursor(mcursor,DaneContract.EtablissementEntry._ID));
            Fragment[] mFragments = new Fragment[] {
                    PersonnelListFragment.newInstance(null,mEtablissementId ),
                    ReferentListFragment.newInstance(null,mEtablissementId)
            };
            String[] mFragmentNames = new String[] {
                    "Personnel",
                    "Référents Numériques"
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
//                List<Personnel> listedespersonnelsfiltre = new ArrayList<Personnel>();
//                for (Personnel perso : mlistepersonnelsparanimateur) {
//                    if (perso.getNom().toLowerCase().contains(newText.toLowerCase())) {
//                        listedespersonnelsfiltre.add(perso);
//                    }
//                }
//                mAdapter = new PersonnelsRecyclerViewAdapter(Personnel.class,R.layout.item_personnel,PersonnelViewHolder.class,listedespersonnelsfiltre);
//                recyclerView.setAdapter(mAdapter);
//                return true;
//            }
//        });
        return true;
    }

}

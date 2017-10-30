package com.bouami.danecreteil2017_cloud;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
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
import com.bouami.danecreteil2017_cloud.Fragments.ConfirmationDialogFragment;
import com.bouami.danecreteil2017_cloud.Fragments.NoticeDialogFragment;
import com.bouami.danecreteil2017_cloud.Fragments.PersonnelListFragment;
import com.bouami.danecreteil2017_cloud.Fragments.ReferentListFragment;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailEtablissementActivity extends AppCompatActivity implements  NoticeDialogFragment.NoticeDialogListener, ConfirmationDialogFragment.ConfirmationDialogListener {
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
    public static Fragment[] mFragments;
    public static String[] mFragmentNames;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            String civiliteanim  = DaneContract.getValueFromCursor(mcursor,"CIVILITE");
            String nomanim  = DaneContract.getValueFromCursor(mcursor,"NOMANIMATEUR");
            String prenomanim  = DaneContract.getValueFromCursor(mcursor,"PRENOMANIMATEUR");
            String nomanimateur = "Animateur DANE : "+(manimateurcursor==null?"":civiliteanim+" "+nomanim+" "+prenomanim);
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
            mFragments = new Fragment[] {
                    PersonnelListFragment.newInstance(null,mEtablissementId ),
                    ReferentListFragment.newInstance(null,mEtablissementId)
            };
            mFragmentNames = new String[] {
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


    @SuppressLint("LongLogTag")
    @Override
    public void onDialogReferentPositiveClick(DialogFragment dialog, ContentValues jsonreferent) {
//        Log.d(TAG, "Création du référent : "+jsonreferent);
        if (jsonreferent.getAsLong("_id")==0) {
//                DaneContract.writeNewReferent(this,jsonreferent);
            jsonreferent.remove("_id");
            DaneContract.addReferent(this,jsonreferent);
        } else {
            DaneContract.updateReferent(this,jsonreferent);
        }
        mFragments[0] = PersonnelListFragment.newInstance(null,mEtablissementId );
        mFragments[1] = ReferentListFragment.newInstance(null,mEtablissementId);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(1);
//        Snackbar.make(this, "La création du référent à réussie", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
//        Log.d(TAG, "onDialogNegativeClick : ");
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onDialogReferentDeleteClick(ConfirmationDialogFragment dialog, JSONObject jsonreferent) {
        Uri muri = null;
        try {
            muri = DaneContract.ReferentEntry.buildReferentUri(Long.parseLong(jsonreferent.getString("id")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (this.getContentResolver().delete(muri, null,null)>0) {
//        DaneContract.deleteReferent(this,jsonreferent);
            Log.d(TAG, "Suppression du référent : "+jsonreferent);
            mFragments[0] = PersonnelListFragment.newInstance(null,mEtablissementId );
            mFragments[1] = ReferentListFragment.newInstance(null,mEtablissementId);
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onDialogReferentQuitterClick(ConfirmationDialogFragment dialog) {

    }
}

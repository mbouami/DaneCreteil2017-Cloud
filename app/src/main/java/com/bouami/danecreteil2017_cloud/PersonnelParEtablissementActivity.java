package com.bouami.danecreteil2017_cloud;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.bouami.danecreteil2017_cloud.Adapter.MyRecycleAdapter;
import com.bouami.danecreteil2017_cloud.Adapter.PersonnelsRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.Models.Personnel;
import com.bouami.danecreteil2017_cloud.Parametres.mesparametres;
import com.bouami.danecreteil2017_cloud.ViewHolder.EtablissementViewHolder;
import com.bouami.danecreteil2017_cloud.ViewHolder.PersonnelViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PersonnelParEtablissementActivity extends AppCompatActivity {
    private static final String TAG = "PersonnelParEtablissementActivity";
    public static final String EXTRA_ETABLISSEMENT_ID = "etablissement_id";
    private String mEtablissementId;
    private String mDepartement;
    private JSONObject mDonneesJson;
    private mesparametres mMesParametres;
    private static List<Personnel> mListedespersonnels= new ArrayList<Personnel>();
    private RecyclerView recyclerView;
    private PersonnelsRecyclerViewAdapter mAdapter;
    private TextView nometab;
    private TextView teletab;
    private TextView faxetab;
    private TextView mailetab;
    private TextView adresseetab;
    private TextView villeetab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_par_etablissement);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        nometab = (TextView) this.findViewById(R.id.nom);
        teletab = (TextView) this.findViewById(R.id.tel);
        faxetab = (TextView) this.findViewById(R.id.fax);
        mailetab = (TextView) this.findViewById(R.id.email);
        adresseetab = (TextView) this.findViewById(R.id.adresse);
        villeetab = (TextView) this.findViewById(R.id.ville);
        recyclerView = (RecyclerView) this.findViewById(R.id.recycler_personnel_par_etab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get post key from intent
        mEtablissementId = getIntent().getStringExtra(EXTRA_ETABLISSEMENT_ID);
        if (mEtablissementId == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ANIMATEUR_KEY");
        }
        mDepartement = MainActivity.departementencours;
        mDonneesJson = MainActivity.mesdonneesjson;
        mMesParametres = MainActivity.mesparametres;
        try {
            Etablissement etablissement = new Etablissement(mDonneesJson.getJSONObject("etablissements").getJSONObject(mDepartement).getJSONObject(mEtablissementId));
            nometab.setText(etablissement.getType()+ " "+etablissement.getNom());
            teletab.setText("Tél : "+etablissement.getTel());
            faxetab.setText("Fax : "+etablissement.getFax());
            mailetab.setText(etablissement.getEmail());
            adresseetab.setText(etablissement.getAdresse());
            villeetab.setText(etablissement.getCp()+ " "+etablissement.getVille());
            mListedespersonnels = mMesParametres.getListePersonnelsCreteilParEtablissement(mDonneesJson,mDepartement,mEtablissementId);
            mAdapter = new PersonnelsRecyclerViewAdapter(Personnel.class,R.layout.item_personnel,PersonnelViewHolder.class,mListedespersonnels);
            recyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animateurs, menu);
        MenuItem menusearch = menu.findItem(R.id.animateurrechercher);
        SearchView searchview = (SearchView) menusearch.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener (){

            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.d(TAG, "setOnQueryTextListener: onQueryTextSubmit " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Personnel> listedespersonnelsfiltre = new ArrayList<Personnel>();
                for (Personnel lepersonnel : mListedespersonnels) {
                    if (lepersonnel.getNom().toLowerCase().contains(newText.toLowerCase())) {
                        listedespersonnelsfiltre.add(lepersonnel);
                    }
                }
                mAdapter = new PersonnelsRecyclerViewAdapter(Personnel.class,R.layout.item_personnel,PersonnelViewHolder.class,listedespersonnelsfiltre);
                recyclerView.setAdapter(mAdapter);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

}

package com.bouami.danecreteil2017_cloud;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bouami.danecreteil2017_cloud.Adapter.EtablissementRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.ViewHolder.EtablissementViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EtablissementsParAnimateurActivity extends AppCompatActivity {


    private static final String TAG = "EtablissementsParAnimateurActivity";
    public static final String EXTRA_ANIMATEUR_ID = "animateur_id";
    private String mAnimateurId;
    private String mDepartement;
    private JSONObject mDonneesJson;
    private mesparametres mMesParametres;
    private static List<Etablissement> mListedesetabs= new ArrayList<Etablissement>();
    private RecyclerView recyclerView;
    private EtablissementRecyclerViewAdapter mAdapter;
    private TextView nomanimateur;
    private TextView telanimateur;
    private TextView mailanimateur;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etablissements_par_animateur);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        nomanimateur = (TextView) this.findViewById(R.id.nom);
        telanimateur = (TextView) this.findViewById(R.id.tel);
        mailanimateur = (TextView) this.findViewById(R.id.email);
        recyclerView = (RecyclerView) this.findViewById(R.id.recycler_etabs_par_anim);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Get post key from intent
        mAnimateurId = getIntent().getStringExtra(EXTRA_ANIMATEUR_ID);
        if (mAnimateurId == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ANIMATEUR_KEY");
        }
        mDepartement = MainActivity.departementencours;
        mDonneesJson = MainActivity.mesdonneesjson;
        mMesParametres = MainActivity.mesparametres;
        try {
            Animateur animateur = new Animateur(mDonneesJson.getJSONObject("animateurs").getJSONObject(mDepartement).getJSONObject(mAnimateurId));
            nomanimateur.setText(animateur.getGenre()+ " "+animateur.getPrenom()+ " "+animateur.getNom());
            telanimateur.setText(animateur.getTel());
            mailanimateur.setText(animateur.getEmail());
            mListedesetabs = mMesParametres.getListeEtablissementsCreteilParAnimateur(mDonneesJson,mDepartement,mAnimateurId);
            mAdapter = new EtablissementRecyclerViewAdapter(Etablissement.class, R.layout.item_etablissement,
                    EtablissementViewHolder.class, mListedesetabs);
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
                List<Etablissement> listedesetablissementsfiltre = new ArrayList<Etablissement>();
                for (Etablissement etab : mListedesetabs) {
                    if (etab.getNom().toLowerCase().contains(newText.toLowerCase())) {
                        listedesetablissementsfiltre.add(etab);
                    }
                }
                mAdapter = new EtablissementRecyclerViewAdapter(Etablissement.class,
                        R.layout.item_etablissement, EtablissementViewHolder.class, listedesetablissementsfiltre) {
                };
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

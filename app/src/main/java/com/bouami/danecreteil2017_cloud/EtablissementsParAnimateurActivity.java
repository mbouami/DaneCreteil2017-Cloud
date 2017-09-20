package com.bouami.danecreteil2017_cloud;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.bouami.danecreteil2017_cloud.Adapter.EtablissementRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Adapter.MyRecycleAdapter;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.ViewHolder.EtablissementViewHolder;

import java.util.ArrayList;
import java.util.List;

public class EtablissementsParAnimateurActivity extends AppCompatActivity {


    private static final String TAG = "EtablissementsParAnimateurActivity";
    public static final String EXTRA_ANIMATEUR_ID = "animateur_id";
    private String mAnimateurId;
    private static final List<Etablissement> ITEMS= new ArrayList<Etablissement>();
    private RecyclerView recyclerView;
    private MyRecycleAdapter<Etablissement,EtablissementViewHolder> mAdapter;
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
//        Query etabsparanimateurQuery = getQueryEtabsParAnim(mDatabase,mAnimateurKey);
//        mAdapter = new EtablissementRecyclerViewAdapter(Etablissement.class, R.layout.item_etablissement,
//                EtablissementViewHolder.class, listedesetablissements);
//        recyclerView.setAdapter(mAdapter);

    }
//
//    public Query getQueryEtabsParAnim(DatabaseReference databaseReference, String animId) {
//        return databaseReference.child("etablissements").orderByChild("animateurs/"+animkey).equalTo(true);
//    }

    @Override
    protected void onStart() {
        super.onStart();
//        mAnimateurReference = FirebaseDatabase.getInstance().getReference().child("animateurs").child(mAnimateurKey);
//        postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Animateur animateur = dataSnapshot.getValue(Animateur.class);
//                nomanimateur.setText(animateur.getGenre()+ " "+animateur.getPrenom()+ " "+animateur.getNom());
//                telanimateur.setText(animateur.getTel());
//                mailanimateur.setText(animateur.getEmail());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//            }
//        };
//        mAnimateurReference.addListenerForSingleValueEvent(postListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animateurs, menu);
        return true;
    }


}

package com.bouami.danecreteil2017_cloud.Adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017_cloud.EtablissementsParAnimateurActivity;
import com.bouami.danecreteil2017_cloud.MainActivity;
import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.AnimateurViewHolder;

import com.bouami.danecreteil2017_cloud.Parametres.mesparametres;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by mbouami on 15/09/2017.
 */

public class AnimateursRecyclerViewAdapter extends MyRecycleAdapter<Animateur,AnimateurViewHolder> {
    private final List<Animateur> mValues;
    private final String TAG = "AnimateursRecyclerViewAdapter";
    private Animateur animateurselectionne;
    private FloatingActionButton mailanimateur;
    private FloatingActionButton phoneanimateur;

    public AnimateursRecyclerViewAdapter(Class<Animateur> mModelClass,@LayoutRes int mModelLayout, Class<AnimateurViewHolder> mViewHolderClass,
                                          List<Animateur> msnapshots) {
        super(mModelClass, mModelLayout, mViewHolderClass, msnapshots);
        mValues = msnapshots;
    }


    @Override
    public AnimateurViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.include_animateur_references, parent, false);
        final mesparametres mesfonctions = new mesparametres();
        mailanimateur = (FloatingActionButton) parent.getRootView().findViewById(R.id.mailanimateur);
        mailanimateur.setVisibility(View.INVISIBLE);
        mailanimateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(mesfonctions.createMailIntent(animateurselectionne.getEmail()));
            }
        });
        phoneanimateur = (FloatingActionButton) parent.getRootView().findViewById(R.id.phoneanimateur);
        phoneanimateur.setVisibility(View.INVISIBLE);
        phoneanimateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(mesfonctions.createPhoneIntent(animateurselectionne.getTel()));

            }
        });
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public Animateur getItem(int position) {
        return super.getItem(position);
    }

    @Override
    protected void populateViewHolder(AnimateurViewHolder viewHolder, final Animateur model, int position) {
        viewHolder.mListeEtabsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch PostDetailActivity
                Intent intent = new Intent(v.getContext(), EtablissementsParAnimateurActivity.class);
                intent.putExtra(EtablissementsParAnimateurActivity.EXTRA_ANIMATEUR_ID, model.getId());
                v.getContext().startActivity(intent);
            }
        });
        // Bind Post to ViewHolder, setting OnClickListener for the star button
        viewHolder.bindToAnimateur(model, new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                // Need to write to both places the post is stored
//                Log.d(TAG, "bindToAnimateur:" + model.getId() + " - " + model.getNom());
                animateurselectionne = model;
                if (mailanimateur.getVisibility()==View.INVISIBLE) {
                    mailanimateur.setVisibility(View.VISIBLE);
                    phoneanimateur.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

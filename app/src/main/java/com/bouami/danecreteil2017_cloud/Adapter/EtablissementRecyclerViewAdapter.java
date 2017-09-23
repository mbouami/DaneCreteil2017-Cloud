package com.bouami.danecreteil2017_cloud.Adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.Parametres.mesparametres;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.EtablissementViewHolder;

import java.util.List;

/**
 * Created by mbouami on 16/09/2017.
 */

public class EtablissementRecyclerViewAdapter extends MyRecycleAdapter<Etablissement,EtablissementViewHolder> {

    private static final String TAG = "EtablissementRecyclerViewAdapter";
    private final List<Etablissement> mValues;
    private FloatingActionButton mailetablissement;
    private FloatingActionButton phoneetablissement;
    private FloatingActionButton addreferentetablissement;
    private Etablissement etablissementselectionne;

    public EtablissementRecyclerViewAdapter(Class<Etablissement> mModelClass, @LayoutRes int mModelLayout,
                                            Class<EtablissementViewHolder> mViewHolderClass, List<Etablissement> mSnapshots) {
        super(mModelClass, mModelLayout, mViewHolderClass, mSnapshots);
        mValues = mSnapshots;

    }

    @Override
    public void cleanup() {
        super.cleanup();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public Etablissement getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public EtablissementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final mesparametres mesfonctions = new mesparametres();
        mailetablissement = (FloatingActionButton) parent.getRootView().findViewById(R.id.mailetablissement);
        mailetablissement.setVisibility(View.INVISIBLE);
        mailetablissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(mesfonctions.createMailIntent(etablissementselectionne.getEmail()));
            }
        });
        phoneetablissement = (FloatingActionButton) parent.getRootView().findViewById(R.id.phoneetablissement);
        phoneetablissement.setVisibility(View.INVISIBLE);
        phoneetablissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(mesfonctions.createPhoneIntent(etablissementselectionne.getTel()));
            }
        });
//        addreferentetablissement = (FloatingActionButton) parent.getRootView().findViewById(R.id.addreferent);
//        addreferentetablissement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                view.getContext().startActivity(mesfonctions.createPhoneIntent(etablissementselectionne.getTel()));
////                Intent intent = new Intent(view.getContext(), NewReferentActivity.class);
////                intent.putExtra(NewReferentActivity.EXTRA_ETABLISSEMENT_KEY, "0931192R");
////                view.getContext().startActivity(intent);
//            }
//        });
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(EtablissementViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected void populateViewHolder(EtablissementViewHolder viewHolder, final Etablissement model, int position) {
        // Set click listener for the whole post view

        viewHolder.mListePersonnelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch PostDetailActivity
//                Intent intent = new Intent(v.getContext(), PersonnelParEtablissementActivity.class);
//                intent.putExtra(PersonnelParEtablissementActivity.EXTRA_ETABLISSEMENT_KEY, etablissementKey);
//                v.getContext().startActivity(intent);
            }
        });

        // Determine if the current user has liked this post and set UI accordingly
/*                if (model.stars.containsKey(getUid())) {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                }*/

        // Bind Post to ViewHolder, setting OnClickListener for the star button
        viewHolder.bindToEtablissement(model, new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                etablissementselectionne = model;
                if (mailetablissement.getVisibility()==View.INVISIBLE) {
                    mailetablissement.setVisibility(View.VISIBLE);
                    phoneetablissement.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

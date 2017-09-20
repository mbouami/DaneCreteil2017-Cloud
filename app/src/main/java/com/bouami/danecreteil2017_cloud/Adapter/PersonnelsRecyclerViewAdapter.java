package com.bouami.danecreteil2017_cloud.Adapter;

import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017_cloud.Models.Personnel;
import com.bouami.danecreteil2017_cloud.Parametres.mesparametres;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.PersonnelViewHolder;

import java.util.List;

/**
 * Created by mbouami on 20/09/2017.
 */

public class PersonnelsRecyclerViewAdapter extends MyRecycleAdapter<Personnel,PersonnelViewHolder> {
    private final List<Personnel> mValues;
    private final String TAG = "AnimateursRecyclerViewAdapter";
    private Personnel personnelselectionne;
    private FloatingActionButton mailpersonnel;
    private FloatingActionButton phonepersonnel;

    public PersonnelsRecyclerViewAdapter(Class<Personnel> mModelClass, int mModelLayout, Class<PersonnelViewHolder> mViewHolderClass, List<Personnel> mSnapshots) {
        super(mModelClass, mModelLayout, mViewHolderClass, mSnapshots);
        mValues = mSnapshots;
    }

    @Override
    public PersonnelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.include_personnel_references, parent, false);
        final mesparametres mesfonctions = new mesparametres();
        mailpersonnel = (FloatingActionButton) parent.getRootView().findViewById(R.id.mailpersonnel);
        mailpersonnel.setVisibility(View.INVISIBLE);
        mailpersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(mesfonctions.createMailIntent(personnelselectionne.getEmail()));
            }
        });
        phonepersonnel = (FloatingActionButton) parent.getRootView().findViewById(R.id.phonepersonnel);
        phonepersonnel.setVisibility(View.INVISIBLE);
        phonepersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(mesfonctions.createPhoneIntent(personnelselectionne.getTel()));

            }
        });
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public Personnel getItem(int position) {
        return super.getItem(position);
    }

    @Override
    protected void populateViewHolder(PersonnelViewHolder viewHolder, final Personnel model, int position) {
//        viewHolder.mListePersonnelView.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("LongLogTag")
//            @Override
//            public void onClick(View v) {
//                // Launch PostDetailActivity
//                Log.d(TAG, "setOnClickListener:" + model.getNom());
//            }
//        });
        // Bind Post to ViewHolder, setting OnClickListener for the star button
        viewHolder.bindToPersonnel(model, new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                // Need to write to both places the post is stored
//                Log.d(TAG, "bindToAnimateur:" + model.getId() + " - " + model.getNom());
                personnelselectionne = model;
                if (mailpersonnel.getVisibility()==View.INVISIBLE) {
                    mailpersonnel.setVisibility(View.VISIBLE);
                    phonepersonnel.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
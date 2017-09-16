package com.bouami.danecreteil2017_cloud.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bouami.danecreteil2017_cloud.Models.Personnel;
import com.bouami.danecreteil2017_cloud.R;

/**
 * Created by mbouami on 16/09/2017.
 */

public class PersonnelViewHolder extends RecyclerView.ViewHolder {
    public final TextView mNomView;
    public final TextView mStatutView;
    public final ImageView mListePersonnelView;
    public final LinearLayout mZoneReferencePersonnel;
    public Personnel mItem;
    public final View mView;

    public PersonnelViewHolder(View itemView) {
        super(itemView);
        mNomView = (TextView) itemView.findViewById(R.id.nom);
        mStatutView = (TextView) itemView.findViewById(R.id.statut);
        mListePersonnelView = (ImageView) itemView.findViewById(R.id.detail);
        mZoneReferencePersonnel = (LinearLayout) itemView.findViewById(R.id.zone_reference_personnel);
        mView = itemView;
    }

    public void bindToPersonnel(Personnel personnel,View.OnClickListener starClickListener) {
        mNomView.setText(personnel.getGenre() + " " + personnel.getPrenom() + " " + personnel.getNom());
        mStatutView.setText(personnel.getStatut());
        mZoneReferencePersonnel.setOnClickListener(starClickListener);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mNomView.getText() + "'";
    }

}

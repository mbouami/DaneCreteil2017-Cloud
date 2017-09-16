package com.bouami.danecreteil2017_cloud.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.R;

/**
 * Created by mbouami on 15/09/2017.
 */

public class AnimateurViewHolder extends RecyclerView.ViewHolder  {
    public final TextView mNomView;
    public final TextView mTelView;
    public final TextView mEmailView;
    public final ImageView mPhotoView;
    public final ImageView mListeEtabsView;
    public final LinearLayout mZoneReferenceAnimateur;
    public Animateur mItem;
    public final View mView;

    public AnimateurViewHolder(View itemView) {
        super(itemView);
        mNomView = (TextView) itemView.findViewById(R.id.nom);
        mTelView = (TextView) itemView.findViewById(R.id.tel);
        mEmailView = (TextView) itemView.findViewById(R.id.email);
        mPhotoView = (ImageView) itemView.findViewById(R.id.photo);
        mListeEtabsView = (ImageView) itemView.findViewById(R.id.detail);
        mZoneReferenceAnimateur = (LinearLayout) itemView.findViewById(R.id.zone_reference_animateur);
        mView = itemView;
    }

    public void bindToAnimateur(Animateur animateur,View.OnClickListener starClickListener) {
        mNomView.setText(animateur.getGenre() + " " + animateur.getPrenom() + " " + animateur.getNom());
        mTelView.setText(animateur.getTel());
        mEmailView.setText(animateur.getEmail());
        mZoneReferenceAnimateur.setOnClickListener(starClickListener);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mNomView.getText() + "'";
    }

}

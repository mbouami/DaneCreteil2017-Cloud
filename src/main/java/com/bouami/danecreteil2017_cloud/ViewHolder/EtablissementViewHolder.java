package com.bouami.danecreteil2017_cloud.ViewHolder;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;

/**
 * Created by mbouami on 16/09/2017.
 */

public class EtablissementViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "EtablissementViewHolder";
    public final TextView mNomView;
    public final TextView mTelView;
    public final TextView mFaxView;
    public final TextView mEmailView;
    public final TextView mAdresseView;
    public final TextView mVilleView;
    public final ImageView mListePersonnelView;
    public final LinearLayout mZoneReferenceEtablissement;
    public Etablissement mItem;
    public final View mView;

    @Override
    public String toString() {
        return super.toString() + " '" + mNomView.getText() + "'";
    }

    public EtablissementViewHolder(View itemView) {
        super(itemView);
        mNomView = (TextView) itemView.findViewById(R.id.nom);
        mTelView = (TextView) itemView.findViewById(R.id.tel);
        mFaxView = (TextView) itemView.findViewById(R.id.fax);
        mEmailView = (TextView) itemView.findViewById(R.id.email);
        mAdresseView = (TextView) itemView.findViewById(R.id.adresse);
        mVilleView = (TextView) itemView.findViewById(R.id.ville);
        mListePersonnelView = (ImageView) itemView.findViewById(R.id.detail);
        mZoneReferenceEtablissement = (LinearLayout) itemView.findViewById(R.id.zone_reference_etablissement);
        mView = itemView;
    }

    public void bindToEtablissement(Cursor cursor, View.OnClickListener starClickListener) {
        String nometab = cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.EtablissementEntry.COLUMN_TYPE))
                        +" "+cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.EtablissementEntry.COLUMN_NOM));
        String teletab = "Tél : "+cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.EtablissementEntry.COLUMN_TEL)) ;
        String faxetab = "Fax : "+cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.EtablissementEntry.COLUMN_TEL));
        String emailetab = cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.EtablissementEntry.COLUMN_EMAIL));
        String adresseetab = cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.EtablissementEntry.COLUMN_ADRESSE));
        String ville = cursor.getString(cursor.getColumnIndexOrThrow("CPVILLE"))+" "+
                cursor.getString(cursor.getColumnIndexOrThrow("NOMVILLE"));
        mNomView.setText(nometab);
        mTelView.setText(teletab);
        mFaxView.setText(faxetab);
        mEmailView.setText(emailetab);
        mAdresseView.setText(adresseetab);
        mVilleView.setText(ville);
        mZoneReferenceEtablissement.setOnClickListener(starClickListener);
    }

}

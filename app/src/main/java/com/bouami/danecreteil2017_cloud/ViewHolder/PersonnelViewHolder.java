package com.bouami.danecreteil2017_cloud.ViewHolder;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bouami.danecreteil2017_cloud.MainActivity;
import com.bouami.danecreteil2017_cloud.Models.Personnel;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;

/**
 * Created by mbouami on 16/09/2017.
 */

public class PersonnelViewHolder extends RecyclerView.ViewHolder {
    public final TextView mNomView;
    public final TextView mStatutView;
    public final TextView mTelView;
    public final TextView mEmailView;
    public final TextView mEtablissementView;
    //    public final ImageView mListePersonnelView;
    public final LinearLayout mZoneReferencePersonnel;
    public final View mView;
    public final ImageButton mMenuPersonnel;

    public PersonnelViewHolder(View itemView) {
        super(itemView);
        mNomView = (TextView) itemView.findViewById(R.id.nom);
        mStatutView = (TextView) itemView.findViewById(R.id.statut);
        mTelView = (TextView) itemView.findViewById(R.id.tel);
        mEmailView = (TextView) itemView.findViewById(R.id.email);
        mEtablissementView = (TextView) itemView.findViewById(R.id.etablissement);
//        mListePersonnelView = (ImageView) itemView.findViewById(R.id.detail);
        mZoneReferencePersonnel = (LinearLayout) itemView.findViewById(R.id.zone_reference_personnel);
        mMenuPersonnel = (ImageButton) itemView.findViewById(R.id.liste_operations);
        mView = itemView;
    }

    public void bindToPersonnel(Cursor cursor, View.OnClickListener starClickListener) {
        String etablissement = "Etablissement : "+cursor.getString(cursor.getColumnIndexOrThrow("NOMETABLISSEMENT"))+" ("+
                cursor.getString(cursor.getColumnIndexOrThrow("RNEETABLISSEMENT"))+" - "+cursor.getString(cursor.getColumnIndexOrThrow("NOMVILLE"))+")";
        String civiliteperso = DaneContract.getValueFromCursor(cursor,"CIVILITE");
//                cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.CiviliteEntry.COLUMN_CIVILITE_NOM));
        String nomperso = cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.PersonnelEntry.COLUMN_NOM));
        String prenomperso = cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.PersonnelEntry.COLUMN_PRENOM));
        mNomView.setText(civiliteperso+" "+nomperso+" "+prenomperso);
        if (DaneContract.getValueFromCursor(cursor,DaneContract.PersonnelEntry.COLUMN_SYNCHRONISER).equals("0")) {
            mNomView.setTextColor(Color.RED);
        }
        mStatutView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.PersonnelEntry.COLUMN_STATUT)));
        mTelView.setText("Tél : "+cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.PersonnelEntry.COLUMN_TEL)));
        mEmailView.setText("Email : "+cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.PersonnelEntry.COLUMN_EMAIL)));
        mEtablissementView.setText(etablissement);

        mZoneReferencePersonnel.setOnClickListener(starClickListener);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mNomView.getText() + "'";
    }

}
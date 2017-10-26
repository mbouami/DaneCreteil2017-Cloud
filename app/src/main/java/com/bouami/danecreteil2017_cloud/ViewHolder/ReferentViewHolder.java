package com.bouami.danecreteil2017_cloud.ViewHolder;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bouami.danecreteil2017_cloud.MainActivity;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.Models.Referent;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;

/**
 * Created by Mohammed on 30/09/2017.
 */

public class ReferentViewHolder extends RecyclerView.ViewHolder {
    public final TextView mNomView;
    public final TextView mStatutView;
    public final TextView mTelView;
    public final TextView mEmailView;
    public final TextView mDisciplineView;
    public final TextView mEtablissementView;
    //    public final ImageView mListePersonnelView;
    public final LinearLayout mZoneReferenceReferent;
    public Referent mItem;
    public final View mView;

    public ReferentViewHolder(View itemView) {
        super(itemView);
        mNomView = (TextView) itemView.findViewById(R.id.nom);
        mStatutView = (TextView) itemView.findViewById(R.id.statut);
        mTelView = (TextView) itemView.findViewById(R.id.tel);
        mEmailView = (TextView) itemView.findViewById(R.id.email);
        mDisciplineView = (TextView) itemView.findViewById(R.id.discipline);
        mEtablissementView = (TextView) itemView.findViewById(R.id.etablissement);
//        mListePersonnelView = (ImageView) itemView.findViewById(R.id.detail);
        mZoneReferenceReferent = (LinearLayout) itemView.findViewById(R.id.zone_reference_referent);
        mView = itemView;
    }

    public void bindToReferent(Cursor cursor, View.OnClickListener starClickListener) {
        String nomref = cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.ReferentEntry.COLUMN_NOM));
        String statutref = cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.ReferentEntry.COLUMN_STATUT));
        String etablissement = "Etablissement : "+cursor.getString(cursor.getColumnIndexOrThrow("NOMETABLISSEMENT"))+" ("+
                cursor.getString(cursor.getColumnIndexOrThrow("RNEETABLISSEMENT"))+" - "+cursor.getString(cursor.getColumnIndexOrThrow("NOMVILLE"))+")";
        String telref = "Tél : "+cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.ReferentEntry.COLUMN_TEL)) ;
        String emailref = "Email : "+cursor.getString(cursor.getColumnIndexOrThrow(DaneContract.ReferentEntry.COLUMN_EMAIL));
        String disciplref = "Discipline : "+cursor.getString(cursor.getColumnIndexOrThrow("NOMDISCIPLINE"));
        mNomView.setText(nomref);
        mStatutView.setText(statutref);
        mTelView.setText(telref);
        mEmailView.setText(emailref);
        mDisciplineView.setText(disciplref);
        mEtablissementView.setText(etablissement);
        mZoneReferenceReferent.setOnClickListener(starClickListener);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mNomView.getText() + "'";
    }
}

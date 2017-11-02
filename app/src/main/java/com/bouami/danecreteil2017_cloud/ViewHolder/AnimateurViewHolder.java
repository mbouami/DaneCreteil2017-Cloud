package com.bouami.danecreteil2017_cloud.ViewHolder;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;

/**
 * Created by mbouami on 15/09/2017.
 */

public class AnimateurViewHolder extends RecyclerView.ViewHolder {

    private final String TAG = "AnimateurViewHolder";
    public final TextView mNomView;
    public final TextView mTelView;
    public final TextView mEmailView;
    public final ImageView mPhotoView;
    public final LinearLayout mZoneReferenceAnimateur;
    public final View mView;
    public final ImageButton mMenuAnimateur;

    public AnimateurViewHolder(View itemView) {
        super(itemView);
        mNomView = (TextView) itemView.findViewById(R.id.nom);
        mTelView = (TextView) itemView.findViewById(R.id.tel);
        mEmailView = (TextView) itemView.findViewById(R.id.email);
        mPhotoView = (ImageView) itemView.findViewById(R.id.photo);
        mZoneReferenceAnimateur = (LinearLayout) itemView.findViewById(R.id.zone_reference_animateur);
        mMenuAnimateur = (ImageButton) itemView.findViewById(R.id.liste_operations);
        mView = itemView;
    }

    public void bindToAnimateur(Cursor cursor, View.OnClickListener starClickListener) {
        String civiliteanimateur  = DaneContract.getValueFromCursor(cursor,"CIVILITE");
        String nomanimateur  = DaneContract.getValueFromCursor(cursor,DaneContract.AnimateurEntry.COLUMN_NOM);
        String prenomanimateur  = DaneContract.getValueFromCursor(cursor,DaneContract.AnimateurEntry.COLUMN_PRENOM);
        String telanimateur = DaneContract.getValueFromCursor(cursor,DaneContract.AnimateurEntry.COLUMN_TEL);
        String emailanimateur = DaneContract.getValueFromCursor(cursor,DaneContract.AnimateurEntry.COLUMN_EMAIL);
        Bitmap photoanim = null;
        if (cursor.getBlob(cursor.getColumnIndex(DaneContract.AnimateurEntry.COLUMN_PHOTO)) != null){
            final byte[] imageanim = cursor.getBlob(cursor.getColumnIndex(DaneContract.AnimateurEntry.COLUMN_PHOTO));
            photoanim = BitmapFactory.decodeByteArray(imageanim, 0, imageanim.length);
        }
        mNomView.setText(civiliteanimateur+" "+nomanimateur+" "+prenomanimateur);
        mTelView.setText(telanimateur);
        mEmailView.setText(emailanimateur);
        if (photoanim!=null) mPhotoView.setImageBitmap(photoanim);
//        mZoneReferenceAnimateur.setOnClickListener(starClickListener);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mNomView.getText() + "'";
    }
}
package com.bouami.danecreteil2017_cloud.Adapter;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017_cloud.Models.Referent;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.ReferentViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohammed on 30/09/2017.
 */

public class ReferentsRecyclerViewAdapter extends MyRecycleAdapter<Referent,ReferentViewHolder> {

    private Cursor mCursor;
    private final String TAG = "ReferentsRecyclerViewAdapter";
    private FloatingActionButton mailreferent, phonereferent, addreferent, deletereferent, editreferent;
    private int ligneselectionnee = 0;
    Boolean mModifier;

    public ReferentsRecyclerViewAdapter(Class<Referent> mModelClass, @LayoutRes int mModelLayout, Class<ReferentViewHolder> mViewHolderClass,
                                        Cursor mcursor,Boolean modifier) {
        super(mModelClass, mModelLayout, mViewHolderClass, mcursor);
        mModifier = modifier;
        swapCursor(mcursor);
    }

    @Override
    public ReferentViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.include_referent_references, parent, false);
        mailreferent = (FloatingActionButton) parent.getRootView().findViewById(R.id.mailreferent);
        mailreferent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCursor.moveToPosition(ligneselectionnee);
                final String emailreferent = mCursor.getString(mCursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_EMAIL));
                view.getContext().startActivity(DaneContract.createMailIntent(emailreferent));
            }
        });
        phonereferent = (FloatingActionButton) parent.getRootView().findViewById(R.id.phonereferent);
        phonereferent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCursor.moveToPosition(ligneselectionnee);
                final String telreferent = mCursor.getString(mCursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_TEL));
                view.getContext().startActivity(DaneContract.createPhoneIntent(telreferent));

            }
        });
        deletereferent = (FloatingActionButton) parent.getRootView().findViewById(R.id.deletereferent);
        editreferent = (FloatingActionButton) parent.getRootView().findViewById(R.id.editreferent);

//        addreferent = (FloatingActionButton) parent.getRootView().findViewById(R.id.addreferent);
//        deletereferent.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("LongLogTag")
//            @Override
//            public void onClick(View view) {
////                mCursor.moveToPosition(ligneselectionnee);
//                final String idreferent = mCursor.getString(mCursor.getColumnIndex(DaneContract.ReferentEntry._ID));
//                final String idbasereferent = mCursor.getString(mCursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_REFERENT_ID));
//                final String nomreferent = mCursor.getString(mCursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_NOM));
//                Log.d(TAG, "Suppression du référent : "+nomreferent+"("+idreferent+"-"+idbasereferent+")");
//                JSONObject jsonreferent = new JSONObject();
//                try {
//                    jsonreferent.put("id",idreferent);
//                    jsonreferent.put("idbase",idbasereferent);
//                    DaneContract.deleteReferent(parent.getRootView().getContext(),jsonreferent);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
////                Log.d(TAG, "setOnClickListener : "+DetailEtablissementActivity.mEtablissementId);
////                Intent intent = new Intent(view.getContext(), NewReferentActivity.class);
////                intent.putExtra(NewReferentActivity.EXTRA_ETABLISSEMENT_ID, DetailEtablissementActivity.mEtablissementId);
////                view.getContext().startActivity(intent);
//            }
//        });
//        editreferent.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("LongLogTag")
//            @Override
//            public void onClick(View view) {
////                mCursor.moveToPosition(ligneselectionnee);
//                final String idreferent = mCursor.getString(mCursor.getColumnIndex(DaneContract.ReferentEntry._ID));
//                final String nomreferent = mCursor.getString(mCursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_NOM));
//                Log.d(TAG, "edition du référent : "+nomreferent+"("+idreferent+")");
////                Log.d(TAG, "setOnClickListener : "+DetailEtablissementActivity.mEtablissementId);
////                Intent intent = new Intent(view.getContext(), NewReferentActivity.class);
////                intent.putExtra(NewReferentActivity.EXTRA_ETABLISSEMENT_ID, DetailEtablissementActivity.mEtablissementId);
////                view.getContext().startActivity(intent);
//            }
//        });
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void populateViewHolder(ReferentViewHolder viewHolder, final Cursor cursor, final int position) {
        viewHolder.bindToReferent(cursor,new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                ligneselectionnee = position;
                mCursor = cursor;
                cursor.moveToPosition(position);
                String emailreferent = cursor.getString(cursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_EMAIL));
                String telreferent = cursor.getString(cursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_TEL));
                if (mailreferent.getVisibility()==View.INVISIBLE && !emailreferent.equals("")) {
                    mailreferent.setVisibility(View.VISIBLE);
                }
                if (phonereferent.getVisibility()==View.INVISIBLE && !telreferent.equals("")) {
                    phonereferent.setVisibility(View.VISIBLE);
                }
                if (mModifier) {
                    deletereferent.setVisibility(View.VISIBLE);
                    editreferent.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

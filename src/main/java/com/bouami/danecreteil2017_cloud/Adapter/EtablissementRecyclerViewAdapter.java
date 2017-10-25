package com.bouami.danecreteil2017_cloud.Adapter;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017_cloud.DetailEtablissementActivity;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.EtablissementViewHolder;

/**
 * Created by mbouami on 16/09/2017.
 */

public class EtablissementRecyclerViewAdapter extends MyRecycleAdapter<Etablissement,EtablissementViewHolder> {

    private static final String TAG = "EtablissementRecyclerViewAdapter";
    private Cursor mCursor;
    private FloatingActionButton mailetablissement;
    private FloatingActionButton phoneetablissement;
    private FloatingActionButton addreferentetablissement;
    private int ligneselectionnee = 0;

//    private boolean mDataValid;
//    private int mRowIDColumn;

    public EtablissementRecyclerViewAdapter(Class<Etablissement> mModelClass, @LayoutRes int mModelLayout, Class<EtablissementViewHolder> mViewHolderClass,
                                            Cursor mcursor) {
        super(mModelClass, mModelLayout, mViewHolderClass, mcursor);
        swapCursor(mcursor);
    }

    @Override
    public EtablissementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mailetablissement = (FloatingActionButton) parent.getRootView().findViewById(R.id.mailetablissement);
        mailetablissement.setVisibility(View.INVISIBLE);
        mailetablissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCursor.moveToPosition(ligneselectionnee);
                String emailetab = mCursor.getString(mCursor.getColumnIndex(DaneContract.EtablissementEntry.COLUMN_EMAIL));
                view.getContext().startActivity(DaneContract.createMailIntent(emailetab));
            }
        });
        phoneetablissement = (FloatingActionButton) parent.getRootView().findViewById(R.id.phoneetablissement);
        phoneetablissement.setVisibility(View.INVISIBLE);
        phoneetablissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCursor.moveToPosition(ligneselectionnee);
                String teletab = mCursor.getString(mCursor.getColumnIndex(DaneContract.EtablissementEntry.COLUMN_TEL));
                view.getContext().startActivity(DaneContract.createPhoneIntent(teletab));
            }
        });
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
    protected void populateViewHolder(EtablissementViewHolder viewHolder, final Cursor cursor, final int position) {
        // Set click listener for the whole post view
        viewHolder.mListePersonnelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch PostDetailActivity
                cursor.moveToPosition(position);
                Long idetab = Long.valueOf(DaneContract.getValueFromCursor(cursor,DaneContract.EtablissementEntry._ID));
                Intent intent = new Intent(v.getContext(), DetailEtablissementActivity.class);
                intent.putExtra(DetailEtablissementActivity.EXTRA_ETABLISSEMENT_ID,idetab );
                v.getContext().startActivity(intent);
            }
        });

        // Bind Post to ViewHolder, setting OnClickListener for the star button
        viewHolder.bindToEtablissement(cursor, new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                ligneselectionnee = position;
                mCursor = cursor;
                if (mailetablissement.getVisibility()==View.INVISIBLE) {
                    mailetablissement.setVisibility(View.VISIBLE);
                    phoneetablissement.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

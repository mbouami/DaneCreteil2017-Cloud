package com.bouami.danecreteil2017_cloud.Adapter;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017_cloud.DetailAnimateurActivity;
import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.AnimateurViewHolder;

/**
 * Created by mbouami on 15/09/2017.
 */

public class AnimateursRecyclerViewAdapter extends MyRecycleAdapter<Animateur,AnimateurViewHolder> {
    private Cursor mCursor;
    private final String TAG = "AnimateursRecyclerViewAdapter";
    private int ligneselectionnee = 0;
    private FloatingActionButton mailanimateur;
    private FloatingActionButton phoneanimateur;

//    private boolean mDataValid;
//    private int mRowIDColumn;

    public AnimateursRecyclerViewAdapter(Class<Animateur> mModelClass, @LayoutRes int mModelLayout, Class<AnimateurViewHolder> mViewHolderClass,
                                         Cursor mcursor) {
        super(mModelClass, mModelLayout, mViewHolderClass, mcursor);
        swapCursor(mcursor);
    }

    @Override
    public AnimateurViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.include_animateur_references, parent, false);
        mailanimateur = (FloatingActionButton) parent.getRootView().findViewById(R.id.mailanimateur);
        mailanimateur.setVisibility(View.INVISIBLE);
        mailanimateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCursor.moveToPosition(ligneselectionnee);
                String emailanim = mCursor.getString(mCursor.getColumnIndex(DaneContract.AnimateurEntry.COLUMN_EMAIL));
                view.getContext().startActivity(DaneContract.createMailIntent(emailanim));
            }
        });
        phoneanimateur = (FloatingActionButton) parent.getRootView().findViewById(R.id.phoneanimateur);
        phoneanimateur.setVisibility(View.INVISIBLE);
        phoneanimateur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCursor.moveToPosition(ligneselectionnee);
                String telanim = mCursor.getString(mCursor.getColumnIndex(DaneContract.AnimateurEntry.COLUMN_TEL));
                view.getContext().startActivity(DaneContract.createPhoneIntent(telanim));

            }
        });
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void populateViewHolder(AnimateurViewHolder viewHolder, final Cursor cursor, final int position) {
        viewHolder.mListeEtabsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToPosition(position);
                Long idanim = Long.valueOf(DaneContract.getValueFromCursor(cursor,DaneContract.AnimateurEntry._ID));
                Intent intent = new Intent(v.getContext(), DetailAnimateurActivity.class);
                intent.putExtra(DetailAnimateurActivity.EXTRA_ANIMATEUR_ID, idanim);
                v.getContext().startActivity(intent);
            }
        });
        // Bind Post to ViewHolder, setting OnClickListener for the star button
        viewHolder.bindToAnimateur(cursor, new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                ligneselectionnee = position;
                mCursor = cursor;
                if (mailanimateur.getVisibility()==View.INVISIBLE) {
                    mailanimateur.setVisibility(View.VISIBLE);
                }
                if (phoneanimateur.getVisibility()==View.INVISIBLE) {
                    phoneanimateur.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
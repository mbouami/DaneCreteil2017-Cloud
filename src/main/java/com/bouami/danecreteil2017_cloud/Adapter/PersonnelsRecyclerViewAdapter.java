package com.bouami.danecreteil2017_cloud.Adapter;

import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017_cloud.Models.Personnel;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.PersonnelViewHolder;

/**
 * Created by Mohammed on 20/09/2017.
 */

public class PersonnelsRecyclerViewAdapter extends MyRecycleAdapter<Personnel,PersonnelViewHolder> {
    private Cursor mCursor;
    private final String TAG = "AnimateursRecyclerViewAdapter";
    private int ligneselectionnee = 0;
    private FloatingActionButton mailpersonnel;
    private FloatingActionButton phonepersonnel;

//    private boolean mDataValid;
//    private int mRowIDColumn;

    public PersonnelsRecyclerViewAdapter(Class<Personnel> mModelClass, @LayoutRes int mModelLayout, Class<PersonnelViewHolder> mViewHolderClass,
                                         Cursor mcursor) {
        super(mModelClass, mModelLayout, mViewHolderClass, mcursor);
        swapCursor(mcursor);
    }

    @Override
    public PersonnelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.include_personnel_references, parent, false);
        mailpersonnel = (FloatingActionButton) parent.getRootView().findViewById(R.id.mailpersonnel);
        mailpersonnel.setVisibility(View.INVISIBLE);
        mailpersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCursor.moveToPosition(ligneselectionnee);
                String emailpersonnel = mCursor.getString(mCursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_EMAIL));
                view.getContext().startActivity(DaneContract.createMailIntent(emailpersonnel));
            }
        });
        phonepersonnel = (FloatingActionButton) parent.getRootView().findViewById(R.id.phonepersonnel);
        phonepersonnel.setVisibility(View.INVISIBLE);
        phonepersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCursor.moveToPosition(ligneselectionnee);
                String telpersonnel = mCursor.getString(mCursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_TEL));
                view.getContext().startActivity(DaneContract.createPhoneIntent(telpersonnel));

            }
        });
        return super.onCreateViewHolder(parent, viewType);
    }


    @Override
    protected void populateViewHolder(PersonnelViewHolder viewHolder, final Cursor cursor, final int position) {
//        viewHolder.mListePersonnelView.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("LongLogTag")
//            @Override
//            public void onClick(View v) {
//                // Launch PostDetailActivity
//                Log.d(TAG, "setOnClickListener:" + model.getNom());
//            }
//        });
        // Bind Post to ViewHolder, setting OnClickListener for the star button
        viewHolder.bindToPersonnel(cursor,new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                ligneselectionnee = position;
                mCursor = cursor;
                cursor.moveToPosition(position);
                String emailpersonnel = cursor.getString(cursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_EMAIL));
                String telpersonnel = cursor.getString(cursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_TEL));
                if (mailpersonnel.getVisibility()==View.INVISIBLE && !emailpersonnel.equals("")) {
                    mailpersonnel.setVisibility(View.VISIBLE);
                }
                if (phonepersonnel.getVisibility()==View.INVISIBLE && !telpersonnel.equals("")) {
                    phonepersonnel.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

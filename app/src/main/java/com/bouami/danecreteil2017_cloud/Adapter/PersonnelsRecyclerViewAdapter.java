package com.bouami.danecreteil2017_cloud.Adapter;

import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.bouami.danecreteil2017_cloud.Fragments.PersonnelListFragment;
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
    private FloatingActionButton mailpersonnel, phonepersonnel, editpersonnel;
    Boolean mModifier;
    private int mPosition = 0;

//    private boolean mDataValid;
//    private int mRowIDColumn;

    public PersonnelsRecyclerViewAdapter(Class<Personnel> mModelClass, @LayoutRes int mModelLayout, Class<PersonnelViewHolder> mViewHolderClass,
                                         Cursor mcursor,Boolean modifier) {
        super(mModelClass, mModelLayout, mViewHolderClass, mcursor);
        mModifier = modifier;
        swapCursor(mcursor);
    }

    @Override
    public PersonnelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.include_personnel_references, parent, false);
//        editpersonnel = (FloatingActionButton) parent.getRootView().findViewById(R.id.editpersonnel);
//        mailpersonnel = (FloatingActionButton) parent.getRootView().findViewById(R.id.mailpersonnel);
//        mailpersonnel.setVisibility(View.INVISIBLE);
//        mailpersonnel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCursor.moveToPosition(ligneselectionnee);
//                String emailpersonnel = mCursor.getString(mCursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_EMAIL));
//                view.getContext().startActivity(DaneContract.createMailIntent(emailpersonnel));
//            }
//        });
//        phonepersonnel = (FloatingActionButton) parent.getRootView().findViewById(R.id.phonepersonnel);
//        phonepersonnel.setVisibility(View.INVISIBLE);
//        phonepersonnel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCursor.moveToPosition(ligneselectionnee);
//                String telpersonnel = mCursor.getString(mCursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_TEL));
//                view.getContext().startActivity(DaneContract.createPhoneIntent(telpersonnel));
//
//            }
//        });
        return super.onCreateViewHolder(parent, viewType);
    }


    @Override
    protected void populateViewHolder(final PersonnelViewHolder viewHolder, final Cursor cursor, final int position) {
        mPosition = position;
        // Bind Post to ViewHolder, setting OnClickListener for the star button
        viewHolder.mMenuPersonnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Cursor personnelcursor = DaneContract.getPersonnelFromId(view.getContext(),viewHolder.getItemId());
                String emailpersonnel = personnelcursor.getString(personnelcursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_EMAIL));
                String telpersonnel = personnelcursor.getString(personnelcursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_TEL));
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.personnel_menu, popup.getMenu());
//                popup.getMenu().getItem(0).setIcon(R.drawable.ic_email);
//                popup.getMenu().getItem(1).setIcon(R.drawable.ic_phone);
                if (telpersonnel.equals("")) popup.getMenu().getItem(0).setEnabled(false);
                if (emailpersonnel.equals("")) popup.getMenu().getItem(1).setEnabled(false);
                if (!mModifier) popup.getMenu().getItem(2).setEnabled(false);
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.personnel_tel:
                                String telpersonnel = personnelcursor.getString(personnelcursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_TEL));
                                view.getContext().startActivity(DaneContract.createPhoneIntent(telpersonnel));
                                return true;
                            case R.id.personnel_email:
                                String emailpersonnel = personnelcursor.getString(personnelcursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_EMAIL));
                                view.getContext().startActivity(DaneContract.createMailIntent(emailpersonnel));
                                return true;
                            case R.id.personnel_modifier:
                                final Long idpersonnel = personnelcursor.getLong(personnelcursor.getColumnIndex(DaneContract.PersonnelEntry._ID));
                                final String nompersonnel = DaneContract.getValueFromCursor(personnelcursor,"CIVILITE") +
                                        " "+ DaneContract.getValueFromCursor(personnelcursor,DaneContract.PersonnelEntry.COLUMN_NOM)+
                                        " "+ DaneContract.getValueFromCursor(personnelcursor,DaneContract.PersonnelEntry.COLUMN_PRENOM);
                                DaneContract.EditerPersonnelDialog(PersonnelListFragment.mfragmentManager,idpersonnel,"Modiofication des informations de : "+nompersonnel, "edit_personnel");
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
        viewHolder.bindToPersonnel(cursor,new View.OnClickListener() {
            @Override
            public void onClick(View starView) {
                ligneselectionnee = mPosition;
                mCursor = cursor;
//                cursor.moveToPosition(position);
//                String emailpersonnel = cursor.getString(cursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_EMAIL));
//                String telpersonnel = cursor.getString(cursor.getColumnIndex(DaneContract.PersonnelEntry.COLUMN_TEL));
//                if (mailpersonnel.getVisibility()==View.INVISIBLE && !emailpersonnel.equals("")) {
//                    mailpersonnel.setVisibility(View.VISIBLE);
//                }
//                if (phonepersonnel.getVisibility()==View.INVISIBLE && !telpersonnel.equals("")) {
//                    phonepersonnel.setVisibility(View.VISIBLE);
//                }
//                if (mModifier) {
//                    editpersonnel.setVisibility(View.VISIBLE);
//                }
            }
        });
    }
}
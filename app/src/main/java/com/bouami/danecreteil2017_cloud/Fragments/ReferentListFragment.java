package com.bouami.danecreteil2017_cloud.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017_cloud.Adapter.ReferentsRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Models.Referent;
import com.bouami.danecreteil2017_cloud.NewReferentActivity;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.ReferentViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohammed on 30/09/2017.
 */

public class ReferentListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "ReferentListFragment";
    private RecyclerView mRecycler;
    private ReferentsRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mManager;
    private String mDepartement;
    private Long mEtablissementId;
    private Cursor mcursor;

    private FloatingActionButton addreferent, deletereferent,editreferent;

    private Uri mUri;
    private static final int REFERENTS_PAR_DEPARTEMENT_LOADER = 0;

    public static ReferentListFragment newInstance(String champ, Long id) {
        ReferentListFragment f = new ReferentListFragment();
        Bundle args = new Bundle();
        if (id==0){
            args.putString("depart", champ);
        } else {
            args.putLong("idetab",id);
        }
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_referent, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.referent_list);
        mRecycler.setHasFixedSize(true);
        addreferent = (FloatingActionButton) rootView.findViewById(R.id.addreferent);
        deletereferent = (FloatingActionButton) rootView.getRootView().findViewById(R.id.deletereferent);
        editreferent = (FloatingActionButton) rootView.getRootView().findViewById(R.id.editreferent);

        if (mEtablissementId!=0) {
            addreferent.setVisibility(View.VISIBLE);
        }
        addreferent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaneContract.AjoutReferentDialog(getFragmentManager(),mEtablissementId,"Ajout d'un nouveau référent","Ajout_Referent");
            }
        });
        deletereferent.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                final String nomreferent = mcursor.getString(mcursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_NOM));
                final String idreferent = mcursor.getString(mcursor.getColumnIndex(DaneContract.ReferentEntry._ID));
                final String idbasereferent = mcursor.getString(mcursor.getColumnIndex(DaneContract.ReferentEntry.COLUMN_REFERENT_ID));
//                DaneContract.confirmationOperation(getFragmentManager(),mcursor,"Voulez-vous supprimer le référent " +nomreferent,"suppression_referent");
                DaneContract.DeleteReferentDialog(getFragmentManager(),"Suppression du référent","Voulez-vous supprimer le référent " +nomreferent,idreferent,idbasereferent,"suppression_referent");
            }
        });

        editreferent.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                final Long idreferent = mcursor.getLong(mcursor.getColumnIndex(DaneContract.ReferentEntry._ID));
                DaneContract.EditerReferentDialog(getFragmentManager(),idreferent,"Edition du référent", "edit_referent");
//                Log.d(TAG, "setOnClickListener : "+DetailEtablissementActivity.mEtablissementId);
//                Intent intent = new Intent(view.getContext(), NewReferentActivity.class);
//                intent.putExtra(NewReferentActivity.EXTRA_ETABLISSEMENT_ID, DetailEtablissementActivity.mEtablissementId);
//                view.getContext().startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(REFERENTS_PAR_DEPARTEMENT_LOADER, null, this);
        mManager = new LinearLayoutManager(getActivity());
//        mManager.setReverseLayout(true);
//        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        if (mEtablissementId!=0) {
            addreferent.setVisibility(View.VISIBLE);
            mAdapter = new ReferentsRecyclerViewAdapter(Referent.class,R.layout.item_referent,ReferentViewHolder.class,mcursor,true);
        } else {
            mAdapter = new ReferentsRecyclerViewAdapter(Referent.class,R.layout.item_referent,ReferentViewHolder.class,mcursor,false);
        }
        mRecycler.setAdapter(mAdapter);
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mAdapter != null) {
//            mAdapter.cleanup();
//        }
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDepartement = getArguments() != null ? getArguments().getString("depart") : null;
        mEtablissementId = getArguments() != null ? getArguments().getLong("idetab") : 0;
        if (mEtablissementId!=0) {
            mUri = DaneContract.ReferentEntry.buildReferentsParIdEtab(String.valueOf(mEtablissementId),"etab");
        }
        if (mDepartement!=null) {
            mUri = DaneContract.ReferentEntry.buildReferentsParDepartement(mDepartement,"referent");
        }
    }

    public Cursor rechercherReferent(String constraint) {
        if (!constraint.equals("")) {
            Uri uri = DaneContract.ReferentEntry.buildReferentsParDepartementContenatLeNom(mDepartement,constraint,"rechercher");
            String[] selectionArgs = new String[]{"%" + constraint +"%",mDepartement};
            String sortOrder = " NOMVILLE ASC";
            String selection = DaneContract.ReferentEntry.TABLE_NAME+"." + DaneContract.ReferentEntry.COLUMN_NOM +
                    " like ? AND "+DaneContract.DepartementEntry.TABLE_NAME+"."+DaneContract.DepartementEntry.COLUMN_DEPARTEMENT_NOM+" = ?";
            return getContext().getContentResolver().query(uri,
                    DaneContract.REFERENTS_COLUMNS,
                    selection,
                    selectionArgs,
                    sortOrder,
                    null
            );
        } else {
            return mcursor;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menusearch = menu.findItem(R.id.rechercher);
        SearchView searchview = (SearchView) menusearch.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.d(TAG, "setOnQueryTextListener: onQueryTextSubmit " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.swapCursor(rechercherReferent(newText));
                return false;
            }
        });
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            CursorLoader cursorLoader = null;
            String sortOrder = " NOMVILLE ASC";
            if (mDepartement!=null) {
                cursorLoader = new CursorLoader(getActivity(),mUri,
                        DaneContract.REFERENTS_COLUMNS, "NOMDEPARTEMENT = ?",
                        new String[]{mDepartement},sortOrder);
            } else {
                cursorLoader = new CursorLoader(getActivity(),mUri,
                        DaneContract.REFERENTS_COLUMNS,null ,null,sortOrder);
            }
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mcursor = data;
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
package com.bouami.danecreteil2017_cloud.Fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;

import com.bouami.danecreteil2017_cloud.Adapter.EtablissementRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.EtablissementViewHolder;

/**
 * Created by mbouami on 16/09/2017.
 */

public class EtablissementListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "EtablissementListFragment";
    private EtablissementRecyclerViewAdapter mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FloatingActionButton mailetablissement;
    private FloatingActionButton phoneetablissement;
    private String mDepartement;
    private Long mAnimateurId;
    private Cursor mcursor;

    private Uri mUri;
    private static final int ETABLISSEMENTS_PAR_DEPARTEMENT_LOADER = 0;

    public static  EtablissementListFragment newInstance(String champ, Long id) {
        EtablissementListFragment f = new EtablissementListFragment();
        Bundle args = new Bundle();
        if (id==0){
            args.putString("depart", champ);
        } else {
            args.putLong("idanim",id);
        }
        f.setArguments(args);
        return f;

    }

//    private String getDepartementId(String nom) {
//        Uri DepartementUri = DaneContract.DepartementEntry.buildDepartementParNom(mDepartement);
//        Cursor departcursor = getContext().getContentResolver().query(DepartementUri,
//                DEPARTEMENT_COLUMNS,
//                null,
//                null,
//                null);
//        if (departcursor.moveToFirst()){
//            return departcursor.getString(departcursor.getColumnIndex(DaneContract.DepartementEntry._ID));
//        }
//        return null;
//    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDepartement = getArguments() != null ? getArguments().getString("depart") : null;
        mAnimateurId = getArguments() != null ? getArguments().getLong("idanim") : 0;
        if (mAnimateurId!=0) {
            mUri = DaneContract.AnimateurEntry.buildEtabParIdAnimateur(String.valueOf(mAnimateurId),"etab");
        }
        if (mDepartement!=null) {
            mUri = DaneContract.EtablissementEntry.buildEtablissementParDepartement(mDepartement,"depart");
        }
    }

    public Cursor rechercherEtablissement(String constraint) {
        if (!constraint.equals("")) {
            Uri uri = DaneContract.EtablissementEntry.buildEtablissementParDepartementContenatLeNom(mDepartement,constraint,"rechercher");
            String[] selectionArgs = new String[]{"%" + constraint +"%",mDepartement,"%" + constraint +"%",mDepartement};
//            String[] selectionArgs = new String[]{"%" + constraint +"%",mDepartement};
            String sortOrder = " NOMVILLE ASC";
            String selection = "( "+DaneContract.EtablissementEntry.TABLE_NAME+"." + DaneContract.EtablissementEntry.COLUMN_NOM +
                    " like ? AND "+DaneContract.DepartementEntry.TABLE_NAME+"."+DaneContract.DepartementEntry.COLUMN_DEPARTEMENT_NOM+
                    " = ? ) OR ( NOMVILLE like ? AND NOMDEPARTEMENT = ? )";
//            String selection = DaneContract.EtablissementEntry.TABLE_NAME+"." + DaneContract.EtablissementEntry.COLUMN_NOM +
//                    " like ? AND AND NOMDEPARTEMENT = ?";
            return getContext().getContentResolver().query(uri,
                    DaneContract.ETABLISSEMENT_COLUMNS,
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
                mAdapter.swapCursor(rechercherEtablissement(newText));
                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_etablissements, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.etablissement_list);
        mRecycler.setHasFixedSize(true);
        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ETABLISSEMENTS_PAR_DEPARTEMENT_LOADER, null, this);
        mManager = new LinearLayoutManager(getActivity());
//        mManager.setReverseLayout(true);
//        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
//        mcursor = null;
        mAdapter = new EtablissementRecyclerViewAdapter(Etablissement.class, R.layout.item_etablissement,
                EtablissementViewHolder.class, mcursor) {
        };
        mRecycler.setAdapter(mAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mAdapter != null) {
//            mAdapter.cleanup();
//        }
//    }

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
        if ( null != mUri) {
            CursorLoader cursorLoader = null;
            String sortOrder = " NOMVILLE ASC";
            if (mAnimateurId==0) {
                cursorLoader = new CursorLoader(getActivity(),mUri,
                        DaneContract.ETABLISSEMENT_COLUMNS, "NOMDEPARTEMENT = ?",
                        new String[]{mDepartement},sortOrder);
            } else {
                cursorLoader = new CursorLoader(getActivity(),mUri,
                        DaneContract.ETABLISSEMENT_COLUMNS,null ,null,sortOrder);
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
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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bouami.danecreteil2017_cloud.Adapter.PersonnelsRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Models.Personnel;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.PersonnelViewHolder;

/**
 * Created by Mohammed on 20/09/2017.
 */

public class PersonnelListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "AnimateurListFragment";
    private RecyclerView mRecycler;
    private PersonnelsRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mManager;
    private String mDepartement;
    private Long mEtablissementId;
    private Cursor mcursor;
    private FloatingActionButton editpersonnel;
    private Uri mUri;
    private static final int PERSONNEL_PAR_DEPARTEMENT_LOADER = 0;

    public PersonnelListFragment() {
    }

    public static PersonnelListFragment newInstance(String champ, Long id) {
        PersonnelListFragment f = new PersonnelListFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_all_personnel, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.personnel_list);
        mRecycler.setHasFixedSize(true);
        editpersonnel = (FloatingActionButton) rootView.getRootView().findViewById(R.id.editpersonnel);
        editpersonnel.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                final Long idpersonnel = mcursor.getLong(mcursor.getColumnIndex(DaneContract.PersonnelEntry._ID));
                DaneContract.EditerPersonnelDialog(getFragmentManager(),idpersonnel,"Edition du personnel", "edit_personnel");
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(PERSONNEL_PAR_DEPARTEMENT_LOADER, null, this);
        mManager = new LinearLayoutManager(getActivity());
//        mManager.setReverseLayout(true);
//        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        if (mEtablissementId!=0) {
            mAdapter = new PersonnelsRecyclerViewAdapter(Personnel.class,R.layout.item_personnel,PersonnelViewHolder.class,mcursor,true);
        } else {
            mAdapter = new PersonnelsRecyclerViewAdapter(Personnel.class,R.layout.item_personnel,PersonnelViewHolder.class,mcursor,false);
        }
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDepartement = getArguments() != null ? getArguments().getString("depart") : null;
        mEtablissementId = getArguments() != null ? getArguments().getLong("idetab") : 0;
        if (mEtablissementId!=0) {
            mUri = DaneContract.PersonnelEntry.buildPersonnelParIdEtab(String.valueOf(mEtablissementId),"etab");
        }
        if (mDepartement!=null) {
            mUri = DaneContract.PersonnelEntry.buildPersonnelParDepartement(mDepartement,"personnel");
        }
    }

    public Cursor rechercherPersonnel(String constraint) {
        if (!constraint.equals("")) {
            Uri uri = DaneContract.PersonnelEntry.buildPersonnelParDepartementContenatLeNom(mDepartement,constraint,"rechercher");
            String[] selectionArgs = new String[]{"%" + constraint +"%",mDepartement};
            String sortOrder = " NOMVILLE ASC";
            String selection = DaneContract.PersonnelEntry.TABLE_NAME+"." + DaneContract.PersonnelEntry.COLUMN_NOM +
                    " like ? AND "+DaneContract.DepartementEntry.TABLE_NAME+"."+DaneContract.DepartementEntry.COLUMN_DEPARTEMENT_NOM+" =?";
            return getContext().getContentResolver().query(uri,
                    DaneContract.PERSONNEL_COLUMNS,
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
                mAdapter.swapCursor(rechercherPersonnel(newText));
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
            if (mEtablissementId==0) {
                cursorLoader = new CursorLoader(getActivity(),mUri,
                        DaneContract.PERSONNEL_COLUMNS, "NOMDEPARTEMENT = ?",
                        new String[]{mDepartement},sortOrder);
            } else {
                cursorLoader = new CursorLoader(getActivity(),mUri,
                        DaneContract.PERSONNEL_COLUMNS,null ,null,sortOrder);
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
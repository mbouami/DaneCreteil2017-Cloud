package com.bouami.danecreteil2017_cloud.Fragments;

import android.annotation.SuppressLint;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.bouami.danecreteil2017_cloud.Adapter.AnimateursRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.AnimateurViewHolder;

/**
 * Created by mbouami on 15/09/2017.
 */

public class AnimateurListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = AnimateurListFragment.class.getSimpleName();;
    private RecyclerView mRecycler;
    private AnimateursRecyclerViewAdapter mAnimsAdapter;
    private LinearLayoutManager mManager;
    private String mDepartement;
    private Cursor mcursor;

    private Uri mUri;

    private static final int ANIMATEURS_PAR_DEPARTEMENT_LOADER = 0;

    public static AnimateurListFragment newInstance(String departement) {
        AnimateurListFragment f = new AnimateurListFragment();
        Bundle args = new Bundle();
        args.putString("depart", departement);
        f.setArguments(args);
        return f;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mUri = DaneContract.AnimateurEntry.buildAnimateursParDepartement(mDepartement,"depart");
        View rootView = inflater.inflate(R.layout.fragment_all_animateurs, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.animateur_list);
        mRecycler.setHasFixedSize(true);
        registerForContextMenu(mRecycler);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ANIMATEURS_PAR_DEPARTEMENT_LOADER, null, this);
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        mAnimsAdapter = new AnimateursRecyclerViewAdapter(Animateur.class,R.layout.item_animateur,AnimateurViewHolder.class,mcursor);
        mRecycler.setAdapter(mAnimsAdapter);
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
////        Log.d(TAG, "onDestroyView ");
//        if (mAnimsAdapter != null) {
//            mAnimsAdapter.cleanup();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d(TAG, "onDestroy ");
////        if (mAnimsAdapter != null) {
////            mAnimsAdapter.cleanup();
////        }
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDepartement = getArguments() != null ? getArguments().getString("depart") : "";
        mcursor = null;
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
                Cursor searchcursor = DaneContract.rechercherAnimateurs(getContext(),newText,mDepartement);
                mAnimsAdapter.swapCursor(searchcursor==null?mcursor:searchcursor);
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
        if ( null != mUri && mDepartement!=null ) {
            String sortOrder = DaneContract.AnimateurEntry.TABLE_NAME+"."+DaneContract.AnimateurEntry.COLUMN_NOM + " ASC";
            CursorLoader cursorLoader = new CursorLoader(getActivity(),mUri,
                    DaneContract.ANIM_COLUMNS,"NOMDEPARTEMENT = ?",
                    new String[]{mDepartement},sortOrder);
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mcursor = data;
        mAnimsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAnimsAdapter.swapCursor(null);
    }

}
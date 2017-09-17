package com.bouami.danecreteil2017_cloud.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.bouami.danecreteil2017_cloud.Adapter.AnimateursRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.AnimateurViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbouami on 15/09/2017.
 */

public class AnimateurListFragment extends Fragment {
    private static final String TAG = "AnimateurListFragment";
    private List<Animateur> listedesanimateurs;
    private RecyclerView mRecycler;
    private AnimateursRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mManager;


    public AnimateurListFragment() {
    }

    public AnimateurListFragment(List<Animateur> listeanim) {
        this.listedesanimateurs = listeanim;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_animateurs, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.animateur_list);
        mRecycler.setHasFixedSize(true);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        mAdapter = new AnimateursRecyclerViewAdapter(Animateur.class,R.layout.item_animateur,AnimateurViewHolder.class,listedesanimateurs);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menusearch = menu.findItem(R.id.animateurrechercher);
        SearchView searchview = (SearchView) menusearch.getActionView();
//        searchView.setIconifiedByDefault(false);

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener (){
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.d(TAG, "setOnQueryTextListener: onQueryTextSubmit " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Animateur> listedesanimateursfiltre = new ArrayList<Animateur>();
                for (Animateur anim : listedesanimateurs) {
                    if (anim.getNom().toLowerCase().contains(newText.toLowerCase()) ||
                            anim.getPrenom().toLowerCase().contains(newText.toLowerCase())) {
                        listedesanimateursfiltre.add(anim);
                    }
                }
//                Log.d(TAG, "onQueryTextChange " + newText + listedesanimateursfiltre.size()+ "-"+listedesanimateurs.size());
                mAdapter = new AnimateursRecyclerViewAdapter(Animateur.class,
                        R.layout.item_animateur,AnimateurViewHolder.class,listedesanimateursfiltre);
                mRecycler.setAdapter(mAdapter);
                return false;
            }
        });
    }


}

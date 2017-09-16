package com.bouami.danecreteil2017_cloud.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

import com.bouami.danecreteil2017_cloud.Adapter.EtablissementRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.EtablissementViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbouami on 16/09/2017.
 */

public class EtablissementListFragment extends Fragment {
    private static final String TAG = "EtablissementListFragment";

    private List<Etablissement> listedesetablissements;
    private EtablissementRecyclerViewAdapter mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FloatingActionButton mailetablissement;
    private FloatingActionButton phoneetablissement;
    private Etablissement etablissementselectionne;


    public EtablissementListFragment() {
    }

    public EtablissementListFragment(List<Etablissement> letablissements) {
        this.listedesetablissements = letablissements;
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
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener (){

            @Override
            public boolean onQueryTextSubmit(String query) {
//                Log.d(TAG, "setOnQueryTextListener: onQueryTextSubmit " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Etablissement> listedesetablissementsfiltre = new ArrayList<Etablissement>();
                for (Etablissement etab : listedesetablissements) {
                    if (etab.getNom().toLowerCase().contains(newText.toLowerCase())) {
                        listedesetablissementsfiltre.add(etab);
                    }
                }
                mAdapter = new EtablissementRecyclerViewAdapter(Etablissement.class,
                        R.layout.item_etablissement, EtablissementViewHolder.class, listedesetablissementsfiltre) {
                };
                mRecycler.setAdapter(mAdapter);
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
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);
        mAdapter = new EtablissementRecyclerViewAdapter(Etablissement.class, R.layout.item_etablissement,
                EtablissementViewHolder.class, listedesetablissements) {
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }
}

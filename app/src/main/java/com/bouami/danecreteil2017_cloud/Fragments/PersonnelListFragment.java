package com.bouami.danecreteil2017_cloud.Fragments;

import android.annotation.SuppressLint;
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

import com.bouami.danecreteil2017_cloud.Adapter.PersonnelsRecyclerViewAdapter;
import com.bouami.danecreteil2017_cloud.Models.Personnel;
import com.bouami.danecreteil2017_cloud.R;
import com.bouami.danecreteil2017_cloud.ViewHolder.PersonnelViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mbouami on 20/09/2017.
 */

public class PersonnelListFragment extends Fragment {

    private static final String TAG = "AnimateurListFragment";
    private List<Personnel> listedespersonnels;
    private RecyclerView mRecycler;
    private PersonnelsRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mManager;

    public PersonnelListFragment() {
    }

    @SuppressLint("ValidFragment")
    public PersonnelListFragment(List<Personnel> listedesperso) {
        this.listedespersonnels = listedesperso;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_all_personnel, container, false);
        mRecycler = (RecyclerView) rootView.findViewById(R.id.personnel_list);
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
        mAdapter = new PersonnelsRecyclerViewAdapter(Personnel.class,R.layout.item_personnel,PersonnelViewHolder.class,listedespersonnels);
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
                List<Personnel> listedespersonnelsfiltre = new ArrayList<Personnel>();
                for (Personnel perso : listedespersonnels) {
                    if (perso.getNom().toLowerCase().contains(newText.toLowerCase()) ||
                            perso.getPrenom().toLowerCase().contains(newText.toLowerCase())) {
                        listedespersonnelsfiltre.add(perso);
                    }
                }
                mAdapter = new PersonnelsRecyclerViewAdapter(Personnel.class,
                        R.layout.item_personnel,PersonnelViewHolder.class,listedespersonnelsfiltre);
                mRecycler.setAdapter(mAdapter);
                return false;
            }
        });
    }

}

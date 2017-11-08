package com.bouami.danecreteil2017_cloud.Services;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mbouami on 08/11/2017.
 */

public class SynchrinisationService extends IntentService {

    public SynchrinisationService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Lancement de la synchronisation des réferents et du personnel", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Toast.makeText(this, "synchronisation en cours starting", Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        JSONObject jsondonneesasynchroniser = new JSONObject();
        try {
            jsondonneesasynchroniser.putOpt("referents",DaneContract.ReferentsASynchroniser(this));
            jsondonneesasynchroniser.putOpt("personnels",DaneContract.PersonnelASynchroniser(this));
            DaneContract.synchroniserDonnees(this,jsondonneesasynchroniser,null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

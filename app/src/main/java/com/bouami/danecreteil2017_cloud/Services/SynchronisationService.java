package com.bouami.danecreteil2017_cloud.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mbouami on 09/11/2017.
 */

public class SynchronisationService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
//        Toast.makeText(this, "Lancement de la synchronisation des réferents et du personnel", Toast.LENGTH_SHORT).show();
        synchroniserValues();
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        synchroniserValues();
        return null;
    }

    private void synchroniserValues() {
        JSONObject jsondonneesasynchroniser = new JSONObject();
        try {
            jsondonneesasynchroniser.putOpt("referents", DaneContract.ReferentsASynchroniser(this));
            jsondonneesasynchroniser.putOpt("personnels",DaneContract.PersonnelASynchroniser(this));
            DaneContract.synchroniserDonnees(this,jsondonneesasynchroniser,null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

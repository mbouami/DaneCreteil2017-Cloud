package com.bouami.danecreteil2017_cloud.Parametres;

import android.content.Intent;
import android.net.Uri;

import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mbouami on 15/09/2017.
 */

public class mesparametres {
    public final String BASE_URL ="http://www.bouami.fr/danecreteil/web/";
    public final String BASE_URL_DEPART = BASE_URL + "listedetailvillespardepart/";
    public final String BASE_URL_DEPART_93 = BASE_URL_DEPART + "93";
    public final String BASE_URL_DEPART_94 = BASE_URL_DEPART + "94";
    public final String BASE_URL_DEPART_77 = BASE_URL_DEPART + "77";
    List<Animateur> listedesanimateurs = new ArrayList<Animateur>();
    List<Etablissement> listedesetablissements = new ArrayList<Etablissement>();


    public List<Animateur> getListeAnimateurs(JSONObject donnees, String depart) {
        listedesanimateurs = new ArrayList<Animateur>();
        try {
            if (depart.length()==0) {
                Iterator<String> keys = donnees.keys();
                while(keys.hasNext()){
                    String keydepart = keys.next();
                    AjouterAnimateurParDepart(donnees, keydepart);
                }
            } else {
                AjouterAnimateurParDepart(donnees, depart);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listedesanimateurs;
    }

    public List<Etablissement> getListeEtablissements(JSONObject donnees, String depart) {
        listedesetablissements = new ArrayList<Etablissement>();
        try {
            if (depart.length()==0) {
                Iterator<String> keys = donnees.keys();
                while(keys.hasNext()){
                    String keydepart = keys.next();
                    AjouterEtablissementParDepart(donnees, keydepart);
                }
            } else {
                JSONArray datadepart = donnees.getJSONArray(depart);
                AjouterEtablissementParDepart(donnees, depart);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listedesetablissements;
    }

    private void AjouterAnimateurParDepart(JSONObject contenu, String depart) throws JSONException {
        JSONArray datadepart = contenu.getJSONArray(depart);
        for (int i = 0; i < datadepart.length(); i++) {
            JSONArray etabsObject = datadepart.getJSONObject(i).getJSONArray("etabs");
            for (int j = 0; j < etabsObject.length(); j++) {
                JSONArray animateursObject = etabsObject.getJSONObject(j).getJSONArray("animateur");
                for (int k = 0; k < animateursObject.length(); k++) {
                    Animateur anim = new Animateur(animateursObject.getJSONObject(k));
                    boolean trouve = false;
                    for (Animateur animateur : listedesanimateurs) {
                        if (animateur.lememeAnimateur(anim)) {
                            trouve = true;
                            break;
                        }
                    }
                    if (!trouve) {
                        listedesanimateurs.add(anim);
                    }
//                        if (listedesanimateurs.contains(anim)==false) {
//                            listedesanimateurs.add(anim);
//                        }
                }
            }
        }
    }

    private void AjouterEtablissementParDepart(JSONObject contenu, String depart) throws JSONException {
        JSONArray datadepart = contenu.getJSONArray(depart);
        for (int i = 0; i < datadepart.length(); i++) {
            JSONArray etabsObject = datadepart.getJSONObject(i).getJSONArray("etabs");
            for (int j = 0; j < etabsObject.length(); j++) {
                listedesetablissements.add(new Etablissement(etabsObject.getJSONObject(j)));
            }
        }
    }

    public Intent createShareIntent(String mNomEtabcast, String mEtabSharecast) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Carte visite : "+mNomEtabcast);
        shareIntent.putExtra(Intent.EXTRA_TEXT, mEtabSharecast);
        return shareIntent;
    }

    public Intent createPhoneIntent(String mTelEtabcast) {
        Intent shareIntent = new Intent(Intent.ACTION_DIAL);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setData(Uri.parse("tel:"+mTelEtabcast));
        return shareIntent;
    }

    public Intent createMailIntent(String mMailEtabcast) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { mMailEtabcast });
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Demande de rendez-vous");
        return shareIntent;
    }

    public Intent createMapIntent(String mAdresseEtabcast) {
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", mAdresseEtabcast)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        return intent;
    }
}

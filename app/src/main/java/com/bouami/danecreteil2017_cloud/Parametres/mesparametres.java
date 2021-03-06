package com.bouami.danecreteil2017_cloud.Parametres;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;

import com.bouami.danecreteil2017_cloud.Models.Animateur;
import com.bouami.danecreteil2017_cloud.Models.Etablissement;
import com.bouami.danecreteil2017_cloud.Models.Personnel;

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
    public final String BASE_URL_EXPORT = BASE_URL + "exportdonnees/";
    public final String BASE_URL_DEPART = BASE_URL + "listedetailvillespardepart/";
    public final String BASE_URL_DEPART_93 = BASE_URL_DEPART + "93";
    public final String BASE_URL_DEPART_94 = BASE_URL_DEPART + "94";
    public final String BASE_URL_DEPART_77 = BASE_URL_DEPART + "77";
    List<Animateur> listedesanimateurs = new ArrayList<Animateur>();
    List<Etablissement> listedesetablissements = new ArrayList<Etablissement>();
    List<Personnel> listedespersonnels = new ArrayList<Personnel>();

    @SuppressLint("LongLogTag")
    public List<Animateur> getListeAnimateursCreteil(JSONObject donnees, String depart) throws JSONException {
        listedesanimateurs = new ArrayList<Animateur>();
        if (depart!="") {
            JSONObject listeanims = donnees.getJSONObject("animateurs").getJSONObject(depart);
            Iterator<String> keys = listeanims.keys();
            try {
                while(keys.hasNext()){
                    String keyanimateur = keys.next();
                    Animateur anim = new Animateur(listeanims.getJSONObject(keyanimateur));
                    listedesanimateurs.add(anim);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject listeanims = donnees.getJSONObject("animateurs");
            Iterator<String> keysdepart = listeanims.keys();
            while (keysdepart.hasNext()){
                String keydepartencours = keysdepart.next();
                JSONObject listeanimspardepart = listeanims.getJSONObject(keydepartencours);
                Iterator<String> keys = listeanimspardepart.keys();
                try {
                    while(keys.hasNext()){
                        String keyanimateur = keys.next();
                        Animateur anim = new Animateur(listeanimspardepart.getJSONObject(keyanimateur));
                        listedesanimateurs.add(anim);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return listedesanimateurs;
    }

    @SuppressLint("LongLogTag")
    public List<Etablissement> getListeEtablissementsCreteil(JSONObject donnees, String depart) throws JSONException {
        listedesetablissements = new ArrayList<Etablissement>();
        if (depart!="") {
            JSONObject listeetabs = donnees.getJSONObject("etablissements").getJSONObject(depart);
            Iterator<String> keys = listeetabs.keys();
            try {
                while (keys.hasNext()) {
                    String keyetablissement = keys.next();
                    Etablissement etab = new Etablissement(listeetabs.getJSONObject(keyetablissement));
                    listedesetablissements.add(etab);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject listeetabs = donnees.getJSONObject("etablissements");
            Iterator<String> keysdepart = listeetabs.keys();
            while (keysdepart.hasNext()){
                String keydepartencours = keysdepart.next();
                JSONObject listeanimspardepart = listeetabs.getJSONObject(keydepartencours);
                Iterator<String> keys = listeanimspardepart.keys();
                try {
                    while(keys.hasNext()){
                        String keyetablissement = keys.next();
                        Etablissement etab = new Etablissement(listeanimspardepart.getJSONObject(keyetablissement));
                        listedesetablissements.add(etab);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return listedesetablissements;
    }

    public List<Personnel> getListePersonnelCreteil(JSONObject donnees, String depart) throws JSONException {
        listedespersonnels = new ArrayList<Personnel>();
        if (depart!="") {
            JSONObject listepersonnels = donnees.getJSONObject("personnel").getJSONObject(depart);
            Iterator<String> keys = listepersonnels.keys();
            try {
                while (keys.hasNext()) {
                    String keypersonnel = keys.next();
                    Personnel personnel = new Personnel(listepersonnels.getJSONObject(keypersonnel));
                    listedespersonnels.add(personnel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            JSONObject listepersonnels = donnees.getJSONObject("personnel");
            Iterator<String> keysdepart = listepersonnels.keys();
            while (keysdepart.hasNext()){
                String keydepartencours = keysdepart.next();
                JSONObject listepersonnelspardepart = listepersonnels.getJSONObject(keydepartencours);
                Iterator<String> keys = listepersonnelspardepart.keys();
                try {
                    while(keys.hasNext()){
                        String keypersonnel = keys.next();
                        Personnel personnel = new Personnel(listepersonnelspardepart.getJSONObject(keypersonnel));
                        listedespersonnels.add(personnel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return listedespersonnels;
    }

    @SuppressLint("LongLogTag")
    public List<Etablissement> getListeEtablissementsCreteilParAnimateur(JSONObject donnees, String depart, String idanimateur) throws JSONException {
        List<Etablissement> etabsparanim = new ArrayList<Etablissement>();
        JSONObject listeetabs = donnees.getJSONObject("animateurs").getJSONObject(depart).getJSONObject(idanimateur).getJSONObject("etablissements");
        JSONObject etabs = donnees.getJSONObject("etablissements").getJSONObject(depart);
        Iterator<String> keys = listeetabs.keys();
        while (keys.hasNext()) {
            String keyetab = keys.next();
            Etablissement etablissement = new Etablissement(etabs.getJSONObject(keyetab));
            etabsparanim.add(etablissement);
        }
        return etabsparanim;
    }

    @SuppressLint("LongLogTag")
    public List<Personnel> getListePersonnelsCreteilParEtablissement(JSONObject donnees, String depart, String idetablissement) throws JSONException {
        List<Personnel> personnesparetab = new ArrayList<Personnel>();
        JSONObject listepersonnels = donnees.getJSONObject("etablissements").getJSONObject(depart).getJSONObject(idetablissement).getJSONObject("personnels");
        JSONObject personnelsjson = donnees.getJSONObject("personnel").getJSONObject(depart);
        Iterator<String> keys = listepersonnels.keys();
        while (keys.hasNext()) {
            String keypersonnel = keys.next();
            Personnel lepersonnel = new Personnel(personnelsjson.getJSONObject(keypersonnel));
            personnesparetab.add(lepersonnel);
        }
        return personnesparetab;
    }

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

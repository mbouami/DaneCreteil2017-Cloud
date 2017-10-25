package com.bouami.danecreteil2017_cloud.Models;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mbouami on 02/09/2017.
 */

public class Etablissement {
    public String id;
    public String rne;
    public String type;
    public String nom;
    public String fax;
    public String tel;
    public String email;
    public String cp;
    public String ville;
    public String adresse;
    public Map<String, Boolean> animateurs = new HashMap<>();
    public Map<String, Boolean> personnel = new HashMap<>();

    public ContentValues etab = new ContentValues();
    public ContentValues vil = new ContentValues();


//    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("rne", rne);
        result.put("type", type);
        result.put("nom", nom);
        result.put("fax", fax);
        result.put("tel", tel);
        result.put("email", email);
        result.put("cp", cp);
        result.put("ville", ville);
        result.put("adresse", adresse);
        result.put("animateurs", animateurs);
        result.put("personnel", personnel);
        return result;
    }

    public Etablissement() {

    }

    public Etablissement(String id, String type, String nom, String fax, String tel,
                         String email, String cp, String ville, String adresse) {
        this.id = id;
        this.type = type;
        this.nom = nom;
        this.fax = fax;
        this.tel = tel;
        this.email = email;
        this.cp = cp;
        this.ville = ville;
        this.adresse = adresse;
    }

//    public Etablissement(JSONObject etab) throws JSONException {
//        this.id = etab.get("id").toString();
////        this.rne = etab.get("rne").toString();
//        this.type = etab.get("type").toString();
//        this.nom = etab.get("nom").toString();
//        this.fax = etab.get("fax").toString();
//        this.tel = etab.get("tel").toString();
//        this.email = etab.get("email").toString();
//        this.cp = etab.get("cp").toString();
//        this.ville = etab.get("ville").toString();
//        this.adresse = etab.get("adresse").toString();
//    }

    public Etablissement(JSONObject etab) throws JSONException {
        this.id = etab.get("id").toString();
        this.type = etab.get("type").toString();
        this.nom = etab.get("nom").toString();
        this.fax = etab.get("fax").toString();
        this.tel = etab.get("tel").toString();
        this.email = etab.get("email").toString();
        this.cp = etab.get("cp").toString();
        this.ville = etab.get("ville").toString();
        this.adresse = etab.get("adresse").toString();
        if (!etab.isNull("animateurs")) {
            Iterator<String> keysanimateurs = etab.getJSONObject("animateurs").keys();
            while (keysanimateurs.hasNext()) {
                this.animateurs.put(keysanimateurs.next(), true);
            }
        } else {
            this.animateurs = null;
        }
        if (!etab.isNull("personnels")) {
            Iterator<String> keyspersonnel = etab.getJSONObject("personnels").keys();
            while (keyspersonnel.hasNext()) {
                this.personnel.put(keyspersonnel.next(), true);
            }
        } else {
            this.personnel = null;
        }
    }

    public ContentValues getEtab() {
        return etab;
    }

    public void setEtab(Long idville,Long idanimateur) {
        this.etab.put("nom", this.nom);
        this.etab.put("rne", this.email.isEmpty()?null:this.email.substring(3,this.email.indexOf("@")));
        this.etab.put("tel", this.tel);
        this.etab.put("fax", this.fax);
        this.etab.put("adresse", this.adresse);
        this.etab.put("ville_id", idville);
        this.etab.put("animateur_id", idanimateur);
        this.etab.put("email", this.email);
        this.etab.put("type", this.type);
        this.etab.put("etablissement_id", this.id);
    }

    public ContentValues getVil() {
        return vil;
    }

    public void setVil(Long iddepart) {
        this.vil.put("nom",this.ville);
        this.vil.put("cp",this.cp);
        this.vil.put("departement_id",iddepart);
    }

    public Etablissement(String id, String rne, String type, String nom, String fax, String tel,
                         String email, String cp, String ville, String adresse, Map<String,
                         Boolean> animateurs, Map<String, Boolean> personnel) {
        this.id = id;
        this.rne = rne;
        this.type = type;
        this.nom = nom;
        this.fax = fax;
        this.tel = tel;
        this.email = email;
        this.cp = cp;
        this.ville = ville;
        this.adresse = adresse;
        this.animateurs = animateurs;
        this.personnel = personnel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRne() {
        return rne;
    }

    public void setRne(String rne) {
        this.rne = rne;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Map<String, Boolean> getAnimateurs() {
        return animateurs;
    }

    public void setAnimateurs(Map<String, Boolean> animateurs) {
        this.animateurs = animateurs;
    }

    public Map<String, Boolean> getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Map<String, Boolean> personnel) {
        this.personnel = personnel;
    }

}

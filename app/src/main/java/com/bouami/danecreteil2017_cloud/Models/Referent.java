package com.bouami.danecreteil2017_cloud.Models;

import android.content.ContentValues;
import android.database.Cursor;

import com.bouami.danecreteil2017_cloud.Parametres.DaneContract;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohammed on 30/09/2017.
 */

public class Referent {

    public String id;
    public String civilite;
    public String nom;
    public String prenom;
    public String email;
    public String tel;
    public String statut;
    public String discipline;
    public String etablissement;

    public ContentValues refer = new ContentValues();

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public Referent() {
    }

    public Referent(JSONObject referent) throws JSONException {
        this.id = referent.get("id").toString();
        this.civilite = referent.get("genre").toString();
        this.nom = referent.get("nom").toString();
        this.prenom = referent.get("prenom").toString();
        this.tel = referent.get("tel").toString();
        this.email = referent.get("email").toString();
        this.statut = referent.get("statut").toString();
        this.discipline = referent.getJSONObject("discipline").keys().next();
        this.etablissement = referent.getJSONObject("etablissements").keys().next();
    }

    public JSONObject getJsonFromCursorReferent(Cursor mcursor) throws JSONException {
        JSONObject lereferent = new JSONObject();
        lereferent.put("id",DaneContract.getValueFromCursor(mcursor,DaneContract.ReferentEntry._ID));
        lereferent.put("civilite",DaneContract.getValueFromCursor(mcursor,"CIVILITE"));
        lereferent.put("nom",DaneContract.getValueFromCursor(mcursor,DaneContract.ReferentEntry.COLUMN_NOM));
        lereferent.put("prenom", DaneContract.getValueFromCursor(mcursor,DaneContract.ReferentEntry.COLUMN_PRENOM));
        lereferent.put("tel",DaneContract.getValueFromCursor(mcursor,DaneContract.ReferentEntry.COLUMN_TEL));
        lereferent.put("email",DaneContract.getValueFromCursor(mcursor,DaneContract.ReferentEntry.COLUMN_EMAIL));
        lereferent.put("statut",DaneContract.getValueFromCursor(mcursor,DaneContract.ReferentEntry.COLUMN_STATUT));
        lereferent.put("discipline",DaneContract.getValueFromCursor(mcursor,DaneContract.ReferentEntry.COLUMN_DISCIPLINE_ID));
        lereferent.put("etablissement",DaneContract.getValueFromCursor(mcursor,DaneContract.ReferentEntry.COLUMN_ETABLISSEMENT_ID));
        return lereferent;
    }

    public ContentValues getRefer() {
        return refer;
    }

    public void setRefer(Boolean sync,Long idcivilite) {
        this.refer.put("civilite_id", idcivilite);
        this.refer.put("nom", this.nom);
        this.refer.put("prenom", this.prenom);
        this.refer.put("tel", this.tel);
        this.refer.put("statut", this.statut);
        this.refer.put("email", this.email);
        this.refer.put("discipline_id", this.discipline);
        this.refer.put("referent_id", this.id);
        this.refer.put("etablissement_id", this.etablissement);
        this.refer.put("synchroniser", sync);
    }



    public Referent(String id, String civilite, String nom, String prenom, String email, String tel, String statut, String discipline, String etablissement) {
        this.id = id;
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.statut = statut;
        this.discipline = discipline;
        this.etablissement = etablissement;
    }

    public Referent(String civilite, String nom, String prenom, String email, String tel, String statut, String discipline, String etablissement) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.statut = statut;
        this.discipline = discipline;
        this.etablissement = etablissement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }
}
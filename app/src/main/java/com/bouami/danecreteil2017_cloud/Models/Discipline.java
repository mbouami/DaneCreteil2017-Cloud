package com.bouami.danecreteil2017_cloud.Models;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mohammed on 22/10/2017.
 */

public class Discipline {

    public String id;
    public String nom;
    public JSONObject discipline;
    public ContentValues disciplines = new ContentValues();


    public Discipline() {
    }

    public Discipline(JSONObject discipline) throws JSONException {
        this.id = discipline.get("id").toString();
        this.nom = discipline.get("nom").toString();
    }

    public ContentValues getDisciplines() {
        return disciplines;
    }

    public void setDisciplines() {
        this.disciplines.put("nom", this.nom);
    }

    //    public ContentValues getDisciplines(JSONObject discipline) throws JSONException {
//        this.disciplines.put("nom", discipline.get("nom").toString());
//        return disciplines;
//    }
}
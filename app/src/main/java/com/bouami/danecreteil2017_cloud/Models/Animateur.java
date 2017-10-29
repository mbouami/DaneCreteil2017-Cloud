package com.bouami.danecreteil2017_cloud.Models;

import android.content.ContentValues;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mbouami on 15/09/2017.
 */

public class Animateur {
    public String id;
    public String civilite;
    public String nom;
    public String prenom;
    public String tel;
    public String email;
    public String photo;

    public ContentValues anim = new ContentValues();

    public Animateur() {

    }

    public Animateur(JSONObject anim) throws JSONException {
        this.id = anim.get("id").toString();
        this.civilite = anim.get("genre").toString();
        this.nom = anim.get("nom").toString();
        this.prenom = anim.get("prenom").toString();
        this.tel = anim.get("tel").toString();
        this.email = anim.get("email").toString();
        this.photo = anim.get("photo").toString();

    }

    public ContentValues getAnim() {
        return anim;
    }

    public void setAnim(Long iddepart,Long idcivilite) {
        this.anim.put("civilite_id", idcivilite);
        this.anim.put("nom", this.nom);
        this.anim.put("prenom", this.prenom);
        this.anim.put("tel", this.tel);
        this.anim.put("photo", Base64.decode(this.photo, Base64.DEFAULT));
//        this.anim.put("photo", this.photo);
        this.anim.put("email", this.email);
        this.anim.put("animateur_id", this.id);
        this.anim.put("departement_id", iddepart);
    }

    public Animateur(String id, String civilite, String nom, String prenom, String tel, String email, String photo) {
        this.id = id;
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.photo = photo;
    }

    public Animateur(String civilite, String nom, String prenom, String tel, String email, String photo) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean lememeAnimateur (Animateur anim) {
        boolean lememe = false;
//        if (anim.civilite.equals(this.civilite) && anim.nom.equals(this.nom) && anim.prenom.equals(this.prenom)&&
//                anim.tel.equals(this.tel)&&anim.email.equals(this.email)&&anim.photo.equals(this.photo)) lememe=true;
        if (anim.civilite.equals(this.civilite) && anim.nom.equals(this.nom) && anim.prenom.equals(this.prenom)) lememe=true;
        return lememe;
    }
}
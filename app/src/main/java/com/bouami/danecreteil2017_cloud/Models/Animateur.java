package com.bouami.danecreteil2017_cloud.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mbouami on 15/09/2017.
 */

public class Animateur {
    public String id;
    public String genre;
    public String nom;
    public String prenom;
    public String tel;
    public String email;
    public String photo;

    public Animateur() {

    }

    public Animateur(JSONObject anim) throws JSONException {
        this.id = anim.get("id").toString();
        this.genre = anim.get("genre").toString();
        this.nom = anim.get("nom").toString();
        this.prenom = anim.get("prenom").toString();
        this.tel = anim.get("tel").toString();
        this.email = anim.get("email").toString();
        this.photo = anim.get("photo").toString();

    }

    public Animateur(String id,String genre, String nom, String prenom, String tel, String email, String photo) {
        this.id = id;
        this.genre = genre;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.photo = photo;
    }

    public Animateur(String genre, String nom, String prenom, String tel, String email, String photo) {
        this.genre = genre;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
//        if (anim.genre.equals(this.genre) && anim.nom.equals(this.nom) && anim.prenom.equals(this.prenom)&&
//                anim.tel.equals(this.tel)&&anim.email.equals(this.email)&&anim.photo.equals(this.photo)) lememe=true;
        if (anim.genre.equals(this.genre) && anim.nom.equals(this.nom) && anim.prenom.equals(this.prenom)) lememe=true;
        return lememe;
    }
}

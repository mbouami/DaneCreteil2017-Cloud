package com.bouami.danecreteil2017_cloud.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mbouami on 02/09/2017.
 */

public class Personnel {

    public String id;
    public String genre;
    public String nom;
    public String prenom;
    public String email;
    public String tel;
    public String statut;

    public Personnel() {

    }

    public Personnel(String id, String genre, String nom, String prenom, String tel, String email, String statut) {
        this.id = id;
        this.genre = genre;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.statut = statut;
    }

    public Personnel(String genre, String nom, String prenom,String tel, String email, String statut) {
        this.genre = genre;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.statut = statut;
    }

    public Map<String, Object> toMap(String etabkey) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> etab = new HashMap<>();
        result.put("genre", genre);
        result.put("nom", nom);
        result.put("prenom", prenom);
        result.put("tel", tel);
        result.put("email", email);
        etab.put(etabkey,true);
        result.put("etablissement",etab);
        return result;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}

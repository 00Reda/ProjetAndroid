package com.androidprojet.projetandroid.Entity;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Demandes implements Serializable{

    int id ;
    String nom ;
    String description ;
    String date_ajout ;
    String tags ;
    int etat ;
    Users user ;


    public Demandes() {
    }

    public Demandes(int id, String nom, String description, String date_ajout, String tags, int etat, Users user) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date_ajout = date_ajout;
        this.tags = tags;
        this.etat = etat;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(String date_ajout) {
        this.date_ajout = date_ajout;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public static ArrayList<Demandes> fromJSON(String json){
        ArrayList l=new ArrayList();
        try {

            JSONObject reader = new JSONObject(json);
            JSONArray demandes = reader.getJSONArray("demandes");

            for (int i = 0; i < demandes.length(); i++) {
                JSONObject c = demandes.getJSONObject(i);
                int id = c.getInt("id");
                String nom = c.getString("nom");
                String description = c.getString("description");
                String date_ajout = c.getString("date_ajout");
                String tags = c.getString("tags");
                int etat = c.getInt("etat");
                JSONObject u = c.getJSONObject("user");
                Users user=Users.fromJSON(u);
                  //Log.wtf("USER",u.toString());
                Demandes d=new Demandes( id,  nom,  description,  date_ajout,  tags,  etat,  user);
                l.add(d);

            }

            return l ;
        }catch (Exception e){

            Log.wtf("errror ",e.getMessage());
            return null;
        }

    }

    @Override
    public String toString() {
        return "Demandes{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", date_ajout='" + date_ajout + '\'' +
                ", tags='" + tags + '\'' +
                ", etat='" + etat + '\'' +
                ", user=" + user +
                '}';
    }
}

package com.androidprojet.projetandroid.Entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Commentaire implements Serializable {

        int id;
        String contenu;
        Users user;
        Demandes demande;
        String date;

    public Commentaire() {
    }

    public Commentaire(int id, String contenu, Users user, Demandes demande , String date) {
        this.id = id;
        this.contenu = contenu;
        this.user = user;
        this.demande = demande;
        this.date=date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Demandes getDemande() {
        return demande;
    }

    public void setDemande(Demandes demande) {
        this.demande = demande;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.contenu+" \n "+"par :"+this.user.getNom() +" \n "+" le :"+this.date;
    }


    public static ArrayList<Commentaire> fromJSON(String json){
        ArrayList l=new ArrayList();
        try {

            JSONObject reader = new JSONObject(json);
            JSONArray coms = reader.getJSONArray("com");

            for (int i = 0; i < coms.length(); i++) {
                JSONObject c = coms.getJSONObject(i);
                int id = c.getInt("id");
                String contenu = c.getString("contenu");
                String date = c.getString("date");
                JSONObject u = c.getJSONObject("user");
                Users user=Users.fromJSON(u);
                //Log.wtf("USER",u.toString());
                Commentaire cmd=new Commentaire( id,  contenu,  user, null,date);

                l.add(cmd);

            }

            return l ;
        }catch (Exception e){

            Log.wtf("errror ",e.getMessage());
            return null;
        }

    }

}

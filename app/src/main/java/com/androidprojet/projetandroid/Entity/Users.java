package com.androidprojet.projetandroid.Entity;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;

public class Users implements Serializable{

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Nom = "nomKey";
    public static final String Email = "emailKey";
    public static final String Tel = "telKey";
    public static final String Role = "roleKey";
    public static final String Tags = "tagsKey";
    public static final String ID = "IDKey";
    public static final String USER_ROLE_CLIENT="client";
    public static final String USER_ROLE_EXPERT="expert";

    private int id;


    private String nom;


    private String email;


    private String password;


    private String tel;


    private String role;


    private String tags;


    public Users(int id, String nom, String email, String password, String tel, String role, String tags) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.role = role;
        this.tags = tags;
    }


    public Users() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public static Users fromJSON(String json){

        try {
            JSONObject reader = new JSONObject(json);
            reader=reader.getJSONObject("user");
            String id = reader.getString("id");
            String nom = reader.getString("nom");
            String email = reader.getString("email");
            String tel = reader.getString("tel");
            String role = reader.getString("role");
            String tags = reader.getString("tags");
            return new Users(Integer.parseInt(id),nom,email,"",tel,role,tags);
        }catch (Exception e){

            Log.wtf("errror ",e.getMessage());
            return null;
        }

    }

    public static Users fromJSON(JSONObject json){

        try {
            JSONObject reader = json;

            String id = reader.getString("id");
            String nom = reader.getString("nom");
            String email = reader.getString("email");
            String tel = reader.getString("tel");
            String role = reader.getString("role");
            String tags = reader.getString("tags");
            return new Users(Integer.parseInt(id),nom,email,"",tel,role,tags);
        }catch (Exception e){

            Log.wtf("errror ",e.getMessage());
            return null;
        }

    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", role='" + role + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}

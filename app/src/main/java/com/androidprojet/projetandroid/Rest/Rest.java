package com.androidprojet.projetandroid.Rest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.androidprojet.projetandroid.AddCommentaireActivity;
import com.androidprojet.projetandroid.AddDemande;
import com.androidprojet.projetandroid.Entity.Commentaire;
import com.androidprojet.projetandroid.Entity.Demandes;
import com.androidprojet.projetandroid.Entity.Users;

import com.androidprojet.projetandroid.ListCommentaireActivity;
import com.androidprojet.projetandroid.MainActivity;
import com.androidprojet.projetandroid.PRofileActivity;
import com.androidprojet.projetandroid.R;
import com.androidprojet.projetandroid.view.list.ListAdapter;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Rest extends AsyncTask<String , Integer ,String>{

    public String result="init";
    private AppCompatActivity activity;
    private int respenseCode=200;
    private String RequestType="";
    private String cnxError="";
    private ProgressDialog dialog;
    public Rest(AppCompatActivity m){
        this.activity=m;
    }

    @Override
    protected void onPreExecute() {
        this.dialog = new ProgressDialog(activity, R.style.MyAlertDialogStyle);
        this.dialog.setMessage("Loading . patienter ...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    public  String doRestRequest(String URL , String data , String method){

        try {

            URL url = new URL(URL);

            HttpURLConnection cnx= (HttpURLConnection) url.openConnection();

            if(! method.equals("GET")){
                cnx.setDoOutput(true);
                cnx.setRequestMethod(method);
                cnx.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                StringBuffer request = new StringBuffer();
                request.append(data);
                byte[] bytes= request.toString().getBytes();
                OutputStream out=cnx.getOutputStream();
                out.write(bytes);
                out.close();

            }

            int statusCode=cnx.getResponseCode();
            StringBuffer res=new StringBuffer();
            if(statusCode==200){
                InputStream in=cnx.getInputStream();
                int c;
                while( (c=in.read()) !=-1)
                    res.append((char) c);
                this.respenseCode=200;
                Log.wtf("result : ",res.toString());
            }else if(statusCode==400){
                // parse error msg
                InputStream in=cnx.getErrorStream();
                int c;
                while( (c=in.read()) !=-1)
                    res.append((char) c);

                this.respenseCode=400;

                Log.wtf("result : ",res.toString());

                Log.wtf("result : ",statusCode+" code 400");



            }else{
                InputStream in=cnx.getErrorStream();
                int c;
                while( (c=in.read()) !=-1)
                    res.append((char) c);

                this.respenseCode=500;
                Log.wtf("result : ",statusCode+" code 500");
                //Log.wtf("result 500 status code : ",res.toString());
            }


            return res.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            String str=e.getMessage();
            StringBuffer err=new StringBuffer();
            err.append(str);
            this.cnxError=str;
            Log.wtf("error",e.getMessage());
            return err.toString();
        }
    }


    @Override
    protected String doInBackground(String... params) {
        this.RequestType=params[3];
        return doRestRequest(params [0],params[1],params[2]).toString();
    }

    @Override
    protected void onPostExecute(String s) {

        this.result=s;
        this.dialog.dismiss();
        this.dialog.cancel();
        Toast info;


        if(this.respenseCode==200) {

            switch (this.RequestType){
                case RestConf.CREATE_OR_EDIT_ACCOUNT_REQUEST_TYPE :{
                        Account(s);
                        break;
                    }
                case RestConf.LOGIN_REQUEST_TYPE :{
                       this.login(s);
                       break;
                }
                case RestConf.LIST_DEMANDE_REQUEST_TYPE :{
                        this.ListDemandes(s);
                        break;
                }
                case RestConf.LIST_DEMANDE_CLIENT_REQUEST_TYPE :{
                        this.ListDemandes(s);
                        break;
                }
                case RestConf.ADD_DEMANDE_REQUEST_TYPE :{
                    this.Demandes(s);
                    break;
                }
                case RestConf.EDIT_DEMANDE_REQUEST_TYPE :{
                    this.Demandes(s);
                    break;
                }
                case RestConf.DELETE_DEMANDE_REQUEST_TYPE :{
                    this.DeleteDemandes(s);
                    break;
                }
                case RestConf.LIST_COM_REQUEST_TYPE :{
                    this.ListCommentaire(s);
                    break;
                }
                case RestConf.ADD_COM_REQUEST_TYPE:{
                    this.AddCommentaire(s);
                    break;
                }
                default:{
                        info=Toast.makeText(activity.getApplicationContext(),"invalid request type !!",Toast.LENGTH_LONG);
                        info.show();
                }
            }

        }else if(this.respenseCode==400){
                this.on404(s);
        }else{
                this.onInternalServerError();
        }
    }








    private void login(String s){

        Toast info;

        if(!this.cnxError.equals("")){
            info=Toast.makeText(activity.getApplicationContext(),this.cnxError,Toast.LENGTH_LONG);
            info.show();
            return;
        }

        Users u = Users.fromJSON(s);
        if(u==null) {
            // this.activity.getJson().setText("erreur lors du parssage de vos donnees !!");
            info=Toast.makeText(activity.getApplicationContext(),"erreur lors du parssage de vos donnees !!",Toast.LENGTH_LONG);
            info.show();
            return;
        }
        // this.activity.getJson().setText(u.toString()+" hi");
        info=Toast.makeText(activity.getApplicationContext(),"authentification avec succes !",Toast.LENGTH_LONG);
        info.show();
                    /*
                       redirection to profile activity
                     */

        Intent toProfileActivity=new Intent(activity,PRofileActivity.class);
        toProfileActivity.putExtra("user",u);
        activity.startActivity(toProfileActivity);
    }

    private void Account(String s){
        Toast info;
        if(!this.cnxError.equals("")){
            info=Toast.makeText(activity.getApplicationContext(),this.cnxError,Toast.LENGTH_LONG);
            info.show();
            return;
        }

        try{

            String msg=ServerMessagesParser.getDoneMassage(s);
            info=Toast.makeText(activity.getApplicationContext(),Html.fromHtml(msg),Toast.LENGTH_LONG);
            info.show();
            Intent toLoginActivity=new Intent(activity,MainActivity.class);
            toLoginActivity.putExtra("logout",true);
            activity.startActivity(toLoginActivity);

        }catch (Exception e){

            info=Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
            info.show();

        }
    }

    private void ListDemandes(String s){
        Toast info;
        if(!this.cnxError.equals("")){
            info=Toast.makeText(activity.getApplicationContext(),this.cnxError,Toast.LENGTH_LONG);
            info.show();
            return;
        }

        ArrayList list= Demandes.fromJSON(s);
        if(list==null) {
            info=Toast.makeText(activity.getApplicationContext(),"erreur lors du parssage de vos donnees !!",Toast.LENGTH_LONG);
            info.show();
            return;
        }
        // this.activity.getJson().setText(u.toString()+" hi");
        info=Toast.makeText(activity.getApplicationContext(),"recuperation des demmandes avec succes !",Toast.LENGTH_LONG);
        info.show();

        PRofileActivity pra=(PRofileActivity) this.activity;
        ListView maList = pra.findViewById(R.id.maliste);
        ListAdapter monAdapter = new ListAdapter(pra.getApplicationContext(), list,pra.getUser() ,this.activity);

        if(maList!=null)
        maList.setAdapter(monAdapter);


    }

    private void on404(String s){
        String msg;
        Toast info;
        try{

            msg= ServerMessagesParser.getErrorMassage(s);


        }catch (Exception e){

            msg=e.getMessage();

        }
        info=Toast.makeText(activity.getApplicationContext(),Html.fromHtml(msg),Toast.LENGTH_LONG);
        info.show();
    }

    private void onInternalServerError(){
        Toast info;
        info=Toast.makeText(activity.getApplicationContext(),"internal server error !",Toast.LENGTH_LONG);
        info.show();
    }

    private void Demandes(String s){
        Toast info;
        if(!this.cnxError.equals("")){
            info=Toast.makeText(activity.getApplicationContext(),this.cnxError,Toast.LENGTH_LONG);
            info.show();
            return;
        }

        try{

            String msg=ServerMessagesParser.getDoneMassage(s);
            info=Toast.makeText(activity.getApplicationContext(),Html.fromHtml(msg),Toast.LENGTH_LONG);
            info.show();

            AddDemande demandeActivity=(AddDemande) this.activity;
            Intent in=new Intent(demandeActivity,PRofileActivity.class);
            in.putExtra("user",demandeActivity.getUser());
            activity.startActivity(in);


        }catch (Exception e){

            info=Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
            info.show();

        }
    }

    private void DeleteDemandes(String s){
        Toast info;
        if(!this.cnxError.equals("")){
            info=Toast.makeText(activity.getApplicationContext(),this.cnxError,Toast.LENGTH_LONG);
            info.show();
            return;
        }

        try{

            String msg=ServerMessagesParser.getDoneMassage(s);
            info=Toast.makeText(activity.getApplicationContext(),Html.fromHtml(msg),Toast.LENGTH_LONG);
            info.show();

            PRofileActivity demandeActivity=(PRofileActivity) this.activity;

            Intent in=new Intent(demandeActivity,PRofileActivity.class);
            in.putExtra("user",demandeActivity.getUser());
            activity.startActivity(in);

        }catch (Exception e){

            info=Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
            info.show();

        }
    }

    private void ListCommentaire(String s){
        Toast info;
        if(!this.cnxError.equals("")){
            info=Toast.makeText(activity.getApplicationContext(),this.cnxError,Toast.LENGTH_LONG);
            info.show();
            return;
        }

        ArrayList list= Commentaire.fromJSON(s);
        if(list==null) {
            info=Toast.makeText(activity.getApplicationContext(),"erreur lors du parssage de vos donnees !!",Toast.LENGTH_LONG);
            info.show();
            return;
        }
        // this.activity.getJson().setText(u.toString()+" hi");
        info=Toast.makeText(activity.getApplicationContext(),"recuperation des commantaire avec succes !",Toast.LENGTH_LONG);
        info.show();

        Intent in=new Intent(this.activity, ListCommentaireActivity.class);
        in.putExtra("com",list);
        this.activity.startActivity(in);



    }

    private void AddCommentaire(String s){
        Toast info;
        if(!this.cnxError.equals("")){
            info=Toast.makeText(activity.getApplicationContext(),this.cnxError,Toast.LENGTH_LONG);
            info.show();
            return;
        }

        try{

            String msg=ServerMessagesParser.getDoneMassage(s);
            info=Toast.makeText(activity.getApplicationContext(),Html.fromHtml(msg),Toast.LENGTH_LONG);
            info.show();

            AddCommentaireActivity ADDCActivity=(AddCommentaireActivity) this.activity;
            Intent in=new Intent(ADDCActivity,PRofileActivity.class);
            in.putExtra("user",ADDCActivity.getU());
            activity.startActivity(in);


        }catch (Exception e){

            info=Toast.makeText(activity.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
            info.show();

        }
    }
}

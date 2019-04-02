package com.androidprojet.projetandroid.view.list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidprojet.projetandroid.Entity.Demandes;
import com.androidprojet.projetandroid.Entity.Users;
import com.androidprojet.projetandroid.ListCommentaireActivity;
import com.androidprojet.projetandroid.R;
import com.androidprojet.projetandroid.Rest.Rest;
import com.androidprojet.projetandroid.Rest.RestConf;

import java.util.List;



public class ListAdapter extends ArrayAdapter<Demandes> {

    private final List<Demandes> values  ;
    private final Context  context  ;

    private Button deleteBtn;
    private Button ComBtn;
    private Button ChangeEtatBtn;
    private AppCompatActivity activity;
    private Users user;

    public ListAdapter(@NonNull Context context , List<Demandes> values , Users user, AppCompatActivity activity) {
        super(context, 0 , values);
        this.context = context ;
        this.values = values ;
        this.user=user;
        this.activity=activity;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

       if(listItem == null )
           listItem = LayoutInflater.from(context).inflate(R.layout.monitem,parent,false);

         // layout influter il va nous permettre d'inserer des elemnt dans une vue
        Demandes m = values.get(position);
        TextView nom = (TextView)listItem.findViewById(R.id.nom);
        TextView desc = (TextView)listItem.findViewById(R.id.description);
        TextView tags = (TextView)listItem.findViewById(R.id.tags);
        TextView date = (TextView)listItem.findViewById(R.id.date);
        TextView owner = (TextView)listItem.findViewById(R.id.user);
        TextView etat = (TextView)listItem.findViewById(R.id.etat);
        TextView id = (TextView)listItem.findViewById(R.id.id);

        this.deleteBtn=listItem.findViewById(R.id.delete);
        this.deleteBtn.setTag(new Integer(m.getId()));
        this.ComBtn=listItem.findViewById(R.id.addCom);
        this.ComBtn.setTag(new Integer(m.getId()));
        this.ChangeEtatBtn=listItem.findViewById(R.id.changeEtat);
        this.ChangeEtatBtn.setTag(new Integer(m.getId()));

         EventManager();

        if(this.user.getRole().equals(Users.USER_ROLE_EXPERT)){

            this.ChangeEtatBtn.setVisibility(View.GONE);
            this.deleteBtn.setVisibility(View.GONE);
        }


        nom.setText(m.getNom());
        desc.setText(m.getDescription());
        tags.setText(m.getTags());
        date.setText(m.getDate_ajout());
        owner.setText(m.getUser().getNom());

        if(m.getEtat()==1){
            etat.setBackgroundColor(0xFFf58f3f);
            etat.setText("traité");
        }else{
            etat.setBackgroundColor(0xFF5fba7d);
            etat.setText("en attend");
        }

        id.setText(m.getId()+"");

        return listItem ;

    }


    public void EventManager(){
        this.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v);
            }
        });
        this.ComBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadCmd(v);
            }
        });
    }

    private void delete(View v){

        this.deleteBtn=(Button)v;
         AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setTitle("Confirmation :");
        builder.setMessage("vous etes sure ? ");
        builder.setCancelable(false);
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ConfirmDelete();
            }
        });

        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();



    }

    private void LoadCmd(View v){

        this.ComBtn=(Button)v;
        Integer i=(Integer)this.ComBtn.getTag();


        Log.wtf("IDDDe",i.toString());
        Rest api=new Rest(this.activity);
        api.execute(RestConf.LIST_COM_PATH+i.toString(),"","GET", RestConf.LIST_COM_REQUEST_TYPE);




    }

    private void ConfirmDelete(){
        Integer i=(Integer)this.deleteBtn.getTag();

        Rest api=new Rest(this.activity);
        Log.wtf("IDDDe",i.toString());
        api.execute(RestConf.DELETE_DEMANDE_PATH+i.toString(),"","POST", RestConf.DELETE_DEMANDE_REQUEST_TYPE);
    }
}

package com.androidprojet.projetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidprojet.projetandroid.Entity.Demandes;
import com.androidprojet.projetandroid.Entity.Users;
import com.androidprojet.projetandroid.Rest.Rest;
import com.androidprojet.projetandroid.Rest.RestConf;

public class AddDemande extends AppCompatActivity {

    EditText Nom;
    EditText Desc;
    EditText Tags;
    Button btn;

    private Demandes demande=null;
    private Users user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_demande);
        setTitle("Ajout Demande");

        Nom=(EditText)findViewById(R.id.nom);
        Desc=(EditText)findViewById(R.id.description);
        Tags=(EditText)findViewById(R.id.tags);
        btn=(Button)findViewById(R.id.CreateAccount);

        Intent in=this.getIntent();

        Demandes d = (Demandes) in.getSerializableExtra("demande");
        Users user = (Users) in.getSerializableExtra("user");

        this.user=user;

        if(d!=null){
            setTitle("modification de la Demande ");
            this.demande=d;
            this.Nom.setText(d.getNom());
            this.Desc.setText(d.getDescription());
            this.Tags.setText(d.getTags());

        }




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatch();
            }
        });





    }

    private void dispatch(){

        if(this.demande==null) createDemande();
        else{
            editDemande();
        }
    }


    private void editDemande(){


        String nom=this.Nom.getText().toString();
        String desc=this.Desc.getText().toString();
        String tags=this.Tags.getText().toString();

        Toast err;



        if(nom.equals("")){
            err= Toast.makeText(getApplicationContext(),"le nom est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }



        if(desc.equals("")){
            err= Toast.makeText(getApplicationContext(),"la description  est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
        }

        if(tags.equals("")){
            err= Toast.makeText(getApplicationContext(),"les tags sont obligatoires  !",Toast.LENGTH_LONG);
            err.show();
            return;
        }

        String data="nom="+nom+"&desc="+desc+"&tags="+tags;

        Rest api=new Rest(this);

        api.execute(RestConf.EDIT_DEMANDE_PATH+this.demande.getId(),data,"POST", RestConf.EDIT_DEMANDE_REQUEST_TYPE);



    }

    private void createDemande(){



        String nom=this.Nom.getText().toString();
        String desc=this.Desc.getText().toString();
        String tags=this.Tags.getText().toString();

        Toast err;



        if(nom.equals("")){
            err= Toast.makeText(getApplicationContext(),"le nom est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }



        if(desc.equals("")){
            err= Toast.makeText(getApplicationContext(),"la description  est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
        }

        if(tags.equals("")){
            err= Toast.makeText(getApplicationContext(),"les tags sont obligatoires  !",Toast.LENGTH_LONG);
            err.show();
            return;
        }


        String data="nom="+nom+"&desc="+desc+"&tags="+tags ;

        Rest api=new Rest(this);
        api.execute(RestConf.ADD_DEMANDE_PATH+this.user.getId(),data,"POST", RestConf.ADD_DEMANDE_REQUEST_TYPE);


    }


    public Users getUser() {
        return user;
    }
}

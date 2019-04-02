package com.androidprojet.projetandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidprojet.projetandroid.Entity.Users;
import com.androidprojet.projetandroid.Rest.Rest;
import com.androidprojet.projetandroid.Rest.RestConf;

public class ExpertActivity extends AppCompatActivity {

    EditText Nom;
    EditText Email;
    EditText Tel;
    EditText Tags;
    EditText Pass;

    Button btn;
    private Users user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expert_activity);
        setTitle("compte Expert");
        Nom=(EditText)findViewById(R.id.nom);
        Email=(EditText)findViewById(R.id.email);
        Tel=(EditText)findViewById(R.id.tel);
        Pass=(EditText)findViewById(R.id.pass);
        Tags=(EditText)findViewById(R.id.tags);
        btn=(Button)findViewById(R.id.CreateAccount);

        Intent in=this.getIntent();
        Users user = (Users) in.getSerializableExtra("user");

        if(user!=null){
            this.user=user;
            this.Nom.setText(user.getNom());
            this.Tel.setText(user.getTel());
            this.Email.setText(user.getEmail());
            this.Tags.setText(user.getTags());

        }




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatch();
            }
        });





    }

    private void dispatch(){

        if(this.user==null) createAccount();
        else{
            editAccount();
        }
    }


    private void editAccount(){

        String email=this.Email.getText().toString();
        String password=this.Pass.getText().toString();
        String nom=this.Nom.getText().toString();
        String tel=this.Tel.getText().toString();
        String tags=this.Tags.getText().toString();

        Toast err;

        if(email.equals("")){
            err= Toast.makeText(getApplicationContext(),"l'email est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }

        if(nom.equals("")){
            err= Toast.makeText(getApplicationContext(),"le nom est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }



        if(tel.equals("")){
            err= Toast.makeText(getApplicationContext(),"le tel est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
        }

        if(tags.equals("")){
            err= Toast.makeText(getApplicationContext(),"les tags sont obligatoires obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }

        String data="id="+this.user.getId()+"&nom="+nom+"&email="+email+"&tel="+tel+"&password="+password+"&role="+ Users.USER_ROLE_EXPERT+"&tags="+tags;

        Rest api=new Rest(this);
        api.execute(RestConf.EDIT_ACCOUNT_PATH,data,"POST", RestConf.CREATE_OR_EDIT_ACCOUNT_REQUEST_TYPE);



    }

    private void createAccount(){

        String email=this.Email.getText().toString();
        String password=this.Pass.getText().toString();
        String nom=this.Nom.getText().toString();
        String tel=this.Tel.getText().toString();
        String tags=this.Tags.getText().toString();

        Toast err;

        if(email.equals("")){
            err= Toast.makeText(getApplicationContext(),"l'email est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }

        if(nom.equals("")){
            err= Toast.makeText(getApplicationContext(),"le nom est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }

        if(password.equals("")){
            err= Toast.makeText(getApplicationContext(),"le password est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }else if(password.length()<8){
            err= Toast.makeText(getApplicationContext(),"le password doit se composer au mois de 8 caracteres !",Toast.LENGTH_LONG);
            err.show();
            return;
        }

        if(tel.equals("")){
            err= Toast.makeText(getApplicationContext(),"le tel est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
        }

        if(tags.equals("")){
            err= Toast.makeText(getApplicationContext(),"les tags sont obligatoires obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }

        String data="nom="+nom+"&email="+email+"&tel="+tel+"&password="+password+"&role="+ Users.USER_ROLE_EXPERT+"&tags="+tags;

        Rest api=new Rest(this);
        api.execute(RestConf.CREATE_ACCOUNT_PATH,data,"POST", RestConf.CREATE_OR_EDIT_ACCOUNT_REQUEST_TYPE);


    }

}

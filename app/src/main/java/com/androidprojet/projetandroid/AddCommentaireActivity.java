package com.androidprojet.projetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidprojet.projetandroid.Entity.Demandes;
import com.androidprojet.projetandroid.Entity.Users;
import com.androidprojet.projetandroid.Rest.Rest;
import com.androidprojet.projetandroid.Rest.RestConf;

public class AddCommentaireActivity extends AppCompatActivity {

    private Users u;
    private Demandes d;
    private EditText Contenu ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commentaire);

        Intent in =this.getIntent();

        this.Contenu=findViewById(R.id.Contenu);
        this.u =(Users) in.getSerializableExtra("user");
        this.d=(Demandes)in.getSerializableExtra("demande");

        TextView Titre=findViewById(R.id.titre);
        Titre.setText("Repondre a la demande :"+d.getNom());

        Button btn = findViewById(R.id.AddCom);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });



    }

    private void Add(){

        String contenu=this.Contenu.getText().toString();


        Toast err;



        if(Contenu.equals("")){
            err= Toast.makeText(getApplicationContext(),"le Contenu est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }


        String data="contenu="+contenu;

        Rest api=new Rest(this);

        api.execute(RestConf.ADD_COM_PATH+this.d.getId()+"/"+this.u.getId(),data,"POST", RestConf.ADD_COM_REQUEST_TYPE);


    }


    public Users getU() {
        return u;
    }

    public void setU(Users u) {
        this.u = u;
    }

    public Demandes getD() {
        return d;
    }

    public void setD(Demandes d) {
        this.d = d;
    }
}

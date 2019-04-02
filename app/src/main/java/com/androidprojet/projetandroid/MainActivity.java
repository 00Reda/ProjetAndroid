package com.androidprojet.projetandroid;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidprojet.projetandroid.Entity.Users;
import com.androidprojet.projetandroid.Rest.Rest;
import com.androidprojet.projetandroid.Rest.RestConf;

public class MainActivity extends AppCompatActivity {
    EditText Email;
    EditText Password;
    Button Connexion;
    Button crateAccount;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Acceuil");


        Intent in=this.getIntent();
        if(in!=null){
            boolean logout=in.getBooleanExtra("logout",false);
            if (logout){
                SharedPreferences sharedpreferences = this.getSharedPreferences(Users.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
            }
        }

        isConnected();

        Email=(EditText)findViewById(R.id.email);
        Password=(EditText)findViewById(R.id.pass);
        Connexion=(Button)findViewById(R.id.connexion);
        crateAccount=findViewById(R.id.newAccount);




       Connexion.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
                login();
           }
       });

        crateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monint =new Intent(MainActivity.this,NextActivity.class);
                startActivity(monint);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add:
                Intent monint =new Intent(MainActivity.this,NextActivity.class);
                startActivity(monint);
                return true;



        }
        if (super.onOptionsItemSelected(item)) return true;
        else return false;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isConnected();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isConnected();
    }

    public void login(){
        String email=this.Email.getText().toString();
        String password=this.Password.getText().toString();

        Toast err;

        if(email.equals("")){
            err=Toast.makeText(getApplicationContext(),"l'email est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }
        if(password.equals("")){
            err=Toast.makeText(getApplicationContext(),"password est un champ obligatoire !",Toast.LENGTH_LONG);
            err.show();
            return;
        }

        Rest restWS= new Rest(this);


        String data="email="+email+"&password="+password;
        restWS.execute(RestConf.LOGIN_PATH,data,"POST", RestConf.LOGIN_REQUEST_TYPE);

    }

    public EditText getEmail() {
        return Email;
    }

    public EditText getPassword() {
        return Password;
    }

    public Button getConnexion() {
        return Connexion;
    }

    public Button getCrateAccount() {
        return crateAccount;
    }

    public ProgressDialog getDialog() {
        return dialog;
    }

    public void CanselDialog(){
        this.dialog.dismiss();
    }

    public void isConnected(){

        SharedPreferences sharedpreferences = getSharedPreferences(Users.MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedpreferences.getInt(Users.ID,-1)!=-1) {
             Users u=new Users();
            u.setNom(sharedpreferences.getString(Users.Nom, ""));
            u.setTel(sharedpreferences.getString(Users.Tel,""));
            u.setEmail(sharedpreferences.getString(Users.Email,""));
            u.setTags(sharedpreferences.getString(Users.Tags,""));
            u.setId(sharedpreferences.getInt(Users.ID,-1));
            u.setRole(sharedpreferences.getString(Users.Role,""));

            Intent toProfileActivity=new Intent(this,PRofileActivity.class);
            toProfileActivity.putExtra("user",u);
            this.startActivity(toProfileActivity);
        }

        return;
    }


}


























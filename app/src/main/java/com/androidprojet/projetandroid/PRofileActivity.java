package com.androidprojet.projetandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.androidprojet.projetandroid.Entity.Demandes;
import com.androidprojet.projetandroid.Entity.Users;
import com.androidprojet.projetandroid.Rest.Rest;
import com.androidprojet.projetandroid.Rest.RestConf;

public class PRofileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


   private Users user=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ListView maListItem = this.findViewById(R.id.maliste);



        isConnected();

        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);

        Intent fromLogin=this.getIntent();
        Users user = (Users) fromLogin.getSerializableExtra("user");
        this.user=user;

        TextView nom=hView.findViewById(R.id.nom);
        TextView email=hView.findViewById(R.id.email);
        TextView tel=hView.findViewById(R.id.tel);
        TextView tags=hView.findViewById(R.id.tags);
        nom.setText(user.getNom());
        email.setText(user.getEmail());
        tel.setText(user.getTel());
        tags.setText(user.getTags());
        this.customMenu(navigationView.getMenu());

        this.SaveUser(user);

        this.loadDataFromServer();

        this.eventListViewManager();

    }

    @Override
    protected void onStart() {
        super.onStart();
        isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isConnected();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isConnected();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit_profile) {

                  if(user.getRole().equals(Users.USER_ROLE_EXPERT)){
                      Intent in =  new Intent(this,ExpertActivity.class);
                      in.putExtra("user",user);
                      this.startActivity(in);
                  }else if(user.getRole().equals(Users.USER_ROLE_CLIENT)){
                      Intent in =  new Intent(this,ClientActivity.class);
                      in.putExtra("user",user);
                      this.startActivity(in);
                  }


            // Handle the camera action
        } else if (id == R.id.nav_addDemande) {

            Intent in =  new Intent(this,AddDemande.class);
            in.putExtra("user",this.user);
            this.startActivity(in);

        } else if (id == R.id.nav_mesDemenade) {

        } else if (id == R.id.nav_logout) {

                  logout();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void SaveUser(Users u){

        SharedPreferences sharedpreferences = getSharedPreferences(Users.MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedpreferences.contains(Users.ID)==true) return;

        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Users.Nom, u.getNom());
        editor.putString(Users.Tel, u.getTel());
        editor.putString(Users.Email, u.getEmail());
        editor.putString(Users.Tags, u.getTags());
        editor.putInt(Users.ID, u.getId());
        editor.putString(Users.Role, u.getRole());

        editor.commit();

    }

    public  void logout(){
        SharedPreferences sharedpreferences = this.getSharedPreferences(Users.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        Intent toLoginActivity=new Intent(this,MainActivity.class);
        this.startActivity(toLoginActivity);

    }

    public void isConnected(){
        SharedPreferences sharedpreferences = getSharedPreferences(Users.MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedpreferences.getInt(Users.ID,-1)!=-1) {
            return  ;
        }else{
            Intent toLoginActivity=new Intent(this,MainActivity.class);

            this.startActivity(toLoginActivity);
        }
    }

    public void customMenu(Menu menu){
        if(user.getRole().equals(Users.USER_ROLE_EXPERT)){
            menu.getItem(2).setVisible(false);

        }
    }



    public void loadDataFromServer(){
        Rest api=new Rest(this);
        if(user.getRole().equals(Users.USER_ROLE_EXPERT)){
            api.execute(RestConf.LIST_DEMANDE_PATH+this.user.getTags(),"","GET", RestConf.LIST_DEMANDE_REQUEST_TYPE);
        }else if(user.getRole().equals(Users.USER_ROLE_CLIENT)){
            api.execute(RestConf.LIST_CLIENT_DEMANDE_PATH+this.user.getId(),"","GET", RestConf.LIST_DEMANDE_CLIENT_REQUEST_TYPE);
        }


    }

    public void eventListViewManager(){
        ListView maList = this.findViewById(R.id.maliste);

        if(user.getRole().equals(Users.USER_ROLE_EXPERT)){
            // load commantaire
            maList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View listItem, int position, long id) {
                    Log.wtf("event ","entrer");
                    TextView ID = (TextView)listItem.findViewById(R.id.id);
                    TextView nom = (TextView)listItem.findViewById(R.id.nom);
                    Demandes m = new Demandes();
                    m.setId(Integer.parseInt(ID.getText().toString()));
                    m.setNom(nom.getText().toString());

                    toAddCommentaire(m);


                }
            });
        }else if(user.getRole().equals(Users.USER_ROLE_CLIENT)){
            Log.wtf("list","her");
            maList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View listItem, int position, long id) {
                    Log.wtf("event ","entrer");
                    TextView nom = (TextView)listItem.findViewById(R.id.nom);
                    TextView desc = (TextView)listItem.findViewById(R.id.description);
                    TextView tags = (TextView)listItem.findViewById(R.id.tags);
                    TextView date = (TextView)listItem.findViewById(R.id.date);
                   // TextView owner = (TextView)listItem.findViewById(R.id.user);
                    TextView etat = (TextView)listItem.findViewById(R.id.etat);
                    TextView ID = (TextView)listItem.findViewById(R.id.id);


                    Demandes m = new Demandes();
                    m.setId(Integer.parseInt(ID.getText().toString()));
                    m.setNom(nom.getText().toString());
                    m.setDescription(desc.getText().toString());
                    m.setTags(tags.getText().toString());
                    m.setDate_ajout(date.getText().toString());
                    m.setUser(null);

                    if(etat.getText().toString().equals("traité")){
                        m.setEtat(1);

                    }else{
                        m.setEtat(0);
                    }

                    ToEtidDemande(m);

                }
            });
        }

    }

    public void ToEtidDemande(Demandes m){
        Intent in =  new Intent(this,AddDemande.class);
        in.putExtra("user",this.user);
        in.putExtra("demande",m);
        this.startActivity(in);
    }

    public void toAddCommentaire(Demandes d){

        Intent in = new Intent(this, AddCommentaireActivity.class);
        in.putExtra("demande",d);
        in.putExtra("user",this.user);
        this.startActivity(in);

    }
    public Users getUser() {
        return user;
    }
}

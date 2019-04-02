package com.androidprojet.projetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidprojet.projetandroid.Entity.Commentaire;

import java.util.ArrayList;

public class ListCommentaireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_commentaire);
        setTitle("list des reponses :");

        Intent in = this.getIntent();
        ArrayList list=(ArrayList) in.getSerializableExtra("com");

        if(list!=null && list.size()>0){

            ArrayAdapter<Commentaire> ad=new ArrayAdapter<Commentaire>(this, android.R.layout.simple_list_item_1, list);
            ListView listView = (ListView) findViewById(R.id.maliste);
            listView.setAdapter(ad);


        }else if(list.size()==0){
            ArrayList l=new ArrayList();
            l.add("pas de commentaire a afficher !!");
            ArrayAdapter<String> ad=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, l);
            ListView listView = (ListView) findViewById(R.id.maliste);
            listView.setAdapter(ad);
        }
    }
}

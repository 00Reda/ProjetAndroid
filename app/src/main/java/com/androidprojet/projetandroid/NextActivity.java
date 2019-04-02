package com.androidprojet.projetandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NextActivity extends AppCompatActivity {

    Button Expert;
    Button Client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_activity);
        setTitle("creation compte");

        Expert=(Button)findViewById(R.id.expert);
        Client=(Button)findViewById(R.id.client);

        Expert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monint =new Intent(NextActivity.this,ExpertActivity.class);
                startActivity(monint);

            }
        });
        Client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent monint =new Intent(NextActivity.this,ClientActivity.class);
                startActivity(monint);

            }
        });
    }
}

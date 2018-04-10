package com.example.parasita.sussemfila;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView botaocadastrese = (TextView) findViewById(R.id.cadastrese);

        botaocadastrese.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent funcaocadastrar = new Intent(MainActivity.this, cadastro.class);
                startActivity(funcaocadastrar);
            }});

    }



}
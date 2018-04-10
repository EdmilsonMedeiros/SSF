package com.example.parasita.sussemfila;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class cadastro extends AppCompatActivity {

    Button voltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button voltar = (Button) findViewById(R.id.bvoltar);

        voltar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent a = new Intent(cadastro.this, MainActivity.class);
                startActivity(a);
            }});

    }

}

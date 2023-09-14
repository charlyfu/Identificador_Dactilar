package com.example.identificadordactilar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    //Boton debito
    public void TarjetaDebito(View view){
        Intent tardeb = new Intent(this, Debito.class);
        startActivity(tardeb);
    }

    //Boton credito
    public void TarjetaCredito(View view){
        Intent tarcredi = new Intent(this, Credito.class);
        startActivity(tarcredi);
    }
}
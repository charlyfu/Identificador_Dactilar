package com.example.identificadordactilar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //Boton ingresar

    public void Ingresar(View view){
        Intent menu = new Intent(this, Home.class);
        startActivity(menu);
    }
}
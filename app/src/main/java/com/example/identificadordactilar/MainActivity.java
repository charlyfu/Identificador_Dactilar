package com.example.identificadordactilar;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101010;

    EditText username;
    EditText password;
    Button loginBoton;
    ImageView imagenHuella;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.txtUsuario);
        password = findViewById(R.id.txtContrasenia);
        loginBoton = findViewById(R.id.btnIniciarSesion);

        imagenHuella = findViewById(R.id.btnHuella);

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Log.e("MY_APP_TAG", "No biometric features available on this device.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                break;
        }

        // Libreria huella digital
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                                "Error en la Autenticación: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Login exitoso, Bienvenido!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(MainActivity.this, Home.class));
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Inicio de sesion en BanCoppel")
                .setSubtitle("Por favor introduce tu huella en el lector")
                .setNegativeButtonText("utiliza patron o contraseña")
                .build();

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.

        imagenHuella.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });


    }


    //Boton ingresar

    public void Ingresar(View view){
        if (username.getText().toString().equals("") && password.getText().toString().equals("")){
            Toast.makeText(this, "Favor de ingresar usuario y contraseña", Toast.LENGTH_SHORT).show();
        }
        else if (username.getText().toString().equals("")){
            Toast.makeText(this, "Capturar el nombre de usuario", Toast.LENGTH_SHORT).show();
        }
        else if (password.getText().toString().equals("")) {
            Toast.makeText(this, "Capturar la contraseña", Toast.LENGTH_SHORT).show();
        }
        else if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            Toast.makeText(this, "Login exitoso, Bienvenido a BanCoppel!", Toast.LENGTH_SHORT).show();
            Intent menu = new Intent(this, Home.class);
            startActivity(menu);
            username.setText("");
            password.setText("");
        }else{
            Toast.makeText(this, "Login fallido", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
            username.setText("");
            password.setText("");
        }
    }
}
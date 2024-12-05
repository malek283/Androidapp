package com.example.insecription;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_EDIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle window insets (optional)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find buttons by their IDs
        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonInscription = findViewById(R.id.buttonInscription);

        // Set onClickListeners for navigation
        buttonLogin.setOnClickListener(v -> {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        });

        buttonInscription.setOnClickListener(v -> {
            Intent inscriptionIntent = new Intent(MainActivity.this, InscriptionActivity.class);
            startActivity(inscriptionIntent);
        });
    }
}

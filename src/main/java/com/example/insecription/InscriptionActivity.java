package com.example.insecription;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscriptionActivity extends AppCompatActivity {

    private EditText etNom, etPrenom, etEmail, etPassword, etRole, etAdresse;
    private CheckBox cbValidated;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        etNom = findViewById(R.id.nom_input);
        etPrenom = findViewById(R.id.prenom_input);
        etEmail = findViewById(R.id.email_input);
        etPassword = findViewById(R.id.password_input);
        etRole = findViewById(R.id.role_input);
        etAdresse = findViewById(R.id.adresse_input);
        cbValidated = findViewById(R.id.validated_checkbox);
        btnSubmit = findViewById(R.id.submit_button);

        btnSubmit.setOnClickListener(v -> inscrireUtilisateur());
    }

    private void inscrireUtilisateur() {
        String nom = etNom.getText().toString().trim();
        String prenom = etPrenom.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String role = etRole.getText().toString().trim();
        String adresse = etAdresse.getText().toString().trim();
        boolean isValidated = cbValidated.isChecked();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty() || adresse.isEmpty()) {
            showErrorAlert("Veuillez remplir tous les champs.");
            return;
        }

        Utilisateur user = new Utilisateur(nom, prenom, email, password, role, isValidated, adresse);
        Log.d("Inscription", "Nom: " + nom);
        Log.d("Inscription", "Prenom: " + prenom);
        Log.d("Inscription", "Email: " + email);
        Log.d("Inscription", "Password: " + password);
        Log.d("Inscription", "Role: " + role);
        Log.d("Inscription", "Adresse: " + adresse);

        Log.d("Inscription", "Utilisateur envoyé: " + user.toString());

        utilisateurService service = ApiClient.getService();
        Call<Void> call = service.inscriptionUtilisateur(user);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showSuccessAlert();
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Erreur inconnue";
                        showErrorAlert(errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                        showErrorAlert("Erreur de traitement.");
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showErrorAlert("Erreur de connexion : " + t.getMessage());
            }
        });
    }

    private void showSuccessAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Inscription Réussie")
                .setMessage("Votre compte a été créé avec succès !")
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(InscriptionActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }

    private void showErrorAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Erreur d'Inscription")
                .setMessage(message)
                .setPositiveButton("Réessayer", null)
                .show();
    }
}

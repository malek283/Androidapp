package com.example.insecription;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifierUtilisateurActivity extends AppCompatActivity {

    private EditText editNom, editPrenom, editEmail, editRole, editAdresse;
    private Button saveButton;
    private int utilisateurId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_utilisateur);

        editNom = findViewById(R.id.edit_name);
        editPrenom = findViewById(R.id.edit_prenom);
        editEmail = findViewById(R.id.edit_email);
        editRole = findViewById(R.id.edit_role);
        editAdresse = findViewById(R.id.edit_adresse);
        saveButton = findViewById(R.id.save_button);

        utilisateurId = getIntent().getIntExtra("utilisateur_id", -1);
        if (utilisateurId == -1) {
            Toast.makeText(this, "ID utilisateur introuvable", Toast.LENGTH_SHORT).show();
            finish();
        }

        loadUtilisateurDetails();

        saveButton.setOnClickListener(v -> updateUtilisateur());
    }

    private void loadUtilisateurDetails() {
        utilisateurService service = ApiClient.getService();
        Call<Utilisateur> call = service.getUtilisateur(utilisateurId);

        call.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Utilisateur utilisateur = response.body();
                    editNom.setText(utilisateur.getName() != null ? utilisateur.getName() : "");
                    editPrenom.setText(utilisateur.getPrenom() != null ? utilisateur.getPrenom() : "");
                    editEmail.setText(utilisateur.getEmail() != null ? utilisateur.getEmail() : "");
                    editRole.setText(utilisateur.getRole() != null ? utilisateur.getRole() : "");
                    editAdresse.setText(utilisateur.getAdresse() != null ? utilisateur.getAdresse() : "");
                } else {
                    Toast.makeText(ModifierUtilisateurActivity.this, "Impossible de charger l'utilisateur", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                Toast.makeText(ModifierUtilisateurActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateUtilisateur() {
        String nouveauNom = editNom.getText().toString().trim();
        String nouveauPrenom = editPrenom.getText().toString().trim();
        String nouveauEmail = editEmail.getText().toString().trim();
        String nouveauRole = editRole.getText().toString().trim();
        String nouvelleAdresse = editAdresse.getText().toString().trim();

        if (nouveauNom.isEmpty() || nouveauPrenom.isEmpty() || nouveauEmail.isEmpty() || nouveauRole.isEmpty() || nouvelleAdresse.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Utilisateur utilisateurMisAJour = new Utilisateur(nouveauNom, nouveauPrenom, nouveauEmail, null, nouveauRole, true, nouvelleAdresse);

        utilisateurService service = ApiClient.getService();
        Call<ResponseBody> call = service.updateUtilisateur(utilisateurId, utilisateurMisAJour);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Envoi des nouvelles valeurs mises à jour
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updated_user_id", utilisateurId);
                    resultIntent.putExtra("updated_nom", nouveauNom);
                    resultIntent.putExtra("updated_prenom", nouveauPrenom);
                    resultIntent.putExtra("updated_email", nouveauEmail);
                    resultIntent.putExtra("updated_role", nouveauRole);
                    resultIntent.putExtra("updated_adresse", nouvelleAdresse);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(ModifierUtilisateurActivity.this, "Erreur de mise à jour", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ModifierUtilisateurActivity.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}



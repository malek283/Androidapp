package com.example.insecription;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.email_input);
        etPassword = findViewById(R.id.password_input);
        btnLogin = findViewById(R.id.login_button);


        btnLogin.setOnClickListener(v -> loginUtilisateur());
    }

    private void loginUtilisateur() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        utilisateurService service = ApiClient.getService(); // Assurez-vous d'utiliser la bonne instance
        Call<Utilisateur> call = service.login(new Login(email, password));

        call.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Intent intent = new Intent(LoginActivity.this, ListUtilisateurActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    if (response.code() == 401) {
                        Toast.makeText(LoginActivity.this, "Échec de la connexion. Vérifiez vos informations.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Erreur serveur, réessayez plus tard.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                // Gestion des erreurs de réseau
                Toast.makeText(LoginActivity.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.insecription;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUtilisateurActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UtilisateurAdapter adapter;
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_utilisateur);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chargerUtilisateurs();
    }

    private void chargerUtilisateurs() {
        utilisateurService service = ApiClient.getService();
        Call<List<Utilisateur>> call = service.getAllUtilisateurs();

        call.enqueue(new Callback<List<Utilisateur>>() {
            @Override
            public void onResponse(Call<List<Utilisateur>> call, Response<List<Utilisateur>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    utilisateurs = response.body();
                    if (adapter == null) {
                        adapter = new UtilisateurAdapter(ListUtilisateurActivity.this, utilisateurs);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(ListUtilisateurActivity.this, "Erreur de récupération des utilisateurs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Utilisateur>> call, Throwable t) {
                Toast.makeText(ListUtilisateurActivity.this, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            chargerUtilisateurs();
        }
    }
}

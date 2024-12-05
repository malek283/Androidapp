package com.example.insecription;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilisateurAdapter extends RecyclerView.Adapter<UtilisateurAdapter.UtilisateurViewHolder> {

    private Context context;
    private List<Utilisateur> utilisateurs;

    public UtilisateurAdapter(Context context, List<Utilisateur> utilisateurs) {
        this.context = context;
        this.utilisateurs = utilisateurs;
    }

    @NonNull
    @Override
    public UtilisateurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_utilisateur, parent, false);
        return new UtilisateurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UtilisateurViewHolder holder, int position) {
        Utilisateur utilisateur = utilisateurs.get(position);
        holder.userId.setText(String.valueOf(utilisateur.getId()));
        holder.nom.setText(utilisateur.getName());
        holder.prenom.setText(utilisateur.getPrenom());
        holder.email.setText(utilisateur.getEmail());
        holder.role.setText(utilisateur.getRole());
        holder.adresse.setText(utilisateur.getAdresse());

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, ModifierUtilisateurActivity.class);
            intent.putExtra("utilisateur_id", utilisateur.getId());
            ((Activity) context).startActivityForResult(intent, 1);
        });

        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirmer la suppression")
                    .setMessage("Voulez-vous vraiment supprimer cet utilisateur ?")
                    .setPositiveButton("Oui", (dialog, which) -> {
                        utilisateurService service = ApiClient.getService();
                        Call<Void> call = service.deleteUtilisateur(utilisateur.getId());

                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {

                                    utilisateurs.remove(position);
                                    notifyItemRemoved(position);
                                    Toast.makeText(context, "Utilisateur supprimé", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(context, "Erreur de connexion : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("Non", null)
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return utilisateurs.size();
    }

    public static class UtilisateurViewHolder extends RecyclerView.ViewHolder {
        TextView userId, nom, prenom, email, role, adresse;
        ImageButton editButton, deleteButton;

        public UtilisateurViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.user_id);
            nom = itemView.findViewById(R.id.user_nom);
            prenom = itemView.findViewById(R.id.user_prenom);
            email = itemView.findViewById(R.id.user_email);
            role = itemView.findViewById(R.id.user_role);
            adresse = itemView.findViewById(R.id.user_adresse);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}

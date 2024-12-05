package com.example.insecription;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface utilisateurService {

    @POST("inscription")

    Call<Void> inscriptionUtilisateur(@Body Utilisateur user);

    @POST("login")
    Call<Utilisateur> login(@Body Login loginRequest);

    @GET("all")
    Call<List<Utilisateur>> getAllUtilisateurs();

    @GET("{id}")
    Call<Utilisateur> getUtilisateur(@Path("id") int id);

    @PUT("{id}")
    Call<ResponseBody> updateUtilisateur(@Path("id") int id, @Body Utilisateur utilisateur);

    @DELETE("{id}")
    Call<Void> deleteUtilisateur(@Path("id") int userId);
}

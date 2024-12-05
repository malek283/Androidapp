package com.example.insecription;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private int id;
    private String name, prenom, email, motDePasse, role, adresse;
    private Boolean isValidated;

    public Utilisateur(String name, String prenom, String email, String motDePasse, String role, Boolean isValidated, String adresse) {
        this.name = name;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.isValidated = isValidated;
        this.adresse = adresse;
    }

    public Utilisateur() {}


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }  // Changed from getNom
    public void setName(String name) { this.name = name; }  // Changed from setNom
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Boolean getValidated() { return isValidated != null && isValidated; }
    public void setValidated(Boolean validated) { isValidated = validated; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "name='" + name + '\'' +  // Changed from nom to name
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", role='" + role + '\'' +
                ", isValidated=" + isValidated +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}

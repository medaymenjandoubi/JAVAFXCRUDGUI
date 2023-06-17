/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crudexamenmatiere.entities;

/**
 *
 * @author 21628
 */
public class MatiereDetails {
        private int id_matiere ;
        private String nom_matiere ;
        private int nombre_heure ;
        private int id_user ;
        private String nom_utilisateur;

    @Override
    public String toString() {
        return "MatiereDetails{" + "id_matiere=" + id_matiere + ", nom_matiere=" + nom_matiere + ", nombre_heure=" + nombre_heure + ", id_user=" + id_user + ", nom_utilisateur=" + nom_utilisateur + '}';
    }

    public MatiereDetails(int id_matiere, String nom_matiere, int nombre_heure, int id_user, String nom_utilisateur) {
        this.id_matiere = id_matiere;
        this.nom_matiere = nom_matiere;
        this.nombre_heure = nombre_heure;
        this.id_user = id_user;
        this.nom_utilisateur = nom_utilisateur;
    }

    public int getId_matiere() {
        return id_matiere;
    }

    public void setId_matiere(int id_matiere) {
        this.id_matiere = id_matiere;
    }

    public String getNom_matiere() {
        return nom_matiere;
    }

    public void setNom_matiere(String nom_matiere) {
        this.nom_matiere = nom_matiere;
    }

    public int getNombre_heure() {
        return nombre_heure;
    }

    public void setNombre_heure(int nombre_heure) {
        this.nombre_heure = nombre_heure;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }
}

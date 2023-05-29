/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crudexamenmatiere.entities;

/**
 *
 * @author 21628
 */
public class Matiere {
    private int id_matiere ;
    private String nom_matiere ;
    private int nombre_heure ;
    private int id_user ;
    
    
    public Matiere(int id_matiere, String nom_matiere, int nombre_heure, int id_user) {
        this.id_matiere = id_matiere;
        this.nom_matiere = nom_matiere;
        this.nombre_heure = nombre_heure;
        this.id_user = id_user;
    }

    public int getId() {
        return id_matiere;
    }

    public void setId(int id_matiere) {
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

    @Override
    public String toString() {
        return "Matiere{" + "id=" + id_matiere + ", nom_matiere=" + nom_matiere + ", nombre_heure=" + nombre_heure + ", id_user=" + id_user + '}';
    }
    
}

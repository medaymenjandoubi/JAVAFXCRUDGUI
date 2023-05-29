/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crudexamenmatiere.entities;
import java.util.Date ;

/**
 *
 * @author 21628
 */


public class Examen {
    private int id_examen ;
    private Date date_examen;
    private int id_matiere ;
    private int id_classe ;
    private int id_salle ;
    
    
    public Examen(int id_examen, Date date_examen, int id_matiere, int id_classe, int id_salle) {
        this.id_examen = id_examen;
        this.date_examen = date_examen;
        this.id_matiere = id_matiere;
        this.id_classe = id_classe;
        this.id_salle = id_salle;
    }

    @Override
    public String toString() {
        return "Examen{" + "id_examen=" + id_examen + ", date_examen=" + date_examen + ", id_matiere=" + id_matiere + ", id_classe=" + id_classe + ", id_salle=" + id_salle + '}';
    }

    public int getId_examen() {
        return id_examen;
    }

    public void setId_examen(int id_examen) {
        this.id_examen = id_examen;
    }

    public Date getDate_examen() {
        return date_examen;
    }

    public void setDate_examen(Date date_examen) {
        this.date_examen = date_examen;
    }

    public int getId_matiere() {
        return id_matiere;
    }

    public void setId_matiere(int id_matiere) {
        this.id_matiere = id_matiere;
    }

    public int getId_classe() {
        return id_classe;
    }

    public void setId_classe(int id_classe) {
        this.id_classe = id_classe;
    }

    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }

}

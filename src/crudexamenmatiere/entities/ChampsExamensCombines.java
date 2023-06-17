/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crudexamenmatiere.entities;

import java.sql.Date;

/**
 *
 * @author 21628
 */
public class ChampsExamensCombines {

        private int idExamen;
        private Date dateExamen;
        private String nomMatiere;
        private String nomClasse;
        private int numeroSalle;
        
    public ChampsExamensCombines(int idExamen, Date dateExamen, String nomMatiere, String nomClasse, int numeroSalle) {
        this.idExamen = idExamen;
        this.dateExamen = dateExamen;
        this.nomMatiere = nomMatiere;
        this.nomClasse = nomClasse;
        this.numeroSalle = numeroSalle;
    }


    @Override
    public String toString() {
        return "ChampsExamensCombines{" + "idExamen=" + idExamen + ", dateExamen=" + dateExamen + ", nomMatiere=" + nomMatiere + ", nomClasse=" + nomClasse + ", numeroSalle=" + numeroSalle + '}';
    }

    public int getIdExamen() {
        return idExamen;
    }

    public void setIdExamen(int idExamen) {
        this.idExamen = idExamen;
    }

    public Date getDateExamen() {
        return dateExamen;
    }

    public void setDateExamen(Date dateExamen) {
        this.dateExamen = dateExamen;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public String getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    public int getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(int numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

}
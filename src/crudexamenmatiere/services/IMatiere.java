/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package crudexamenmatiere.services;


import crudexamenmatiere.entities.Matiere;
import java.util.List;

/**
 *
 * @author 21628
 */
public interface IMatiere<T> {
    
    public void ajouter(T m);
    public void modifier(T m);
    public void supprimer(T m);
    public List<T> afficher();
}
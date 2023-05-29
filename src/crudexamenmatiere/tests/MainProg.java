/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crudexamenmatiere.tests;

import crudexamenmatiere.entities.Matiere;
import crudexamenmatiere.services.MatiereService;

/**
 *
 * @author 21628
 */
public class MainProg {
        public static void main(String[] args) {
        System.out.println("bonjour ala ");
        MatiereService es2 = new MatiereService();
        System.out.println("bonjour ala 1");
        es2.ajouter(new Matiere(1,"date", 1, 10));
        System.out.println("bonjour ala b");
    }
}

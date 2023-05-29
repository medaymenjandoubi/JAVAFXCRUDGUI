/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package crudexamenmatiere;

import crudexamenmatiere.entities.Examen;
import crudexamenmatiere.entities.Matiere;
import crudexamenmatiere.services.ExamenService;
import crudexamenmatiere.services.MatiereService;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import static java.time.Clock.system;
import java.util.Date;
import utils.DataSource;
/**
 *
 * @author 21628
 */
public class Crudexamenmatiere {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("bonjour ala ");
        MatiereService es2 = new MatiereService();
        System.out.println("bonjour ala 1");
        es2.ajouter(new Matiere(1,"date", 1, 10));
        System.out.println("bonjour ala b");
    }
    
}

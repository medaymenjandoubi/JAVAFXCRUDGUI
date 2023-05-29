/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crudexamenmatiere.services;
import crudexamenmatiere.entities.Examen;
import utils.DataSource ;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 21628
 */
public class ExamenService implements IExamen<Examen> {
    private  Connection cnx = DataSource.getInstance().getCnx() ;
    private List<Examen> listeDesExamens;
    public void ajouter(Examen m){
        try{
            String req = "INSERT INTO examen(date_examen, id_matiere, id_classe, id_salle) VALUES ('"
                    +m.getDate_examen()+"','"+m.getId_matiere() +"','"+m.getId_classe()+"','"+m.getId_salle() +"');";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("examen ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void modifier(Examen m) {
        try {
            String req = "UPDATE examen SET date_examen='"
                    +m.getDate_examen()+"', id_matiere='"+ m.getId_matiere() + "', id_classe='"+ m.getId_classe()  + "', id_salle='"+ m.getId_salle()
                    +"' WHERE id_examen="+m.getId_examen() ;
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("examen modifiée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void supprimer(Examen m) {
        try {
            String req = "DELETE from examen WHERE id_examen="+m.getId_examen();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("examen supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
 
    public List<Examen> afficher() {
        List<Examen> list = new ArrayList<>();
        
        String req = "SELECT * FROM examen";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                list.add(new Examen(rs.getInt("id_examen"),rs.getDate("date_examen"), rs.getInt("id_matiere"), rs.getInt("id_classe"), rs.getInt("id_salle")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
    public Examen getExamenById(int examenId) {
    String req = "SELECT * FROM examen WHERE id_examen = ?";
    try (PreparedStatement st = cnx.prepareStatement(req)) {
        st.setInt(1, examenId);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return new Examen(rs.getInt("id_examen"), rs.getDate("date_examen"), rs.getInt("id_matiere"), rs.getInt("id_classe"), rs.getInt("id_salle"));
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return null;
}
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crudexamenmatiere.services;
import crudexamenmatiere.entities.Matiere ;
import crudexamenmatiere.services.IMatiere;
import utils.DataSource ;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 21628
 */
public class MatiereService implements IMatiere<Matiere> {
    private  Connection cnx = DataSource.getInstance().getCnx() ;
    
    public void ajouter(Matiere m){
        try{
            String req = "INSERT INTO matiere(nom_matiere, nombre_heure, id_user) VALUES ('"
                    +m.getNom_matiere()+"','"+m.getNombre_heure()+"','"+m.getId_user() +"');";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Matiere ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void modifier(Matiere m) {
        try {
            String req = "UPDATE matiere SET nom_matiere='"
                    +m.getNom_matiere()+"', nombre_heure='"+ m.getNombre_heure() + "', id_user='"+ m.getId_user()
                    +"' WHERE id_matiere="+m.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Matiere modifiée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public void supprimer(Matiere m) {
        try {
            String req = "DELETE from matiere WHERE id_matiere="+m.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Matiere supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<Matiere> afficher() {
        List<Matiere> list = new ArrayList<>();
        
        String req = "SELECT * FROM matiere";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                list.add(new Matiere(rs.getInt("id_matiere"), rs.getString("nom_matiere"), rs.getInt("nombre_heure"), rs.getInt("id_user")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
}

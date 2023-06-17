/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crudexamenmatiere.services;
import crudexamenmatiere.entities.ChampsExamensCombines;
import crudexamenmatiere.entities.Matiere ;
import crudexamenmatiere.entities.MatiereDetails;
import crudexamenmatiere.services.IMatiere;
import utils.DataSource ;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
/**
 *
 * @author 21628
 */
public class MatiereService implements IMatiere<Matiere> {
    private  Connection cnx = DataSource.getInstance().getCnx() ;

  public void ajouter(String nomMatiere, int nombreHeure, int idUser) {
        String sql = "INSERT INTO matiere (nom_matiere, nombre_heure, id_user) VALUES (?, ?, ?)";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, nomMatiere);
            statement.setInt(2, nombreHeure);
            statement.setInt(3, idUser);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception selon vos besoins
        }
    }


    @Override
    public void modifier(Matiere m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void supprimer(Matiere m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    public List<MatiereDetails> afficher() {
    List<MatiereDetails> list = new ArrayList<>();

    String req =     "SELECT m.id_matiere, m.nom_matiere, m.nombre_heure, u.nom , m.id_user " +
                     "FROM Matiere m " +
                     "JOIN user u ON m.id_user = u.id_user " ;
    try {
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            int IdUser =rs.getInt("id_user");
            int NbrHeure = rs.getInt("nombre_heure");
            String NomMatiere = rs.getString("nom_matiere");
            int idMatiere = rs.getInt("id_matiere");
            String nomUser = rs.getString("nom");
            MatiereDetails detailsMatiere = new MatiereDetails( idMatiere, NomMatiere, NbrHeure, IdUser,nomUser);
            list.add(detailsMatiere);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return list;
}
    
    
    public List<Pair<Integer, String>> getNomUtilisateursEnseignants() {
        List<Pair<Integer, String>> nomsUtilisateurs = new ArrayList<>();

        String req = "SELECT id_user, nom FROM user WHERE role = 'enseignant'";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                int idUser = rs.getInt("id_user");
                String nomUtilisateur = rs.getString("nom");
                Pair<Integer, String> utilisateur = new Pair<>(idUser, nomUtilisateur);
                nomsUtilisateurs.add(utilisateur);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return nomsUtilisateurs;
    }
    public void supprimer(int idMatiere) {
        String req = "DELETE FROM matiere WHERE id_matiere = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, idMatiere);
            pst.executeUpdate();
            System.out.println("Matiere supprimée avec succès.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
public void updateMatiere(int idMatiere, String nomMatiere, int nombreHeure, int idUtilisateur) {
    String query = "UPDATE matiere SET nom_matiere = ?, nombre_heure = ?, id_user = ? WHERE id_matiere = ?";
    
    try (PreparedStatement statement = cnx.prepareStatement(query)) {
        statement.setString(1, nomMatiere);
        statement.setInt(2, nombreHeure);
        statement.setInt(3, idUtilisateur);
        statement.setInt(4, idMatiere);
        
        statement.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    @Override
    public void ajouter(Matiere m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


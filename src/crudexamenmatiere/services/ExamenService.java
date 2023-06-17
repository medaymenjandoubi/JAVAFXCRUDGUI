/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crudexamenmatiere.services;
import crudexamenmatiere.entities.ChampsExamensCombines;
import crudexamenmatiere.entities.Examen;
import utils.DataSource ;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 21628
 */
    public class ExamenService implements IExamen<Examen> {
    private  Connection cnx = DataSource.getInstance().getCnx() ;

//    public void modifier(Examen m) {
//         try {
//            String req = "UPDATE examen SET date_examen='"
//                    +m.getDate_examen()+"', id_matiere='"+ m.getId_matiere() + "', id_classe='"+ m.getId_classe()  + "', id_salle='"+ m.getId_salle()
//                    +"' WHERE id_examen="+m.getId_examen() ;
//            Statement st = cnx.createStatement();
//            st.executeUpdate(req);
//            System.out.println("examen modifiée !");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }

    public List<String> getNomMatieres() {
    List<String> matieres = new ArrayList<>();
    String req = "SELECT nom_matiere FROM matiere";
    try {
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            String nomMatiere = rs.getString("nom_matiere");
            matieres.add(nomMatiere);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return matieres;
}
    public List<ChampsExamensCombines> afficher() {
    List<ChampsExamensCombines> list = new ArrayList<>();

    String req = "SELECT e.id_examen, e.date_examen, m.nom_matiere, c.nom_classe, s.numero_salle "
               + "FROM examen e "
               + "JOIN matiere m ON e.id_matiere = m.id_matiere "
               + "JOIN classe c ON e.id_classe = c.id_classe "
               + "JOIN salle s ON e.id_salle = s.id_salle";

    try {
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            int idExamen = rs.getInt("id_examen");
            Date dateExamen = rs.getDate("date_examen");
            String nomMatiere = rs.getString("nom_matiere");
            String nomClasse = rs.getString("nom_classe");
            int numeroSalle = rs.getInt("numero_salle");
            ChampsExamensCombines details = new ChampsExamensCombines(idExamen, dateExamen, nomMatiere, nomClasse, numeroSalle);
            list.add(details);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return list;
}
     public List<String> getAllClasses() {
        List<String> classes = new ArrayList<>();
        String query = "SELECT nom_classe FROM classe";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String classe = resultSet.getString("nom_classe");
                classes.add(classe);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return classes;
    }
     
     public List<Integer> getAllSalles() {
        List<Integer> salles = new ArrayList<>();
        String query = "SELECT numero_salle FROM salle";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int salle = resultSet.getInt("numero_salle");
                salles.add(salle);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return salles;
    }

    public int getIdMatiereByNom(String nomMatiere) {
        String req = "SELECT id_matiere FROM matiere WHERE nom_matiere = ?";
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, nomMatiere);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_matiere");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    public int getIdClasseByNom(String nomClasse) {
        String req = "SELECT id_classe FROM classe WHERE nom_classe = ?";
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, nomClasse);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_classe");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    public int getIdSalleByNumero(int numeroSalle) {
        String req = "SELECT id_salle FROM salle WHERE numero_salle = ?";
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, numeroSalle);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_salle");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    /*    @Override
    public List afficher() {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/


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

    public int getIdExamenByDetails(String dateExamen, String nomMatiere, String nomClasse, int numeroSalle) {
    try {
        String sql = "SELECT e.id_examen "
                + "FROM examen e "
                + "JOIN matiere m ON e.id_matiere = m.id_matiere "
                + "JOIN classe c ON e.id_classe = c.id_classe "
                + "JOIN salle s ON e.id_salle = s.id_salle "
                + "WHERE e.date_examen = ? "
                + "AND m.nom_matiere = ? "
                + "AND c.nom_classe = ? "
                + "AND s.numero_salle = ?";
        
        PreparedStatement stmt = cnx.prepareStatement(sql);
        stmt.setString(1, dateExamen);
        stmt.setString(2, nomMatiere);
        stmt.setString(3, nomClasse);
        stmt.setInt(4, numeroSalle);
        
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            int idExamen = rs.getInt("id_examen");
            return idExamen;
        } else {
            // L'examen n'a pas été trouvé
            return -1;
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        return -1;
    }
}
    public void supprimerExamenById(String dateExamen, String nomMatiere, String nomClasse, int numeroSalle) {
    int idExamen = getIdExamenByDetails(dateExamen, nomMatiere, nomClasse, numeroSalle);
    
    if (idExamen != -1) {
        try {
            String sql = "DELETE FROM examen WHERE id_examen = ?";
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, idExamen);
            stmt.executeUpdate();
            System.out.println("L'examen a été supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    } else {
        System.out.println("L'examen n'a pas été trouvé !");
    }
}
//    public void modifierExamen(String dateExamen, String nomMatiere, String nomClasse, int numeroSalle, int nouvelIdMatiere, int nouvelIdClasse, int nouvelIdSalle, Date nouvelleDateExamen) {
//    int idExamen = getIdExamenByDetails(dateExamen, nomMatiere, nomClasse, numeroSalle);
//
//    if (idExamen != -1) {
//        try {
//            String sql = "UPDATE examen SET id_matiere = ?, id_classe = ?, id_salle = ?, date_examen = ? WHERE id_examen = ?";
//            PreparedStatement stmt = cnx.prepareStatement(sql);
//            stmt.setInt(1, idExamen);
//            stmt.setDate(2, nouvelleDateExamen);
//            stmt.setInt(3, nouvelIdMatiere);
//            stmt.setInt(4, nouvelIdClasse);
//            stmt.setInt(5, nouvelIdSalle);
//            stmt.executeUpdate();
//            System.out.println("L'examen a été modifié avec succès !");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    } else {
//        System.out.println("L'examen n'a pas été trouvé !");
//    }
//}

    public void ajouterexamen( LocalDate dateExamen, int idMatiere, int idClasse, int idSalle) {
       try {
        String sql = "INSERT INTO examen (date_examen, id_matiere, id_classe, id_salle) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = cnx.prepareStatement(sql);
        stmt.setDate(1, java.sql.Date.valueOf(dateExamen));
        stmt.setInt(2, idMatiere);
        stmt.setInt(3, idClasse);
        stmt.setInt(4, idSalle);
           System.out.println(idMatiere);
        int rowsAffected = stmt.executeUpdate();
        System.out.println(rowsAffected + " L'examens a été correctement ajouter.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }

    @Override
    public void modifier(Examen m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void supprimer(Examen m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public int getIdSalle(int numeroSalle) {
        int idSalle = -1;

        try {
            String sql = "SELECT id_salle FROM salle WHERE numero_salle = ?";
            PreparedStatement stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, numeroSalle);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                idSalle = resultSet.getInt("id_salle");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return idSalle;
    }
    public int getIdMatiere(String nomMatiere) {
    int idMatiere = -1;

    try {
        String sql = "SELECT id_matiere FROM matiere WHERE nom_matiere = ?";
        PreparedStatement stmt = cnx.prepareStatement(sql);
        stmt.setString(1, nomMatiere);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            idMatiere = resultSet.getInt("id_matiere");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return idMatiere;
}

public int getIdClasse(String nomClasse) {
    int idClasse = -1;

    try {
        String sql = "SELECT id_classe FROM classe WHERE nom_classe = ?";
        PreparedStatement stmt = cnx.prepareStatement(sql);
        stmt.setString(1, nomClasse);
        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            idClasse = resultSet.getInt("id_classe");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return idClasse;
}
public void modifierExamens(int idExamen, LocalDate dateExamen, int idMatiere, int idClasse, int idSalle) {
    try {
        String sql = "UPDATE examen SET date_examen = ?, id_matiere = ?, id_classe = ?, id_salle = ? WHERE id_examen = ?";
        PreparedStatement stmt = cnx.prepareStatement(sql);
        stmt.setDate(1, java.sql.Date.valueOf(dateExamen));
        stmt.setInt(2, idMatiere);
        stmt.setInt(3, idClasse);
        stmt.setInt(4, idSalle);
        stmt.setInt(5, idExamen);

        int rowsAffected = stmt.executeUpdate();
        System.out.println(rowsAffected + " L'examens a été correctement mis a jours.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

    @Override
    public void ajouter(Examen m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}


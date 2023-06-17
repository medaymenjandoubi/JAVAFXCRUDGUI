/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package crudexamenmatiere.gui;

import crudexamenmatiere.entities.ChampsExamensCombines;
import crudexamenmatiere.entities.MatiereDetails;
import crudexamenmatiere.services.MatiereService;
import java.awt.Insets;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
/**
 * FXML Controller class
 *
 * @author 21628
 */
public class MatiereFXMLController implements Initializable {

    @FXML
    private TableView<MatiereDetails> Matiere;
    private MatiereService matiereService;
    @FXML
    private TableColumn<MatiereDetails, String> NomUserColumn;
    @FXML
    private TableColumn<MatiereDetails, String> nomMatiereColumn;
    @FXML
    private TableColumn<MatiereDetails, String> actionsColumn;
    @FXML
    private TableColumn<MatiereDetails, Integer> NbrHeureColumn;
    private ObservableList<MatiereDetails> MatiereObservable;
    @FXML
    private ComboBox<String> NomEnseignantComboBox;
    @FXML
    private TextField nomMatiereTextField;
    private List<Pair<Integer, String>> utilisateurs;
    @FXML
    private TextField nombreHeureTextField;
    @FXML
    private Button ajouterBtn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        matiereService = new MatiereService(); // Assign the instance to the member variable
        NomUserColumn.setCellValueFactory(new PropertyValueFactory<>("nom_utilisateur"));
        nomMatiereColumn.setCellValueFactory(new PropertyValueFactory<>("nom_matiere"));
        NbrHeureColumn.setCellValueFactory(new PropertyValueFactory<>("nombre_heure"));
        ajouterBtn.setOnAction(this::handleAjouterButtonAction);
        actionsColumn.setCellFactory(column -> {
    return new TableCell<MatiereDetails, String>() {
        private final Button deleteButton = new Button("Supprimer");

        {
            deleteButton.setOnAction(event -> {
                MatiereDetails item = getTableView().getItems().get(getIndex());
                int idMatiere = item.getId_matiere();
                showConfirmationDialog(idMatiere);
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(deleteButton);
            }
        }
    };
});
        Matiere.setRowFactory(tv -> {
            TableRow<MatiereDetails> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !row.isEmpty()) {
                MatiereDetails matiere = row.getItem();
                showModificationDialog(matiere);
            }
        });
        return row;
    });
        refreshMatiereTable();
//        matiereService.afficher();
        populateNomEnseignantComboBox();
    }


    private void refreshMatiereTable() {
        List<MatiereDetails> Matieres = matiereService.afficher();
        MatiereObservable = FXCollections.observableArrayList(Matieres);
        Matiere.setItems(MatiereObservable);
    }
private void populateNomEnseignantComboBox() {
    
    MatiereService matiereService = new MatiereService();
    this.utilisateurs = matiereService.getNomUtilisateursEnseignants();

    List<String> nomsUtilisateurs = utilisateurs.stream()
            .map(Pair::getValue)
            .collect(Collectors.toList());

    NomEnseignantComboBox.setItems(FXCollections.observableArrayList(nomsUtilisateurs));

    NomEnseignantComboBox.setOnAction(event -> {
        String nomUtilisateur = NomEnseignantComboBox.getSelectionModel().getSelectedItem();
        int idUtilisateur = utilisateurs.stream()
                .filter(pair -> pair.getValue().equals(nomUtilisateur))
                .findFirst()
                .map(Pair::getKey)
                .orElse(-1); // Default value if ID is not found
        // Do whatever you want with the selected user ID
        System.out.println("Selected user ID: " + idUtilisateur);
    });
}

@FXML
private void handleAjouterButtonAction(ActionEvent event) {

    String nomMatiere = nomMatiereTextField.getText();
    String nombreHeure = nombreHeureTextField.getText();
    String nomUtilisateur = NomEnseignantComboBox.getSelectionModel().getSelectedItem();
    
    if (nomMatiere.isEmpty() || nombreHeure.isEmpty() || nomUtilisateur == null) {
        // Affichage d'un message d'erreur si un champ est manquant
        showAlert("Champ(s) manquant(s)", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
    } else {
        // Récupération de l'ID utilisateur correspondant au nom sélectionné
        int idUtilisateur = utilisateurs.stream()
                .filter(pair -> pair.getValue().equals(nomUtilisateur))
                .findFirst()
                .map(Pair::getKey)
                .orElse(-1); // Valeur par défaut si l'ID n'est pas trouvé

        // Appel de la méthode ajouter du service en passant l'ID utilisateur
        matiereService.ajouter(nomMatiere, Integer.parseInt(nombreHeure), idUtilisateur);
        
        // Réinitialisation des champs
        nomMatiereTextField.clear();
        nombreHeureTextField.clear();
        NomEnseignantComboBox.getSelectionModel().clearSelection();

        // Actualisation de la table ou d'autres opérations si nécessaire
        refreshMatiereTable();

        // Affichage d'un message de succès
        showAlert("Succès", "La matière a été ajoutée avec succès.", Alert.AlertType.INFORMATION);
    }
}
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showConfirmationDialog(int idMatiere) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette matière ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            matiereService.supprimer(idMatiere);
            refreshMatiereTable();
        }
    }
    private void populateNomEnseignantComboBoxForModificationDialog(ComboBox<String> comboBox) {
    MatiereService matiereService = new MatiereService();

    List<Pair<Integer, String>> utilisateurs = matiereService.getNomUtilisateursEnseignants();

    List<String> nomsUtilisateurs = utilisateurs.stream()
            .map(Pair::getValue)
            .collect(Collectors.toList());

    comboBox.setItems(FXCollections.observableArrayList(nomsUtilisateurs));
}
private void showModificationDialog(MatiereDetails matiere) {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Modify Matiere");
    dialog.setHeaderText("Modify Matiere Details");

    // Create fields for modifying matiere details
    TextField nomMatiereField = new TextField(matiere.getNom_matiere());
    TextField nombreHeureField = new TextField(String.valueOf(matiere.getNombre_heure()));
    ComboBox<String> nomEnseignantComboBox = new ComboBox<>();
    populateNomEnseignantComboBoxForModificationDialog(nomEnseignantComboBox);
    nomEnseignantComboBox.getSelectionModel().select(matiere.getNom_utilisateur());

    // Add fields to the dialog layout
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    javafx.geometry.Insets insets = new javafx.geometry.Insets(10, 10, 10, 10);

    grid.addRow(0, new Label("Nom Matiere:"), nomMatiereField);
    grid.addRow(1, new Label("Nombre Heure:"), nombreHeureField);
    grid.addRow(2, new Label("Nom Enseignant:"), nomEnseignantComboBox);

    dialog.getDialogPane().setContent(grid);

    // Add buttons to the dialog
    ButtonType confirmButtonType = new ButtonType("Modify", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

   dialog.setResultConverter(dialogButton -> {
        if (dialogButton == confirmButtonType) {
            String nomMatiere = nomMatiereField.getText();
            int nombreHeure = Integer.parseInt(nombreHeureField.getText());
            int idUtilisateur = utilisateurs.stream()
                .filter(pair -> pair.getValue().equals(nomEnseignantComboBox.getValue()))
                .findFirst()
                .map(Pair::getKey)
                .orElse(-1); // Valeur par défaut si l'ID n'est pas trouvé

            // Mise à jour de la matière dans la base de données en utilisant la méthode updateMatiere de MatiereService
            MatiereService matiereService = new MatiereService();
            matiereService.updateMatiere(matiere.getId_matiere(), nomMatiere, nombreHeure, idUtilisateur);

            // Rafraîchir le tableau des matières
            refreshMatiereTable();
        }
        return null;
    });

    dialog.showAndWait();
}
}

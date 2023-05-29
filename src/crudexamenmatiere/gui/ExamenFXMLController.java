package crudexamenmatiere.gui;

import crudexamenmatiere.entities.Examen;
import crudexamenmatiere.services.ExamenService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.ButtonType;

public class ExamenFXMLController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Examen> examen;
    @FXML
    private TableColumn<Examen, Integer> idExamenColumn;
    @FXML
    private TableColumn<Examen, Date> dateExamenColumn;
    @FXML
    private TableColumn<Examen, Integer> idMatiereColumn;
    @FXML
    private TableColumn<Examen, Integer> idClasseColumn;
    @FXML
    private TableColumn<Examen, Integer> idSalleColumn;
    @FXML
    private TableColumn<Examen, Void> actionsColumn;

    private ExamenService examenService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        examenService = new ExamenService();

        idExamenColumn.setCellValueFactory(new PropertyValueFactory<>("id_examen"));
        dateExamenColumn.setCellValueFactory(new PropertyValueFactory<>("date_examen"));
        idMatiereColumn.setCellValueFactory(new PropertyValueFactory<>("id_matiere"));
        idClasseColumn.setCellValueFactory(new PropertyValueFactory<>("id_classe"));
        idSalleColumn.setCellValueFactory(new PropertyValueFactory<>("id_salle"));
        actionsColumn.setCellFactory(param -> new ActionButtonTableCell());

        refreshExamenTable();
    }

    private void refreshExamenTable() {
        List<Examen> examens = examenService.afficher();
        ObservableList<Examen> examensObservable = FXCollections.observableArrayList(examens);
        examen.setItems(examensObservable);
    }

    @FXML
    private void ajouterExamen(ActionEvent event) throws IOException {
        ExamenService es = new ExamenService();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate != null) {
            Date date = java.sql.Date.valueOf(selectedDate);
            es.ajouter(new Examen(1, date, 1, 1, 1));
            showAlert(Alert.AlertType.INFORMATION, "Examen ajouté !");
            refreshExamenTable();
        } else {
            showAlert(Alert.AlertType.ERROR, "Veuillez sélectionner une date !");
        }
    }

    @FXML
    private void modifierExamen(ActionEvent event) throws IOException {
        ExamenService es = new ExamenService();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modification de l'examen");
        dialog.setHeaderText(null);
        dialog.setContentText("Entrez l'ID de l'examen à modifier :");
        dialog.showAndWait();

        String input = dialog.getResult();
        if (input != null && !input.isEmpty()) {
            int examenId = Integer.parseInt(input);
            Examen examen = es.getExamenById(examenId);

            if (examen != null) {
                LocalDate selectedDate = datePicker.getValue();
                if (selectedDate != null) {
                    Date date = java.sql.Date.valueOf(selectedDate);
                    examen.setDate_examen(date);

                    es.modifier(examen);
                    showAlert(Alert.AlertType.INFORMATION, "Examen modifié !");
                    refreshExamenTable();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Veuillez sélectionner une date !");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "L'examen avec l'ID spécifié n'existe pas !");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Veuillez entrer un ID d'examen valide !");
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private class ActionButtonTableCell extends TableCell<Examen, Void> {
        private final HBox cellContainer;
        private final Button deleteButton;

        public ActionButtonTableCell() {
            cellContainer = new HBox();
            deleteButton = new Button("Supprimer");
            deleteButton.setOnAction(event -> {
                Examen examen = (Examen) getTableRow().getItem();
                if (examen != null) {
                    showDeleteConfirmation(examen);
                }
            });
            cellContainer.getChildren().add(deleteButton);
            cellContainer.setSpacing(10);
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setGraphic(cellContainer);
            } else {
                setGraphic(null);
            }
        }

        private void showDeleteConfirmation(Examen examen) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Supprimer l'examen");
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cet examen ?");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                examenService.supprimer(examen);
                showAlert(Alert.AlertType.INFORMATION, "Examen supprimé !");
                refreshExamenTable();
            }
        }
    }
}







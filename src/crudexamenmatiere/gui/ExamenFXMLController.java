package crudexamenmatiere.gui;

import javafx.scene.control.ComboBox;
import crudexamenmatiere.entities.ChampsExamensCombines;
import crudexamenmatiere.services.ExamenService;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;



public class ExamenFXMLController implements Initializable {

    @FXML
    private TableView<ChampsExamensCombines> examen;
    @FXML
    private TableColumn<ChampsExamensCombines, Date> dateExamenColumn;
    @FXML
    private TableColumn<ChampsExamensCombines, String> nomMatiereColumn;
    @FXML
    private TableColumn<ChampsExamensCombines, String> nomClasseColumn;
    @FXML
    private TableColumn<ChampsExamensCombines, Integer> numeroSalleColumn;
    @FXML
    private TableColumn<ChampsExamensCombines, Void> actionsColumn;

    private ExamenService examenService;
    @FXML
    private ComboBox<String> matiereComboBox;
    @FXML
    private ComboBox<String> classeComboBox;
    @FXML
    private ComboBox<Integer> salleComboBox;
    @FXML
    private ComboBox<String> matiereComboBoxx;
    @FXML
    private ComboBox<String> classeComboBoxx;
    @FXML
    private ComboBox<Integer> salleComboBoxx;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableColumn<ChampsExamensCombines, Void> modifierColumn;
    @FXML
    private Button btnAdd;
    private ObservableList<ChampsExamensCombines> examensObservable;
    @FXML
    private Button btnSort;
    @FXML
    private Button convertpdf;
    @FXML
    private RadioButton radioBtnOption1;
    @FXML
    private RadioButton radioBtnOption2;
    @FXML
    private RadioButton radioBtnOption3;
    @FXML
    private RadioButton radioBtnOption4;
    private ToggleGroup toggleGroup;
    @FXML
    private Button filtrercolumn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        examenService = new ExamenService();
        btnAdd.setOnAction(this::ajouterExamen);
        btnSort.setOnAction(this::handleSortByButton);
        filtrercolumn.setOnAction(this::handlerfiltrebybutton);
        toggleGroup = new ToggleGroup();
        matiereComboBoxx.setVisible(false);
        salleComboBoxx.setVisible(false);
        classeComboBoxx.setVisible(false);
        radioBtnOption1.setToggleGroup(toggleGroup);
        radioBtnOption2.setToggleGroup(toggleGroup);
        radioBtnOption3.setToggleGroup(toggleGroup);
        radioBtnOption4.setToggleGroup(toggleGroup);
        radioBtnOption4.setVisible(false);
        radioBtnOption3.setVisible(false);
        radioBtnOption2.setVisible(false);
        radioBtnOption1.setVisible(false);
        dateExamenColumn.setCellValueFactory(new PropertyValueFactory<>("dateExamen"));
        nomMatiereColumn.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));
        nomClasseColumn.setCellValueFactory(new PropertyValueFactory<>("nomClasse"));
        numeroSalleColumn.setCellValueFactory(new PropertyValueFactory<>("numeroSalle"));
        actionsColumn.setCellFactory(param -> new TableCell<ChampsExamensCombines, Void>() {
            
            private final Button deleteButton = new Button("Supprimer");

            {   
               
                deleteButton.setOnAction(event -> {
                    ChampsExamensCombines examen = getTableView().getItems().get(getIndex());
                    String dateExamen = examen.getDateExamen().toString();
                    String nomMatiere = examen.getNomMatiere();
                    String nomClasse = examen.getNomClasse();
                    int numeroSalle = examen.getNumeroSalle();

                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirmation de suppression");
                    confirmationAlert.setHeaderText("Supprimer l'examen");
                    confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cet examen ?");

                    Optional<ButtonType> result = confirmationAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        examenService.supprimerExamenById(dateExamen, nomMatiere, nomClasse, numeroSalle);
                        refreshExamenTable();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        examen.setRowFactory(tv -> {
           TableRow<ChampsExamensCombines> row = new TableRow<>();
           row.setOnMouseClicked(event -> {
               if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !row.isEmpty()) {
                           matiereComboBoxx.setVisible(true);
                           salleComboBoxx.setVisible(true);
                           classeComboBoxx.setVisible(true);
                   ChampsExamensCombines examen = row.getItem();
                   showExamDetails(examen);
               }
           });
           return row;
       });
        refreshExamenTable();
        populateMatiereComboBox();
        populateClasseComboBox();
        populateSalleComboBox();
    }

    private void populateMatiereComboBox() {
        List<String> matieres = examenService.getNomMatieres();
        matieres.add(0, null); 
        matiereComboBox.setItems(FXCollections.observableArrayList(matieres));
        matiereComboBoxx.setItems(FXCollections.observableArrayList(matieres));

    }

    private void populateClasseComboBox() {
        List<String> classeList = examenService.getAllClasses();
        classeList.add(0, null); 
        classeComboBox.getItems().addAll(classeList);
        classeComboBoxx.getItems().addAll(classeList);

    }

    private void populateSalleComboBox() {
        List<Integer> salleList = examenService.getAllSalles();
        salleList.add(0, null); 
        salleComboBox.getItems().addAll(salleList);
        salleComboBoxx.getItems().addAll(salleList);

    }
    

    private void refreshExamenTable() {
        List<ChampsExamensCombines> examens = examenService.afficher();
        examensObservable = FXCollections.observableArrayList(examens);
        examen.setItems(examensObservable);
    }

    @FXML
    private void ajouterExamen(ActionEvent event) {
        String selectedMatiere = matiereComboBox.getValue();
        String selectedClasse = classeComboBox.getValue();
        Integer selectedSalle = salleComboBox.getValue();
        LocalDate selectedDate = datePicker.getValue();

        StringBuilder errorMessage = new StringBuilder("Veuillez remplir les champs suivants :");

        if (selectedMatiere == null) {
            errorMessage.append("\n- Matière");
        }
        if (selectedClasse == null) {
            errorMessage.append("\n- Classe");
        }
        if (selectedSalle == null) {
            errorMessage.append("\n- Salle");
        }
        if (selectedDate == null) {
            errorMessage.append("\n- Date d'examen");
        }

        if (selectedMatiere != null && selectedClasse != null && selectedSalle != null && selectedDate != null) {
            int idMatiere = examenService.getIdMatiere(selectedMatiere);
            int idClasse = examenService.getIdClasse(selectedClasse);
            int idSalle = examenService.getIdSalle(selectedSalle);
            LocalDate examDate = new java.sql.Date(selectedDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).toLocalDate();
            System.out.println(idMatiere);
            // Ajouter le code d'ajout d'examen ici en utilisant les valeurs sélectionnées
            examenService.ajouterexamen(examDate, idMatiere, idClasse, idSalle);

            // Rafraîchir la table des examens
            refreshExamenTable();

            // Afficher une alerte de succès
            showAlert(AlertType.INFORMATION, "Ajout d'examen", "Examen ajouté", "L'examen a été ajouté avec succès.");
    } else {
        // Afficher une alerte d'erreur avec les champs manquants
        showAlert(AlertType.ERROR, "Erreur d'ajout", "Champs manquants", errorMessage.toString());
    }
}

private void showExamDetails(ChampsExamensCombines examen) {
    Alert examDetailsAlert = new Alert(AlertType.INFORMATION);
    examDetailsAlert.setTitle("Modification de l'examen");
    examDetailsAlert.setHeaderText("Détails de l'examen sélectionné");
    
    matiereComboBoxx.setValue(examen.getNomMatiere());
    classeComboBoxx.setValue(examen.getNomClasse());
    salleComboBoxx.setValue(examen.getNumeroSalle());
    datePicker.setValue(examen.getDateExamen().toLocalDate());
    
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    // Labels
    Label idLabel = new Label("ID Examen:");
    Label dateLabel = new Label("Date d'examen:");
    Label matiereLabel = new Label("Matière:");
    Label classeLabel = new Label("Classe:");
    Label salleLabel = new Label("Numéro de salle:");

    // Values
    Label idValueLabel = new Label(String.valueOf(examen.getIdExamen()));
    Label dateValueLabel = new Label(examen.getDateExamen().toString());
    Label matiereValueLabel = new Label(examen.getNomMatiere());
    Label classeValueLabel = new Label(examen.getNomClasse());
    Label salleValueLabel = new Label(String.valueOf(examen.getNumeroSalle()));

    // Add labels and values to the grid pane
    gridPane.add(idLabel, 0, 0);
    gridPane.add(idValueLabel, 1, 0);
    gridPane.add(dateLabel, 0, 1);
    gridPane.add(dateValueLabel, 1, 1);
    gridPane.add(matiereLabel, 0, 2);
    gridPane.add(matiereValueLabel, 1, 2);
    gridPane.add(classeLabel, 0, 3);
    gridPane.add(classeValueLabel, 1, 3);
    gridPane.add(salleLabel, 0, 4);
    gridPane.add(salleValueLabel, 1, 4);

    // DatePicker
    DatePicker datePicker = new DatePicker();
    gridPane.add(new Label("Nouvelle date d'examen:"), 0, 5);
    gridPane.add(datePicker, 1, 5);

    // Add the grid pane and combo boxes to the dialog pane
    DialogPane dialogPane = examDetailsAlert.getDialogPane();
    dialogPane.setContent(new VBox(10, gridPane, matiereComboBoxx, classeComboBoxx, salleComboBoxx));

    // Add a custom button with functionality
    Button customButton = new Button("Modifier");
    dialogPane.setPrefWidth(400);
    dialogPane.setPrefHeight(300);
    dialogPane.setExpandableContent(customButton);
    dialogPane.setExpanded(true);
    // Set the action for the custom button
    customButton.setOnAction(event -> {
        String selectedMatiere = matiereComboBoxx.getValue();
        String selectedClasse = classeComboBoxx.getValue();
        Integer selectedSalle = salleComboBoxx.getValue();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedMatiere != null || selectedClasse != null || selectedSalle != null || selectedDate != null) {
            // Vérifier les champs remplis et appeler les fonctions appropriées
            int idMatiere = -1;
            int idClasse = -1;
            int idSalle = -1;

            if (selectedMatiere != null) {
                idMatiere = examenService.getIdMatiere(selectedMatiere);
                if (idMatiere == -1) {
                    // Si l'ID de la matière n'est pas trouvé, récupérer l'ID de la première valeur de la liste
                    String firstMatiere = matiereComboBoxx.getItems().get(0);
                    idMatiere = examenService.getIdMatiere(firstMatiere);
                }
            }
            if (selectedClasse != null) {
                idClasse = examenService.getIdClasse(selectedClasse);
                if (idClasse == -1) {
                    // Si l'ID de la classe n'est pas trouvé, récupérer l'ID de la première valeur de la liste
                    String firstClasse = classeComboBoxx.getItems().get(0);
                    idClasse = examenService.getIdClasse(firstClasse);
                }
            }
            if (selectedSalle != null) {
                idSalle = examenService.getIdSalle(selectedSalle);
                if (idSalle == -1) {
                    // Si l'ID de la salle n'est pas trouvé, récupérer l'ID de la première valeur de la liste
                    Integer firstSalle = salleComboBoxx.getItems().get(0);
                    idSalle = examenService.getIdSalle(firstSalle);
                }
            }
            LocalDate examDate = selectedDate != null ? new java.sql.Date(selectedDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).toLocalDate() : examen.getDateExamen().toLocalDate();
            
            showAlert(AlertType.INFORMATION, "Fields Filled", "Selected Fields",
                    "Matiere: " + (selectedMatiere != null ? selectedMatiere : "Not Selected") + "\n" +
                            "Classe: " + (selectedClasse != null ? selectedClasse : "Not Selected") + "\n" +
                            "Salle: " + (selectedSalle != null ? selectedSalle : "Not Selected") + "\n" +
                            "Date: " + (examDate != null ? examDate.toString() : "Not Selected"));
            int idExamen = examen.getIdExamen();
            examenService.modifierExamens(idExamen, examDate, idMatiere, idClasse, idSalle);
        } else {
            showAlert(AlertType.WARNING, "Fields Not Filled", "No Fields Selected",
                    "Please select at least one field.");
        }
    });

    examDetailsAlert.showAndWait();
    refreshExamenTable();
}


    private void showAlert(AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
@FXML
private void trierExamen() {
        ToggleButton selectedButton = (ToggleButton) toggleGroup.getSelectedToggle();

    if (selectedButton == radioBtnOption1) {
        examen.getSortOrder().clear();
        examen.getSortOrder().add(dateExamenColumn);
    } else if (selectedButton == radioBtnOption2) {
        examen.getSortOrder().clear();
        examen.getSortOrder().add(nomMatiereColumn);
    } else if (selectedButton == radioBtnOption3) {
        examen.getSortOrder().clear();
        examen.getSortOrder().add(nomClasseColumn);
    }
    else if (selectedButton == radioBtnOption4) {
        examen.getSortOrder().clear();
        examen.getSortOrder().add(numeroSalleColumn);
    }
    else {
           showAlert(AlertType.WARNING, "Fields Not Filled", "No Fields Selected", "Please select at least one sorter .");}
    }
private boolean sortByButtonClicked = false;
private void handleSortByButton(ActionEvent event) {
    if (sortByButtonClicked) {
        // Exécutez la fonction trierExamen ici
        trierExamen();
    } else {
        // Affichez les RadioButtons
        radioBtnOption4.setVisible(true);
        radioBtnOption3.setVisible(true);
        radioBtnOption2.setVisible(true);
        radioBtnOption1.setVisible(true);
    sortByButtonClicked = !sortByButtonClicked;
    btnSort.setText("Sort By");
    }
}

@FXML
private boolean filterByButtonClicked = false;
private void handlerfiltrebybutton(ActionEvent event) {
        filterByButtonClicked = !filterByButtonClicked;
    if (filterByButtonClicked) {
        // Exécutez la fonction trierExamen ici
        filtrerExamen();
        filtrercolumn.setText("refresh");

    } else {
        refreshExamenTable();
        filtrercolumn.setText("filtrer");
        matiereComboBox.setValue(null);
        classeComboBox.setValue(null);
        salleComboBox.setValue(null);
        datePicker.setValue(null);
    }
}

private void filtrerExamen() {
    ObservableList<ChampsExamensCombines> exams = examen.getItems();
    ObservableList<ChampsExamensCombines> filteredExams = FXCollections.observableArrayList();

    String selectedMatiere = matiereComboBox.getValue();
    String selectedClasse = classeComboBox.getValue();
    Integer selectedSalle = salleComboBox.getValue();
    LocalDate selectedDate = datePicker.getValue();
    System.out.println();
    for (ChampsExamensCombines exam : exams) {
        java.sql.Date sqlDate = exam.getDateExamen();
        Date utilDate = new Date(sqlDate.getTime());
        LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if ((selectedMatiere == null || exam.getNomMatiere().equals(selectedMatiere))
                && (selectedClasse == null || exam.getNomClasse().equals(selectedClasse))
                && (selectedSalle == null || exam.getNumeroSalle() == selectedSalle)
                && (selectedDate == null || localDate.equals(selectedDate))) {
            filteredExams.add(exam);
        }
    }

    examen.setItems(filteredExams);
}
private void generatePDF(String filePath, TableView<ChampsExamensCombines> examen) {
    try {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        float margin = 50;
        float yStart = page.getMediaBox().getHeight() - margin;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        float yPosition = yStart;
        float bottomMargin = 70;
        float tableHeight = (yStart - bottomMargin) - 300;

        int numberOfColumns = examen.getColumns().size();
        int numberOfRows = examen.getItems().size();
        float rowHeight = 20f;
        float tableRowHeight = 15f;

        // Dessine l'en-tête du tableau
    float tableYPosition = yPosition;
        for (int i = 0; i < numberOfColumns-1; i++) {
            String columnName = examen.getColumns().get(i).getText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD_OBLIQUE, 12);// Utilize PDType1Font.HELVETICA_BOLD instead of BOLD
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(margin + (i * tableWidth / numberOfColumns), tableYPosition);
            contentStream.drawString(columnName);
            contentStream.endText();
        }
        tableYPosition -= tableRowHeight;
        // Dessine les données du tableau
        for (int row = 0; row < numberOfRows; row++) {
            for (int col = 0; col < numberOfColumns-1; col++) {
                String cellValue = examen.getColumns().get(col).getCellData(row).toString();
                System.out.println(examen.getColumns()+"test1");
                System.out.println(examen.getColumns().get(col)+"test2");
                System.out.println( examen.getColumns().get(col).getCellData(row)+"test3");
                System.out.println(examen.getColumns().get(col).getCellData(row).toString()+"test4");
                contentStream.setFont(PDType1Font.HELVETICA, 12); // Use PDType1Font.HELVETICA instead of REGULAR
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(margin + (col * tableWidth / numberOfColumns), tableYPosition);
                contentStream.drawString(cellValue);
                contentStream.endText();
            }
            tableYPosition -= tableRowHeight;
        }

        contentStream.close();

        // Enregistre le document PDF
        document.save(filePath);
        document.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
@FXML
private void convertToPDF(ActionEvent event) {
    // Crée une instance de FileChooser
    FileChooser fileChooser = new FileChooser();
    
    // Configure le FileChooser pour spécifier les extensions de fichiers autorisées
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
    fileChooser.getExtensionFilters().add(extFilter);
    
    // Affiche la boîte de dialogue de sélection de fichier et attend la sélection de l'utilisateur
    Stage stage = (Stage) convertpdf.getScene().getWindow(); // Récupère la fenêtre actuelle
    File file = fileChooser.showSaveDialog(stage);
    
    // Vérifie si un fichier a été sélectionné
    if (file != null) {
        // Génère le PDF en utilisant le chemin du fichier sélectionné
        generatePDF(file.getAbsolutePath(), examen);
    }
}

}



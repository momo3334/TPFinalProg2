/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author usager
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private CheckMenuItem mniEdit;
    @FXML
    private Tab tbpTableauBord;
    @FXML
    private Label txfJeux;
    @FXML
    private Label txfProcesseur;
    @FXML
    private Label txfAutomobile;
    @FXML
    private Label txfSkateboard;
    @FXML
    private Label txfDivers;
    @FXML
    private Label txfTotal;
    @FXML
    private Label txfJeuxPrix;
    @FXML
    private Label txfAutomobilePrix;
    @FXML
    private Label txfSkateboardPrix;
    @FXML
    private Label txfDiversPrix;
    @FXML
    private Label txfTotalPrix;
    @FXML
    private Tab tbpListeInventaire;
    @FXML
    private TableView<Inventaire> tbvClasse;
    @FXML
    private TableColumn<Inventaire, String> tbvNom;
    @FXML
    private TableColumn<Inventaire, String> tbvCategorie;
    @FXML
    private TableColumn<Inventaire, Double> tbvPrix;
    @FXML
    private TableColumn<Inventaire, LocalDate> tbvDateAchat;
    @FXML
    private TableColumn<Inventaire, String> tbvAutre;
    @FXML
    private TextField txfNom;
    @FXML
    private TextField txfValeur;
    @FXML
    private ComboBox<String> cmbCategorie;
    @FXML
    private DatePicker dtpDate;
    @FXML
    private TextField txfAutre;
    @FXML
    private Tab tbpEntretiens;
    @FXML
    private TableView<Inventaire> tbvInventaireNom;
    @FXML
    private TableColumn<Inventaire, String> tbvNom2;
    @FXML
    private TextField txfCategorie2;
    @FXML
    private TextField txfAutre2;
    @FXML
    private TextField txfDate2;
    @FXML
    private ListView<String> lsvAutre;
    @FXML
    private DatePicker dtpDate2;
    @FXML
    private TextArea txaAutre;
    
    public ObservableList<Inventaire> liste = FXCollections.observableArrayList();
    
    
    
    @FXML
    private Label lblChamp;
    @FXML
    private Label txfProcesseurPrix;
    
    public String path = "";
    
    public String nomFic = "";
    
    public boolean enregistrer = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbCategorie.getItems().addAll("Jeux", "Processeur","Automobile","Skateboard", "Divers");
        tbvNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tbvCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        tbvPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tbvDateAchat.setCellValueFactory(new PropertyValueFactory<>("dateAchat"));
        tbvAutre.setCellValueFactory(new PropertyValueFactory<>("autre"));
        tbvNom2.setCellValueFactory(new PropertyValueFactory<>("nom"));
        
//        tbvNom.setCellFactory(TextFieldTableCell.forTableColumn());
//        tbvCategorie.setCellFactory(ComboBoxTableCell.forTableColumn("Jeux", "Processeur","Automobile","Skateboard", "Divers"));
//        tbvPrix.setCellFactory(TextFieldTableCell.forTableColumn());
//        tbvDateAchat.setCellFactory(TextFieldTableCell.forTableColumn());
//        tbvAutre.setCellFactory(TextFieldTableCell.forTableColumn());
    }    

    @FXML
    private void mniNouveauAction(ActionEvent event) {
    }

    /**
     * Ouvrir un fichier avec un fileChooser
     * 
     */
    @FXML
    private void mniOuvrirAction(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("FichierDat(*.dat)", "*.dat" ));
        fc.setTitle("Choisir ou sauvegarder le fichier");
        fc.setInitialDirectory(new File("fichiers"));
        File fichier = fc.showOpenDialog(null);
        if (fichier != null) {
            path = fichier.getAbsolutePath();
            FileInputStream fis = new FileInputStream(fichier);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Inventaire> list = (ArrayList<Inventaire>) ois.readObject();
            liste = FXCollections.observableList(list);
            tbvClasse.setItems(liste);
            tbvInventaireNom.setItems(liste);
            ois.close();
            enregistrer = true;
        }

        compteurs();
    }

    @FXML
    private void mniFermerAction(ActionEvent event) throws IOException {
        Alert  alerte = new Alert(AlertType.CONFIRMATION);
        alerte.setTitle("Attention");
        alerte.setHeaderText("Confirmation de fermeture du programme");
        alerte.setContentText("Veuillez enregistrer votre inventaire avant de fermer.");
        Optional<ButtonType> result = alerte.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK && enregistrer == false) {
            mniEnregistrerSousAction(event);
            enregistrer = true;
            liste.clear();
            compteurs();
        }else if (result.isPresent() && result.get() == ButtonType.OK && enregistrer == true){
            mniEnregistrerAction(event);
            enregistrer = true;
            liste.clear();
            compteurs();
        }
    }
    
    /**
     * Enregistrer dans le fichier actuel ou
     * Enregistrer dans une destination choisi par fileChooser
     */
    @FXML
    private void mniEnregistrerAction(ActionEvent event) throws FileNotFoundException, IOException {
        if (path.equals("")) {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("FichierDat(*.dat)", "*.dat"));
            fc.setTitle("Choisir ou sauvegarder le fichier");
            fc.setInitialDirectory(new File("fichiers"));
            File fichier = fc.showSaveDialog(null);
            if (fichier != null) {
                ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream(fichier));
                sortie.writeObject(new ArrayList<>(liste));
                sortie.close();
                path = fichier.getAbsolutePath();
                nomFic = fichier.getName();
            }
        }else{
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<>(liste));
            oos.close();
        }
        enregistrer = true;
        
    }
    
    /**
     * Enregistrer dans une destination choisi par fileChooser
     * 
     */
    @FXML
    private void mniEnregistrerSousAction(ActionEvent event) throws FileNotFoundException, IOException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("FichierDat(*.dat)", "*.dat" ));
        fc.setTitle("Choisir ou sauvegarder le fichier");
        fc.setInitialDirectory(new File("fichiers"));
        File fichier = fc.showSaveDialog(null);
        if (fichier != null) {
            ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream(fichier)); 
            sortie.writeObject(new ArrayList<>(liste));
            sortie.close(); 
            path = fichier.getAbsolutePath();
            nomFic = fichier.getName();
        }
        enregistrer = true;
    }

    @FXML
    private void mniQuitterAction(ActionEvent event) throws IOException {
        Alert  alerte = new Alert(AlertType.CONFIRMATION);
        alerte.setTitle("Attention");
        alerte.setHeaderText("Confirmation de fermeture du programme");
        alerte.setContentText("Veuillez enregistrer votre inventaire avant de quitter.");
        Optional<ButtonType> result = alerte.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK && enregistrer == false) {
            mniEnregistrerSousAction(event);
            enregistrer = true;
            Platform.exit();
        }else if (result.isPresent() && result.get() == ButtonType.OK && enregistrer == true){
            mniEnregistrerAction(event);
            enregistrer = true;
            Platform.exit();
        }else if (result.isPresent() && result.get() == ButtonType.CANCEL){
            Platform.exit();
        }
    }

    @FXML
    private void mniProposAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("À propos");
        alert.setHeaderText("Programme crée par :");
        alert.setContentText("Jesse Galarneau & Marc Antoine Griffiths Lorange" + "\n" + "2018-05-18");
        alert.showAndWait();
    }
    

//-------------------------------------btn--------------------------------------
    /**
     * Ajouter un objet dans le tbv et observable list
     * 
     */
    @FXML
    private void btnAjouter(ActionEvent event) {
        int total;
        double totalPrix;
        double totalJeuxPrix = 0;
        double totalProcesseurPrix = 0;
        double totalAutomobilePrix = 0;
        double totalSkateboardPrix = 0;
        double totalDiversPrix = 0;
        int compteurJeux = 0;
        int compteurProcesseur = 0;
        int compteurAutomobile = 0;
        int compteurSkateboard = 0;
        int compteurDivers = 0;

        LocalDate date = dtpDate.getValue();
        if (!txfNom.getText().isEmpty() 
                && !cmbCategorie.getSelectionModel().getSelectedItem().isEmpty() 
                && !txfValeur.getText().isEmpty() && date != null) {
            liste.add(new Inventaire(txfNom.getText(), cmbCategorie.getSelectionModel().getSelectedItem(), Double.parseDouble(txfValeur.getText()), date, txfAutre.getText()));
            tbvClasse.setItems(liste);
            tbvInventaireNom.setItems(liste);
            txfNom.clear();
            cmbCategorie.getSelectionModel().select(-1);
            txfValeur.clear();
            dtpDate.setValue(null);
            txfAutre.clear();
            tbvClasse.getSelectionModel().select(-1);
            lblChamp.setVisible(false);
        }else
            lblChamp.setVisible(true);
        
        compteurs();
    }

    @FXML
    private void btnSupprimerSelection(ActionEvent event) {
        if (!tbvClasse.getSelectionModel().isEmpty()) {
            Alert alertConf = new Alert(AlertType.CONFIRMATION);
            alertConf.setHeaderText("Supprimer?");
            alertConf.setTitle("Supprimer la sélection?");
            alertConf.setContentText("La sélection supprimer n'est pas récupérable.");
            alertConf.getButtonTypes().setAll(ButtonType.YES);
            alertConf.getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = alertConf.showAndWait();
            if (result.get() == ButtonType.YES) {
                liste.remove(tbvClasse.getSelectionModel().getSelectedIndex());
                alertConf.close();
            }else{
                alertConf.close();
            }
        }
    }


    @FXML
    private void btnSupprimer2Action(ActionEvent event) {
    }

    @FXML
    private void btnAjouter2(ActionEvent event) {
    }

    @FXML
    private void tbvNomEdit(CellEditEvent event) {
    }

    @FXML
    private void tbvCategorieEdit(CellEditEvent event) {
    }

    @FXML
    private void tbvPrixEdit(CellEditEvent event) {
    }

    @FXML
    private void tbvDateAchatEdit(CellEditEvent event) {
    }

    @FXML
    private void tbvAutreEdit(CellEditEvent event) {
    }
    
    /**
     * compter inventaire par categorie
     * remplir les labels avec les compteurs de categorie
     */
    private void compteurs(){
        int total;
        double totalPrix;
        double totalJeuxPrix = 0;
        double totalProcesseurPrix = 0;
        double totalAutomobilePrix = 0;
        double totalSkateboardPrix = 0;
        double totalDiversPrix = 0;
        int compteurJeux = 0;
        int compteurProcesseur = 0;
        int compteurAutomobile = 0;
        int compteurSkateboard = 0;
        int compteurDivers = 0;
        
        for(Inventaire inv : liste){
            String cat = inv.getCategorie();
            if (cat.equals("Jeux")) {
                totalJeuxPrix += inv.getPrix();
                compteurJeux++;
            }else if(cat.equals("Processeur")){
                totalProcesseurPrix += inv.getPrix();
                compteurProcesseur++;
            }else if(cat.equals("Automobile")){
                totalAutomobilePrix += inv.getPrix();
                compteurAutomobile++;
            }else if(cat.equals("Skateboard")){
                totalSkateboardPrix += inv.getPrix();
                compteurSkateboard++;
            }else{
                totalDiversPrix += inv.getPrix();
                compteurDivers++;
            }
        }
        
        txfJeux.setText(String.valueOf(compteurJeux));
        txfJeuxPrix.setText(String.valueOf(totalJeuxPrix) + "$");
        txfProcesseur.setText(String.valueOf(compteurProcesseur));
        txfProcesseurPrix.setText(String.valueOf(totalProcesseurPrix) + "$");
        txfAutomobile.setText(String.valueOf(compteurAutomobile));
        txfAutomobilePrix.setText(String.valueOf(totalAutomobilePrix) + "$");
        txfSkateboard.setText(String.valueOf(compteurSkateboard));
        txfSkateboardPrix.setText(String.valueOf(totalSkateboardPrix) + "$");
        txfDivers.setText(String.valueOf(compteurDivers));
        txfDiversPrix.setText(String.valueOf(totalDiversPrix) + "$");
        total = compteurJeux + compteurProcesseur + compteurAutomobile + compteurSkateboard + compteurDivers; 
        txfTotal.setText(String.valueOf(total));
        totalPrix = totalJeuxPrix + totalProcesseurPrix + totalAutomobilePrix + totalSkateboardPrix + totalDiversPrix;
        txfTotalPrix.setText(String.valueOf(totalPrix) + "$");
    }

    @FXML
    private void tbvNomPressed(MouseEvent event) {
        int index =tbvInventaireNom.getSelectionModel().getSelectedIndex();
        txfCategorie2.setText(liste.get(index).getCategorie());
        txfDate2.setText(String.valueOf(liste.get(index).getDateAchat()));
        txfCategorie2.setText(liste.get(index).getAutre());
        lsvAutre.getItems().clear();
        for (Map.Entry<LocalDate, String> map : liste.get(index).getEntretiens().entrySet()) {
            LocalDate key = map.getKey();
            String value = map.getValue();
            lsvAutre.getItems().add(key + "   " + value);
        }      
    } 
    
    private void effacerInventaire() {
        liste.clear();
    }
    
}

   
   
    


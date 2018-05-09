/**
 * L'application permet de gérer un inventaire selon des catégories de votre choix.
 * Pour chaque élément de l'inventaire
 * il est possible de faire un suivi d'entretiens. Un tableau de bord donne un
 * aperçue de l'inventaire par catégorie.
 * Plusieurs inventaires peuvent être créés.
 * Auteurs : Jesse Galarneau et Marc-Antoine Griffiths
 * Date : 21 mai 2018
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
import javafx.scene.control.TabPane;
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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LocalDateStringConverter;

public class FXMLDocumentController implements Initializable {
    @FXML
    private CheckMenuItem mniEdit;
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
    @FXML
    private Label lblChamp;
    @FXML
    private Label txfProcesseurPrix;
    @FXML
    private TabPane tbpPane;
    @FXML
    private Label lblChamps2;

    //-------------------------Variables globales-------------------------------
    public ObservableList<Inventaire> liste = FXCollections.observableArrayList(); //contenir les objets de l'inventaire

    public String path = ""; //contenir le chemin du fichier en cours

    public boolean enregistrer = true; //determiner si les derniers changements son sauvegarder

    //-----------------------------code-----------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbCategorie.getItems().addAll("Jeux", "Processeur", "Automobile", "Skateboard", "Divers");
        tbvNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tbvCategorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        tbvPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tbvDateAchat.setCellValueFactory(new PropertyValueFactory<>("dateAchat"));
        tbvAutre.setCellValueFactory(new PropertyValueFactory<>("autre"));
        tbvNom2.setCellValueFactory(new PropertyValueFactory<>("nom"));

        tbvNom.setCellFactory(TextFieldTableCell.forTableColumn());
        tbvCategorie.setCellFactory(ComboBoxTableCell.forTableColumn("Jeux", "Processeur", "Automobile", "Skateboard", "Divers"));
        tbvPrix.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverterWEH()));
        tbvDateAchat.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        tbvAutre.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    //----------------------------menus-----------------------------------------
    /**
     * Creer un nouvel inventaire avec demande de sauvegarde
     */
    @FXML
    private void mniNouveauAction(ActionEvent event) throws IOException, IOException {
        mniFermerAction(event);
    }

    /**
     * Ouvrir un fichier avec un fileChooser
     *
     */
    @FXML
    private void mniOuvrirAction(ActionEvent event) throws FileNotFoundException, IOException, ClassNotFoundException {
        if (enregistrer) {
            ouvrir();
        } else {
            confirmation(event);
            ouvrir();
        }
    }

    /**
     * fermer le fichier ouvert avec demande de sauvegarde si non-sauvegarde
     */
    @FXML
    private void mniFermerAction(ActionEvent event) throws IOException {
        if (enregistrer) {
            liste.clear();
            compteurs();
            path = "";
            tbpPane.getSelectionModel().select(0);
        } else {
            confirmation(event);
        }

    }

    /**
     * Enregistrer dans le fichier actuel ou Appeler enregistrerSous pour
     * choisir par fileChooser
     */
    @FXML
    private void mniEnregistrerAction(ActionEvent event) throws FileNotFoundException, IOException {
        if (path.equals("")) {
            mniEnregistrerSousAction(event);
        } else {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<>(liste));
            oos.close();
            enregistrer = true;
        }
    }

    /**
     * Enregistrer dans une destination choisi par fileChooser
     *
     */
    @FXML
    private void mniEnregistrerSousAction(ActionEvent event) throws FileNotFoundException, IOException {
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
            enregistrer = true;
        }

    }

    /**
     * Fermer le programme avec demande de sauvegarde
     */
    @FXML
    private void mniQuitterAction(ActionEvent event) throws IOException {
        if (enregistrer) {
            Platform.exit();
        } else {
            Alert alerte = new Alert(AlertType.CONFIRMATION);
            alerte.setTitle("Attention");
            alerte.setHeaderText("Confirmation de fermeture du programme");
            alerte.setContentText("Veuillez enregistrer votre inventaire avant de quitter.");
            alerte.getButtonTypes().setAll(ButtonType.YES);
            alerte.getButtonTypes().add(ButtonType.CANCEL);
            alerte.getButtonTypes().add(ButtonType.NO);
            Optional<ButtonType> result = alerte.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                mniEnregistrerAction(event);
                Platform.exit();
            } else if (result.isPresent() && result.get() == ButtonType.NO) {
                Platform.exit();
            } else {
                alerte.close();
            }
        }
    }

    @FXML
    private void mniEditAction(ActionEvent event) {
        if (mniEdit.isSelected()) {
            tbvClasse.setEditable(true);
        } else {
            tbvClasse.setEditable(false);
        }
    }

    /**
     * Afficher les details des developpeurs
     *
     */
    @FXML
    private void mniProposAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("À propos");
        alert.setHeaderText("Programme crée par :");
        alert.setContentText("Jesse Galarneau & Marc Antoine Griffiths Lorange" + "\n" + "2018-05-21");
        alert.showAndWait();
    }

//-------------------------------------boutons----------------------------------
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
            enregistrer = false;
        } else {
            lblChamp.setVisible(true);
        }

        compteurs();
    }

    /**
     * Appeler la methode supprimer
     */
    @FXML
    private void btnSupprimerSelection(ActionEvent event) {
        supprimerObjet();
    }

    /**
     * Supprimer un entretien avec une alerte de confirmation
     */
    @FXML
    private void btnSupprimer2Action(ActionEvent event) {
        supprimerEntretien();
    }

    /**
     * Ajouter un entretiens a l'objet selectionner
     *
     */
    @FXML
    private void btnAjouter2(ActionEvent event) {
        if (!tbvInventaireNom.getSelectionModel().isEmpty() && dtpDate2.getValue() != null && !txaAutre.getText().isEmpty()) {
            int index = tbvInventaireNom.getSelectionModel().getSelectedIndex();
            liste.get(index).ajouterEntretiens(dtpDate2.getValue(), txaAutre.getText());
            dtpDate2.setValue(null);
            txaAutre.clear();
            lsvAutre.getItems().clear();
            miseAjoursLsv(index);
            lblChamps2.setVisible(false);
            enregistrer = false;
        } else {
            lblChamps2.setVisible(true);
        }
    }

    //---------------------------Modifications----------------------------------
    /**
     * Modifier le nom
     */
    @FXML
    private void tbvNomEdit(CellEditEvent editCell) {
        Inventaire iven = tbvClasse.getSelectionModel().getSelectedItem();
        iven.setNom(editCell.getNewValue().toString());
        enregistrer = false;
    }

    /**
     * Modifier la categorie
     */
    @FXML
    private void tbvCategorieEdit(CellEditEvent editCell) {
        Inventaire iven = tbvClasse.getSelectionModel().getSelectedItem();
        iven.setCategorie(editCell.getNewValue().toString());
        enregistrer = false;
    }

    /**
     * Modifier le prix
     */
    @FXML
    private void tbvPrixEdit(CellEditEvent editCell) {
        Inventaire iven = tbvClasse.getSelectionModel().getSelectedItem();
        iven.setPrix(Double.parseDouble(editCell.getNewValue().toString()));
        enregistrer = false;
    }

    /**
     * Modifier la date
     */
    @FXML
    private void tbvDateAchatEdit(CellEditEvent editCell) {
        System.out.println("aloo");
        Inventaire iven = tbvClasse.getSelectionModel().getSelectedItem();
        iven.setDateAchat(LocalDate.parse(editCell.getNewValue().toString()));
        enregistrer = false;
    }

    /**
     * Modifier la section autre
     */
    @FXML
    private void tbvAutreEdit(CellEditEvent editCell) {
        Inventaire iven = tbvClasse.getSelectionModel().getSelectedItem();
        iven.setAutre(editCell.getNewValue().toString());
        enregistrer = false;
    }

    //--------------------------contextMenu-------------------------------------
    /**
     * Appeler la methode supprimerObjet
     */
    @FXML
    private void ctmSupprimer(ActionEvent event) {
        supprimerObjet();
    }
    
    /**
     *  Appeler la methode supprimerEntretien
     */
    @FXML
    private void ctmSupprimer2(ActionEvent event) {
        supprimerEntretien();
    }
    
    //-------------------------MouseEvent---------------------------------------
    /**
     * Afficher les entretiens lorsqu'un objet se fait double clicker
     *
     * @param event
     */
    @FXML
    private void tbvDoubleClick(MouseEvent event) {
        if (mniEdit.isSelected() == false) {
            if (event.getClickCount() >= 2) {
                if (!tbvClasse.getSelectionModel().isEmpty()) {
                    tbpPane.getSelectionModel().select(2);
                    int index = tbvClasse.getSelectionModel().getSelectedIndex();
                    txfCategorie2.setText(liste.get(index).getCategorie());
                    txfDate2.setText(String.valueOf(liste.get(index).getDateAchat()));
                    txfAutre2.setText(liste.get(index).getAutre());
                    lsvAutre.getItems().clear();
                    tbvInventaireNom.getSelectionModel().select(index);
                    miseAjoursLsv(index);
                }
            }
        }
    }

    /**
     * Afficher les entretiens de l'objet selectionner
     *
     */
    @FXML
    private void tbvNomPressed(MouseEvent event) {
        if (tbvInventaireNom.getSelectionModel().getSelectedIndex() != -1) {
            int index = tbvInventaireNom.getSelectionModel().getSelectedIndex();
            txfCategorie2.setText(liste.get(index).getCategorie());
            txfDate2.setText(String.valueOf(liste.get(index).getDateAchat()));
            txfAutre2.setText(liste.get(index).getAutre());
            miseAjoursLsv(index);
        }
    }

    //---------------------Methodes reutiliser----------------------------------
    /**
     * compter inventaire par categorie remplir les labels avec les compteurs de
     * categorie
     */
    private void compteurs() {
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

        for (Inventaire inv : liste) {
            String objet = inv.getCategorie();
            if (objet.equals("Jeux")) {
                totalJeuxPrix += inv.getPrix();
                compteurJeux++;
            } else if (objet.equals("Processeur")) {
                totalProcesseurPrix += inv.getPrix();
                compteurProcesseur++;
            } else if (objet.equals("Automobile")) {
                totalAutomobilePrix += inv.getPrix();
                compteurAutomobile++;
            } else if (objet.equals("Skateboard")) {
                totalSkateboardPrix += inv.getPrix();
                compteurSkateboard++;
            } else {
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

    /**
     * Mettre a jours la listView
     *
     * @param index
     */
    public void miseAjoursLsv(int index) {
        lsvAutre.getItems().clear();
        for (Map.Entry<LocalDate, String> map : liste.get(index).getEntretiens().entrySet()) {
            LocalDate key = map.getKey();
            String value = map.getValue();
            lsvAutre.getItems().add(key + "   " + value);
        }
    }

    /**
     * Supprimer un objet de l'inventaire
     */
    private void supprimerObjet() {
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
                enregistrer = false;
            } else {
                alertConf.close();
            }
            tbvClasse.setItems(liste);
            tbvInventaireNom.setItems(liste);
        }
        compteurs();
    }
    
    /**
     * Supprimer un entretiens de l'objet selectionner
     */
    public void supprimerEntretien(){
        if (!tbvInventaireNom.getSelectionModel().isEmpty() && lsvAutre.getSelectionModel().getSelectedIndex() != -1) {
            Alert alertConf = new Alert(AlertType.CONFIRMATION);
            alertConf.setHeaderText("Supprimer?");
            alertConf.setTitle("Supprimer l'entretien?");
            alertConf.setContentText("La l'entretien supprimer n'est pas récupérable.");
            alertConf.getButtonTypes().setAll(ButtonType.YES);
            alertConf.getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = alertConf.showAndWait();
            if (result.get() == ButtonType.YES) {
                int index = tbvInventaireNom.getSelectionModel().getSelectedIndex();
                liste.get(index).supprimerEntretiens(LocalDate.parse(lsvAutre.getSelectionModel().getSelectedItem().substring(0, 10)));
                miseAjoursLsv(index);
                
                enregistrer = false;
            } else {
                alertConf.close();
            }
        }
    }

    /**
     * Afficher une confirmation de sauvergarde
     */
    private void confirmation(ActionEvent event) throws IOException, IOException {
        Alert alerte = new Alert(AlertType.CONFIRMATION);
        alerte.setTitle("Attention");
        alerte.setHeaderText("Confirmation de fermeture du programme");
        alerte.setContentText("Veuillez enregistrer votre inventaire avant de fermer.");
        alerte.getButtonTypes().setAll(ButtonType.YES);
        alerte.getButtonTypes().add(ButtonType.CANCEL);
        alerte.getButtonTypes().add(ButtonType.NO);
        Optional<ButtonType> result = alerte.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            mniEnregistrerAction(event);
            enregistrer = false;
            liste.clear();
            compteurs();
            path = "";

        } else if (result.isPresent() && result.get() == ButtonType.NO) {
            enregistrer = false;
            liste.clear();
            compteurs();
            path = "";
        } else {
            alerte.close();
        }
        tbpPane.getSelectionModel().select(0);
    }

    /**
     * Afficher le fileChooser pour ouvrir
     */
    private void ouvrir() throws FileNotFoundException, FileNotFoundException, IOException, ClassNotFoundException {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("FichierDat(*.dat)", "*.dat"));
        fc.setTitle("Choisir ou sauvegarder le fichier");
        fc.setInitialDirectory(new File("fichiers"));
        File fichier = fc.showOpenDialog(null);
        if (fichier != null) {
            liste.clear();
            path = fichier.getAbsolutePath();
            FileInputStream fis = new FileInputStream(fichier);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Inventaire> list = (ArrayList<Inventaire>) ois.readObject();
            liste = FXCollections.observableList(list);
            tbvClasse.setItems(liste);
            tbvInventaireNom.setItems(liste);
            ois.close();
            enregistrer = true;
            tbpPane.getSelectionModel().select(0);
        }
        compteurs();
    }

    
}

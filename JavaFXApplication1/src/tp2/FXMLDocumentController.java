/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    private Label txfProcessuerPrix;
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
    private ComboBox cmbCategorie;
    @FXML
    private DatePicker dtpDate;
    @FXML
    private TextField txfAutre;
    @FXML
    private Tab tbpEntretiens;
    @FXML
    private TableView<?> tbvInventaireNom;
    @FXML
    private TableColumn<?, ?> tbvNom2;
    @FXML
    private TextField txfCategorie2;
    @FXML
    private TextField txfAutre2;
    @FXML
    private TextField txfDate2;
    @FXML
    private ListView<?> lsvAutre;
    @FXML
    private DatePicker dtpDate2;
    @FXML
    private TextArea txaAutre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void mniNouveauAction(ActionEvent event) {
    }

    @FXML
    private void mniOuvrirAction(ActionEvent event) {
    }

    @FXML
    private void mniFermerAction(ActionEvent event) {
    }

    @FXML
    private void mniEnregistrerAction(ActionEvent event) {
    }

    @FXML
    private void mniEnregistrerSousAction(ActionEvent event) {
    }

    @FXML
    private void mniQuitterAction(ActionEvent event) {
    }

    @FXML
    private void mniProposAction(ActionEvent event) {
    }


    @FXML
    private void btnAjouter(ActionEvent event) {
    }

    @FXML
    private void btnSupprimerSelection(ActionEvent event) {
    }

    @FXML
    private void tbvNomEdit(TableColumn.CellEditEvent<Inventaire, String> event) {
    }

    @FXML
    private void tbvCategorieEdit(TableColumn.CellEditEvent<Inventaire, String> event) {
    }

    @FXML
    private void tbvPrixEdit(TableColumn.CellEditEvent<Inventaire, Double> event) {
    }

    @FXML
    private void tbvDateAchatEdit(TableColumn.CellEditEvent<Inventaire, LocalDate> event) {
    }

    @FXML
    private void tbvAutreEdit(TableColumn.CellEditEvent<Inventaire, String> event) {
    }

    @FXML
    private void btnSupprimer2Action(ActionEvent event) {
    }

    @FXML
    private void btnAjouter2(ActionEvent event) {
    }
    
}

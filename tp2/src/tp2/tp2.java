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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class tp2 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

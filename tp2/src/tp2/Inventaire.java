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

import java.io.Serializable;
import java.time.LocalDate;
import java.util.TreeMap;


public class Inventaire implements Serializable{
    private String nom;
    private String categorie;
    private LocalDate dateAchat;
    private double prix;
    private String autre;
    private TreeMap<LocalDate, String> entretiens;
    
    public Inventaire(String nom, String categorie, double prix, LocalDate dateAchat, String autre) {
        this.nom = nom;
        this.categorie = categorie;
        this.dateAchat = dateAchat;
        this.prix = prix;
        this.autre = autre;
        this.entretiens = new TreeMap();
    }

    public String getAutre() {
        return autre;
    }

    public void setAutre(String autre) {
        this.autre = autre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    public void ajouterEntretiens(LocalDate date, String details){
        entretiens.put(date, details);
    }
    
    public void supprimerEntretiens(LocalDate key){
        entretiens.remove(key);
    }

    public TreeMap<LocalDate, String> getEntretiens() {
        return entretiens;
    }

    public void setEntretiens(TreeMap<LocalDate, String> entretiens) {
        this.entretiens = entretiens;
    }
}

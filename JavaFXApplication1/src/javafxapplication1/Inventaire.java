/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 *
 * @author usager
 */
public class Inventaire {
    private String nom;
    private String categorie;
    private String autre;
    private LocalDate dateAchat;
    private double prix;
    private TreeMap entretiens;

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

    //CODE TO DO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    @Override
    public String toString() {
        return null;
    }

    public Inventaire(String nom, String categorie, LocalDate dateAchat, double prix, TreeMap entretiens) {
        this.nom = nom;
        this.categorie = categorie;
        this.dateAchat = dateAchat;
        this.prix = prix;
        this.entretiens = entretiens;
        this.autre = autre;
    }

}

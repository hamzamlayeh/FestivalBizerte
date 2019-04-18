package com.user.festivalbizerte.Model;

import java.io.Serializable;

public class Programmes implements Serializable {
    private int id_prog;
    private String titre;
    private String date;
    private String horaire;
    private String prix;

    public Programmes(String titre, String date, String horaire, String prix) {
        this.titre = titre;
        this.date = date;
        this.horaire = horaire;
        this.prix = prix;
    }

    public int getId_prog() {
        return id_prog;
    }

    public void setId_prog(int id_prog) {
        this.id_prog = id_prog;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}

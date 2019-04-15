package com.user.festivalbizerte.Model;

/**
 * Created by user on 02/10/2018.
 */

public class ArtistesItem {
    private String jourNum;
    private String image;
    private String jourLet;
    private String NonArtiste;
    private String prix1;
    private String Prix2;

    public ArtistesItem(String jourNum, String jourLet, String nonArtiste) {
        this.jourNum = jourNum;
        this.jourLet = jourLet;
        NonArtiste = nonArtiste;
    }

    public ArtistesItem(String jourNum, String nonArtiste, String prix1, String prix2) {
        this.jourNum = jourNum;
        NonArtiste = nonArtiste;
        this.prix1 = prix1;
        Prix2 = prix2;
    }

    public String getPrix1() {
        return prix1;
    }

    public void setPrix1(String prix1) {
        this.prix1 = prix1;
    }

    public String getPrix2() {
        return Prix2;
    }

    public void setPrix2(String prix2) {
        Prix2 = prix2;
    }

    public String getJourNum() {
        return jourNum;
    }

    public void setJourNum(String jourNum) {
        this.jourNum = jourNum;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getJourLet() {
        return jourLet;
    }

    public void setJourLet(String jourLet) {
        this.jourLet = jourLet;
    }

    public String getNonArtiste() {
        return NonArtiste;
    }

    public void setNonArtiste(String nonArtiste) {
        NonArtiste = nonArtiste;
    }
}
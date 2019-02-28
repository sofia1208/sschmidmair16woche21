package com.example.sschmidmair16woche21;

public class Rechnung {
    private String date;
    private String ausgabe;
    private double betrag;
    private String category;
    public static double cash;

    public Rechnung(String date, String ausgabe, String betrag, String category) {
        this.date = date;
        this.ausgabe = ausgabe;
        this.betrag = Double.parseDouble(betrag);
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAusgabe() {
        return ausgabe;
    }

    public void setAusgabe(String ausgabe) {
        this.ausgabe = ausgabe;
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(int betrag) {
        this.betrag = betrag;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}

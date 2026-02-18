package com.bank.konto; // Paketdeklaration

import java.time.LocalDate; // Import für das Datum

import jakarta.persistence.Column; // JPA-Annotation für Spalten
import jakarta.persistence.DiscriminatorValue; // JPA-Annotation für Vererbungstyp
import jakarta.persistence.Entity; // JPA-Annotation für Entity

@Entity // Markiert diese Klasse als JPA-Entity (wird als Tabelle gespeichert)
@DiscriminatorValue("KARTE") // Wert für die Unterscheidung im Single Table Inheritance
public class Kartenzahlung extends Kontoauszug { // Kartenzahlung erbt von Kontoauszug

    @Column(nullable = false) // Spalte darf nicht null sein
    private String kartennummer; // Nummer der verwendeten Karte

    @Column(nullable = false) // Spalte darf nicht null sein
    private String haendler; // Name des Händlers, bei dem gezahlt wurde

    protected Kartenzahlung() {
        // Geschützter Standardkonstruktor für JPA (wird für das Laden aus der DB
        // benötigt)
    }

    public Kartenzahlung(IBAN girokontoIban, LocalDate datum, double betrag, String verwendungszweck,
            String kartennummer, String haendler) {
        super(girokontoIban, datum, betrag, verwendungszweck); // Aufruf des Konstruktors der Basisklasse
        this.kartennummer = kartennummer; // Setzt die Kartennummer
        this.haendler = haendler; // Setzt den Händlernamen
    }

    public String getKartennummer() {
        return kartennummer; // Gibt die Kartennummer zurück
    }

    public void setKartennummer(String kartennummer) {
        this.kartennummer = kartennummer; // Setzt die Kartennummer neu
    }

    public String getHaendler() {
        return haendler; // Gibt den Händlernamen zurück
    }

    public void setHaendler(String haendler) {
        this.haendler = haendler; // Setzt den Händlernamen neu
    }

    @Override
    public String toString() {
        return "Kartenzahlung{" +
                "id=" + getId() + // ID aus der Basisklasse
                ", girokontoIban=" + getGirokontoIban() + // IBAN aus der Basisklasse
                ", datum=" + getDatum() + // Datum aus der Basisklasse
                ", betrag=" + getBetrag() + // Betrag aus der Basisklasse
                ", verwendungszweck='" + getVerwendungszweck() + '\'' + // Verwendungszweck aus der Basisklasse
                ", kartennummer='" + kartennummer + '\'' + // Kartennummer aus dieser Klasse
                ", haendler='" + haendler + '\'' + // Händlernamen aus dieser Klasse
                '}';
    }
}
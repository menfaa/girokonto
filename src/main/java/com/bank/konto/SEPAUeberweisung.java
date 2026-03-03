package com.bank.konto; // Paketdeklaration

import java.time.LocalDate; // Import für das Datum

import jakarta.persistence.Column; // JPA-Annotation für Spalten
import jakarta.persistence.DiscriminatorValue; // JPA-Annotation für Vererbungstyp
import jakarta.persistence.Entity; // JPA-Annotation für Entity

@Entity // Markiert diese Klasse als JPA-Entity (wird als Tabelle gespeichert)
@DiscriminatorValue("SEPA") // Wert für die Unterscheidung im Single Table Inheritance
public class SEPAUeberweisung extends Kontoauszug { // SEPAUeberweisung erbt von Kontoauszug

    @Column(nullable = false) // Spalte darf nicht null sein
    private String empfaengerIban; // IBAN des Empfängers

    @Column(nullable = false) // Spalte darf nicht null sein
    private String empfaengerName; // Name des Empfängers

    protected SEPAUeberweisung() {
        // Geschützter Standardkonstruktor für JPA (wird für das Laden aus der DB
        // benötigt)
    }

    public SEPAUeberweisung(IBAN girokontoIban, LocalDate datum, Betrag betrag, String verwendungszweck,
            String empfaengerIban, String empfaengerName) {
        super(girokontoIban, datum, betrag, verwendungszweck); // Aufruf des Konstruktors der Basisklasse
        this.empfaengerIban = empfaengerIban; // Setzt die Empfänger-IBAN
        this.empfaengerName = empfaengerName; // Setzt den Empfänger-Namen
    }

    public String getEmpfaengerIban() {
        return empfaengerIban; // Gibt die Empfänger-IBAN zurück
    }

    public void setEmpfaengerIban(String empfaengerIban) {
        this.empfaengerIban = empfaengerIban; // Setzt die Empfänger-IBAN neu
    }

    public String getEmpfaengerName() {
        return empfaengerName; // Gibt den Empfänger-Namen zurück
    }

    public void setEmpfaengerName(String empfaengerName) {
        this.empfaengerName = empfaengerName; // Setzt den Empfänger-Namen neu
    }

    @Override
    public String toString() {
        return "SEPAUeberweisung{" +
                "id=" + getId() + // ID aus der Basisklasse
                ", girokontoIban=" + getGirokontoIban() + // IBAN aus der Basisklasse
                ", datum=" + getDatum() + // Datum aus der Basisklasse
                ", betrag=" + getBetrag().wert() + // Betrag aus der Basisklasse
                ", verwendungszweck='" + getVerwendungszweck() + '\'' + // Verwendungszweck aus der Basisklasse
                ", empfaengerIban='" + empfaengerIban + '\'' + // Empfänger-IBAN aus dieser Klasse
                ", empfaengerName='" + empfaengerName + '\'' + // Empfänger-Name aus dieser Klasse
                '}';
    }
}
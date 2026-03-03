package com.bank.impl;

import java.time.LocalDate;

import com.bank.api.PaymentStrategy;
import com.bank.konto.Betrag;
import com.bank.konto.Girokonto;
import com.bank.konto.Kartenzahlung;

public class KartenPaymentStrategy implements PaymentStrategy {
    private final String kartennummer;
    private final String haendler;

    public KartenPaymentStrategy(String kartennummer, String haendler) {
        this.kartennummer = kartennummer;
        this.haendler = haendler;
    }

    @Override
    public void executePayment(Girokonto girokonto, Betrag betrag, String verwendungszweck) {
        Kartenzahlung kartenzahlung = new Kartenzahlung(
                girokonto.getIban(),
                LocalDate.now(),
                betrag,
                verwendungszweck,
                kartennummer,
                haendler);
        girokonto.addKontoauszug(kartenzahlung);
    }
}

package com.bank.impl;

import java.time.LocalDate;

import com.bank.api.PaymentStrategy;
import com.bank.konto.Betrag;
import com.bank.konto.Girokonto;
import com.bank.konto.SEPAUeberweisung;

public class SepaPaymentStrategy implements PaymentStrategy {
    private final String empfaengerIban;
    private final String empfaengerName;

    public SepaPaymentStrategy(String empfaengerIban, String empfaengerName) {
        this.empfaengerIban = empfaengerIban;
        this.empfaengerName = empfaengerName;
    }

    @Override
    public void executePayment(Girokonto girokonto, Betrag betrag, String verwendungszweck) {
        SEPAUeberweisung ueberweisung = new SEPAUeberweisung(
                girokonto.getIban(),
                LocalDate.now(),
                betrag,
                verwendungszweck,
                empfaengerIban,
                empfaengerName);
        girokonto.addKontoauszug(ueberweisung);
    }
}
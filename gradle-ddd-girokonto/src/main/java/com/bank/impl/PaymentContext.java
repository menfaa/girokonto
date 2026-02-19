package com.bank.impl;

import com.bank.api.PaymentStrategy;
import com.bank.konto.Betrag;
import com.bank.konto.Girokonto;

public class PaymentContext {
    private PaymentStrategy strategy;

    // Konstruktor oder Setter für die Strategie
    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    // Führt die Zahlung mit der aktuell gesetzten Strategie aus
    public void executePayment(Girokonto girokonto, Betrag betrag, String verwendungszweck) {
        if (strategy == null) {
            throw new IllegalStateException("Keine Zahlungsstrategie gesetzt!");
        }
        strategy.executePayment(girokonto, betrag, verwendungszweck);
    }
}

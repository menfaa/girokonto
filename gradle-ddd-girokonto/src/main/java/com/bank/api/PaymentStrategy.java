package com.bank.api;

import com.bank.konto.Betrag;
import com.bank.konto.Girokonto;

public interface PaymentStrategy {
    void executePayment(Girokonto girokonto, Betrag betrag, String verwendungszweck);
}

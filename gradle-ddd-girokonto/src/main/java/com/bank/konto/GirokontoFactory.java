package com.bank.konto;

import org.springframework.stereotype.Service;

import com.bank.inhaber.InhaberID;

// Factory für die Erstellung von Girokonto-Aggregaten
@Service
public class GirokontoFactory {

    public Girokonto create(String ibanValue, InhaberID inhaberId) {
        IBAN iban = new IBAN(ibanValue);
        return new Girokonto(iban, inhaberId);
    }
}
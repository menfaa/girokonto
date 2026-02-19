package com.bank.facory;

import org.springframework.stereotype.Service;

import com.bank.inhaber.InhaberID;
import com.bank.konto.Girokonto;
import com.bank.konto.IBAN;

// Factory für die Erstellung von Girokonto-Aggregaten
@Service
public class GirokontoFactory {

    public Girokonto create(String ibanValue, InhaberID inhaberId) {
        IBAN iban = new IBAN(ibanValue);
        return new Girokonto(iban, inhaberId);
    }
}
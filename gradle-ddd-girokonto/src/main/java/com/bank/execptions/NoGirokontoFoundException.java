package com.bank.execptions;

import com.bank.konto.IBAN;

public class NoGirokontoFoundException extends RuntimeException {
    IBAN iban;

    public NoGirokontoFoundException(IBAN iban) {
        this.iban = iban;
    }

    public IBAN getIban() {
        return iban;
    }

}

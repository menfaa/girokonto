package com.bank.api;

import com.bank.konto.Girokonto;
import com.bank.konto.Kontoauszug;

public interface KontoObserver {
    void update(Girokonto konto, Kontoauszug auszug);
}
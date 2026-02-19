package com.bank.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.api.PaymentStrategy;
import com.bank.impl.PaymentContext;
import com.bank.konto.Betrag;
import com.bank.konto.Girokonto;

@Service
public class PaymentService {

    private final GirokontoService service;

    public PaymentService(GirokontoService service) {
        this.service = service;
    }

    @Transactional
    public void processPayment(String iban, Betrag betrag, String verwendungszweck, PaymentStrategy strategy) {
        Girokonto girokonto = service.findByIban(iban);
        PaymentContext context = new PaymentContext(strategy);
        context.executePayment(girokonto, betrag, verwendungszweck);
        service.save(girokonto);
    }
}

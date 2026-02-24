package com.bank;

import com.bank.konto.Betrag;
import com.bank.konto.Girokonto;
import com.bank.konto.IBAN;
import com.bank.konto.Kontoauszug;
import com.bank.konto.Kontostand;
import com.bank.middleware.security.*;
import com.bank.service.GirokontoService;
import com.bank.service.PaymentService;
import com.bank.api.PaymentStrategy;
import com.bank.impl.EmailBenachrichtigungObserver;
import com.bank.impl.KartenPaymentStrategy;
import com.bank.impl.PushBenachrichtigungObserver;
import com.bank.impl.SepaPaymentStrategy;
import com.bank.inhaber.InhaberID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DemoService {

        private static final Logger log = LoggerFactory.getLogger(DemoService.class);

        private final GirokontoService girokontoService;
        private final PaymentService paymentService;

        public DemoService(GirokontoService girokontoService, PaymentService paymentService) {
                this.girokontoService = girokontoService;
                this.paymentService = paymentService;
        }

        @Transactional
        public void demoAblauf() {
                // 1. Girokonto anlegen
                IBAN iban = new IBAN("DE12345678901234567890");
                InhaberID inhaberId = new InhaberID("INHABER123");
                Girokonto girokonto = new Girokonto(iban, inhaberId);
                girokonto.addObserver(new PushBenachrichtigungObserver());
                girokonto.addObserver(new EmailBenachrichtigungObserver());

                log.info("Neues Girokonto angelegt: {}", girokonto);

                Kontostand kontostand = girokonto.getKontostand();
                log.info("Aktueller Kontostand: {} EUR", kontostand.betrag().wert());

                // Token generieren
                String token = TokenUtil.generateToken(iban.getValue());
                log.info("PKI-Token für IBAN {}: {}", iban.getValue(), token);

                // 2. Einzahlung (Kontoauszug) hinzufügen
                Betrag betrag = new Betrag(500);
                Kontoauszug einzahlung = new Kontoauszug(
                                iban,
                                LocalDate.now(),
                                betrag,
                                "Ersteinzahlung");
                girokonto.addKontoauszug(einzahlung);

                // Token-Check vor save
                if (TokenUtil.validateToken(iban.getValue(), token)) {
                        girokontoService.save(girokonto);
                        log.info("Einzahlung hinzugefügt und gespeichert: {}", einzahlung);
                } else {
                        log.warn("Token ungültig, Einzahlung nicht gespeichert!");
                }

                // 3. Auszahlung (Kontoauszug) hinzufügen
                betrag = new Betrag(-200);
                Kontoauszug auszahlung = new Kontoauszug(
                                iban,
                                LocalDate.now(),
                                betrag,
                                "Erstauszahlung");

                girokonto.addKontoauszug(auszahlung);

                // Token-Check vor save
                if (TokenUtil.validateToken(iban.getValue(), token)) {
                        girokontoService.save(girokonto);
                        log.info("Auszahlung hinzugefügt und gespeichert: {}", auszahlung);
                } else {
                        log.warn("Token ungültig, Auszahlung nicht gespeichert!");
                }

                // 4. Zahlungen ausführen (hier kein Token-Check, da kein save)
                betrag = new Betrag(1000.0);
                PaymentStrategy sepa = new SepaPaymentStrategy("DE44556677889900112233", "Vermieter GmbH");
                paymentService.processPayment("DE12345678901234567890", betrag, "Miete", sepa);

                betrag = new Betrag(50.0);
                PaymentStrategy karte = new KartenPaymentStrategy("1234-5678-9012-3456", "EDEKA");
                paymentService.processPayment("DE12345678901234567890", betrag, "Supermarkt", karte);

                // 5. Girokonto erneut laden und Kontostand/Auszüge ausgeben
                girokonto = girokontoService.findByIbanWithKontoauszuege(girokonto.getIban().getValue()).orElseThrow();
                kontostand = girokonto.getKontostand();
                log.info("Aktueller Kontostand nach Auszahlung: {} EUR", kontostand.betrag().wert());

                log.info("Alle Kontoauszüge:");
                List<Kontoauszug> kontoauzuege = girokonto.getKontoauszuege();
                kontoauzuege.forEach(auszug -> log.info(auszug.toString()));
        }
}
package com.bank;

import com.bank.konto.Girokonto;
import com.bank.konto.GirokontoService;
import com.bank.konto.IBAN;
import com.bank.konto.Kartenzahlung;
import com.bank.konto.Kontoauszug;
import com.bank.konto.Kontostand;
import com.bank.konto.SEPAUeberweisung;
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

    public DemoService(GirokontoService girokontoService) {
        this.girokontoService = girokontoService;
    }

    @Transactional
    public void demoAblauf() {
        // 1. Girokonto anlegen
        IBAN iban = new IBAN("DE12345678901234567890");
        InhaberID inhaberId = new InhaberID("INHABER123");
        Girokonto girokonto = new Girokonto(iban, inhaberId);

        log.info("Neues Girokonto angelegt: {}", girokonto);

        Kontostand kontostand = girokonto.getKontostand();
        log.info("Aktueller Kontostand: {} EUR", kontostand.betrag());

        // 2. Einzahlung (Kontoauszug) hinzufügen
        Kontoauszug einzahlung = new Kontoauszug(
                iban,
                LocalDate.now(),
                500.0,
                "Ersteinzahlung");
        girokonto.addKontoauszug(einzahlung);

        log.info("Einzahlung hinzugefügt: {}", einzahlung);

        // 3. Auszahlung (Kontoauszug) hinzufügen
        Kontoauszug auszahlung = new Kontoauszug(
                iban,
                LocalDate.now(),
                -200.0,
                "Erstauszahlung");

        girokonto.addKontoauszug(auszahlung);
        log.info("Auszahlung hinzugefügt: {}", auszahlung);

        Kontoauszug sepa = new SEPAUeberweisung(
                iban,
                LocalDate.now(),
                -100.0,
                "Miete",
                "DE44556677889900112233",
                "Vermieter GmbH");

        Kontoauszug karte = new Kartenzahlung(
                iban,
                LocalDate.now(),
                -20.0,
                "Supermarkt",
                "1234-5678-9012-3456",
                "EDEKA");

        // 5. Sepa (Kontoauszug) hinzufügen
        girokonto.addKontoauszug(sepa);

        // 5. Karten (Kontoauszug) hinzufügen
        girokonto.addKontoauszug(karte);

        // 4. Girokonto speichern
        girokontoService.save(girokonto);

        // 5. Girokonto erneut laden und Kontostand/Auszüge ausgeben
        girokonto = girokontoService.findByIbanWithKontoauszuege(girokonto.getIban().getValue()).orElseThrow();
        kontostand = girokonto.getKontostand();
        log.info("Aktueller Kontostand nach Auszahlung: {} EUR", kontostand.betrag());

        log.info("Alle Kontoauszüge:");
        List<Kontoauszug> kontoauzuege = girokonto.getKontoauszuege();
        kontoauzuege.forEach(auszug -> log.info(auszug.toString()));
    }
}
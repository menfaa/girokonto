package com.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.execptions.NoGirokontoFoundException;
import com.bank.konto.Girokonto;
import com.bank.konto.GirokontoService;
import com.bank.konto.IBAN;
import java.util.Optional;

@RestController
@RequestMapping("/api/girokonten")
@CrossOrigin(origins = "http://localhost:3000") // Erlaubt Zugriff vom React-Frontend
public class GirokontoController {

    private final GirokontoService service;

    public GirokontoController(GirokontoService service) {
        this.service = service;
    }

    // Alle Girokonten abrufen
    @GetMapping
    public Iterable<Girokonto> alleGirokonten() {
        return service.findAllGirokontos();
    }

    // Einzelnes Girokonto per IBAN abrufen
    @GetMapping("/{iban}")
    public Girokonto kontoByIban(@PathVariable IBAN iban) {
        return service.findByIban(iban.getValue());
    }

    // Einzelnes Girokonto per IBAN abrufen
    @GetMapping("/kontoauszuege/{iban}")
    public Optional<Girokonto> kontoByIbanithKontoauszuege(@PathVariable String iban) {
        return service.findByIbanWithKontoauszuege(iban);
    }

    // Neues Girokonto anlegen
    @PostMapping
    public void neuesKonto(@RequestBody Girokonto girokonto) {
        service.save(girokonto);
    }

    // Konto löschen
    @DeleteMapping("/{iban}")
    public void deleteKonto(@PathVariable IBAN iban) {
        service.deleteByIban(iban.getValue());
    }

    @ExceptionHandler(NoGirokontoFoundException.class)
    public ResponseEntity<String> handleNoGirokontoFoundException(NoGirokontoFoundException ex) {

        return ResponseEntity.internalServerError().body("No Girokonto found with iban: " + ex.getIban().getValue());
    }
}
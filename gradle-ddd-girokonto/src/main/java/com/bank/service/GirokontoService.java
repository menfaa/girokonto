package com.bank.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.execptions.NoGirokontoFoundException;
import com.bank.facory.GirokontoFactory;
import com.bank.inhaber.InhaberID;
import com.bank.konto.Girokonto;
import com.bank.konto.GirokontoRepository;
import com.bank.konto.IBAN;

// Service für Geschäftslogik rund um Girokonto
@Service
public class GirokontoService {

    private final GirokontoRepository repository;
    private final GirokontoFactory factory;

    public GirokontoService(GirokontoRepository repository, GirokontoFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    // Erzeugt und speichert ein neues Girokonto
    public Girokonto createGirokonto(String ibanValue, InhaberID inhaberId) {
        Girokonto girokonto = factory.create(ibanValue, inhaberId);
        repository.save(girokonto);
        return girokonto;
    }

    // Sucht ein Girokonto nach IBAN
    public Iterable<Girokonto> findAllGirokontos() {
        return repository.findAll();
    }

    // Sucht ein Girokonto nach IBAN
    public Girokonto findByIban(String ibanValue) {
        IBAN iban = new IBAN(ibanValue);
        return repository.findByIban(iban).orElseThrow(() -> new NoGirokontoFoundException(iban));
    }

    // Sucht ein Girokonto nach IBAN
    public Optional<Girokonto> findByIbanWithKontoauszuege(String ibanValue) {
        return repository.findByIbanWithKontoauszuege(ibanValue);
    }

    // Löscht ein Girokonto nach IBAN
    @Transactional
    public void deleteByIban(String ibanValue) {
        IBAN iban = new IBAN(ibanValue);
        repository.deleteByIban(iban);
    }

    // Save Girokonto
    public void save(Girokonto konto) {
        repository.save(konto);
    }

    public Iterable<Girokonto> getAllGirokontos() {
        return repository.findAll();
    }

}
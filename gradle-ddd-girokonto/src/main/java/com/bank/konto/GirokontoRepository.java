package com.bank.konto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// Repository-Interface für Girokonto

public interface GirokontoRepository extends CrudRepository<Girokonto, IBAN> {

    @Query("SELECT g FROM Girokonto g LEFT JOIN FETCH g.kontoauszuege WHERE g.iban.value = :iban")
    Optional<Girokonto> findByIbanWithKontoauszuege(@Param("iban") String iban);

    Optional<Girokonto> findByIban(IBAN iban);

    void deleteByIban(IBAN iban);
}
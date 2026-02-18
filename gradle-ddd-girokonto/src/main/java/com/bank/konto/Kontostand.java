package com.bank.konto;

import jakarta.persistence.Embeddable;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject // DDD-Annotation für Value Object
@Embeddable // JPA-Annotation für eingebettetes Objekt
public record Kontostand(double betrag) {
    // Der Kontostand besteht nur aus einem Wert (Betrag)
    // Mit einem Record bekommst du automatisch equals, hashCode und toString
}
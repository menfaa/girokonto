// Bounded Context: Inhaber
package com.bank.inhaber;

import jakarta.persistence.*;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

@AggregateRoot
@Entity
public class Inhaber {

    @Id
    @Embedded
    @Identity
    private InhaberID inhaberId; // InhaberID als Value Object und Identität

    @Column(nullable = false)
    private String name; // Name des Inhabers

    protected Inhaber() {
    }

    public Inhaber(InhaberID inhaberId, String name) {
        this.inhaberId = inhaberId;
        this.name = name;
    }

    public InhaberID getInhaberId() {
        return inhaberId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Inhaber{" +
                "inhaberId=" + inhaberId +
                ", name='" + name + '\'' +
                '}';
    }

}
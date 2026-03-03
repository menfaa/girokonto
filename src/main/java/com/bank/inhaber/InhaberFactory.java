package com.bank.inhaber;

// Factory für die Erstellung von Inhaber-Aggregaten
public class InhaberFactory {

    public Inhaber create(String inhaberIdValue, String name) {
        InhaberID inhaberId = new InhaberID(inhaberIdValue);
        return new Inhaber(inhaberId, name);
    }
}
package com.bank.inhaber;

// Service für Geschäftslogik rund um Inhaber
public class InhaberService {

    private final InhaberFactory factory;

    public InhaberService(InhaberFactory factory) {
        this.factory = factory;
    }

    // Erzeugt einen neuen Inhaber
    public Inhaber createInhaber(String inhaberIdValue, String name) {
        return factory.create(inhaberIdValue, name);
    }
}
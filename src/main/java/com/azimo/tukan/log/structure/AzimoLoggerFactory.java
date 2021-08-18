package com.azimo.tukan.log.structure;

@SuppressWarnings("unused")
public final class AzimoLoggerFactory {

    private AzimoLoggerFactory() {
    }

    public static AzimoLogger getLogger(final Class<?> clazz) {
        return new AzimoLogger(clazz);
    }

    public static AzimoLogger getLogger(String name) {
        return new AzimoLogger(name);
    }
}


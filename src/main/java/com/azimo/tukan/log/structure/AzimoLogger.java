package com.azimo.tukan.log.structure;

import org.apache.logging.log4j.Level;

public final class AzimoLogger extends AbstractLogger<AzimoLogger.Api> {

    public interface Api extends LoggingApi<Api> {
    }

    private static final class NoOp extends LoggingApi.NoOp<Api> implements Api {
    }

    static final NoOp NO_OP = new NoOp();


    AzimoLogger(Class<?> clazz) {
        super(clazz);
    }

    AzimoLogger(String name) {
        super(name);
    }

    @Override
    public Api at(Level level) {
        boolean isLoggable = isLoggable(level);
        return (isLoggable) ? new Context(level) : NO_OP;
    }

    final class Context extends LogContext<AzimoLogger, Api> implements Api {
        private Context(final Level level) {
            super(level);
        }

        @Override
        protected AzimoLogger getLogger() {
            return AzimoLogger.this;
        }

        @Override
        protected Api api() {
            return this;
        }

        @Override
        protected Api noOp() {
            return NO_OP;
        }
    }
}

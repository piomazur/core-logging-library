package com.azimo.tukan.log.structure;

import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;

import static com.azimo.tukan.log.structure.ExtendedLevelLogger.CRITICAL;
import static com.azimo.tukan.log.structure.utils.StructuredMessageBuilder.provideStructuredException;
import static com.azimo.tukan.log.structure.utils.StructuredMessageBuilder.provideStructuredKeyValues;
import static lombok.Lombok.checkNotNull;

@SuppressWarnings("unused")
public abstract class AbstractLogger<API extends LoggingApi<API>> {

    private final ExtendedLevelLogger subLogger;

    private Map<String, Object> metadata = new HashMap<>();

    protected AbstractLogger(final Class<?> clazz) {
        final Class<?> loggerClass = checkNotNull(clazz, "Logger class");
        this.subLogger = ExtendedLevelLogger.create(loggerClass);
    }

    protected AbstractLogger(final String name) {
        final String loggerName = checkNotNull(name, "Logger name");
        this.subLogger = ExtendedLevelLogger.create(loggerName);
    }

    public abstract API at(Level level);

    public final API atWarning() {
        return at(Level.WARN);
    }

    public final API atInfo() {
        return at(Level.INFO);
    }

    public final API atDebug() {
        return at(Level.DEBUG);
    }

    public final API atTrace() {
        return at(Level.TRACE);
    }

    public final API atError() {
        return at(Level.ERROR);
    }

    public final API atCritical() {
        return at(CRITICAL);
    }

    public void trace(final String var1) {
        subLogger.trace(var1, provideStructuredKeyValues(metadata));
    }

    public void trace(final String var1, final Object... var2) {
        subLogger.trace(var1, provideStructuredKeyValues(metadata, var2));
    }

    public void trace(final String var1, final Throwable var2) {
        subLogger.trace(var1, provideStructuredException(var2));
    }


    public void debug(final String var1) {
        subLogger.debug(var1, provideStructuredKeyValues(metadata));
    }

    public void debug(final String var1, final Object... var2) {
        subLogger.debug(var1, provideStructuredKeyValues(metadata, var2));
    }

    public void debug(final String var1, final Throwable var2) {
        subLogger.debug(var1, provideStructuredException(var2));
    }


    public void info(final String var1) {
        subLogger.info(var1, provideStructuredKeyValues(metadata));
    }

    public void info(final String var1, final Object... var2) {
        subLogger.info(var1, provideStructuredKeyValues(metadata, var2));
    }

    public void info(final String var1, final Throwable var2) {
        subLogger.info(var1, provideStructuredException(var2));
    }


    public void warn(final String var1) {
        subLogger.warn(var1, provideStructuredKeyValues(metadata));
    }

    public void warn(final String var1, final Object... var2) {
        subLogger.warn(var1, provideStructuredKeyValues(metadata, var2));
    }

    public void warn(final String var1, final Throwable var2) {
        subLogger.warn(var1, provideStructuredException(var2));
    }


    public void error(final String var1) {
        subLogger.error(var1, provideStructuredKeyValues(metadata));
    }

    public void error(final String var1, final Object... var2) {
        subLogger.error(var1, provideStructuredKeyValues(metadata, var2));
    }

    public void error(final String var1, final Throwable var2) {
        subLogger.error(var1, provideStructuredException(var2));
    }


    public void critical(final String var1) {
        subLogger.critical(var1, provideStructuredKeyValues(metadata));
    }

    public void critical(final String var1, final Object... var2) {
        subLogger.critical(var1, provideStructuredKeyValues(metadata, var2));
    }

    public void critical(final String var1, final Throwable var2) {
        subLogger.critical(var1, provideStructuredException(var2));
    }

    public void put(final String key, final Object value) {

        if (metadata == null) {
            metadata = new HashMap<>();
        }

        if (metadata.containsKey(key)) {
            throw new IllegalArgumentException("Provided key " + key + " has been already used.");
        }
        metadata.put(key, value);
    }

    public void remove(final String key) {
        if (metadata != null && metadata.containsKey(key))
            metadata.remove(key);
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void clearMetadata() {
        metadata.clear();
    }

    protected String getName() {
        return subLogger.getName();
    }

    protected boolean isLoggable(final Level level) {
        return subLogger.isEnabled(level);
    }

    final ExtendedLevelLogger getSubLogger() {
        return subLogger;
    }
}

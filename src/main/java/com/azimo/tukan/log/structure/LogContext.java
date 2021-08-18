package com.azimo.tukan.log.structure;

import com.azimo.tukan.log.structure.model.ExceptionDetails;
import com.azimo.tukan.log.structure.model.HttpRequest;
import com.azimo.tukan.log.structure.model.HttpResponse;
import com.azimo.tukan.log.structure.model.KafkaMessage;
import com.azimo.tukan.log.structure.model.ProcessDetails;
import com.azimo.tukan.log.structure.utils.ExceptionMessagesBuilder;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.azimo.tukan.log.structure.utils.StructuredMessageBuilder.provideStructuredKeyValues;
import static lombok.Lombok.checkNotNull;

@SuppressWarnings("unused")
public abstract class LogContext<LOGGER extends AbstractLogger<API>, API extends LoggingApi<API>>
        implements LoggingApi<API> {

    public final static String LOG_CAUSE = "exception";

    public final static String LOG_HTTP_REQUEST = "request";

    public final static String LOG_HTTP_RESPONSE = "response";

    public final static String LOG_KAFKA_MESSAGE = "kafka";

    public final static String LOG_PROCESS_DETAILS = "extra";

    private Map<String, Object> metadata = new HashMap<>();

    private final Level level;

    protected LogContext(final Level level) {
        this.level = checkNotNull(level, "level");
    }

    protected abstract API api();

    protected abstract LOGGER getLogger();

    protected abstract API noOp();


    public final Level getLevel() {
        return level;
    }

    public final Map getMetadata() {
        final Map<String, Object> combinedMetadata = new HashMap<>();

        if (getLogger().getMetadata() != null) {
            combinedMetadata.putAll(getLogger().getMetadata());
        }

        if (metadata != null) {
            combinedMetadata.putAll(metadata);
        }

        return combinedMetadata;
    }

    protected final <T> void addMetadata(final String key, final T value) {
        if (metadata == null) {
            metadata = new HashMap<>();
        }
        metadata.put(key, value);
    }

    protected final void removeMetadata(final String key) {
        if (metadata != null) {
            metadata.remove(key);
        }
    }

    @Override
    public final boolean isEnabled() {
        return getLogger().isLoggable(level);
    }

    @Override
    public final <T> API with(final String key, final T value) {
        checkNotNull(key, "metadata key");
        if (value != null) {
            addMetadata(key, value);
        }
        return api();
    }

    @Override
    public final API withCause(final Throwable cause) {
        return withCause(cause, new ArrayList<>());
    }

    @Override
    public final API withCause(final Throwable cause, final List<String> context) {
        if (cause != null) {
            ExceptionDetails exceptionDetails = new ExceptionDetails(cause.getMessage(), ExceptionMessagesBuilder.build(cause), context);
            addMetadata(LOG_CAUSE, exceptionDetails);
        }
        return api();
    }

    @Override
    public API withCause(final Throwable cause, final int stackSize, final String packagePrefix) {
        return withCause(cause, stackSize, packagePrefix, new ArrayList<>());
    }

    @Override
    public API withCause(final Throwable cause, final int stackSize, final String packagePrefix, final List<String> context) {
        if (cause != null) {
            ExceptionDetails exceptionDetails = new ExceptionDetails(cause.getMessage(), ExceptionMessagesBuilder.build(cause, stackSize, packagePrefix), context);
            addMetadata(LOG_CAUSE, exceptionDetails);
        }
        return api();
    }

    @Override
    public API withHttpRequest(final HttpRequest request) {
        if (request != null) {
            addMetadata(LOG_HTTP_REQUEST, request);
        }
        return api();
    }

    @Override
    public API withHttpResponse(final HttpResponse response) {
        if (response != null) {
            addMetadata(LOG_HTTP_RESPONSE, response);
        }
        return api();
    }

    @Override
    public API withKafkaMessage(final KafkaMessage kafkaMessage) {
        if (kafkaMessage != null) {
            addMetadata(LOG_KAFKA_MESSAGE, kafkaMessage);
        }
        return api();
    }

    @Override
    public API withProcessDetails(final ProcessDetails processDetails) {
        if (processDetails != null) {
            addMetadata(LOG_PROCESS_DETAILS, processDetails);
        }
        return api();
    }

    @Override
    public final void log() {
        log("");
    }

    @Override
    public final void log(final String msg) {
        getLogger().getSubLogger().log(level, msg, provideStructuredKeyValues(getMetadata()));
    }

    @Override
    public final void log(final String msg, final Object... p1) {
        getLogger().getSubLogger().log(level, msg, provideStructuredKeyValues(getMetadata(), p1));
    }
}

package com.azimo.tukan.log.structure.utils;

import com.azimo.tukan.log.structure.LogContext;
import com.azimo.tukan.log.structure.model.ExceptionDetails;
import com.azimo.tukan.log.structure.model.HttpRequest;
import com.azimo.tukan.log.structure.model.HttpResponse;
import com.azimo.tukan.log.structure.model.KafkaMessage;
import com.azimo.tukan.log.structure.model.ProcessDetails;
import net.logstash.logback.argument.StructuredArgument;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.logstash.logback.argument.StructuredArguments.kv;

public final class StructuredMessageBuilder {
    public static StructuredArgument provideStructuredException(final Throwable var2) {
        return kv("exception", new ExceptionDetails(var2.getMessage(), ExceptionMessagesBuilder.build(var2), null));
    }

    public static Object[] provideStructuredKeyValues(Map<String, Object> keyValueMap, final Object... vars) {
        return Stream.concat(
                provideStructuredKeyValueForVars(vars),
                provideStructureKeyValueForMap(keyValueMap))
                .collect(Collectors.toList())
                .toArray(new Object[0]);
    }

    private static Stream<Object> provideStructuredKeyValueForVars(final Object[] vars) {
        return Stream.of(vars).map(value -> provideStructuredKeyValue(value));
    }

    private static Object provideStructuredKeyValue(final Object value) {
        if (value instanceof HttpRequest) {
            return kv(LogContext.LOG_HTTP_REQUEST, value);
        }
        if (value instanceof HttpResponse) {
            return kv(LogContext.LOG_HTTP_RESPONSE, value);
        }
        if (value instanceof KafkaMessage) {
            return kv(LogContext.LOG_KAFKA_MESSAGE, value);
        }
        if (value instanceof ProcessDetails) {
            return kv(LogContext.LOG_PROCESS_DETAILS, value);
        }
        if (value instanceof ExceptionDetails) {
            return kv(LogContext.LOG_CAUSE, value);
        }

        return value;
    }

    private static Stream<StructuredArgument> provideStructureKeyValueForMap(final Map<String, Object> keyValueMap) {
        return keyValueMap.entrySet().stream()
                .map(entry -> kv(entry.getKey(), entry.getValue()));
    }

    private StructuredMessageBuilder() {
    }
}

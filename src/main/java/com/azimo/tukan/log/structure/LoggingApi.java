package com.azimo.tukan.log.structure;

import com.azimo.tukan.log.structure.model.HttpRequest;
import com.azimo.tukan.log.structure.model.HttpResponse;
import com.azimo.tukan.log.structure.model.KafkaMessage;
import com.azimo.tukan.log.structure.model.ProcessDetails;

import java.util.List;

@SuppressWarnings("unused")
public interface LoggingApi<API extends LoggingApi<API>> {

    API withCause(Throwable cause);

    API withCause(Throwable cause, List<String> context);

    API withCause(Throwable cause, int stackSize, String packagePrefix);

    API withCause(Throwable cause, int stackSize, String packagePrefix, List<String> context);

    API withHttpRequest(HttpRequest request);

    API withHttpResponse(HttpResponse response);

    API withKafkaMessage(KafkaMessage kafkaMessage);

    API withProcessDetails(ProcessDetails processDetails);

    <T> API with(String key, T value);

    boolean isEnabled();

    void log();

    void log(String msg);

    void log(String msg, final Object... p1);

    public static class NoOp<API extends LoggingApi<API>> implements LoggingApi<API> {
        @SuppressWarnings("unchecked")
        protected final API noOp() {
            return (API) this;
        }

        @Override
        public final boolean isEnabled() {
            return false;
        }

        @Override
        public final <T> API with(final String key, final T value) {
            return noOp();
        }

        @Override
        public final API withCause(final Throwable cause) {
            return noOp();
        }

        @Override
        public final API withCause(final Throwable cause, final List<String> context) {
            return noOp();
        }

        @Override
        public final API withCause(final Throwable cause, final int stackSize, final String packagePrefix) {
            return noOp();
        }

        @Override
        public final API withCause(final Throwable cause, final int stackSize, final String packagePrefix, final List<String> context) {
            return noOp();
        }

        @Override
        public final API withHttpRequest(final HttpRequest request) {
            return noOp();
        }

        @Override
        public final API withHttpResponse(final HttpResponse response) {
            return noOp();
        }

        @Override
        public final API withKafkaMessage(final KafkaMessage kafkaMessage) {
            return noOp();
        }

        @Override
        public final API withProcessDetails(final ProcessDetails processDetails) {
            return noOp();
        }

        @Override
        public final void log() {
        }

        @Override
        public final void log(final String msg) {
        }

        @Override
        public final void log(final String msg, Object... p1) {
        }
    }
}

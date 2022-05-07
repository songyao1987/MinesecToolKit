package com.minesec.android.toolkit.text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public abstract class Json {
    public static Encoder getEncoder() {
        return Encoder.INSTANCE;
    }

    public static Decoder getDecoder() {
        return Decoder.INSTANCE;
    }

    public static class Encoder {

        private Encoder() {}

        static final Encoder INSTANCE = new Encoder();

        public String encode(Object value) {
            return Impl.GSON.toJson(value);
        }

    }

    public static class Decoder {

        private Decoder() {}

        static final Decoder INSTANCE = new Decoder();

        public <T> T decode(String json, Class<T> type) {
            return Impl.GSON.fromJson(json, type);
        }

    }

    private static final class Impl {

        static final Gson GSON;

        static {
            GSON = new GsonBuilder().create();
        }

    }

    public static final class Maskable {

        public static Maskable of(String value) {
            return new Maskable(value);
        }

        private final String value;

        private Maskable(String value) {
            this.value = value;
        }

        public String get() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }

    }

    public static class MaskableAdapter extends TypeAdapter<Maskable> {

        @Override
        public void write(JsonWriter out, Maskable value) throws IOException {
            //TODO output masked string
            out.value(value.get());
        }

        @Override
        public Maskable read(JsonReader in) throws IOException {
            return Maskable.of(in.nextString());
        }

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Masking {
    }

}

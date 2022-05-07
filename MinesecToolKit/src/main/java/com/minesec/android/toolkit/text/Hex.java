package com.minesec.android.toolkit.text;

public abstract class Hex {

    public static Decoder getDecoder() {
        return Decoder.INSTANCE;
    }

    public static Encoder getEncoder() {
        return Encoder.INSTANCE;
    }

    public static class Decoder {

        static final Decoder INSTANCE = new Decoder();

        public byte[] decode(String src) {
            char[] hex = src.toCharArray();
            int len = hex.length / 2;
            byte[] out = new byte[len];
            for (int i = 0; i < len; i++) {
                int high =  Character.digit(hex[i * 2], 16);
                int low =  Character.digit(hex[i * 2 + 1], 16);
                int value = (high << 4) | low;
                //if (value > 127) value -= 256;
                out[i] = (byte)value;
            }
            return out;
        }

    }

    public static class Encoder {

        static final Encoder INSTANCE = new Encoder();

        private static final char[] toHex = {
                '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
        };

        public String encode(byte[] src) {
            char[] hex = toHex;
            StringBuilder sb = new StringBuilder();
            for (byte b : src) {
                sb.append(hex[(b & 0xF0) >> 4]);
                sb.append(hex[b & 0x0F]);
            }
            return sb.toString();
        }

    }

}

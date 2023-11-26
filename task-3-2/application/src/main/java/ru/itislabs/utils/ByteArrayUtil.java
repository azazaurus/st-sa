package ru.itislabs.utils;

public class ByteArrayUtil {
    public static byte[] concat(byte[] first, byte[] second) {
        if (first == null || first.length == 0)
            return second;
        if (second == null || second.length == 0)
            return first;
        var result = new byte[first.length + second.length];
        System.arraycopy(first, 0, result, 0, first.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}

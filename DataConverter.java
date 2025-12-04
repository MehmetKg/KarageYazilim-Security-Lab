package com.example.karageyazilimsecuritylab;

public class DataConverter {

    // String -> Hex
    public static String stringToHex(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            sb.append(Integer.toHexString((int) c)).append(" ");
        }
        return "HEX: " + sb.toString().toUpperCase();
    }

    // String -> Binary
    public static String stringToBinary(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            sb.append(Integer.toBinaryString((int) c)).append(" ");
        }
        return "BIN: " + sb.toString();
    }
}
package com.simonbrunner.msnswitchctrl.util;

public class StringUtil {

    public static final String WHITESPACE = "\u0020";

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }
}

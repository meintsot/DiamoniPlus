package com.tedi.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime fromStringToLocalDateTime(String localDateTimeString) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

        return LocalDateTime.parse(localDateTimeString, formatter);
    }

    public static String fromLocalDateTimeToString(LocalDateTime localDateTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

        return localDateTime.format(formatter);
    }
}

package com.tedi.utils;

import com.tedi.dto.RentalSpaceDateRangeType;
import com.tedi.model.RentalSpaceDateRange;

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

    public static boolean areDatesEqual(String localDateTimeStr, LocalDateTime localDateTime) {
        return fromLocalDateTimeToString(localDateTime).equals(localDateTimeStr);
    }

    public static boolean areDateRangesEqual(
            RentalSpaceDateRangeType rentalSpaceDateRangeType,
            RentalSpaceDateRange rentalSpaceDateRange
    ) {
        return areDatesEqual(rentalSpaceDateRangeType.getStartDate(), rentalSpaceDateRange.getStartDate()) &&
                areDatesEqual(rentalSpaceDateRangeType.getEndDate(), rentalSpaceDateRange.getEndDate());
    }
}

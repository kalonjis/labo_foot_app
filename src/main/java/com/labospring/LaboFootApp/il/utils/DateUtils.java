package com.labospring.LaboFootApp.il.utils;

import java.time.LocalDateTime;

public class DateUtils {

    /**
     * Checks if a date is between two other dates.
     *
     * @param date The date to check
     * @param startDate The start date (inclusive)
     * @param endDate The end date (inclusive)
     * @return true if the date is between startDate and endDate, otherwise false
     */
    public static boolean isDateBetween(LocalDateTime date, LocalDateTime startDate, LocalDateTime endDate) {
        if (date == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Date can not be null");
        }

        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}

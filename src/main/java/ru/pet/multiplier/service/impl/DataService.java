package ru.pet.multiplier.service.impl;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataService {

    private static final String STANDARD_DATE_FORMAT = "dd.MM.yyyy";

    private static final DateTimeFormatter STANDARD_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(STANDARD_DATE_FORMAT);

    public static Timestamp parseTimestamp(String dateString, boolean isDateFrom) {
        if (StringUtils.hasLength(dateString)) {
            if (isDateFrom) {
                LocalDateTime ldt = LocalDate.parse(dateString, STANDARD_DATE_TIME_FORMATTER).atStartOfDay();
                return Timestamp.valueOf(ldt);
            } else {
                LocalDateTime ldt = LocalDate.parse(dateString, STANDARD_DATE_TIME_FORMATTER).atTime(23, 59, 59);
                return Timestamp.valueOf(ldt);
            }
        }

        if (isDateFrom) {
            return Timestamp.from(Instant.ofEpochMilli(1546300800));
        }
        return Timestamp.valueOf(LocalDateTime.now());
    }
}

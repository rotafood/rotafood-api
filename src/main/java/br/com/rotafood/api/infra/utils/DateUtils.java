package br.com.rotafood.api.infra.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateUtils {
    private static final ZoneId ZONE_BRAZIL = ZoneId.of("America/Sao_Paulo");

    public static String formatToBrazilianTime(Instant instant) {
        if (instant == null) return null;
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZONE_BRAZIL)
                .format(instant);
    }

    public static Date convertInstantToDate(Instant instant) {
        return instant != null ? Date.from(instant) : null;
    }

    public static Instant convertDateToInstant(Date date) {
        return date != null ? date.toInstant() : null;
    }

    public static String formatDateToBrazilianTime(Date date) {
        if (date == null) return null;
        Instant instant = date.toInstant();
        return formatToBrazilianTime(instant);
    }

    public static Instant parseDateStringToInstant(String dateStr, boolean endOfDay) {
        if (dateStr == null || dateStr.isBlank()) return null;
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
        try {
            LocalDate localDate = LocalDate.parse(dateStr, formatter);
            return endOfDay
                    ? localDate.atTime(23, 59, 59).atZone(ZoneId.of("America/Sao_Paulo")).toInstant()
                    : localDate.atStartOfDay(ZoneId.of("America/Sao_Paulo")).toInstant();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use 'yyyy-MM-dd'.", e);
        }
    }
    
}

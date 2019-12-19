package net.bittx.h2.conf;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


/**
 * String to LocalDatetime converter.
 *
 * @see net.bittx.h2.controller.BindingController#getSpecificDateInfo(LocalDateTime)
 */
public class StringToLocalDateTimeConverter implements Converter<String,LocalDateTime> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public LocalDateTime convert(String source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        return LocalDateTime.parse(source, formatter);
    }
}

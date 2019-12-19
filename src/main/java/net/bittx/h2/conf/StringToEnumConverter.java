package net.bittx.h2.conf;

import org.springframework.core.convert.converter.Converter;

/**
 * Convert String to Enum object.
 */
public class StringToEnumConverter implements Converter<String,Object> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Object convert(String source) {
        return null;
    }
}

package hu.bgachip.voting.config;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonToken;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

import java.time.Instant;
import java.time.format.DateTimeParseException;

public class StrictInstantDeserializer extends ValueDeserializer<Instant> {

    @Override
    public Instant deserialize(
            JsonParser parser,
            DeserializationContext context
    ) throws JacksonException {

        if (parser.currentToken() != JsonToken.VALUE_STRING) {
            return (Instant) context.handleUnexpectedToken(
                    Instant.class,
                    parser
            );
        }

        String value = parser.getString();

        try {
            Instant instant = Instant.parse(value);

            if (instant.getNano() != 0) {
                throw new IllegalArgumentException(
                        "Az idopont csak másodperces pontosságú lehet."
                );
            }

            return instant;

        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Az idopont nem érvényes ISO 8601 időpont.",
                    exception
            );
        }
    }
}
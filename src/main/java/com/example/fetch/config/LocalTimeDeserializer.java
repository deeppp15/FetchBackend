package com.example.fetch.config;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String timeString = jsonParser.getText();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        LocalTime myTime= LocalTime.parse(timeString, formatter);
        System.out.println(myTime.toString());
        return myTime;
    }
}

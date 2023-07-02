package org.dsr.practice.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.dsr.practice.models.User;

import java.io.IOException;

public class UserJsonSerializer extends StdSerializer<User> {

    public UserJsonSerializer() {
        this(null);
    }

    public UserJsonSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(
            User user, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", user.getUserId());
        jgen.writeStringField("name", user.getName());
        jgen.writeStringField("surname", user.getSurname());
        jgen.writeStringField("imgLink", user.getImgLink());
        jgen.writeEndObject();
    }
}
package com.fiap.techmesa.infrastructure.persistence.entity;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ReserveEntitySerializer extends StdSerializer<ReserveEntity> {

    public ReserveEntitySerializer() {
        this(null);
    }

    public ReserveEntitySerializer(Class<ReserveEntity> t) {
        super(t);
    }

    @Override
    public void serialize(ReserveEntity reserve, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", reserve.getId());
        gen.writeNumberField("clientId", reserve.getClient().getId());
        gen.writeNumberField("restaurantId", reserve.getRestaurant().getId());
        gen.writeNumberField("tableRestaurants", reserve.getTableRestaurant().getId());
        gen.writeNumberField("numberPeople", reserve.getNumberPeople());
        gen.writeStringField("dateReserve", reserve.getDateReserve().toString());
        gen.writeStringField("dateCreated", reserve.getDateCreated().toString());
        gen.writeStringField("startReserve", reserve.getStartReserve().toString());
        gen.writeNumberField("toleranceMinutes", reserve.getToleranceMinutes());
        gen.writeStringField("timeLimit", reserve.getTimeLimit().toString());
        gen.writeStringField("statusReserve", reserve.getStatusReserve().toString());
        gen.writeEndObject();
    }
}
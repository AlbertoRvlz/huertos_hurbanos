package com.huertos.huertos_urbanos.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParcelRequestDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void normalizesTextBeforeValidation() {
        ParcelRequestDTO request = validRequest();
        request.setName("  Huerto   Central ");
        request.setOwnerOrLead("  Ana   López ");
        request.setSoilType(" ARCILLOSO ");

        assertTrue(validator.validate(request).isEmpty());
        assertEquals("Huerto Central", request.getName());
        assertEquals("Ana López", request.getOwnerOrLead());
        assertEquals("arcilloso", request.getSoilType());
    }

    @Test
    void reportsPersonalizedMessagesForInvalidCoordinatesAndSoilType() {
        ParcelRequestDTO request = validRequest();
        request.getLocation().setLatitude(91.0);
        request.getLocation().setLongitude(-181.0);
        request.setSoilType("volcánico");

        Set<String> messages = validator.validate(request)
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toSet());

        assertTrue(messages.contains("La latitud debe ser igual o menor a 90."));
        assertTrue(messages.contains("La longitud debe ser igual o mayor a -180."));
        assertTrue(messages.contains("El tipo de suelo debe ser 'arcilloso', 'limoso' o 'arenoso'."));
    }

    private ParcelRequestDTO validRequest() {
        ParcelRequestDTO request = new ParcelRequestDTO();
        request.setName("Huerto Central");
        request.setOwnerOrLead("Ana López");
        request.setSoilType("arcilloso");
        request.setLocation(new ParcelRequestDTO.LocationDTO(19.4, -99.1));
        return request;
    }
}

package com.huertos.huertos_urbanos.service.impl;

import com.huertos.huertos_urbanos.dto.ParcelRequestDTO;
import com.huertos.huertos_urbanos.exception.ResourceNotFoundException;
import com.huertos.huertos_urbanos.model.Parcel;
import com.huertos.huertos_urbanos.repository.ParcelRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ParcelServiceImplTest {

    @Test
    void sanitizesTextBeforeSavingParcel() {
        ParcelRepository repository = mock(ParcelRepository.class);
        when(repository.save(any(Parcel.class))).thenAnswer(invocation -> invocation.getArgument(0));
        ParcelServiceImpl service = new ParcelServiceImpl(repository);

        ParcelRequestDTO request = new ParcelRequestDTO();
        request.setName("  Huerto   Central ");
        request.setOwnerOrLead("  Ana   López ");
        request.setSoilType(" ARCILLOSO ");
        request.setLocation(new ParcelRequestDTO.LocationDTO(19.4, -99.1));

        service.createParcel(request);

        org.mockito.ArgumentCaptor<Parcel> parcelCaptor = org.mockito.ArgumentCaptor.forClass(Parcel.class);
        verify(repository).save(parcelCaptor.capture());
        assertEquals("Huerto Central", parcelCaptor.getValue().getName());
        assertEquals("Ana López", parcelCaptor.getValue().getOwnerOrLead());
        assertEquals("arcilloso", parcelCaptor.getValue().getSoilType());
    }

    @Test
    void throwsCustomExceptionWhenParcelDoesNotExist() {
        ParcelRepository repository = mock(ParcelRepository.class);
        when(repository.findById("missing-id")).thenReturn(Optional.empty());
        ParcelServiceImpl service = new ParcelServiceImpl(repository);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.getParcelById("missing-id"));

        assertEquals("No se encontró una parcela con el ID solicitado.", exception.getMessage());
    }
}

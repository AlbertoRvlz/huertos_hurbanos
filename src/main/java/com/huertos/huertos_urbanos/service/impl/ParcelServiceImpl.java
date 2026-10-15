package com.huertos.huertos_urbanos.service.impl;

import com.huertos.huertos_urbanos.dto.ParcelRequestDTO;
import com.huertos.huertos_urbanos.dto.ParcelResponseDTO;
import com.huertos.huertos_urbanos.exception.ResourceNotFoundException;
import com.huertos.huertos_urbanos.model.Parcel;
import com.huertos.huertos_urbanos.repository.ParcelRepository;
import com.huertos.huertos_urbanos.service.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;

    @Autowired
    public ParcelServiceImpl(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    @Override
    public ParcelResponseDTO createParcel(ParcelRequestDTO requestDTO) {
        String sanitizedName = normalizeText(requestDTO.getName());
        String sanitizedOwner = normalizeText(requestDTO.getOwnerOrLead());
        String sanitizedSoil = normalizeText(requestDTO.getSoilType()).toLowerCase(Locale.ROOT);

        Parcel.Location location = new Parcel.Location(
                requestDTO.getLocation().getLatitude(),
                requestDTO.getLocation().getLongitude()
        );

        Parcel parcel = new Parcel();
        parcel.setName(sanitizedName);
        parcel.setLocation(location);
        parcel.setSoilType(sanitizedSoil);
        parcel.setOwnerOrLead(sanitizedOwner);
        parcel.setCreatedAt(LocalDateTime.now());

        Parcel savedParcel = parcelRepository.save(parcel);
        return mapToResponseDTO(savedParcel);
    }

    @Override
    public List<ParcelResponseDTO> getAllParcels() {
        return parcelRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ParcelResponseDTO getParcelById(String id) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró una parcela con el ID solicitado."));
        return mapToResponseDTO(parcel);
    }

    private String normalizeText(String value) {
        return value.strip().replaceAll("\\s+", " ");
    }

    private ParcelResponseDTO mapToResponseDTO(Parcel parcel) {
        ParcelResponseDTO.LocationDTO locationDTO = null;
        if (parcel.getLocation() != null) {
            locationDTO = new ParcelResponseDTO.LocationDTO(
                    parcel.getLocation().getLatitude(),
                    parcel.getLocation().getLongitude()
            );
        }

        ParcelResponseDTO dto = new ParcelResponseDTO();
        dto.setId(parcel.getId());
        dto.setName(parcel.getName());
        dto.setLocation(locationDTO);
        dto.setSoilType(parcel.getSoilType());
        dto.setOwnerOrLead(parcel.getOwnerOrLead());
        dto.setCreatedAt(parcel.getCreatedAt());

        return dto;
    }
}
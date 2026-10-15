package com.huertos.huertos_urbanos.service;

import com.huertos.huertos_urbanos.dto.ParcelRequestDTO;
import com.huertos.huertos_urbanos.dto.ParcelResponseDTO;

import java.util.List;

public interface ParcelService {
    ParcelResponseDTO createParcel(ParcelRequestDTO requestDTO);
    List<ParcelResponseDTO> getAllParcels();
    ParcelResponseDTO getParcelById(String id);
}
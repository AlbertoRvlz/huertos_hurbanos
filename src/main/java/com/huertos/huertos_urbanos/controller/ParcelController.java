package com.huertos.huertos_urbanos.controller;

import com.huertos.huertos_urbanos.dto.ParcelRequestDTO;
import com.huertos.huertos_urbanos.dto.ParcelResponseDTO;
import com.huertos.huertos_urbanos.service.ParcelService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/parcels")
public class ParcelController {

    private final ParcelService parcelService;

    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    // POST /api/v1/parcels - Registrar nueva parcela
    @PostMapping
    public ResponseEntity<ParcelResponseDTO> createParcel(@Valid @RequestBody ParcelRequestDTO requestDTO) {
        ParcelResponseDTO response = parcelService.createParcel(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /api/v1/parcels - Listar todas las parcelas
    @GetMapping
    public ResponseEntity<List<ParcelResponseDTO>> getAllParcels() {
        List<ParcelResponseDTO> parcels = parcelService.getAllParcels();
        return ResponseEntity.ok(parcels);
    }

    // GET /api/v1/parcels/{id} - Consultar detalle por ID
    @GetMapping("/{id}")
    public ResponseEntity<ParcelResponseDTO> getParcelById(
            @PathVariable @NotBlank(message = "El ID de la parcela es obligatorio.") String id) {
        ParcelResponseDTO parcel = parcelService.getParcelById(id);
        return ResponseEntity.ok(parcel);
    }
}
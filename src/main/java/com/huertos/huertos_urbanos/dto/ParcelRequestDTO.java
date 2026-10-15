package com.huertos.huertos_urbanos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcelRequestDTO {

    @NotBlank(message = "El nombre de la parcela no puede estar vacío.")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    private String name;

    @NotNull(message = "La ubicación GPS es obligatoria.")
    @Valid
    private LocationDTO location;

    @NotBlank(message = "El tipo de suelo es obligatorio.")
    @Pattern(regexp = "(?i)^(arcilloso|limoso|arenoso)$", message = "El tipo de suelo debe ser 'arcilloso', 'limoso' o 'arenoso'.")
    private String soilType;

    @NotBlank(message = "El nombre del responsable o líder es obligatorio.")
    @Size(min = 3, max = 100, message = "El nombre del responsable debe tener entre 3 y 100 caracteres.")
    private String ownerOrLead;

    public void setName(String name) {
        this.name = normalizeText(name);
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType == null ? null : normalizeText(soilType).toLowerCase(Locale.ROOT);
    }

    public void setOwnerOrLead(String ownerOrLead) {
        this.ownerOrLead = normalizeText(ownerOrLead);
    }

    private static String normalizeText(String value) {
        return value == null ? null : value.strip().replaceAll("\\s+", " ");
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LocationDTO {

        @NotNull(message = "La latitud es obligatoria.")
        @DecimalMin(value = "-90.0", message = "La latitud debe ser igual o mayor a -90.")
        @DecimalMax(value = "90.0", message = "La latitud debe ser igual o menor a 90.")
        private Double latitude;

        @NotNull(message = "La longitud es obligatoria.")
        @DecimalMin(value = "-180.0", message = "La longitud debe ser igual o mayor a -180.")
        @DecimalMax(value = "180.0", message = "La longitud debe ser igual o menor a 180.")
        private Double longitude;
    }
}
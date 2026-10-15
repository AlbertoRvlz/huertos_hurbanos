package com.huertos.huertos_urbanos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcelResponseDTO {

    private String id;
    private String name;
    private LocationDTO location;
    private String soilType;
    private String ownerOrLead;
    private LocalDateTime createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LocationDTO {
        private Double latitude;
        private Double longitude;
    }
}
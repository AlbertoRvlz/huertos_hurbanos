package com.huertos.huertos_urbanos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "parcels")
public class Parcel {

    @Id
    private String id;

    private String name;

    private Location location;

    private String soilType; // arcilloso, limoso, arenoso

    private String ownerOrLead;

    private LocalDateTime createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Location {
        private Double latitude;
        private Double longitude;
    }
}
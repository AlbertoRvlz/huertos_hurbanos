package com.huertos.huertos_urbanos.repository;

import com.huertos.huertos_urbanos.model.Parcel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelRepository extends MongoRepository<Parcel, String> {
}
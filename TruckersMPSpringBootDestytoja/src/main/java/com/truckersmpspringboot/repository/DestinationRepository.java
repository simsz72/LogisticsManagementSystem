package com.truckersmpspringboot.repository;

import com.truckersmpspringboot.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination,Integer> {
    Destination findDestinationByPlaceAddress(String placeAddress);
}

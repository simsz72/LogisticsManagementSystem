package com.truckersmpspringboot.repository;

import com.truckersmpspringboot.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<Cargo,Integer> {
    Cargo findCargoByCargoName(String cargoName);
}

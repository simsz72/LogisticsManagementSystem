package com.truckersmpspringboot.repository;

import com.truckersmpspringboot.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Integer> {
    Car findCarByPlateNumber(String plateNumber);
}

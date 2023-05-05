package com.truckersmpspringboot.repository;

import com.truckersmpspringboot.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Driver findDriverByLoginAndPassword(String login, String password);
}

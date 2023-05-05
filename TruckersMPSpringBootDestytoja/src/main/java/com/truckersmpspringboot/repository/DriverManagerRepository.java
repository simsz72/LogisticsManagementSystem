package com.truckersmpspringboot.repository;

import com.truckersmpspringboot.model.Driver;
import com.truckersmpspringboot.model.DriverManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverManagerRepository extends JpaRepository<DriverManager,Integer> {
    DriverManager findDriverManagerByLoginAndPassword(String login, String password);
}

package com.truckersmpspringboot.error;

public class DriverManagerNotFound extends RuntimeException{

    public DriverManagerNotFound(Integer id) {
        super("Unable to find Driver" + id);
    }
}
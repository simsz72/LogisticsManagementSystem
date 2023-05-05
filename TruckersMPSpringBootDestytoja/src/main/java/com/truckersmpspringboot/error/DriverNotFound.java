package com.truckersmpspringboot.error;

public class DriverNotFound extends RuntimeException{
    public DriverNotFound(Integer id) {
        super("Unable to find Driver" + id);
    }
}


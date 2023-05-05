package com.truckersmpspringboot.error;

public class CarNotFound extends RuntimeException{
    public CarNotFound(Integer id) {
        super("Unable to find Car" + id);
    }
}


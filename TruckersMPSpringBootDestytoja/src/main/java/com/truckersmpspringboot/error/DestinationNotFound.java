package com.truckersmpspringboot.error;

public class DestinationNotFound extends RuntimeException{
    public DestinationNotFound(Integer id) {
        super("Unable to find Destination" + id);
    }
}

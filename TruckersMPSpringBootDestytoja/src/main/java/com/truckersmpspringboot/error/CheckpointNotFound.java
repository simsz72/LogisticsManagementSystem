package com.truckersmpspringboot.error;

public class CheckpointNotFound extends RuntimeException{
    public CheckpointNotFound(Integer id) {
        super("Unable to find Checkpoint" + id);
    }
}

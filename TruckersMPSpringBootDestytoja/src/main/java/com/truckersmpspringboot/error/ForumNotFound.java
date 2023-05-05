package com.truckersmpspringboot.error;

public class ForumNotFound extends RuntimeException{
    public ForumNotFound(Integer id) {
        super("Unable to find Forum" + id);
    }
}

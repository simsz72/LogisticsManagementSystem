package com.truckersmpspringboot.error;

public class CommentNotFound extends RuntimeException{
    public CommentNotFound(Integer id) {
        super("Unable to find Comment" + id);
    }
}

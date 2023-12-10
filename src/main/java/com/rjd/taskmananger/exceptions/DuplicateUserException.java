package com.rjd.taskmananger.exceptions;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(String message, Throwable e){
        super(message, e);
    }
}

package com.project.task_manager.services.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{

    public EmailAlreadyExistsException(String msg){
        super(msg);
    }
}

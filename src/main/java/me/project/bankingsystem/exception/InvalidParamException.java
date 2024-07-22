package me.project.bankingsystem.exception;

public class InvalidParamException extends RuntimeException{
    public InvalidParamException(String message) {
        super(message);
    }
}

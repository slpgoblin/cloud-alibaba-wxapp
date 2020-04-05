package com.goblin.contentcenter.auth;

public class SecurityException extends RuntimeException{
    public SecurityException(String message) {
        super(message);
    }
    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
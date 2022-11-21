package com.modules.system.exception;

/**
 * @author chenlingl
 */
public class TokenErrorException extends RuntimeException{

    public TokenErrorException(String message) {
        super(message);
    }

    public TokenErrorException() {
    }
}

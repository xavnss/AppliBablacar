package com.poe.trajetfacile.exception;

@SuppressWarnings("serial")
public class RideIsFullBusinessException extends Exception {

    public RideIsFullBusinessException(String message) {
        super(message);
    }
}

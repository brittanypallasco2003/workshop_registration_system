package com.app.workshop_registration_system.Advice;

public class PlacesSoldOutException extends RuntimeException {
    public PlacesSoldOutException(String message){
        super(message);
    }

}

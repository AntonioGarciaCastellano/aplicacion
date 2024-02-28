package com.example.aplicacion.services;

public class PeliculaNotFoundException extends RuntimeException {
    public PeliculaNotFoundException(String message) {
        super(message);
    }
}

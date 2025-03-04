package com.example.franquicias.franquicias_prueba.infrastructure.in.execptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}

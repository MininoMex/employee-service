package com.example.employeeservice.exception;

/**
 * Excepción personalizada para errores de negocio o entrada inválida.
 *
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
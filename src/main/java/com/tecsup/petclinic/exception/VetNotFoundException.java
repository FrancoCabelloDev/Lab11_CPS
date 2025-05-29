package com.tecsup.petclinic.exception;

public class VetNotFoundException extends RuntimeException {

    public VetNotFoundException(Long id) {
        super("Veterinario no encontrado con id: " + id);
    }

    public VetNotFoundException(String message) {
        super(message);
    }
}

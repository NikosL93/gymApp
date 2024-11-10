package com.gymApp.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    String resourceNAme;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String field, String fieldName, String resourceNAme) {
        super(resourceNAme + " not found " + "with " + field + ": " + fieldName);
        this.field = field;
        this.fieldName = fieldName;
        this.resourceNAme = resourceNAme;
    }

    public ResourceNotFoundException(String field, String resourceNAme, Long fieldId) {
        super(resourceNAme + " not found " + "with " + field + ": " + fieldId);
        this.field = field;
        this.fieldId = fieldId;
        this.resourceNAme = resourceNAme;
    }
}

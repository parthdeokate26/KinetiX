package com.kinetix.webhookservice.exception;

public class WorkflowDisabledException extends RuntimeException {

    public WorkflowDisabledException(String message) {
        super(message);
    }

}
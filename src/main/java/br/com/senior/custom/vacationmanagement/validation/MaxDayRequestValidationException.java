package br.com.senior.custom.vacationmanagement.validation;

class MaxDayRequestValidationException extends RuntimeException {

    MaxDayRequestValidationException(String message) {
        super(message);
    }

    MaxDayRequestValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}

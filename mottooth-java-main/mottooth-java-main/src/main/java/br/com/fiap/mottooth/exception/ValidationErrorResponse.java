package br.com.fiap.mottooth.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> validationErrors;

    // Construtor padrão
    public ValidationErrorResponse() {
        super();
    }

    // Construtor com todos os parâmetros
    public ValidationErrorResponse(int status, String error, String message, LocalDateTime timestamp, Map<String, String> validationErrors) {
        super(status, error, message, timestamp);
        this.validationErrors = validationErrors;
    }

    // Getters e Setters
    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    // Métodos equals, hashCode e toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ValidationErrorResponse that = (ValidationErrorResponse) o;
        return Objects.equals(validationErrors, that.validationErrors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), validationErrors);
    }

    @Override
    public String toString() {
        return "ValidationErrorResponse{" +
                "status=" + getStatus() +
                ", error='" + getError() + '\'' +
                ", message='" + getMessage() + '\'' +
                ", timestamp=" + getTimestamp() +
                ", validationErrors=" + validationErrors +
                '}';
    }
}

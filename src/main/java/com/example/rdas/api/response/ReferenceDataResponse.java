package com.example.rdas.api.response;

public record ReferenceDataResponse<T>(
        String service,
        String status,
        String code,
        String message,
        T data) {

    public static <T> ReferenceDataResponse<T> success(String service, T data) {
        return new ReferenceDataResponse<>(service, "SUCCESS", "200", "Request processed successfully", data);
    }

    public static ReferenceDataResponse<Void> error(String service, String code, String message) {
        return new ReferenceDataResponse<>(service, "ERROR", code, message, null);
    }
}

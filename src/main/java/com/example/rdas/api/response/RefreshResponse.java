package com.example.rdas.api.response;

import java.time.Instant;

public record RefreshResponse(String refreshType, String refreshStatus, Instant refreshedAt) {
}

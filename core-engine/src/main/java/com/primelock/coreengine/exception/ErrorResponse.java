package com.primelock.coreengine.exception;

import java.time.OffsetDateTime;

public record ErrorResponse(int status, String error, String message, OffsetDateTime timestamp) {
}

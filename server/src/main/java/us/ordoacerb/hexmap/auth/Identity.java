package us.ordoacerb.hexmap.auth;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import java.util.Map;

/**
 * Represents an external user.
 */
public record Identity(
        @NonNull String provider,
        @NonNull User user,
        @NonNull String externalId,
        @Nullable String email,
        @NonNull Map<String, Object> attributes) {}

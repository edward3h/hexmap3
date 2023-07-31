package us.ordoacerb.hexmap.auth;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import java.io.Serializable;
import java.util.Set;

/**
 * Internal view of a user
 * @param id
 * @param displayName
 * @param avatarUrl
 * @param flags
 */
public record User(
        @NonNull String id, @Nullable String displayName, @Nullable String avatarUrl, @NonNull Set<String> flags)
        implements Serializable {}

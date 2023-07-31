package us.ordoacerb.hexmap.persistence;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import java.sql.Timestamp;
import java.util.Set;
import us.ordoacerb.hexmap.persistence.id.GeneratedId;

public record UserEntity(
        @GeneratedId @Id String id,
        @Nullable String displayName,
        @Nullable String pictureUrl,
        @TypeDef(type = DataType.STRING_ARRAY) Set<String> flags,
        @DateCreated @Nullable Timestamp createdAt,
        @DateUpdated @Nullable Timestamp updatedAt) {
    public UserEntity(String id, String displayName, String pictureUrl, String... flags) {
        this(id, displayName, pictureUrl, Set.of(flags), null, null);
    }
}

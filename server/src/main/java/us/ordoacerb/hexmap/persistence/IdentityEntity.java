package us.ordoacerb.hexmap.persistence;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import java.sql.Timestamp;
import java.util.Map;

@MappedEntity
public record IdentityEntity(
        @Id String id,
        @NonNull String provider,
        @Relation(Relation.Kind.MANY_TO_ONE) UserEntity user,
        @NonNull String externalId,
        @Nullable String email,
        @TypeDef(type = DataType.JSON) Map<String, Object> attributes,
        @DateCreated @Nullable Timestamp createdAt,
        @DateUpdated @Nullable Timestamp updatedAt) {
    public IdentityEntity(
            String provider, UserEntity user, String externalId, String email, Map<String, Object> attributes) {
        this(provider + "#" + externalId, provider, user, externalId, email, attributes, null, null);
    }
}

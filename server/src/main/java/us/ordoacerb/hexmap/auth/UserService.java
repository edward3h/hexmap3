package us.ordoacerb.hexmap.auth;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationResponse;
import java.util.Optional;

public interface UserService {
    AuthenticationResponse identityToInternalUser(AuthenticationResponse providerResponse);

    User userFromAuthentication(@Nullable Authentication auth);

    Optional<User> findById(String id);
}

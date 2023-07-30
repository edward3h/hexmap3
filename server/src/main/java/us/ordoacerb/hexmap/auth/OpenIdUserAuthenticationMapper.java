package us.ordoacerb.hexmap.auth;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.config.AuthenticationModeConfiguration;
import io.micronaut.security.oauth2.configuration.OpenIdAdditionalClaimsConfiguration;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.DefaultOpenIdAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdClaims;
import io.micronaut.security.oauth2.endpoint.token.response.OpenIdTokenResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

@Singleton
@Replaces(DefaultOpenIdAuthenticationMapper.class)
public class OpenIdUserAuthenticationMapper extends DefaultOpenIdAuthenticationMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenIdUserAuthenticationMapper.class);
    private final UserService userService;

    public OpenIdUserAuthenticationMapper(
            OpenIdAdditionalClaimsConfiguration openIdAdditionalClaimsConfiguration,
            AuthenticationModeConfiguration authenticationModeConfiguration,
            UserService userService) {
        super(openIdAdditionalClaimsConfiguration, authenticationModeConfiguration);
        this.userService = userService;
    }

    @Override
    public @NonNull Publisher<AuthenticationResponse> createAuthenticationResponse(
            String providerName, OpenIdTokenResponse tokenResponse, OpenIdClaims openIdClaims, State state) {
        var publisher = Flux.from(super.createAuthenticationResponse(providerName, tokenResponse, openIdClaims, state));

        return publisher.map(userService::identityToInternalUser);
    }
}

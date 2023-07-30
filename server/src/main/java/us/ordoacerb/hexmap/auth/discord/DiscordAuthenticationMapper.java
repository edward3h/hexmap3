package us.ordoacerb.hexmap.auth.discord;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.oauth2.endpoint.authorization.state.State;
import io.micronaut.security.oauth2.endpoint.token.response.OauthAuthenticationMapper;
import io.micronaut.security.oauth2.endpoint.token.response.TokenResponse;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import us.ordoacerb.hexmap.auth.UserService;

import java.util.Map;
import java.util.stream.Collectors;

@Named("discord")
@Singleton
public class DiscordAuthenticationMapper implements OauthAuthenticationMapper {

    private final DiscordApiClient apiClient;
    private final UserService userService;

    public DiscordAuthenticationMapper(DiscordApiClient apiClient, UserService userService) {
        this.apiClient = apiClient;
        this.userService = userService;
    }

    @Override
    public Publisher<AuthenticationResponse> createAuthenticationResponse(
            TokenResponse tokenResponse, @Nullable State state) {
        var accessToken = tokenResponse.getAccessToken();
        var auth = DiscordApiClient.authorization(accessToken);
        var userPub = Mono.from(apiClient.getUser(auth));
        var guildsPub = Flux.from(apiClient.getUserGuilds(auth)).collectList();
        return Mono.zip(
                userPub,
                guildsPub,
                (user, guilds) -> userService.identityToInternalUser(AuthenticationResponse.success(
                        user.id(),
                        Map.of(
                                OauthAuthenticationMapper.PROVIDER_KEY,
                                "discord",
                                "user",
                                user,
                                "guilds",
                                guilds.stream().map(DiscordGuild::name).collect(Collectors.toList()),
                                "email",
                                user.email(),
                                "name",
                                user.username(),
                                "picture",
                                getAvatarUrl(user)))));
    }

    // https://discord.com/developers/docs/reference#image-formatting
    private String getAvatarUrl(DiscordUser user) {
        if (user.avatar() == null) {
            int discriminator;
            try {
                discriminator = Integer.parseInt(user.discriminator());
            } catch (NumberFormatException e) {
                discriminator = 0;
            }
            return "https://cdn.discordapp.com/embed/avatars/%d.png".formatted(discriminator % 5);
        }
        return "https://cdn.discordapp.com/avatars/%s/%s.png?size=128".formatted(user.id(), user.avatar());
    }
}


package us.ordoacerb.hexmap.auth;

import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.bind.binders.TypedRequestArgumentBinder;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.filters.SecurityFilter;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
public class UserRequestArgumentBinder implements TypedRequestArgumentBinder<User> {
    private static final Argument<User> TYPE = Argument.of(User.class);

    private final UserService userService;

    public UserRequestArgumentBinder(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Argument<User> argumentType() {
        return TYPE;
    }

    @Override
    public BindingResult<User> bind(ArgumentConversionContext<User> context, HttpRequest<?> source) {
        if (!source.getAttributes().contains(SecurityFilter.KEY)) {
            return BindingResult.unsatisfied();
        }

        final Optional<Authentication> existing = source.getUserPrincipal(Authentication.class);
        if (existing.isEmpty()) {
            return BindingResult.empty();
        } else {
            return () -> existing.map(userService::userFromAuthentication);
        }
    }
}

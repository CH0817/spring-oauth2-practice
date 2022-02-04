package tw.com.rex.oauth2.config;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String casToken = (String) authentication.getCredentials();
        UserDetails userDetails = userDetailsService.loadUserDetailsByCasToken(casToken);
        return new CustomAuthenticationToken(userDetails, casToken, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomAuthenticationToken.class.isAssignableFrom(aClass);
    }

}

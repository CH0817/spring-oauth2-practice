package tw.com.rex.oauth2.config;

import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CustomAuthenticationSecurityConfig
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security.authenticationProvider(customAuthenticationProvider);
    }

}

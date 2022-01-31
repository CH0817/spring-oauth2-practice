package tw.com.rex.oauth2.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author Rex Yu
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @NonNull
    private final ValidateCodeFilter validateCodeFilter;

    /**
     * 加密工具
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .headers().frameOptions().sameOrigin()
            .and()
            .authorizeRequests().antMatchers("/code/email", "/auth/email").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }

    /**
     * in memory user
     *
     * @return UserDetailsService
     */
    // 註冊為 bean 否則不能刷新 token
    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(getDefaultUser());
        return manager;
    }

    /**
     * create user
     *
     * @return UserDetails
     */
    private UserDetails getDefaultUser() {
        return User.withUsername("rex")
                   .password(passwordEncoder().encode("1"))
                   .authorities("ROLE_USER")
                   .build();
    }

    // 註冊為 bean 否則密碼模式無法使用
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}

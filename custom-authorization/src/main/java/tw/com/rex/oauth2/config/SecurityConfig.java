package tw.com.rex.oauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Rex Yu
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
            .authorizeRequests().antMatchers("/actuator/**").permitAll()
            .anyRequest().authenticated()
            .and();
    }

    /**
     * in memory user
     *
     * @return UserDetailsService
     */
    // 註冊為 bean 否則不能刷新 token
    // @Bean
    // @Override
    // public UserDetailsService userDetailsServiceBean() {
    //     InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    //     manager.createUser(getDefaultUser());
    //     return manager;
    // }
    //
    // /**
    //  * create user
    //  *
    //  * @return UserDetails
    //  */
    // private UserDetails getDefaultUser() {
    //     return User.withUsername("rex")
    //                .password(passwordEncoder().encode("1"))
    //                .authorities("ROLE_USER")
    //                .build();
    // }

    // 註冊為 bean 否則密碼模式無法使用
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

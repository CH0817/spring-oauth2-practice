package tw.com.rex.oauth2.config;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {

    UserDetails loadUserDetailsByCasToken(String jwtToken);

}

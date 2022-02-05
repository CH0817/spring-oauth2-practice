package tw.com.rex.oauth2.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService {

    UserDetails loadUserBySsoToken(String ssoToken) throws UsernameNotFoundException;

}

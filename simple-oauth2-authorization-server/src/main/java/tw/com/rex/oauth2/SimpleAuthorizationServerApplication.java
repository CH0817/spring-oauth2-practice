package tw.com.rex.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author Rex Yu
 */
@SpringBootApplication
@EnableAuthorizationServer
public class SimpleAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleAuthorizationServerApplication.class, args);
    }

}

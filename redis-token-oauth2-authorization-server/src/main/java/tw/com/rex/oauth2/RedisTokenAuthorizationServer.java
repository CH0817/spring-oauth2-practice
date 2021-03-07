package tw.com.rex.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Rex Yu
 */
@SpringBootApplication
public class RedisTokenAuthorizationServer {

    public static void main(String[] args) {
        SpringApplication.run(RedisTokenAuthorizationServer.class, args);
    }

}

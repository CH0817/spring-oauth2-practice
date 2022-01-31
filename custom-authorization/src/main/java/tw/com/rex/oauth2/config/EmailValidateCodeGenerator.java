package tw.com.rex.oauth2.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class EmailValidateCodeGenerator implements ValidateCodeGenerator {

    @Override
    public String generate(ServletWebRequest request) {
        return RandomCode.random(6);
    }

}

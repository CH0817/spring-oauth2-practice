package tw.com.rex.oauth2.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

@Component
public class EmailValidateCodeProcessor extends AbstractValidateCodeProcessor {

    @Override
    protected void send(ServletWebRequest request, String validateCode) {
        System.out.println(request.getParameter("email") +
                                   "邮箱验证码发送成功，验证码为：" + validateCode);
    }

}

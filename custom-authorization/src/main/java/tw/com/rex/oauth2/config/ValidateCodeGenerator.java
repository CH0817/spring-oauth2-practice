package tw.com.rex.oauth2.config;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {

    /**
     * 生成验证码
     *
     * @param request 请求
     * @return 生成结果
     */
    String generate(ServletWebRequest request);

}

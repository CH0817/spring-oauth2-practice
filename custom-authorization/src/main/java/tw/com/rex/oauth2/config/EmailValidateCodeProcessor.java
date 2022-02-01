package tw.com.rex.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class EmailValidateCodeProcessor implements ValidateCodeProcessor {

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void create(ServletWebRequest request) {
        String validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    @Override
    public void validate(ServletWebRequest request) {
        String type = getValidateCodeType(request);
        String code = getValidateCode(request, type);
        // 验证码是否存在
        if (Objects.isNull(code)) {
            throw new ValidateCodeException("获取验证码失败，请检查输入是否正确或重新发送！");
        }
        // 验证码输入是否正确
        if (!code.equalsIgnoreCase(request.getParameter("code"))) {
            throw new ValidateCodeException("验证码不正确，请重新输入！");
        }
        // 验证通过后，清除验证码
        removeValidateCode(request, type);
    }

    protected void send(ServletWebRequest request, String validateCode) {
        System.out.println(request.getParameter("email") +
                                   "邮箱验证码发送成功，验证码为：" + validateCode);
    }

    /**
     * 保存验证码，保存到 redis 中
     *
     * @param request      请求
     * @param validateCode 验证码
     */
    private void save(ServletWebRequest request, String validateCode) {
        saveValidateCode(request, validateCode, getValidateCodeType(request));
    }

    /**
     * 生成验证码
     *
     * @param request 请求
     * @return 验证码
     */
    private String generate(ServletWebRequest request) {
        String type = getValidateCodeType(request);
        String componentName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator generator = validateCodeGenerators.get(componentName);
        if (Objects.isNull(generator)) {
            throw new ValidateCodeException("验证码生成器 " + componentName + " 不存在。");
        }
        return generator.generate(request);
    }

    /**
     * 根据请求 url 获取验证码类型
     *
     * @return 结果
     */
    private String getValidateCodeType(ServletWebRequest request) {
        String uri = request.getRequest().getRequestURI();
        int index = uri.lastIndexOf("/") + 1;
        return uri.substring(index).toLowerCase();
    }

    private String getValidateCode(ServletWebRequest request, String type) {
        return redisTemplate.opsForValue().get(buildKey(request, type));
    }

    private void removeValidateCode(ServletWebRequest request, String type) {
        redisTemplate.delete(buildKey(request, type));
    }

    private void saveValidateCode(ServletWebRequest request, String code, String type) {
        redisTemplate.opsForValue().set(buildKey(request, type), code,
                                        //  有效期可以从配置文件中读取或者请求中读取
                                        Duration.ofMinutes(10).getSeconds(), TimeUnit.SECONDS);
    }

    private String buildKey(ServletWebRequest request, String type) {
        String deviceId = request.getParameter(type);
        if (StringUtils.isEmpty(deviceId)) {
            throw new ValidateCodeException("请求中不存在 " + type);
        }
        return "code:" + type + ":" + deviceId;
    }

}

package tw.com.rex.oauth2.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class ValidateCodeProcessorHolder {

    @NonNull
    private final Map<String, ValidateCodeProcessor> validateCodeProcessors;

    /**
     * 通过验证码类型查找
     *
     * @param type 验证码类型
     * @return 验证码处理器
     */
    public ValidateCodeProcessor findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = validateCodeProcessors.get(name);
        if (Objects.isNull(processor)) {
            throw new ValidateCodeException("验证码处理器" + name + "不存在");
        }
        return processor;
    }

}

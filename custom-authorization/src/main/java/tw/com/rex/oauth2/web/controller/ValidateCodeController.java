package tw.com.rex.oauth2.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import tw.com.rex.oauth2.config.ValidateCodeProcessorHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class ValidateCodeController {

    private final ValidateCodeProcessorHolder validateCodeProcessorHolder;


    /**
     * 通过 type 进行查询到对应的处理器
     * 同时创建验证码
     *
     * @param request  请求
     * @param response 响应
     * @param type     验证码类型
     * @throws Exception 异常
     */
    @GetMapping("/code/{type}")
    public void creatCode(HttpServletRequest request, HttpServletResponse response,
                          @PathVariable String type) throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type)
                                   .create(new ServletWebRequest(request, response));
    }


}

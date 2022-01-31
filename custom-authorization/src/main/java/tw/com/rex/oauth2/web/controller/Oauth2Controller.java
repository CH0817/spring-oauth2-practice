package tw.com.rex.oauth2.web.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class Oauth2Controller {

    @PostMapping("/email")
    public HttpEntity<String> email() {
        return ResponseEntity.ok("ok");
    }

}

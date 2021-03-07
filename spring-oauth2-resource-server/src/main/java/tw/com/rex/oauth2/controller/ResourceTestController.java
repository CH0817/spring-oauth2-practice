package tw.com.rex.oauth2.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Rex Yu
 */
@RestController
@RequestMapping("/resource")
public class ResourceTestController {

    @RequestMapping("/test")
    public HttpEntity<?> test(Principal principal) {
        return ResponseEntity.ok(principal);
    }

}

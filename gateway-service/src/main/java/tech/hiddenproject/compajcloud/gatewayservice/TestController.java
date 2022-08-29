package tech.hiddenproject.compajcloud.gatewayservice;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/")
  public String index() {
    return "Index page";
  }

  @GetMapping("/hello")
  public String hello(Principal principal) {
    return principal.getName();
  }
}

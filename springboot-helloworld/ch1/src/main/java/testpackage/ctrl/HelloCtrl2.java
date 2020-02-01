package testpackage.ctrl;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * 这个类没有在App所在的包内部 所以 不会被注册  你也无法访问这个/hello2
 *
 *
 *
 */
@Controller
@EnableAutoConfiguration
public class HelloCtrl2 {

    @ResponseBody
    @RequestMapping("/hello2")
    String home() {
        return "hello world from ctrl 2 , now is - " + new Date();
    }
}


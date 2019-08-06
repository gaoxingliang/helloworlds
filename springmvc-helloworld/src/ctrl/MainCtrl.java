package ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainCtrl {

    @RequestMapping("/")
    public ModelAndView main() {
        return new ModelAndView("main");
    }
}

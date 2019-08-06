package ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * 使用注解方式的控制器
 * 最终路径: localhost:port/helloWithAnnot/hello
 */
@org.springframework.stereotype.Controller
@RequestMapping("/helloWithAnnot")
public class HelloCtrlWithAnnotation implements Controller {


    @RequestMapping("/hello")
    @Override
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest,
                                      javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {

        // 在一个对象上同时返回模型和视图
        // 如果是这样会访问/helloWithAnnot/index.jsp
        ModelAndView modelAndView = new ModelAndView("index.jsp");
        modelAndView.addObject("message", "Hello spring mvc");
        return modelAndView;
    }
}

package ctrl;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class HelloCtrl implements Controller {
    @Override
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest,
                                      javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {

        // 在一个对象上同时返回模型和视图
        // 当我们配置了视图解析器后, 解析器会自动添加前缀和后缀.
        // 这里就不需要了
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("message", "Hello spring mvc");
        return modelAndView;
    }

}

package ctrl;

import model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@org.springframework.stereotype.Controller
public class ParamCtrl {

    @RequestMapping("/test")
    public ModelAndView testPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("test");
    }

    /**
     * 传递参数: 自己从Req里面获取
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/param1", method = RequestMethod.POST)
    public ModelAndView postParam(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        return print(username, password);
    }

    /**
     * 传递参数2: 直接使用同名参数
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/param2", method = RequestMethod.POST)
    public ModelAndView postParam2(String username, String password) throws Exception {
        return print(username, password);
    }

    /**
     * 传递参数3: 使用@RequestParam来指定UI发过来的名字
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/param3", method = RequestMethod.POST)
    public ModelAndView postParam3(@RequestParam("YourUsername") String username,
                                   @RequestParam("password") String password) throws Exception {
        return print(username, password);
    }

    /**
     * 传递参数4: 使用模型传参
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/param4", method = RequestMethod.POST)
    public ModelAndView postParam4(User user) throws Exception {
        return print(user.getUsername(), user.getPassword());
    }

    /**
     * 客户端跳转
     * @return
     */
    @RequestMapping("/jump")
    public ModelAndView jump() {
        ModelAndView m = new ModelAndView("redirect:/test");
        return m;
    }

    private ModelAndView print(String username, String password) {
        System.out.println("username is " + username);
        System.out.println("password is " + password);
        String message =  String.format("user=%s,password=%s", username, password);
        ModelAndView modelAndView = new ModelAndView("test2");
        modelAndView.addObject("message", message);
        return modelAndView;
    }

}

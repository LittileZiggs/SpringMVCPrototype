package xluo.github.spring.springcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xluo.github.spring.springcrud.model.ImUser;
import xluo.github.spring.springcrud.service.im.ImService;
import xluo.github.spring.springcrud.util.ServletUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaoluo on 17-7-25.
 */
@Controller
@RequestMapping("/im")
public class ImController {

    @Autowired
    private ImService imService;

    @RequestMapping("/register.form")
    @ResponseBody
    public String register(HttpServletRequest request) {
        ImUser imUser = new ImUser();
        imUser.setUsername(ServletUtils.getParameter(request, "username"));
        imUser.setPassword(ServletUtils.getParameter(request, "password"));
        imUser.setNickname(ServletUtils.getParameter(request, "nickname"));

        return imService.register(imUser);
    }
}

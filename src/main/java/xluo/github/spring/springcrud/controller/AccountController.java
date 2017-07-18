package xluo.github.spring.springcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xluo.github.spring.springcrud.model.Account;
import xluo.github.spring.springcrud.service.account.AccountService;
import xluo.github.spring.springcrud.util.ServletUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiaoluo on 17-7-17.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/account.form")
    @ResponseBody
    public String addAccount(HttpServletRequest request) {
        Account account = new Account();
        account.setPhone(ServletUtils.getParameter(request, "phone"));
        accountService.addAccount(account);
        return "success";
    }
}

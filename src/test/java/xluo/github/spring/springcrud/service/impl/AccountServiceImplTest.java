package xluo.github.spring.springcrud.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import xluo.github.spring.springcrud.model.Account;
import xluo.github.spring.springcrud.service.account.AccountService;

/**
 * Created by xiaoluo on 17-7-14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AccountServiceImplTest {

    @Autowired
    AccountService accountService;

    @Transactional
    @Test
    @Rollback(false)
    public void testAddAccount() throws Exception {
        Account account = new Account();
        account.setPhone("+8615072488162");
        accountService.addAccount(account);
    }
}

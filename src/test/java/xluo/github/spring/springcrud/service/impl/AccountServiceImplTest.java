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

import static org.junit.Assert.assertEquals;

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

    @Transactional
    @Test
    @Rollback(false)
    public void testGetAccount() throws Exception {
        long id = 7;

        Account account = accountService.getAccountById(7);
        assertEquals("+8615072488162", account.getPhone());
    }

    @Transactional
    @Test
    @Rollback(false)
    public void testUpdateAccount() throws Exception {
        long id = 12;
        Account account = new Account();
        account.setId(id);
        account.setPhone("+8618989342348");
        accountService.updateAccount(account);
    }
}

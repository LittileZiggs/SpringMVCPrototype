package xluo.github.spring.springcrud.service.account.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xluo.github.spring.springcrud.dao.AccountDao;
import xluo.github.spring.springcrud.model.Account;
import xluo.github.spring.springcrud.service.account.AccountService;

/**
 * Created by xiaoluo on 17-7-14.
 */
@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public void addAccount(Account account) {
        accountDao.insert(account);
    }
}

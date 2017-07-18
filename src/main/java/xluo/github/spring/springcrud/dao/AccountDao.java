package xluo.github.spring.springcrud.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import xluo.github.spring.springcrud.model.Account;

/**
 * Created by xiaoluo on 17-7-13.
 */

@Repository
public interface AccountDao extends BaseDao {

    @Insert("insert into account (phone) values(#{phone})")
    void insert(Account account);

    @Select("select * from account where id = #{id}")
    Account getAccountById(long id);

    @Update("update account set phone = #{phone} where id = #{id}")
    void updateAccount(Account account);
}

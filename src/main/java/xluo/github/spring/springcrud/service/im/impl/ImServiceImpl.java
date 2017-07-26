package xluo.github.spring.springcrud.service.im.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xluo.github.spring.springcrud.model.ImUser;
import xluo.github.spring.springcrud.service.im.ImService;
import xluo.github.spring.springcrud.util.EasemobUtils;

/**
 * Created by xiaoluo on 17-7-25.
 */
@Service("imService")
@Transactional
public class ImServiceImpl implements ImService {
    @Override
    public String register(ImUser imUser) {
        return EasemobUtils.register(imUser);
    }
}

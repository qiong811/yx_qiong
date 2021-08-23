package cn.baizhi.test;

import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao ud;


    @Test
    public void testQueryRange(){
        System.out.println(ud.queryRange(0, 3));
        System.out.println(ud.queryCount());
        User user=new User();
        user.setStatus(0);
        user.setId("1");
        ud.update(user);
    }

}

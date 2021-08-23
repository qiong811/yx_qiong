package cn.baizhi;

import cn.baizhi.dao.AdminDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YxQiongApplicationTests {
    @Autowired
    private AdminDao adminDao;
    @Test
    void contextLoads() {
        System.out.println(adminDao.queryByUsername("gj"));
    }

}

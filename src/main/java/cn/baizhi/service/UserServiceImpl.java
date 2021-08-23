package cn.baizhi.service;

import cn.baizhi.annotation.AroundAspect;
import cn.baizhi.annotation.DeleteCaChe;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.util.Aliyun;
import cn.baizhi.vo.MonthAndSex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao ud;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    @AroundAspect
    public Map<String,Object> queryByPage(int page, int size) {
        Map<String,Object> map=new HashMap<>();
        int count;
        if(ud.queryCount()%size!=0){
            count=ud.queryCount()/size+1;
        }else{
            count=ud.queryCount()/size;
        }
        map.put("count", count);
        List<User> list = ud.queryRange((page - 1) * size, size);
        map.put("data", list);
        return map;
    }

    @Override
    @DeleteCaChe
    public void update(User user) {

        ud.update(user);
    }

    @Override
    @DeleteCaChe
    public void insert(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreatedate(new Date());
        user.setStatus(0);
        user.setSex("男");
        ud.insert(user);
    }

    @Override
    @DeleteCaChe
    public void delete(String id) {
        ud.delete(id);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    @AroundAspect
    public User queryOne(String id) {
        return ud.queryOne(id);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    @AroundAspect
    public List<User> queryAll() {
        List<User> list = ud.queryAll();
        for(int i=0;i<list.size();i++){
            User user = list.get(i);
            String headimg = user.getHeadimg();
            String[] split = headimg.split("com/");
            Aliyun.testDownLoad(split[1]);
            user.setHeadimg("D:\\headimg\\"+split[1]);
            System.out.println("service");
            System.out.println(user);
        }
//        for (User user : list) {
//            String headimg = user.getHeadimg();
//            Aliyun.testDownLoad(headimg );
//            String[] split = headimg.split("com/");
//            user.setHeadimg("D:\\headimg\\"+split[1]);
//        }

        return list;

    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    @AroundAspect
    public List<MonthAndSex> selectByMonth(String sex) {

        return ud.selectByMonth(sex);
    }
}

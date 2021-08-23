package cn.baizhi.service;

import cn.baizhi.annotation.AroundAspect;
import cn.baizhi.dao.AdminDao;
import cn.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao ad;
    //登录业务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    //@AroundAspect
    public Map<String, Object> login(String username, String password) {
        Admin admin = ad.queryByUsername(username);
        Map<String,Object> map = new HashMap<>();
        if(admin!=null){
            if(admin.getPassword().equals(password)){
                //查询成功
                map.put("flag", true);
                map.put("msg", "登录成功");
            }else{
                //密码错误
                map.put("flag", false);
                map.put("msg", "密码错误");
            }
        }else{
            //无该用户
            map.put("flag", false);
            map.put("msg", "该用户不存在");
        }
        return map;
    }
}

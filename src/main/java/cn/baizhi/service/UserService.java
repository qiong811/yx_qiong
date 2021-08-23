package cn.baizhi.service;

import cn.baizhi.entity.User;
import cn.baizhi.vo.MonthAndSex;

import java.util.List;
import java.util.Map;

public interface UserService {
    //查询总页数及分页显示
    Map<String,Object> queryByPage(int page,int size);
    //修改用户
    void update(User user);
    //添加用户
    void insert(User user);
    //删除用户
    void delete(String id);
    //查一个
    User queryOne(String id);
    //查所有
    List<User> queryAll();
    //查询每个月男女注册人数
    List<MonthAndSex> selectByMonth(String sex);

}

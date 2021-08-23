package cn.baizhi.dao;

import cn.baizhi.entity.User;
import cn.baizhi.vo.MonthAndSex;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //分页查询
    List<User> queryRange(@Param("start") int start,@Param("size") int size);
    //查询总数
    Integer queryCount();
    //修改
    void update(User user);
    //根据id查一个
    User queryOne(String id);
    //添加用户
    void insert(User user);
    //删除用户
    void delete(String id);
    //查所有
    List<User> queryAll();
    //查询每个月男女注册人数
    List<MonthAndSex> selectByMonth(String sex);

}

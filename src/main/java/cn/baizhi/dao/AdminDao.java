package cn.baizhi.dao;

import cn.baizhi.entity.Admin;

public interface AdminDao {
    //根据名字查询管理员
    Admin queryByUsername(String username);
}

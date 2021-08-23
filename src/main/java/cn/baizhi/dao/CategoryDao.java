package cn.baizhi.dao;

import cn.baizhi.entity.Category;

import java.util.List;

public interface CategoryDao {
    //根据一级查所有类别
    List<Category> queryByLevels(int levels);
    //查询某个一级下的所有二级类别
    List<Category> queryByParentId(String id);
    //添加二级类别
    void add(Category category);
    //删除类别
    void deleteById(String id);
    //查一个
    Category selectOne(String id);
}

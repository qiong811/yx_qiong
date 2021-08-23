package cn.baizhi.service;

import cn.baizhi.annotation.AroundAspect;
import cn.baizhi.annotation.DeleteCaChe;
import cn.baizhi.dao.CategoryDao;
import cn.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryDao cd;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AroundAspect
    public List<Category> queryByLevels(int levels) {
        return cd.queryByLevels(levels);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AroundAspect
    public List<Category> queryByParentId(String id) {
        return cd.queryByParentId(id);
    }

    @Override
    @DeleteCaChe
    public void add(Category category) {
        category.setId(UUID.randomUUID().toString());
        cd.add(category);
    }

    @Override
    @DeleteCaChe
    public void deleteById(String id) {
        cd.deleteById(id);
    }
}

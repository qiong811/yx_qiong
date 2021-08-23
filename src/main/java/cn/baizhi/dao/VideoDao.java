package cn.baizhi.dao;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {
    //分页查
    List<Video> selectByPage(@Param("start") int start,@Param("end") int end);
    //根据id删除
    void delete(String id);
    //添加视频
    void insert(Video video);
    //查一个
    Video selectById(String id);
    //查询总数
    Integer queryCount();
}

package cn.baizhi.service;

import cn.baizhi.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface VideoService {
    //分页查
    Map<String,Object> selectByPage(int page, int size);
    //根据id删除
    void delete(String id);
    //添加视频
    void insert(MultipartFile vfile,Video video);
}

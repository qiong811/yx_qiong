package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.entity.Video;
import cn.baizhi.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService vs;
    @RequestMapping("/findAll")
    public Map<String,Object> findAll(int page){
        int size=3;
        return vs.selectByPage(page, size);
    }
    @RequestMapping("/delete")
    public void delete(String id){
        vs.delete(id);
    }
    @RequestMapping("/add")
    public void insert(MultipartFile video,String title,String brief,String id){
        System.out.println(video.getOriginalFilename());
        System.out.println(title);
        System.out.println(brief);
        System.out.println(id);
        Video v=new Video();
        v.setTitle(title);
        v.setBrief(brief);
        Category category=new Category();
        category.setId(id);
        v.setCategory(category);
        vs.insert(video, v);
    }
}

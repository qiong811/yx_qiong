package cn.baizhi.service;

import cn.baizhi.annotation.AroundAspect;
import cn.baizhi.annotation.DeleteCaChe;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.entity.Video;
import cn.baizhi.util.Aliyun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoDao vd;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    @AroundAspect
    public Map<String,Object> selectByPage(int page, int size) {
        Map<String, Object> map = new HashMap<>();
        Integer count = 0;
        if(vd.queryCount()%size==0){
            count=vd.queryCount()/size;
        }else{
            count=vd.queryCount()/size+1;
        }
        map.put("count", count);
        List<Video> list = vd.selectByPage((page-1)*size, size);
        map.put("data",list);
        return map;
    }

    @Override
    @DeleteCaChe
    public void delete(String id) {
        Video video = vd.selectById(id);
        Aliyun.deleteFile(video.getCoverPath());
        Aliyun.deleteFile(video.getVideoPath());
        vd.delete(id);
    }

    @Override
    @DeleteCaChe
    public void insert(MultipartFile vfile,Video video) {

        String filename = new Date().getTime()+vfile.getOriginalFilename();
        System.out.println(filename);
        Aliyun.uploadVideo(vfile, "video/"+filename);
//        Aliyun.upload(vfile,"video/"+filename);
//        String endpoint = AliyunConfig.ENDPOINT;
//        String accessKeyId = AliyunConfig.ACCESS_KEY_ID;
//        String accessKeySecret = AliyunConfig.ACCESS_KEY_SECRET;
//        String bucketName = AliyunConfig.BUCKET_NAME;  //存储空间名
//        String objectName = "video/"+filename;
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        String style = "video/snapshot,t_1000,f_jpg,w_500,h_800";
//        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
//        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
//        req.setExpiration(expiration);
//        req.setProcess(style);
//        URL signedUrl = ossClient.generatePresignedUrl(req);
//        System.out.println(signedUrl);
//
//        InputStream inputStream =null;
//        try {
//            inputStream =new URL(signedUrl.toString()).openStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String s2 = objectName.replace("mp4", "jpg");
//        ossClient.putObject(AliyunConfig.BUCKET_NAME, s2, inputStream);
//
//
//
//
//        ossClient.shutdown();


        video.setVideoPath("http://yxqiong.oss-cn-beijing.aliyuncs.com/video/"+filename);
        String s3 = filename.replace("mp4", "jpg");
        video.setCoverPath("http://yxqiong.oss-cn-beijing.aliyuncs.com/video/"+s3);
        video.setId(UUID.randomUUID().toString());
        video.setCreateDate(new Date());
        video.setGroup("1");

        vd.insert(video);
    }
}

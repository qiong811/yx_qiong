package cn.baizhi.controller;

import cn.baizhi.entity.User;
import cn.baizhi.service.UserService;
import cn.baizhi.util.EasyPoi;
import cn.baizhi.vo.MonthAndSex;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService us;
    @RequestMapping("/queryByPage")
    public Map<String,Object> queryByPage(int page){
        int size=3;
        return us.queryByPage(page, size);
    }
    @RequestMapping("/update")
    public void update(String id,Integer stu){
        User user=new User();
        user.setId(id);
        user.setStatus(stu);
        us.update(user);
    }
    @RequestMapping("/add")
    public void add(MultipartFile photo, String username, String phone, String brief, HttpServletRequest request) throws IOException {
        User user=new User();
        user.setUsername(username);
        user.setPhone(phone);
        user.setBrief(brief);
        String filename = photo.getOriginalFilename();
        String s= UUID.randomUUID().toString();
        byte[] bytes = photo.getBytes();
//        String path="D:\\ideacodes\\yx_qiong\\src\\main\\webapp\\headimg";
//        System.out.println(path);
//        File file=new File(path);
//        file.mkdir();
//        photo.transferTo(new File(file,filename));

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI5tKAc5BfmS6uLc82Yqam";
        String accessKeySecret = "785ufuAis4DYCZOg1fn6hZalmhh0Dc";
        String bucketName = "yxqiong";  //存储空间名
        String objectName = s+filename;  //文件名  可以指定文件目录
//        String localFile=path+"//"+filename;  //本地视频路径

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。 参数：Bucket名字，指定文件名，文件本地路径
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(bytes));
      //  PutObjectRequest putObjectRequest = new PutObjectRequest("examplebucket", "exampledir/exampleobject.txt", new ByteArrayInputStream(content.getBytes()));
        // 上传文件。
        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient。
        ossClient.shutdown();


//        String path = request.getSession(true).getServletContext().getRealPath("/headimg");

        user.setHeadimg("https://yxqiong.oss-cn-beijing.aliyuncs.com/"+s+filename);
//        user.setHeadimg(path+"//"+filename);
        us.insert(user);
    }
    @RequestMapping("/delete")
    public void delete(String id){
        User user = us.queryOne(id);
        String headimg=user.getHeadimg();
        String[] split = headimg.split("com/");


        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        //阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI5tKAc5BfmS6uLc82Yqam";
        String accessKeySecret = "785ufuAis4DYCZOg1fn6hZalmhh0Dc";
        String bucketName = "yxqiong";  //存储空间名
        String objectName = split[1];  //文件名

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();

        us.delete(id);
    }
    @RequestMapping("/exportData")
    public void exportData(){
        List<User> list = us.queryAll();
        for (User user : list) {
            System.out.println(user);
        }
        EasyPoi.exportData(list);
    }
    @RequestMapping("/echarts")
    public Map<String,Object> echarts(){
        Map<String,Object> map=new HashMap<>();
        List<MonthAndSex> man = us.selectByMonth("男");
        List<Integer> lman=Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);

        for (MonthAndSex monthAndSex : man) {
            int s = Integer.parseInt(monthAndSex.getMonths());

            for(int i=1;i<13;i++){
                if(s==i){
                    lman.set(i-1, monthAndSex.getSexCount());

                }

            }
        }
        for (Integer integer : lman) {
            System.out.println(integer);
        }
        map.put("man", lman);
        System.out.println("====");
        List<MonthAndSex> woman = us.selectByMonth("女");
        List<Integer> lwoman=Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
        for (MonthAndSex monthAndSex : woman) {
            int s = Integer.parseInt(monthAndSex.getMonths());
            for(int i=1;i<13;i++){

                if(s==i){
                    lwoman.set(i-1, monthAndSex.getSexCount());
                }
            }
        }
        for (Integer integer : lwoman) {
            System.out.println(integer);
        }
        map.put("woman", lwoman);
        map.put("data", Arrays.asList("一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"));
        return map;

    }

}

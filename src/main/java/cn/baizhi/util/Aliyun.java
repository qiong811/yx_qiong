package cn.baizhi.util;

import cn.baizhi.config.AliyunConfig;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class Aliyun implements AliyunConfig {
    //创建存储空间
    public static void createBucket(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ACCESS_KEY_ID;
        String accessKeySecret = ACCESS_KEY_SECRET;
        String bucketName = BUCKET_NAME;  //存储空间名

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建CreateBucketRequest对象。
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        ossClient.createBucket(createBucketRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //上传图片到存储空间
    public static void uploadPhoto(MultipartFile photo) throws IOException {
        String filename = photo.getOriginalFilename();
        String s= UUID.randomUUID().toString();
        byte[] bytes = photo.getBytes();

        String endpoint = ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ACCESS_KEY_ID;
        String accessKeySecret = ACCESS_KEY_SECRET;
        String bucketName = BUCKET_NAME;  //存储空间名
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
    }
    //删除存储空间中的文件
    public static void deleteFile(String headimg){
        String[] split = headimg.split("com/");


        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ACCESS_KEY_ID;
        String accessKeySecret = ACCESS_KEY_SECRET;
        String bucketName = BUCKET_NAME;  //存储空间名
        String objectName = split[1];  //文件名

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //上传视频到存储空间
    public static void upload(MultipartFile photo,String filename)  {
        String endpoint = ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ACCESS_KEY_ID;
        String accessKeySecret = ACCESS_KEY_SECRET;
        String bucketName = BUCKET_NAME;  //存储空间名
          //文件名  可以指定文件目录
//        String localFile=path+"//"+filename;  //本地视频路径
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        byte[] bytes=null;
        try {
            bytes=photo.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 创建PutObjectRequest对象。 参数：Bucket名字，指定文件名，文件本地路径
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, new ByteArrayInputStream(bytes));
        //  PutObjectRequest putObjectRequest = new PutObjectRequest("examplebucket", "exampledir/exampleobject.txt", new ByteArrayInputStream(content.getBytes()));
        // 上传文件。
        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
    public static void uploadVideo(MultipartFile video,String filename) {



        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ACCESS_KEY_ID;
        String accessKeySecret = ACCESS_KEY_SECRET;
        String bucketName = BUCKET_NAME;  //存储空间名
// 填写视频文件的完整路径。若视频文件不在Bucket根目录，需携带文件访问路径，例如examplefolder/videotest.mp4。
        //String objectName = filename;
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        byte[] bytes =null;
        try {
            bytes=video.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, new ByteArrayInputStream(bytes));
        // 上传文件。
        ossClient.putObject(putObjectRequest);
// 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_1000,f_jpg,w_500,h_800";
// 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, filename, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);

        InputStream inputStream =null;
        try {
            inputStream =new URL(signedUrl.toString()).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        //String[] split=filename.split("\\.");
        String s = filename.replace("mp4", "jpg");
        ossClient.putObject(BUCKET_NAME, s, inputStream);


// 关闭OSSClient。


        ossClient.shutdown();
    }
//下载
    public static void testDownLoad(String headimg){


        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ACCESS_KEY_ID;
        String accessKeySecret = ACCESS_KEY_SECRET;
        String bucketName = BUCKET_NAME;  //存储空间名//存储空间名
        String objectName = headimg;  //文件名
        String localFile="D:\\headimg\\"+objectName;  //下载本地地址  地址加保存名字
        System.out.println(localFile);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }


}

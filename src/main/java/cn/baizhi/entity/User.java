package cn.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    @Excel(name="用户ID")
    private String id;
    @Excel(name="用户ming")
    private String username;
    @Excel(name="手机号")
    private String phone;
    @Excel(name="用户头像",type=2)
    private String headimg;
    @Excel(name="描述")
    private String brief;
    private String wechat;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name="创建日期",format = "yyyy-MM-dd")
    private Date createdate;
    private Integer status;
    private String sex;


}

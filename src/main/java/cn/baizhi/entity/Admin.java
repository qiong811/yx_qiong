package cn.baizhi.entity;

import lombok.*;

import java.io.Serializable;

//@Data
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements Serializable {
    private String id;
    private String username;
    private String password;
    private Integer status;
}

package cn.baizhi.controller;

import cn.baizhi.entity.Admin;
import cn.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService as;
    @RequestMapping("/login")
    public Map<String,Object> login(@RequestBody Admin admin){
        System.out.println(admin.toString());
//        System.out.println(admin.getPassword());
        return as.login(admin.getUsername(),admin.getPassword());

    }

}

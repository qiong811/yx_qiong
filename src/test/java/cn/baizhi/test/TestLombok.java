package cn.baizhi.test;

import cn.baizhi.entity.Admin;

public class TestLombok {
    public static void main(String[] args) {
        Admin admin = new Admin();
        admin.setUsername("zs");
        System.out.println(admin);
        Admin gj = new Admin("1", "gj", "123", 11);
        System.out.println(gj.getUsername());
        System.out.println(gj);
    }
}

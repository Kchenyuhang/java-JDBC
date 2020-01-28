package org.example.dao;

import org.example.entity.Admin;
import org.junit.Test;

import java.util.List;

/**
 * @Author yhChen
 * @Description
 * @Date 2020/1/28
 */
public class AdminDaoTest {
    private AdminDao adminDao = new AdminDao();
    @Test
    public void selectAll() {
        System.out.println("***********带参查询*********");
        String sql = "SELECT * FROM t_admin WHERE name = ?";
        Object[] params = {"admin"};
        List<Admin> admins = adminDao.selectAll(sql,params);
        System.out.println(admins);
        System.out.println("***********无参查询整表**********");
        sql = "SELECT * FROM t_admin";
        admins = adminDao.selectAll(sql,null);
        admins.forEach(System.out::println);
    }

}

package com.qm;

import com.qm.entity.Report;
import com.qm.entity.Role;
import com.qm.entity.User;
import com.qm.mapper.ReportMapper;
import com.qm.mapper.RoleMapper;
import com.qm.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @program: RealSight-1.0.0
 * @description:
 * @author: ZhangQingMin
 * @create: 2025-05-04 22:46
 **/
@SpringBootTest
public class MyBatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ReportMapper reportMapper;

    @Test
    public void testSelectUser() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelect() {
        List<Report> reports = reportMapper.selectList(null);
        reports.forEach(System.out::println);
    }


}

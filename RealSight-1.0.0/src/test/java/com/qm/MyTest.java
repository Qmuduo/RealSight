package com.qm;

import com.qm.entity.Role;
import com.qm.service.UserRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @program: RealSight-1.0.0
 * @description:
 * @author: ZhangQingMin
 * @create: 2025-05-10 18:05
 **/
@SpringBootTest
public class MyTest {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    public void userRoleServiceTest() {
        List<Role> roles = userRoleService.getRolesByUserId(1l);
        for (Role role : roles) {
            System.out.println(role.getName());
        }
    }
}

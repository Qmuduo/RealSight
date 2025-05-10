package com.qm.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.qm.entity.Role;
import com.qm.entity.User;
import com.qm.entity.vo.LoginUser;
import com.qm.mapper.UserMapper;
import com.qm.service.UserRoleService;
import com.qm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program:
 * @description:
 * @author: ZhangQingMin
 * @create: 2025-05-06 19:38
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    //@Autowired
    //private MenuMapper menuMapper;

    /**
     * UserServiceImpl的authenticationManager.authenticate()方法会调用这个方法，来加载用户信息包括权限
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 如果用户名为空，抛出异常提示
        if (username.equals("")) {
            // 传入空字符串，后面的LoginFailureHandler会去匹配
            throw new InternalAuthenticationServiceException("");
        }

        // 1.连接数据库，用用户名查询user

        /**
         * old:用userMapper来查
         */
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_name", username);
//        User user = userMapper.selectOne(queryWrapper);

        User user = userService.getUserByUsername(username);

        if (Objects.isNull(user)) {
            // 传入空字符串，后面的LoginFailureHandler会去匹配
            throw new UsernameNotFoundException("");
        }
        // 2.授权操作
        // TODO

        // 权限列表，死的权限
//        List<String> stringAuthorities = new ArrayList<>();
//        stringAuthorities.add("ROLE_USER");
//        stringAuthorities.add("select");
//        stringAuthorities.add("update");

        // 去数据库查的用户权限
        //List<String> stringAuthorities = menuMapper.getMenuByUserId(user.getId());
        List<String> stringAuthorities = new ArrayList<>();

        List<Role> roles = userRoleService.getRolesByUserId(user.getId());
        for (Role role : roles) {
            stringAuthorities.add(role.getName());
        }

        // 3.返回UserDetail对象

        return new LoginUser(user, stringAuthorities);
    }
}

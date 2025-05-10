package com.qm.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qm.domain.User;
import com.qm.domain.vo.LoginUser;
import com.qm.mapper.MenuMapper;
import com.qm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: springsecurity-hello
 * @description:
 * @author: ZhangQingMin
 * @create: 2025-05-06 19:38
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        User user = userMapper.selectOne(queryWrapper);

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
        List<String> stringAuthorities = menuMapper.getMenuByUserId(user.getId());

        // 3.返回UserDetail对象

        return new LoginUser(user, stringAuthorities);
    }
}

package com.qm.entity.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.qm.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @program:
 * @description: security要用，UserDetailService里需要用，把自己的User和Security的User做关联
 * @author: ZhangQingMin
 * @create: 2025-05-06 19:34
 **/
@Data
public class LoginUser implements UserDetails {

    // 数据库查好传进来的用户权限列表
    private List<String> stringAuthorities = new ArrayList<>();

    // 封装成Security要用的权限列表
    @JSONField(serialize = false) // 这个属性只是作为一个中转操作，不用序列化
    private List<SimpleGrantedAuthority> simpleGrantedAuthorities;

    private User user;

    public LoginUser(){}

    public LoginUser(User user, List<String> stringAuthorities) {
        this.stringAuthorities = stringAuthorities;
        this.user = user;
    }



    // 获取权限列表，放到security管理的权限容器里
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (simpleGrantedAuthorities != null) {
            return simpleGrantedAuthorities;
        }
        simpleGrantedAuthorities = new ArrayList<>();
        // 遍历数据库传来的权限数据，再放到security的权限列表里
        for (String stringAuthority : stringAuthorities) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(stringAuthority));
        }

        return simpleGrantedAuthorities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getStringAuthorities() {
        return stringAuthorities;
    }

    public void setStringAuthorities(List<String> stringAuthorities) {
        this.stringAuthorities = stringAuthorities;
    }

    public List<SimpleGrantedAuthority> getSimpleGrantedAuthorities() {
        return simpleGrantedAuthorities;
    }

    public void setSimpleGrantedAuthorities(List<SimpleGrantedAuthority> simpleGrantedAuthorities) {
        this.simpleGrantedAuthorities = simpleGrantedAuthorities;
    }

    // 获取密码
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    // 获取用户名
    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    // 账号是否未过期
    @Override
    public boolean isAccountNonExpired() {
        // 这里先设置true
        return true;
    }

    // 账号是否被锁定
    @Override
    public boolean isAccountNonLocked() {
        // 这里先设置true
        return true;
    }

    // 账号是否超时
    @Override
    public boolean isCredentialsNonExpired() {
        // 这里先设置true
        return true;
    }

    // 是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "user=" + user +
                '}';
    }
}

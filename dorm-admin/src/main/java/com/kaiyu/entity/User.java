package com.kaiyu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author mxxxl
 * @date 2021/6/19
 */
@Data
public class User implements UserDetails {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    @Getter(value = AccessLevel.NONE)
    private Boolean enabled;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private String remark;

    private List<Role> roles;

    private static final long serialVersionUID = 1L;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (null == roles) {
            return null;
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(roles.size());
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
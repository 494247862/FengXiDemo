package com.fengxi.auth.vo;

import com.fengxi.auth.entity.DeyiUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 封装给Security的用户VO
 * @author wujiuhe
 * @description: TODO
 * @title: LoginUserVO
 * @projectName demo
 * @date 2023/1/22 1:49:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserVO implements UserDetails {

    private DeyiUser user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount();
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
        return true;
    }
}

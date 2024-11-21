package com.uzay.securitygeneltekrarr.security;

import com.uzay.securitygeneltekrarr.entity.Role;


import com.uzay.securitygeneltekrarr.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MyUserDetails implements UserDetails {
    User user;
    public MyUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> userRole = user.getRole();
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        userRole.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName().name())));
        return authorities;


    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}

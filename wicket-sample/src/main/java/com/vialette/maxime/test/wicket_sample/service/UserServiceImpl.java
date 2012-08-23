package com.vialette.maxime.test.wicket_sample.service;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails result = null;

        ArrayList<GrantedAuthority> listGuest = new ArrayList<GrantedAuthority>();
        listGuest.add(new GrantedAuthorityImpl("ROLE_GUEST"));

        ArrayList<GrantedAuthority> listUser = new ArrayList<GrantedAuthority>();
        listUser.add(new GrantedAuthorityImpl("ROLE_USER"));

        ArrayList<GrantedAuthority> listManager = new ArrayList<GrantedAuthority>();
        listManager.add(new GrantedAuthorityImpl("ROLE_MANAGER"));

        ArrayList<GrantedAuthority> listAdmin = new ArrayList<GrantedAuthority>();
        listAdmin.add(new GrantedAuthorityImpl("ROLE_ADMIN"));

        ArrayList<GrantedAuthority> listRoleA = new ArrayList<GrantedAuthority>();
        listRoleA.add(new GrantedAuthorityImpl("ROLE_A"));

        if (username.equals("totoAdm")) {
            result = new User(username, "passAdm", listAdmin);
        } else if (username.equals("totoManager")) {
            result = new User(username, "passManager", listManager);
        } else if (username.equals("totoUser")) {
            result = new User(username, "passUser", listUser);
        } else if (username.equals("totoGuest")) {
            result = new User(username, "passGuest", listGuest);
        } else if (username.equals("tutuAdm")) {
            result = new User(username, "passAdm", listAdmin);
        } else if (username.equals("tutuManager")) {
            result = new User(username, "passManager", listManager);
        } else if (username.equals("tutuUser")) {
            result = new User(username, "passUser", listUser);
        } else if (username.equals("tutuGuest")) {
            result = new User(username, "passGuest", listGuest);
        } else if (username.equals("tutuA")) {
            result = new User(username, "passA", listRoleA);
        }

        return result;
    }

}

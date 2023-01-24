package com.bootcamp.robotikka.robotikkaapi.service.impl;


import com.bootcamp.robotikka.robotikkaapi.dto.core.ApplicationUser;
import com.bootcamp.robotikka.robotikkaapi.entity.User;
import com.bootcamp.robotikka.robotikkaapi.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.bootcamp.robotikka.robotikkaapi.config.permission.ApplicationUserRole.*;

public class ApplicationUserServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public ApplicationUserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userByUsername = userRepo.findByEmail(username);
        if (userByUsername.isEmpty()){
            throw new UsernameNotFoundException(String.format("username : %s not found",username));
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        switch (userByUsername.get().getUserRole().getRoleName()){
            case "USER" :
                grantedAuthorities = USER.getGrantedAuthorities();
                break;
            case "ADMIN":
                grantedAuthorities = ADMIN.getGrantedAuthorities();
                break;
            case "MANAGER":
                grantedAuthorities = MANAGER.getGrantedAuthorities();
                break;
        }


        ApplicationUser applicationUser = new ApplicationUser(
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

    }
}

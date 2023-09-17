package com.project.smartICT.services;

import com.project.smartICT.entities.User;
import com.project.smartICT.repos.IUserRepository;
import com.project.smartICT.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private IUserRepository iUserRepository;

    public UserDetailsServiceImpl(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserRepository.findByUserName(username);
        return JwtUserDetails.create(user);
    }

    public UserDetails loadUserById(Long id){
        User user = iUserRepository.findById(id).get();
        return JwtUserDetails.create(user);
    }
}

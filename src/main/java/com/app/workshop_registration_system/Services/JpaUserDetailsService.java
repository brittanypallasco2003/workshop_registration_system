package com.app.workshop_registration_system.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.workshop_registration_system.Models.UserModel;
import com.app.workshop_registration_system.Repositories.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserModel> optionalUserModel=userRepository.findByEmail(email);

        optionalUserModel.orElseThrow(()->new UsernameNotFoundException("The user "+email+" no existe en la base de datos"));

        UserModel userDb=optionalUserModel.get();

        SimpleGrantedAuthority authority= new SimpleGrantedAuthority("ROLE_".concat(userDb.getRoleModel().getRoleEnum().name()));

        List<SimpleGrantedAuthority> authorities=new ArrayList<>(Arrays.asList(authority));

       return new User(
        userDb.getEmail(),
        userDb.getPassword(),
        userDb.isEnable(),
        userDb.isAccountNoExpired(),
        userDb.isCredentialNoExpired(),
        userDb.isAccountNoLocked(),
        authorities
       );
         
    }

}

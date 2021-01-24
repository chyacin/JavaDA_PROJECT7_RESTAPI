package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

    Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    /**
     * The service method which creates a user details object from the given user name
     * @param username the username logged in with
     * @return the User detail object
     * @throws UsernameNotFoundException an exception which is thrown if the user is not found in the database
     */
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User userByUsername = userRepository.findUserByUsername(username);

       if(userByUsername == null) {
           throw new UsernameNotFoundException("Could not find user By Username");
       }
       GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userByUsername.getRole());

       log.info("User : " + username + "role : " + grantedAuthority );

       return new org.springframework.security.core.userdetails.User(userByUsername.getUsername(), userByUsername.getPassword(), Collections.singletonList(grantedAuthority));

   }
}

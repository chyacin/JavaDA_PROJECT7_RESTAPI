package com.nnk.springboot.serviceTest;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserDetailsServiceImpl;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserDetailsServiceTest {


    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserServiceImpl userService;

    @Test
    public void loadUserByUserName_returnUserDetails(){

        //arrange
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("password");
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setRole("ADMIN");
        user.setPassword(password);

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        //act
        UserDetails result = userDetailsService.loadUserByUsername("username");

        assertEquals("username", result.getUsername());

    }
}

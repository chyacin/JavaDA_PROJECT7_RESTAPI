package com.nnk.springboot.serviceTest;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;


    @Test
    public void findUserByUsername(){

        //arrange
        User user = new User();
        user.setId(1);
        user.setUsername("username");

        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        //act
        User result = userService.findUserByUsername(user.getUsername());

        //assert
        assertNotNull(result);
        assertEquals("username", result.getUsername());

    }


    @Test
    public void saveUser_returnUser(){

        //arrange
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("password");

        User user = new User("username",  "fullname", "role");
        user.setId(1);
        user.setPassword(password);

        when(userRepository.save(user)).thenReturn(user);

        //act
        User saveUser = userService.saveUser(user);

        //assert
        assertNotNull(saveUser);
        assertEquals(user.getId(), saveUser.getId(),0);
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    public void findUserById__return() {

        //arrange
        User user = new User();
        user.setId(1);

        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        //act
        User result = userService.findUserById(user.getId());

        //assert
        Assert.assertEquals(user.getId(), result.getId());
    }



    @Test
    public void findAllUsers_returnFindAllUsers(){


        //arrange
        User user = new User();
        user.setId(1);

        User user1 = new User();
        user1.setId(2);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user,user1));

        //act
        List<User> users = userService.findAllUsers();

        //assert
        assertEquals(2, users.size());
        assertEquals(1, users.get(0).getId(),0);
        assertEquals(2, users.get(1).getId(),0);
        Assert.assertTrue(users.size() > 0);

    }



    @Test
    public void updateUser_updateUser() {

        //arrange
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("password234");

        User user = new User("username234", "fullname234", "role");
        user.setId(1);
        user.setPassword(password);

        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        //act
        userService.updateUser(user);

        //assert
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    public void deleteUser_deleteUser(){

        //act
        userService.deleteUser(1);

        //assert
        Optional<User> user = userRepository.findById(1);
        Assert.assertFalse(user.isPresent());

    }
}

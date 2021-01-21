package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;

import java.util.List;

public interface UserService {

    User findUserByUsername(String username);
    public User saveUser(User user);
    public User findUserById(int id);
    List<User> findAllUsers();
    public void updateUser(User user);
    public void deleteUser(int id);

}

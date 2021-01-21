package com.nnk.springboot.services;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;



    /**
     * The service method which finds the User by their username
     * @param username the username to be found
     * @return the found username object
     */
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    /**
     * The service method which saves user to the database
     * @param user to be saved
     * @return the saved user object
     */
    @Override
    public User saveUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * The service method which finds the respective user by their corresponding id
     * @param id of the user
     * @return the found user
     */
    @Override
    public User findUserById(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return user;
        }
        return null;
    }

    /**
     * The service method which finds and returns all the users in a list
     * @return the list of all the users
     */
    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    /**
     *  The service method which updates the user's details
     * @param user to be updated
     */
    @Override
    public void updateUser(User user) {
        User updateUser = findUserById(user.getId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(updateUser != null){
            updateUser.setFullname(user.getFullname());
            updateUser.setUsername(user.getUsername());
            updateUser.setPassword(encoder.encode(user.getPassword()));
            updateUser.setRole(user.getRole());
            userRepository.save(updateUser);
        }

    }

    /**
     * The service method which deletes the user's details
     * @param id of the user
     */
    @Override
    public void deleteUser(int id) {
      userRepository.deleteById(id);

    }


}

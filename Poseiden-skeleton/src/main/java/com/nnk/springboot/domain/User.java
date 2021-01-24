package com.nnk.springboot.domain;

import com.nnk.springboot.config.ValidPassword;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    @Size(max = 125)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "password")
    @ValidPassword
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "fullname")
    @Size(max = 125)
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @Column(name = "role")
    @Size(max = 125)
    @NotBlank(message = "Role is mandatory")
    private String role;

    public User() {
    }



    public User( String username,String fullname, String role) {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

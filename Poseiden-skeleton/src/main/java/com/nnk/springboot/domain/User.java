package com.nnk.springboot.domain;

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
    @Size(max = 125)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "Password must have at least one uppercase character [A-Z]," +
            " it must contain at least one lowercase character [a-z], it must contain at least one special character like ! @ # & ( )," +
            " it must contain a length of at least 8 characters and a maximum of 20 characters, it must contain at least one digit [0-9] ")
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

    public User(Integer id, @NotBlank(message = "Username is mandatory") String username,
                @NotBlank(message = "Password is mandatory") @Length(min = 8)
                @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
                        message = "Password must have at least one uppercase character [A-Z]," +
                                " it must contain at least one lowercase character [a-z]," +
                                " it must contain at least one special character like ! @ # & ( )," +
                                " it must contain a length of at least 8 characters and a maximum of 20 characters, " +
                                "it must contain at least one digit [0-9] ") String password,
                @NotBlank(message = "FullName is mandatory") String fullname, @NotBlank(message = "Role is mandatory") String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
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

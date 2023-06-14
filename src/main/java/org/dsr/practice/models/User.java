package org.dsr.practice.models;

import jakarta.persistence.*;
import org.dsr.practice.utils.generators.CodeGenerator;

import java.util.List;

@Entity
@Table( name = "users" )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false, unique = true)
    private Long userId;

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imLink) {
        this.imgLink = imLink;
    }

    @Column(name="IMGLINK")
    String imgLink;

    @Column(name="PHONE")
    private String phone;

    @Column(name="CODE")
    private String code;
    @Column(name="NAME")
    private String name;
    @Column(name="SURNAME")
    private String surname;
    @Column(name="ROLE")
    private String Role;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Debt> debts;

    public User() {
    }

    public User(String phone, String name, String surname){
        this.userId = null;
        this.name = name;
        this.phone = phone;
        this.Role = "USER";
        this.surname = surname;
        this.code = CodeGenerator.getNewCode();
        this.imgLink = "";
    }

    public String getName() {
        return name;
    }

    public List<Debt> getDebts() {
        return debts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
    public void setId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String password) {
        this.code = password;
    }
    public Long getId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

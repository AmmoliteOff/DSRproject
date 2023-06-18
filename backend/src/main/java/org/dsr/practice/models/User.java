package org.dsr.practice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.dsr.practice.utils.JsonViews;
import org.dsr.practice.utils.generators.CodeGenerator;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table( name = "users" )
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private Long userId;

    @Column
    @JsonView({JsonViews.Public.class, JsonViews.OnlyForUser.class, JsonViews.LimitedPublic.class})
    String imgLink;

    @JsonIgnore
    @Column
    @JsonView(JsonViews.OnlyForUser.class)
    private String phone;
//    @Column
//    @JsonView(JsonViews.OnlyForUser.class)
//    private Double totalDebt;
    @JsonIgnore
    @Column
    private String code;
    @Column
    @JsonView({JsonViews.Public.class, JsonViews.OnlyForUser.class, JsonViews.LimitedPublic.class})
    private String name;
    @Column
    @JsonView({JsonViews.Public.class, JsonViews.OnlyForUser.class, JsonViews.LimitedPublic.class})
    private String surname;

    @Column
    @JsonView({JsonViews.Public.class, JsonViews.OnlyForUser.class, JsonViews.LimitedPublic.class})
    private Double totalDebt;
    @JsonIgnore
    @Column
    private String role;

    @JsonIgnore
    @Column
    private String email;

    @JsonView({JsonViews.OnlyForUser.class})
    @OneToMany(mappedBy = "user")
    private List<Bill> bills;


    @ManyToMany
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    @JoinTable(
            name = "accounts_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<Account> accounts;

    public User() {
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Double getTotalDebt() {
        double res = 0;
        for(int i = 0; i<bills.size(); i++){
            res+=bills.get(i).getDebt();
        }
        return res;
    }

    public void setTotalDebt(Double totalDebt) {
        this.totalDebt = totalDebt;
    }

    public User(String phone, String name, String surname){
        //this.totalDebt = getTotalDebt();
        this.userId = null;
        this.name = name;
        this.phone = phone;
        this.role = "USER";
        this.surname = surname;
        this.code = CodeGenerator.getNewCode();
        this.imgLink = "";
    }

    public User(String email) {
        this.role = "USER";
        this.code = CodeGenerator.getNewCode();
        this.email = email;
    }

//    public Double getTotalDebt(){
//        double totalDebt = 0;
//
//        for(int i = 0; i<bills.size(); i++){
//            totalDebt+=bills.get(i).getDebt();
//        }
//        return totalDebt;
//    }


    public List<Bill> getBills() {
        return bills;
    }

    public List<Bill> getBills(Long accountId) {
        return bills.stream().filter(bill -> bill.getAccount().getAccountId() == accountId).collect(Collectors.toList());
    }
    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public void setTotalDebt(Double totalDebt) {
//        this.totalDebt = totalDebt;
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
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
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


//    public List<Bill> getBills() {
//        return bills;
//    }
//
//    public void setBills(List<Bill> bills) {
//        this.bills = bills;
//    }
}

package org.dsr.practice.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import org.dsr.practice.utils.JsonViews;
import org.dsr.practice.utils.generators.CodeGenerator;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table( name = "users" )
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId", scope = JsonViews.OnlyForUser.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonView({JsonViews.BasicDetails.class})
    private Long userId;

    @Column
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.BasicDetails.class})
    private String imgLink;

    @ManyToMany
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class})
    @JoinTable(
            name = "account_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<Account> accounts;

    @JsonIgnore
    @Column
    private String phone;
    @JsonIgnore
    @Column
    private String code;
    @Column
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class, JsonViews.BasicDetails.class})
    private String name;
    @Column
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class, JsonViews.BasicDetails.class})
    private String surname;

    @JsonIgnore
    @Column
    private String role;

    @JsonIgnore
    @Column
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<BillInfo> billInfos;



    public User() {
    }


    public User(String phone, String name, String surname){
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


    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Account getAccount(Long accountId){
        try {
            return accounts.stream().filter(account -> accountId == account.getAccountId()).collect(Collectors.toList()).get(0);
        }
        catch (Exception e){
            return null;
        }
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

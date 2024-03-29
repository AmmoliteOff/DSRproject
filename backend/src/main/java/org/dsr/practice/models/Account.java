package org.dsr.practice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.dsr.practice.utils.JsonViews;
import org.dsr.practice.utils.UserJsonSerializer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonView({JsonViews.BasicDetails.class})
    private Long accountId;

    @OneToMany(mappedBy = "account")
    @JsonView({JsonViews.BasicDetails.class})
    private List<Bill> bills;

    @Column
    @JsonView({JsonViews.BasicDetails.class})
    private String title;

    @Column
    @JsonView({JsonViews.BasicDetails.class})
    private String description;

    @ManyToMany(mappedBy = "accounts")
    @JsonView({JsonViews.BasicDetails.class})
    private List<User> users;

    public Account(String title, String description, List<User> users) {
        this.accountId = null;
        this.title = title;
        this.users = users;
        this.description = description;
    }

    public Account() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public Bill getBill(Long billId){
        try {
            return bills.stream().filter(bill -> billId == bill.getBillId()).collect(Collectors.toList()).get(0);
        }
        catch (Exception e){
            return null;
        }
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}

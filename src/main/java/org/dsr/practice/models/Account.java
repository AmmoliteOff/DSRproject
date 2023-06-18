package org.dsr.practice.models;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.dsr.practice.utils.JsonViews;

import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId", nullable = false, unique = true)
    @JsonView({JsonViews.Public.class,JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private Long accountId;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToMany(mappedBy = "accounts")
    private List<User> users;

    @OneToMany(mappedBy = "account")
    private List<Bill> bills;

    public Account() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Account(String title, String description, List<User> users) {
        this.title = title;
        this.description = description;
        this.users = users;
    }
}

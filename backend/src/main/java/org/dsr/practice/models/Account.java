package org.dsr.practice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.dsr.practice.utils.JsonViews;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private Long accountId;

    @OneToMany(mappedBy = "account")
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private List<Bill> bills;

    @Column
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private String title;

    @Column
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private String description;

    @ManyToMany(mappedBy = "accounts")
    @JsonView({JsonViews.Public.class,JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private Set<User> users;

    public Account(String title, String description, Set<User> users) {
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

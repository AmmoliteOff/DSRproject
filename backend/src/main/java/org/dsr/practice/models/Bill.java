package org.dsr.practice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.dsr.practice.utils.JsonViews;

import java.util.List;

@Entity
@Table(name="bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billId", nullable = false, unique = true)
    @JsonView({JsonViews.Public.class,JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private Long billId;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private double debt;

    @Column
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private String title;

    @Column
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private String description;

    public Bill() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bill(Account account, User user, double debt, String title, String description) {
        this.account = account;
        this.user = user;
        this.debt = debt;
        this.title = title;
        this.description = description;
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

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

//    public User getUser() {
//        return user;
//    }

//    public void setUser(User user) {
//        this.user = user;
//    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }
}

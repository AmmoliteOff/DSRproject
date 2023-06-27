package org.dsr.practice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.dsr.practice.utils.JsonViews;

import java.util.List;

@Entity
public class BillInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private Long billInfoId;

    @ManyToOne
    @JsonIgnore
    private Bill bill;

    @ManyToOne
    @JsonIgnore
    private User user;

    @Column
    @JsonView({JsonViews.Public.class, JsonViews.LimitedPublic.class, JsonViews.OnlyForUser.class})
    private int debt;

    public BillInfo() {
    }

    public BillInfo(Bill bill, User user, Long debt) {
        this.bill = bill;
        this.user = user;
        this.debt = (int) (debt*1000); //значимы только 3 знака после запятой
    }

    public Long getBillInfoId() {
        return billInfoId;
    }

    public void setBillInfoId(Long billInfoId) {
        this.billInfoId = billInfoId;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }
}

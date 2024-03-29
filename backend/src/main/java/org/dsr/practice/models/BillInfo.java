package org.dsr.practice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.dsr.practice.utils.JsonViews;
import org.dsr.practice.utils.UserJsonSerializer;

import java.util.List;

@Entity
public class BillInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonView({JsonViews.BasicDetails.class})
    private Long billInfoId;

    @ManyToOne
    @JsonIgnore
    private Bill bill;

    @ManyToOne
//    @JsonSerialize(using = UserJsonSerializer.class)
    @JsonView({JsonViews.BasicDetails.class})
    private User user;

    @Column
    @JsonView({JsonViews.BasicDetails.class})
    private Long debt;

    public BillInfo() {
    }

    public BillInfo(Bill bill, User user, Long debt) {
        this.bill = bill;
        this.user = user;
        this.debt = debt;
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

    public Double getDebt() { //SHOW USE ONLY, DON'T USE IN MATH OPERATIONS
        return debt/100.0;
    }

    public Long getRawDebt(){
        return debt;
    }

    public void setDebt(Long debt) {
        this.debt = debt;
    }
}

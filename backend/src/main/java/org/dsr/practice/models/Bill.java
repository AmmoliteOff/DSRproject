package org.dsr.practice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import org.dsr.practice.utils.JsonViews;

import java.util.List;

@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long billId;

    @ManyToOne
    @JsonIgnore
    private Account account;

    @Column
    @JsonView({JsonViews.BasicDetails.class})
    private String title;

    @Column
    @JsonView(JsonViews.BasicDetails.class)
    private String description;

    @Column
    @JsonView({JsonViews.BasicDetails.class})
    private Long fullPrice;

    @OneToMany(mappedBy = "bill")
    @JsonView(JsonViews.ExtendedDetails.class)
    private List<BillInfo> billInfo;

    public Bill() {
    }

    public Bill(Account account, String title, String description, List<BillInfo> billInfo, Long fullPrice) {
        this.account = account;
        this.title = title;
        this.description = description;
        this.billInfo = billInfo;
        this.fullPrice = fullPrice;
    }

    public Bill(Account account, String title, String description, Long fullPrice) {
        this.account = account;
        this.title = title;
        this.description = description;
        this.fullPrice = fullPrice;
    }

    public Long getFullPrice() {
        return fullPrice/100;
    }

    public void setFullPrice(Long fullPrice) {
        this.fullPrice = fullPrice;
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

    public List<BillInfo> getBillInfo() {
        return billInfo;
    }

    public void setBillInfo(List<BillInfo> billInfo) {
        this.billInfo = billInfo;
    }
}

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
    @JsonView({JsonViews.BasicDetails.class})
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

    @Column
    @JsonView({JsonViews.BasicDetails.class})
    private Long owed;
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

    public Double getOwed() {
        var res = 0.0;
        for(int i = 0; i<billInfo.size(); i++){
            res+=billInfo.get(i).getDebt();
        }
        return res;
    }

    public void setOwed(Long owed) {
        var res = 0L;
        for(int i = 0; i<billInfo.size(); i++){
            res+=billInfo.get(i).getRawDebt();
        }
        this.owed = res;
    }

    public Double getFullPrice() {
        return fullPrice/100.0;
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

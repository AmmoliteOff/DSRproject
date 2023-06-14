package org.dsr.practice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="debts")
public class Debt {
    public Debt() {
    }

    public Debt(Long debtId, double total, String status, User user, Long billId) {
        this.debtId = debtId;
        this.total = total;
        this.status = status;
        this.user = user;
        this.billId = billId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debtId", nullable = false, unique = true)
    @JsonIgnore
    private Long debtId;


    @Column
    private double total;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @Column
    @JsonIgnore
    private Long billId;

    public Long getDebtId() {
        return debtId;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Long getBillId() {
        return billId;
    }
}

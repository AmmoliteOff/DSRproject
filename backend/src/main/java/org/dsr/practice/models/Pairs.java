package org.dsr.practice.models;

import java.math.BigDecimal;

public class Pairs {

    private Long userId;
    private BigDecimal debt;

    public Pairs() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }
}

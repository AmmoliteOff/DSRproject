package org.dsr.practice.services.billspliter;

import org.dsr.practice.models.Pairs;
import org.dsr.practice.utils.PriceConverter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BillSplitData {
    private String title;
    private String description;
    private Long fullPrice;
    private Long accountId;
    private Map<Long, Long> usersMap;

    private int type;

    public BillSplitData() {
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public Long getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice.longValue();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Map<Long, Long> getUsersMap() {
        return usersMap;
    }

    public void setUsersMap(List<Pairs> usersMap) {
        this.usersMap = PriceConverter.convert(usersMap);
    }
}

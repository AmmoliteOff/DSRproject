package org.dsr.practice.services.billspliter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BillSpliterProvider {

    public static final int AUTO = 0;
    public static final int PERCENT = 1;
    public static final int MANUAL = 2;

    private PercentsBillSplitter percentsBillSplitter;
    private AutoBillSplitter autoBillSplitter;
    private ManualBillSplitter manualBillSplitter;

    public BillSpliterProvider(@Autowired PercentsBillSplitter percentsBillSplitter, @Autowired AutoBillSplitter autoBillSplitter, @Autowired ManualBillSplitter manualBillSplitter) {
        this.percentsBillSplitter = percentsBillSplitter;
        this.autoBillSplitter = autoBillSplitter;
        this.manualBillSplitter = manualBillSplitter;
    }

    public BillSplitter getBillSpliter(int type){
        switch (type){
            case AUTO->{
                return autoBillSplitter;
            }
            case PERCENT -> {
                return percentsBillSplitter;
            }
            case MANUAL -> {
                return manualBillSplitter;
            }
        }
        return null;
    }
}

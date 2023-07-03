package org.dsr.practice.services.billspliter;

import org.dsr.practice.models.BillSplitData;

public interface BillSplitter {
    boolean createBill(BillSplitData billSplitData);
}

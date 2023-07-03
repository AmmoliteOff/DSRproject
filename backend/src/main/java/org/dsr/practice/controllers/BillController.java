package org.dsr.practice.controllers;

import org.dsr.practice.models.BillSplitData;
import org.dsr.practice.services.billspliter.*;
import org.dsr.practice.services.BillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillController {


    private BillSpliterProvider billSpliterProvider;
    private BillsService billsService;
    public BillController(@Autowired BillSpliterProvider billSpliterProvider, @Autowired BillsService billsService)
    {
        this.billsService = billsService;
        this.billSpliterProvider = billSpliterProvider;
    }

    @GetMapping("/api/bill")
    public ResponseEntity getBill(@RequestParam(name = "billId") Long billId, @RequestParam(name = "accountId") Long accountId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var b = billsService.getBill(auth.getPrincipal().toString(), accountId, billId);
        if(b!=null) {
            return new ResponseEntity(b, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/createBill")
    public ResponseEntity createBill(@RequestBody BillSplitData billSplitData) {
        BillSplitter billSplitter = billSpliterProvider.getBillSpliter(billSplitData.getType());
        if(billSplitter !=null)
            billSplitter.createBill(billSplitData);
        return new ResponseEntity(HttpStatus.OK);
    }
}
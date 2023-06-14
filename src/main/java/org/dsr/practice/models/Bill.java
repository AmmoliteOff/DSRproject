package org.dsr.practice.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billId", nullable = false, unique = true)
    private Long billId;

    //@Column
    //@OneToMany(mappedBy = "billId")
    //private List<Debt> debts;
}

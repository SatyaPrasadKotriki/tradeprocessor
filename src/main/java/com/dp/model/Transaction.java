package com.dp.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Transaction {

    @Id
    @Column
    private Integer transactionId;
    @Column
    private String fromAddress;
    @Column
    private String toAddress;
    @Column
    private String note;
    @Column
    private Double amount;
    @Column
    private String userId;
    @Column
    private String status;
}



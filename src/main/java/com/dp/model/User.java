package com.dp.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class User {
    @Id
    @Column
    private String userId;
    @Column
    private String fromAddress;
}

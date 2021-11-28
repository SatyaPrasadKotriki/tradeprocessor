package com.dp.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table
public class Trade {
    @Id
    @Column
    private String tradeId;
    @Column
    private int version;
    @Column
    private String counterPartyId;
    @Column
    private String bookId;
    @Column
    private LocalDate maturityDate;
    @Column
    private LocalDateTime createdTime;
    @Column
    private String isExpired;

    public Trade() {
    }

    public Trade(String tradeId, int version, String counterPartyId, String bookId, LocalDate maturityDate, LocalDateTime createdTime, String isExpired) {
        this.tradeId = tradeId;
        this.version = version;
        this.counterPartyId = counterPartyId;
        this.bookId = bookId;
        this.maturityDate = maturityDate;
        this.createdTime = createdTime;
        this.isExpired = isExpired;
    }
}

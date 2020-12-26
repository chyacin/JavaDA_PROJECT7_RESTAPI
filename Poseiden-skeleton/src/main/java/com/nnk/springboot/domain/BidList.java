package com.nnk.springboot.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "bid_list_id")
    Integer BidListId;

    @Column(name = "account")
    @Size(max = 30)
    @NotBlank(message = "Account is mandatory")
    String account;

    @Column(name = "type")
    @Size(max = 30)
    @NotBlank(message = "Type is mandatory")
    String type;

    @PositiveOrZero
    @NotNull
    @Column(name = "bid_quantity")
    Double bidQuantity;

    @PositiveOrZero
    @NotNull
    @Column(name = "ask_quantity")
    Double askQuantity;

    @PositiveOrZero
    @NotNull
    @Column(name = "bid")
    Double bid;

    @PositiveOrZero
    @NotNull
    @Column(name = "ask")
    Double ask;

    @Column(name = "benchmark")
    @Size(max = 125)
    String benchmark;

    @CreationTimestamp
    @DateTimeFormat(pattern="yyyy.MM.dd")
    @Column(name = "bid_list_date")
    Timestamp bidListDate;

    @Column(name = "commentary")
    @Size(max = 125)
    String commentary;

    @Column(name = "security")
    @Size(max = 125)
    String security;

    @Column(name = "status")
    @Size(max = 10)
    String status;

    @Column(name = "trader")
    @Size(max = 125)
    String trader;

    @Column(name = "book")
    @Size(max = 125)
    String book;

    @Column(name = "creation_name")
    @Size(max = 125)
    String creationName;

    @CreationTimestamp
    @DateTimeFormat(pattern="yyyy.MM.dd")
    @Column(name = "creation_date")
    Timestamp creationDate;

    @Column(name = "revision_name")
    @Size(max = 125)
    String revisionName;

    @CreationTimestamp
    @DateTimeFormat(pattern="yyyy.MM.dd")
    @Column(name = "revision_date")
    Timestamp revisionDate;

    @Column(name = "deal_name")
    @Size(max = 125)
    String dealName;

    @Column(name = "deal_type")
    @Size(max = 125)
    String dealType;

    @Column(name = "source_list_id")
    @Size(max = 125)
    String sourceListId;

    @Column(name = "side")
    @Size(max = 125)
    String side;

    public BidList(String account, String type, double bidQuantity) {
    }


    public Integer getBidListId() {
        return BidListId;
    }

    public void setBidListId(Integer bidListId) {
        BidListId = bidListId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public Double getAskQuantity() {
        return askQuantity;
    }

    public void setAskQuantity(Double askQuantity) {
        this.askQuantity = askQuantity;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getBidListDate() {
        return bidListDate;
    }

    public void setBidListDate(Timestamp bidListDate) {
        this.bidListDate = bidListDate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getRevisionName() {
        return revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Timestamp revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSourceListId() {
        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}

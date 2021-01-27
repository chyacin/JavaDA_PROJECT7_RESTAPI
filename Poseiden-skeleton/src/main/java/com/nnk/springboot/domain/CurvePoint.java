package com.nnk.springboot.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    Integer id;

    @NotNull(message = "Curve Id must not be null")
    @PositiveOrZero
    @Column(name = "curveId")
    Integer curveId;

    @CreationTimestamp
    @Column(name = "asOfDate")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate asOfDate;

    @Column(name = "term")
    Double term;

    @Column(name = "value")
    Double value;

    @CreationTimestamp
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "creationDate")
    LocalDate creationDate;

    public CurvePoint() {
    }

    public CurvePoint(int curveId, double term, double value) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public  LocalDate getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate( LocalDate asOfDate) {
        this.asOfDate = asOfDate;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}

package com.nnk.springboot.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    Integer id;

    @NotBlank(message = "Curve Id must not be null")
    @NotNull
    @PositiveOrZero
    @Column(name = "curve_id")
    Integer curveId;

    @CreationTimestamp
    @FutureOrPresent
    @Column(name = "as_of_date")
    @DateTimeFormat(pattern="yyyy.MM.dd")
    Timestamp asOfDate;

    @NotNull
    @Column(name = "term")
    Double term;

    @NotNull
    @Column(name = "value")
    Double value;

    @CreationTimestamp
    @DateTimeFormat(pattern="yyyy.MM.dd")
    @Column(name = "creation_date")
    Timestamp creationDate;

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

    public Timestamp getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}

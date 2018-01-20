package com.sdyc.dto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by yangxun on 2018-01-21.
 */
@Entity
@Table(name = "acc_user_btc", schema = "", catalog = "btcfactory")
public class AccUserBtcDTO {
    private String userId;
    private BigDecimal investBtc;
    private BigDecimal initBtc;
    private BigDecimal currBtc;
    private Double addRatio;
    private Timestamp updateDate;

    @Id
    @Column(name = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "investBtc")
    public BigDecimal getInvestBtc() {
        return investBtc;
    }

    public void setInvestBtc(BigDecimal investBtc) {
        this.investBtc = investBtc;
    }

    @Basic
    @Column(name = "initBtc")
    public BigDecimal getInitBtc() {
        return initBtc;
    }

    public void setInitBtc(BigDecimal initBtc) {
        this.initBtc = initBtc;
    }

    @Basic
    @Column(name = "currBtc")
    public BigDecimal getCurrBtc() {
        return currBtc;
    }

    public void setCurrBtc(BigDecimal currBtc) {
        this.currBtc = currBtc;
    }

    @Basic
    @Column(name = "addRatio")
    public Double getAddRatio() {
        return addRatio;
    }

    public void setAddRatio(Double addRatio) {
        this.addRatio = addRatio;
    }

    @Basic
    @Column(name = "updateDate")
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccUserBtcDTO that = (AccUserBtcDTO) o;

        if (addRatio != null ? !addRatio.equals(that.addRatio) : that.addRatio != null) return false;
        if (currBtc != null ? !currBtc.equals(that.currBtc) : that.currBtc != null) return false;
        if (initBtc != null ? !initBtc.equals(that.initBtc) : that.initBtc != null) return false;
        if (investBtc != null ? !investBtc.equals(that.investBtc) : that.investBtc != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (investBtc != null ? investBtc.hashCode() : 0);
        result = 31 * result + (initBtc != null ? initBtc.hashCode() : 0);
        result = 31 * result + (currBtc != null ? currBtc.hashCode() : 0);
        result = 31 * result + (addRatio != null ? addRatio.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}

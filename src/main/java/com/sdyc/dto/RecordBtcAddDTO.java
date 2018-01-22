package com.sdyc.dto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by yangxun on 2018-01-22.
 */
@Entity
@Table(name = "record_btc_add", schema = "", catalog = "btcfactory")
public class RecordBtcAddDTO {
    private long seqkey;
    private String userId;
    private BigDecimal investBtc;
    private BigDecimal initBtc;
    private BigDecimal currBtc;
    private BigDecimal addRatio;
    private Timestamp time;

    public RecordBtcAddDTO(){

    }

    public RecordBtcAddDTO(AccUserBtcDTO accUserBtcDTO){
        this.setInitBtc(accUserBtcDTO.getInitBtc());
        this.setInvestBtc(accUserBtcDTO.getInvestBtc());
        this.setCurrBtc(accUserBtcDTO.getCurrBtc());
        this.setAddRatio(new BigDecimal(accUserBtcDTO.getAddRatio()) );
        this.setUserId(accUserBtcDTO.getUserId());
    }

    @Id
    @Column(name = "seqkey")
    public long getSeqkey() {
        return seqkey;
    }

    public void setSeqkey(long seqkey) {
        this.seqkey = seqkey;
    }

    @Basic
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
    public BigDecimal getAddRatio() {
        return addRatio;
    }

    public void setAddRatio(BigDecimal addRatio) {
        this.addRatio = addRatio;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecordBtcAddDTO that = (RecordBtcAddDTO) o;

        if (seqkey != that.seqkey) return false;
        if (addRatio != null ? !addRatio.equals(that.addRatio) : that.addRatio != null) return false;
        if (currBtc != null ? !currBtc.equals(that.currBtc) : that.currBtc != null) return false;
        if (initBtc != null ? !initBtc.equals(that.initBtc) : that.initBtc != null) return false;
        if (investBtc != null ? !investBtc.equals(that.investBtc) : that.investBtc != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (seqkey ^ (seqkey >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (investBtc != null ? investBtc.hashCode() : 0);
        result = 31 * result + (initBtc != null ? initBtc.hashCode() : 0);
        result = 31 * result + (currBtc != null ? currBtc.hashCode() : 0);
        result = 31 * result + (addRatio != null ? addRatio.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}

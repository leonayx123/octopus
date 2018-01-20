package com.sdyc.dto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/20  17:30
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/20  17:30
 * Author:
 * </pre>
 */

@Entity
@Table(name = "t_user_btc", schema = "", catalog = "btcfactory")
public class TUserBtcDTO {
    private String userId;
    private BigDecimal investBtc;
    private BigDecimal initBtc;
    private BigDecimal currBtc;
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

        TUserBtcDTO that = (TUserBtcDTO) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (investBtc != null ? !investBtc.equals(that.investBtc) : that.investBtc != null) return false;
        if (initBtc != null ? !initBtc.equals(that.initBtc) : that.initBtc != null) return false;
        if (currBtc != null ? !currBtc.equals(that.currBtc) : that.currBtc != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (investBtc != null ? investBtc.hashCode() : 0);
        result = 31 * result + (initBtc != null ? initBtc.hashCode() : 0);
        result = 31 * result + (currBtc != null ? currBtc.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}

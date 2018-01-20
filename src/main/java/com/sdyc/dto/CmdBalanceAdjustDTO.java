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
@Table(name = "cmd_balance_adjust", schema = "", catalog = "btcfactory")
public class CmdBalanceAdjustDTO {
    private long seqkey;
    private String userId;
    private String exchangeId;
    private String coinId;
    private BigDecimal amtChange;
    private String status;
    private Timestamp adjustSetTime;
    private Timestamp adjustTime;

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
    @Column(name = "exchangeId")
    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    @Basic
    @Column(name = "coinId")
    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    @Basic
    @Column(name = "amtChange")
    public BigDecimal getAmtChange() {
        return amtChange;
    }

    public void setAmtChange(BigDecimal amtChange) {
        this.amtChange = amtChange;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "adjust_set_time")
    public Timestamp getAdjustSetTime() {
        return adjustSetTime;
    }

    public void setAdjustSetTime(Timestamp adjustSetTime) {
        this.adjustSetTime = adjustSetTime;
    }

    @Basic
    @Column(name = "adjust_time")
    public Timestamp getAdjustTime() {
        return adjustTime;
    }

    public void setAdjustTime(Timestamp adjustTime) {
        this.adjustTime = adjustTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmdBalanceAdjustDTO that = (CmdBalanceAdjustDTO) o;

        if (seqkey != that.seqkey) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (exchangeId != null ? !exchangeId.equals(that.exchangeId) : that.exchangeId != null) return false;
        if (coinId != null ? !coinId.equals(that.coinId) : that.coinId != null) return false;
        if (amtChange != null ? !amtChange.equals(that.amtChange) : that.amtChange != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (adjustSetTime != null ? !adjustSetTime.equals(that.adjustSetTime) : that.adjustSetTime != null)
            return false;
        if (adjustTime != null ? !adjustTime.equals(that.adjustTime) : that.adjustTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (seqkey ^ (seqkey >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (exchangeId != null ? exchangeId.hashCode() : 0);
        result = 31 * result + (coinId != null ? coinId.hashCode() : 0);
        result = 31 * result + (amtChange != null ? amtChange.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (adjustSetTime != null ? adjustSetTime.hashCode() : 0);
        result = 31 * result + (adjustTime != null ? adjustTime.hashCode() : 0);
        return result;
    }
}

package com.sdyc.dto;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by yangxun on 2018-01-22.
 */
@Entity
@Table(name = "record_wallet_spot", schema = "", catalog = "btcfactory")
public class RecordWalletSpotDTO {
    private long seqkey;
    private String userId;
    private String exchangeId;
    private String coinInfo;
    private Timestamp groupDate;
    private Timestamp updateDate;

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
    @Column(name = "coinInfo")
    public String getCoinInfo() {
        return coinInfo;
    }

    public void setCoinInfo(String coinInfo) {
        this.coinInfo = coinInfo;
    }

    @Basic
    @Column(name = "groupDate")
    public Timestamp getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(Timestamp groupDate) {
        this.groupDate = groupDate;
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

        RecordWalletSpotDTO that = (RecordWalletSpotDTO) o;

        if (seqkey != that.seqkey) return false;
        if (coinInfo != null ? !coinInfo.equals(that.coinInfo) : that.coinInfo != null) return false;
        if (exchangeId != null ? !exchangeId.equals(that.exchangeId) : that.exchangeId != null) return false;
        if (groupDate != null ? !groupDate.equals(that.groupDate) : that.groupDate != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (seqkey ^ (seqkey >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (exchangeId != null ? exchangeId.hashCode() : 0);
        result = 31 * result + (coinInfo != null ? coinInfo.hashCode() : 0);
        result = 31 * result + (groupDate != null ? groupDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }
}

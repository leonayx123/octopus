package com.sdyc.dto;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by yangxun on 2018-01-22.
 */
@Entity
@javax.persistence.Table(name = "record_trade_turnover", schema = "", catalog = "btcfactory")
public class RecordTradeTurnoverDTO {
    private long seqkey;

    @Id
    @javax.persistence.Column(name = "seqkey")
    public long getSeqkey() {
        return seqkey;
    }

    public void setSeqkey(long seqkey) {
        this.seqkey = seqkey;
    }

    private String userId;

    @Basic
    @javax.persistence.Column(name = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String exchangeId;

    @Basic
    @javax.persistence.Column(name = "exchangeId")
    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    private String coinId;

    @Basic
    @javax.persistence.Column(name = "coinId")
    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    private Byte status;

    @Basic
    @javax.persistence.Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    private String higherEx;

    @Basic
    @javax.persistence.Column(name = "higherEx")
    public String getHigherEx() {
        return higherEx;
    }

    public void setHigherEx(String higherEx) {
        this.higherEx = higherEx;
    }

    private Double higherBidVal1;

    @Basic
    @javax.persistence.Column(name = "higherBidVal1")
    public Double getHigherBidVal1() {
        return higherBidVal1;
    }

    public void setHigherBidVal1(Double higherBidVal1) {
        this.higherBidVal1 = higherBidVal1;
    }

    private Double higherBidVal2;

    @Basic
    @javax.persistence.Column(name = "higherBidVal2")
    public Double getHigherBidVal2() {
        return higherBidVal2;
    }

    public void setHigherBidVal2(Double higherBidVal2) {
        this.higherBidVal2 = higherBidVal2;
    }

    private Double higherBidVal3;

    @Basic
    @javax.persistence.Column(name = "higherBidVal3")
    public Double getHigherBidVal3() {
        return higherBidVal3;
    }

    public void setHigherBidVal3(Double higherBidVal3) {
        this.higherBidVal3 = higherBidVal3;
    }

    private String lowerEx;

    @Basic
    @javax.persistence.Column(name = "lowerEx")
    public String getLowerEx() {
        return lowerEx;
    }

    public void setLowerEx(String lowerEx) {
        this.lowerEx = lowerEx;
    }

    private Double lowerAskVal1;

    @Basic
    @javax.persistence.Column(name = "lowerAskVal1")
    public Double getLowerAskVal1() {
        return lowerAskVal1;
    }

    public void setLowerAskVal1(Double lowerAskVal1) {
        this.lowerAskVal1 = lowerAskVal1;
    }

    private Double lowerAskVal2;

    @Basic
    @javax.persistence.Column(name = "lowerAskVal2")
    public Double getLowerAskVal2() {
        return lowerAskVal2;
    }

    public void setLowerAskVal2(Double lowerAskVal2) {
        this.lowerAskVal2 = lowerAskVal2;
    }

    private Double lowerAskVal3;

    @Basic
    @javax.persistence.Column(name = "lowerAskVal3")
    public Double getLowerAskVal3() {
        return lowerAskVal3;
    }

    public void setLowerAskVal3(Double lowerAskVal3) {
        this.lowerAskVal3 = lowerAskVal3;
    }

    private Double priceDiff;

    @Basic
    @javax.persistence.Column(name = "priceDiff")
    public Double getPriceDiff() {
        return priceDiff;
    }

    public void setPriceDiff(Double priceDiff) {
        this.priceDiff = priceDiff;
    }

    private Double minTradbleQtty;

    @Basic
    @javax.persistence.Column(name = "minTradbleQtty")
    public Double getMinTradbleQtty() {
        return minTradbleQtty;
    }

    public void setMinTradbleQtty(Double minTradbleQtty) {
        this.minTradbleQtty = minTradbleQtty;
    }

    private Double tradeValueBuy;

    @Basic
    @javax.persistence.Column(name = "tradeValueBuy")
    public Double getTradeValueBuy() {
        return tradeValueBuy;
    }

    public void setTradeValueBuy(Double tradeValueBuy) {
        this.tradeValueBuy = tradeValueBuy;
    }

    private Double tradeValueSell;

    @Basic
    @javax.persistence.Column(name = "tradeValueSell")
    public Double getTradeValueSell() {
        return tradeValueSell;
    }

    public void setTradeValueSell(Double tradeValueSell) {
        this.tradeValueSell = tradeValueSell;
    }

    private Double tradeValueMargin;

    @Basic
    @javax.persistence.Column(name = "tradeValueMargin")
    public Double getTradeValueMargin() {
        return tradeValueMargin;
    }

    public void setTradeValueMargin(Double tradeValueMargin) {
        this.tradeValueMargin = tradeValueMargin;
    }

    private Double tradeValueMarginPct;

    @Basic
    @javax.persistence.Column(name = "tradeValueMarginPct")
    public Double getTradeValueMarginPct() {
        return tradeValueMarginPct;
    }

    public void setTradeValueMarginPct(Double tradeValueMarginPct) {
        this.tradeValueMarginPct = tradeValueMarginPct;
    }

    private Timestamp time;

    @Basic
    @javax.persistence.Column(name = "time")
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

        RecordTradeTurnoverDTO that = (RecordTradeTurnoverDTO) o;

        if (seqkey != that.seqkey) return false;
        if (coinId != null ? !coinId.equals(that.coinId) : that.coinId != null) return false;
        if (exchangeId != null ? !exchangeId.equals(that.exchangeId) : that.exchangeId != null) return false;
        if (higherBidVal1 != null ? !higherBidVal1.equals(that.higherBidVal1) : that.higherBidVal1 != null)
            return false;
        if (higherBidVal2 != null ? !higherBidVal2.equals(that.higherBidVal2) : that.higherBidVal2 != null)
            return false;
        if (higherBidVal3 != null ? !higherBidVal3.equals(that.higherBidVal3) : that.higherBidVal3 != null)
            return false;
        if (higherEx != null ? !higherEx.equals(that.higherEx) : that.higherEx != null) return false;
        if (lowerAskVal1 != null ? !lowerAskVal1.equals(that.lowerAskVal1) : that.lowerAskVal1 != null) return false;
        if (lowerAskVal2 != null ? !lowerAskVal2.equals(that.lowerAskVal2) : that.lowerAskVal2 != null) return false;
        if (lowerAskVal3 != null ? !lowerAskVal3.equals(that.lowerAskVal3) : that.lowerAskVal3 != null) return false;
        if (lowerEx != null ? !lowerEx.equals(that.lowerEx) : that.lowerEx != null) return false;
        if (minTradbleQtty != null ? !minTradbleQtty.equals(that.minTradbleQtty) : that.minTradbleQtty != null)
            return false;
        if (priceDiff != null ? !priceDiff.equals(that.priceDiff) : that.priceDiff != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (tradeValueBuy != null ? !tradeValueBuy.equals(that.tradeValueBuy) : that.tradeValueBuy != null)
            return false;
        if (tradeValueMargin != null ? !tradeValueMargin.equals(that.tradeValueMargin) : that.tradeValueMargin != null)
            return false;
        if (tradeValueMarginPct != null ? !tradeValueMarginPct.equals(that.tradeValueMarginPct) : that.tradeValueMarginPct != null)
            return false;
        if (tradeValueSell != null ? !tradeValueSell.equals(that.tradeValueSell) : that.tradeValueSell != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (seqkey ^ (seqkey >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (exchangeId != null ? exchangeId.hashCode() : 0);
        result = 31 * result + (coinId != null ? coinId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (higherEx != null ? higherEx.hashCode() : 0);
        result = 31 * result + (higherBidVal1 != null ? higherBidVal1.hashCode() : 0);
        result = 31 * result + (higherBidVal2 != null ? higherBidVal2.hashCode() : 0);
        result = 31 * result + (higherBidVal3 != null ? higherBidVal3.hashCode() : 0);
        result = 31 * result + (lowerEx != null ? lowerEx.hashCode() : 0);
        result = 31 * result + (lowerAskVal1 != null ? lowerAskVal1.hashCode() : 0);
        result = 31 * result + (lowerAskVal2 != null ? lowerAskVal2.hashCode() : 0);
        result = 31 * result + (lowerAskVal3 != null ? lowerAskVal3.hashCode() : 0);
        result = 31 * result + (priceDiff != null ? priceDiff.hashCode() : 0);
        result = 31 * result + (minTradbleQtty != null ? minTradbleQtty.hashCode() : 0);
        result = 31 * result + (tradeValueBuy != null ? tradeValueBuy.hashCode() : 0);
        result = 31 * result + (tradeValueSell != null ? tradeValueSell.hashCode() : 0);
        result = 31 * result + (tradeValueMargin != null ? tradeValueMargin.hashCode() : 0);
        result = 31 * result + (tradeValueMarginPct != null ? tradeValueMarginPct.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}

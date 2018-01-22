package com.sdyc.dto;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by yangxun on 2018-01-22.
 */
@Entity
@Table(name = "record_trade_turnover", schema = "", catalog = "btcfactory")
public class RecordTradeTurnoverDTO {
    private long seqkey;
    private String userId;
    private String exchangeId;
    private String coinId;
    private int status;
    private String higherEx;
    private Double higherBidVal1;
    private Double higherBidVal2;
    private Double higherBidVal3;
    private String lowerEx;
    private Double lowerAskVal1;
    private Double lowerAskVal2;
    private Double lowerAskVal3;
    private Double priceDiff;
    private Double minTradbleQtty;
    private Double tradeValueBuy;
    private Double tradeValueSell;
    private Double tradeValueMargin;
    private Double tradeValueMarginPct;
    private Timestamp time;
    private String msg;
    private Double highPrice;
    private Double lowerPrice;

    public void setStatus(Integer status) {
        this.status = status;
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
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "higherEx")
    public String getHigherEx() {
        return higherEx;
    }

    public void setHigherEx(String higherEx) {
        this.higherEx = higherEx;
    }

    @Basic
    @Column(name = "higherBidVal1")
    public Double getHigherBidVal1() {
        return higherBidVal1;
    }

    public void setHigherBidVal1(Double higherBidVal1) {
        this.higherBidVal1 = higherBidVal1;
    }

    @Basic
    @Column(name = "higherBidVal2")
    public Double getHigherBidVal2() {
        return higherBidVal2;
    }

    public void setHigherBidVal2(Double higherBidVal2) {
        this.higherBidVal2 = higherBidVal2;
    }

    @Basic
    @Column(name = "higherBidVal3")
    public Double getHigherBidVal3() {
        return higherBidVal3;
    }

    public void setHigherBidVal3(Double higherBidVal3) {
        this.higherBidVal3 = higherBidVal3;
    }

    @Basic
    @Column(name = "lowerEx")
    public String getLowerEx() {
        return lowerEx;
    }

    public void setLowerEx(String lowerEx) {
        this.lowerEx = lowerEx;
    }

    @Basic
    @Column(name = "lowerAskVal1")
    public Double getLowerAskVal1() {
        return lowerAskVal1;
    }

    public void setLowerAskVal1(Double lowerAskVal1) {
        this.lowerAskVal1 = lowerAskVal1;
    }

    @Basic
    @Column(name = "lowerAskVal2")
    public Double getLowerAskVal2() {
        return lowerAskVal2;
    }

    public void setLowerAskVal2(Double lowerAskVal2) {
        this.lowerAskVal2 = lowerAskVal2;
    }

    @Basic
    @Column(name = "lowerAskVal3")
    public Double getLowerAskVal3() {
        return lowerAskVal3;
    }

    public void setLowerAskVal3(Double lowerAskVal3) {
        this.lowerAskVal3 = lowerAskVal3;
    }

    @Basic
    @Column(name = "priceDiff")
    public Double getPriceDiff() {
        return priceDiff;
    }

    public void setPriceDiff(Double priceDiff) {
        this.priceDiff = priceDiff;
    }

    @Basic
    @Column(name = "minTradbleQtty")
    public Double getMinTradbleQtty() {
        return minTradbleQtty;
    }

    public void setMinTradbleQtty(Double minTradbleQtty) {
        this.minTradbleQtty = minTradbleQtty;
    }

    @Basic
    @Column(name = "tradeValueBuy")
    public Double getTradeValueBuy() {
        return tradeValueBuy;
    }

    public void setTradeValueBuy(Double tradeValueBuy) {
        this.tradeValueBuy = tradeValueBuy;
    }

    @Basic
    @Column(name = "tradeValueSell")
    public Double getTradeValueSell() {
        return tradeValueSell;
    }

    public void setTradeValueSell(Double tradeValueSell) {
        this.tradeValueSell = tradeValueSell;
    }

    @Basic
    @Column(name = "tradeValueMargin")
    public Double getTradeValueMargin() {
        return tradeValueMargin;
    }

    public void setTradeValueMargin(Double tradeValueMargin) {
        this.tradeValueMargin = tradeValueMargin;
    }

    @Basic
    @Column(name = "tradeValueMarginPct")
    public Double getTradeValueMarginPct() {
        return tradeValueMarginPct;
    }

    public void setTradeValueMarginPct(Double tradeValueMarginPct) {
        this.tradeValueMarginPct = tradeValueMarginPct;
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

        RecordTradeTurnoverDTO that = (RecordTradeTurnoverDTO) o;

        if (seqkey != that.seqkey) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (seqkey ^ (seqkey >>> 32));

        return result;
    }

    @Basic
    @Column(name = "msg")
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Basic
    @Column(name = "highPrice")
    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    @Basic
    @Column(name = "lowerPrice")
    public Double getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(Double lowerPrice) {
        this.lowerPrice = lowerPrice;
    }
}

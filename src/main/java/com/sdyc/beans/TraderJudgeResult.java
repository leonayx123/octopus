package com.sdyc.beans;

import java.util.Date;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/24  17:15
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:
 * Discription:
 *
 * 交易判断结果
 *
 * 这是核算中心用来判断是否进行交易的返回结果.
 * 返回核算结果.  满足核算结果的话 会根据里面提供的值进行交易
 *
 * Modify:      2018/1/24  17:15
 * Author:
 * </pre>
 */

public class TraderJudgeResult {
    private  int status; // 这是交易返回的状态码  由李总指定
    private  String userId; //执行交易的用户id
    private  String coinId; //执行交易的币名称
    private  String higherEx;  //高价位方
    private  String lowerEx;  //价位低方
    private  Double sellPrice; //卖价
    private  Double buyPrice; //买价
    private  Double quantity;  //数量
    private  Date  timestamp;  //返回时间戳


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getHigherEx() {
        return higherEx;
    }

    public void setHigherEx(String higherEx) {
        this.higherEx = higherEx;
    }

    public String getLowerEx() {
        return lowerEx;
    }

    public void setLowerEx(String lowerEx) {
        this.lowerEx = lowerEx;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

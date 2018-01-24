package com.sdyc.beans;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/24  17:34
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *              交易结果的bean
 * Modify:      2018/1/24  17:34
 * Author:
 * </pre>
 */

public class TradeResult {


    private  Boolean commonRes ; //总的结果状态

    private  String sellEx;

    private  String buyEx;

    private  String coinId;

    private  Boolean sellRes;  //卖的结果

    private  String sellOrderId; //卖的订单好

    private  Boolean buyRes;   //买的结果

    private  String bugOrderId; //买的订单号

    private  String error; //错误信息


    public Boolean getCommonRes() {
        return commonRes;
    }

    public void setCommonRes(Boolean commonRes) {
        this.commonRes = commonRes;
    }

    public String getSellEx() {
        return sellEx;
    }

    public void setSellEx(String sellEx) {
        this.sellEx = sellEx;
    }

    public String getBuyEx() {
        return buyEx;
    }

    public void setBuyEx(String buyEx) {
        this.buyEx = buyEx;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public Boolean getSellRes() {
        return sellRes;
    }

    public void setSellRes(Boolean sellRes) {
        this.sellRes = sellRes;
    }

    public String getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(String sellOrderId) {
        this.sellOrderId = sellOrderId;
    }

    public Boolean getBuyRes() {
        return buyRes;
    }

    public void setBuyRes(Boolean buyRes) {
        this.buyRes = buyRes;
    }

    public String getBugOrderId() {
        return bugOrderId;
    }

    public void setBugOrderId(String bugOrderId) {
        this.bugOrderId = bugOrderId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

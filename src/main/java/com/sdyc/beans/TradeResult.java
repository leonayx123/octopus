package com.sdyc.beans;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/24  17:34
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     ����ʶ���˳���Ϣ�Ƽ����޹�˾
 * Discription:
 *              ���׽����bean
 * Modify:      2018/1/24  17:34
 * Author:
 * </pre>
 */

public class TradeResult {


    private  Boolean commonRes ; //�ܵĽ��״̬

    private  String sellEx;

    private  String buyEx;

    private  String coinId;

    private  Boolean sellRes;  //���Ľ��

    private  String sellOrderId; //���Ķ�����

    private  Boolean buyRes;   //��Ľ��

    private  String bugOrderId; //��Ķ�����

    private  String error; //������Ϣ


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

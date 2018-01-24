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
 * �����жϽ��
 *
 * ���Ǻ������������ж��Ƿ���н��׵ķ��ؽ��.
 * ���غ�����.  ����������Ļ� ����������ṩ��ֵ���н���
 *
 * Modify:      2018/1/24  17:15
 * Author:
 * </pre>
 */

public class TraderJudgeResult {
    private  int status; // ���ǽ��׷��ص�״̬��  ������ָ��
    private  String userId; //ִ�н��׵��û�id
    private  String coinId; //ִ�н��׵ı�����
    private  String higherEx;  //�߼�λ��
    private  String lowerEx;  //��λ�ͷ�
    private  Double sellPrice; //����
    private  Double buyPrice; //���
    private  Double quantity;  //����
    private  Date  timestamp;  //����ʱ���


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

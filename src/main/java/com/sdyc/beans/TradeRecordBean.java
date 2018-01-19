package com.sdyc.beans;

import java.util.Date;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/18  18:09
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     ����ʶ���˳���Ϣ�Ƽ����޹�˾
 * Discription:
 *
 * Modify:      2018/1/18  18:09
 * Author:
 * </pre>
 */

public class TradeRecordBean {
    //���� id
    Long seqkey;

    //�ϸ�һ���Ľ�����
    String higherEx;

    //�ϵ�һ���Ľ�����
    String lowerEx;

    //�ҶԶ�
    String icoCouple;

    //������������״̬
    //no ʧ�� yes �ɹ�
    String status;

    //����״̬  no ʧ�� yes �ɹ�
    String sellStatus;

    //�����Ķ�����
    String sellOrder;


    //���״̬  no ʧ�� yes�ɹ�
    String buyStatus;

    //����Ķ�����
    String buyOrder;


    //����ʱ�� ����ͳ����
    Date groupTime;

    //���ʱ��
    Date createTime;


    public Long getSeqkey() {
        return seqkey;
    }

    public void setSeqkey(Long seqkey) {
        this.seqkey = seqkey;
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

    public String getIcoCouple() {
        return icoCouple;
    }

    public void setIcoCouple(String icoCouple) {
        this.icoCouple = icoCouple;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(String sellStatus) {
        this.sellStatus = sellStatus;
    }

    public String getSellOrder() {
        return sellOrder;
    }

    public void setSellOrder(String sellOrder) {
        this.sellOrder = sellOrder;
    }

    public String getBuyStatus() {
        return buyStatus;
    }

    public void setBuyStatus(String buyStatus) {
        this.buyStatus = buyStatus;
    }

    public String getBuyOrder() {
        return buyOrder;
    }

    public void setBuyOrder(String buyOrder) {
        this.buyOrder = buyOrder;
    }

    public Date getGroupTime() {
        return groupTime;
    }

    public void setGroupTime(Date groupTime) {
        this.groupTime = groupTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

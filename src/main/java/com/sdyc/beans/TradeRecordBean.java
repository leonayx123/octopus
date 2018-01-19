package com.sdyc.beans;

import java.util.Date;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/18  18:09
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/18  18:09
 * Author:
 * </pre>
 */

public class TradeRecordBean {
    //主键 id
    Long seqkey;

    //较高一方的交易所
    String higherEx;

    //较低一方的交易所
    String lowerEx;

    //币对儿
    String icoCouple;

    //本次完整交易状态
    //no 失败 yes 成功
    String status;

    //卖出状态  no 失败 yes 成功
    String sellStatus;

    //卖出的订单号
    String sellOrder;


    //买进状态  no 失败 yes成功
    String buyStatus;

    //买入的订单号
    String buyOrder;


    //分组时间 分组统计用
    Date groupTime;

    //入库时间
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

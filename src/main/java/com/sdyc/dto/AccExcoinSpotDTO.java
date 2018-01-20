package com.sdyc.dto;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/20  17:29
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/20  17:29
 * Author:
 * </pre>
 */

@Entity
@Table(name = "acc_excoin_spot", schema = "", catalog = "btcfactory")
public class AccExcoinSpotDTO {
    private long seqkey;
    private String userId;
    private String exchaneId;
    private Long batchNum;
    private Timestamp groupDate;
    private Timestamp createDate;
    private Timestamp updateDate;
    private String coinInfo;

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
    @Column(name = "exchaneId")
    public String getExchaneId() {
        return exchaneId;
    }

    public void setExchaneId(String exchaneId) {
        this.exchaneId = exchaneId;
    }

    @Basic
    @Column(name = "batchNum")
    public Long getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(Long batchNum) {
        this.batchNum = batchNum;
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
    @Column(name = "createDate")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "updateDate")
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "coinInfo")
    public String getCoinInfo() {
        return coinInfo;
    }

    public void setCoinInfo(String coinInfo) {
        this.coinInfo = coinInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccExcoinSpotDTO that = (AccExcoinSpotDTO) o;

        if (seqkey != that.seqkey) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (exchaneId != null ? !exchaneId.equals(that.exchaneId) : that.exchaneId != null) return false;
        if (batchNum != null ? !batchNum.equals(that.batchNum) : that.batchNum != null) return false;
        if (groupDate != null ? !groupDate.equals(that.groupDate) : that.groupDate != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (coinInfo != null ? !coinInfo.equals(that.coinInfo) : that.coinInfo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (seqkey ^ (seqkey >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (exchaneId != null ? exchaneId.hashCode() : 0);
        result = 31 * result + (batchNum != null ? batchNum.hashCode() : 0);
        result = 31 * result + (groupDate != null ? groupDate.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (coinInfo != null ? coinInfo.hashCode() : 0);
        return result;
    }
}

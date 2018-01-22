package com.sdyc.dto;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by yangxun on 2018-01-22.
 */
@Entity
@Table(name = "acc_user_exchange", schema = "", catalog = "btcfactory")
public class AccUserExchangeDTO {
    private String guid;
    private String userId;
    private String exchangeId;
    private Timestamp createDate;
    private Byte isActive;
    private String key;
    private String secert;

    @Id
    @Column(name = "guid")
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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
    @Column(name = "createDate")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "isActive")
    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    @Basic
    @Column(name = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "secert")
    public String getSecert() {
        return secert;
    }

    public void setSecert(String secert) {
        this.secert = secert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccUserExchangeDTO that = (AccUserExchangeDTO) o;

        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (exchangeId != null ? !exchangeId.equals(that.exchangeId) : that.exchangeId != null) return false;
        if (guid != null ? !guid.equals(that.guid) : that.guid != null) return false;
        if (isActive != null ? !isActive.equals(that.isActive) : that.isActive != null) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (secert != null ? !secert.equals(that.secert) : that.secert != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = guid != null ? guid.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (exchangeId != null ? exchangeId.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (secert != null ? secert.hashCode() : 0);
        return result;
    }
}

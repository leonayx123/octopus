package com.sdyc.dto;

import javax.persistence.*;

/**
 * Created by yangxun on 2018-01-22.
 */
@Entity
@Table(name = "acc_user_ex_seting", schema = "", catalog = "btcfactory")
public class AccUserExSetingDTO {
    private String userId;
    private String userCoins;
    private String userExchanges;
    private Byte isActive;
    private String keyStr;

    @Id
    @Column(name = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "userCoins")
    public String getUserCoins() {
        return userCoins;
    }

    public void setUserCoins(String userCoins) {
        this.userCoins = userCoins;
    }

    @Basic
    @Column(name = "userExchanges")
    public String getUserExchanges() {
        return userExchanges;
    }

    public void setUserExchanges(String userExchanges) {
        this.userExchanges = userExchanges;
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
    @Column(name = "keyStr")
    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccUserExSetingDTO that = (AccUserExSetingDTO) o;

        if (isActive != null ? !isActive.equals(that.isActive) : that.isActive != null) return false;
        if (keyStr != null ? !keyStr.equals(that.keyStr) : that.keyStr != null) return false;
        if (userCoins != null ? !userCoins.equals(that.userCoins) : that.userCoins != null) return false;
        if (userExchanges != null ? !userExchanges.equals(that.userExchanges) : that.userExchanges != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userCoins != null ? userCoins.hashCode() : 0);
        result = 31 * result + (userExchanges != null ? userExchanges.hashCode() : 0);
        result = 31 * result + (isActive != null ? isActive.hashCode() : 0);
        result = 31 * result + (keyStr != null ? keyStr.hashCode() : 0);
        return result;
    }
}

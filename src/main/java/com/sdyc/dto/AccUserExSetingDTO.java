package com.sdyc.dto;

/**
 * Created by yangxun on 2018-01-20.
 */
public class AccUserExSetingDTO {

    private String userId; //�û���id

    private String userCoins; //�û���ע�ı����ļ���

    private String userExchanges;//�û���ע�Ľ��������ļ���

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCoins() {
        return userCoins;
    }

    public void setUserCoins(String userCoins) {
        this.userCoins = userCoins;
    }

    public String getUserExchanges() {
        return userExchanges;
    }

    public void setUserExchanges(String userExchanges) {
        this.userExchanges = userExchanges;
    }
}

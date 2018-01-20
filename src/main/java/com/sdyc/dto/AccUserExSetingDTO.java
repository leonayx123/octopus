package com.sdyc.dto;

/**
 * Created by yangxun on 2018-01-20.
 */
public class AccUserExSetingDTO {

    private String userId; //用户的id

    private String userCoins; //用户关注的币是哪几个

    private String userExchanges;//用户关注的交易所是哪几个

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

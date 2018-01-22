package com.sdyc.beans;

/**
 * Created by yangxun on 2018-01-23.
 */
public class ExAccount {

    private String exchangeId;

    private String key;

    private String secret;

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public int hashCode() {
        return exchangeId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ExAccount ){

            return ( (ExAccount)o).getExchangeId().equals(this.exchangeId);
        }
        return false;
    }
}

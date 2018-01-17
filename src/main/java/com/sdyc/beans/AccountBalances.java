package com.sdyc.beans;

import java.util.Date;
import java.util.HashMap;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/15  20:26
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     sdyc
 * Discription:
 *
 * Modify:      2018/1/15  20:26
 * Author:
 * </pre>
 */

/**
 * user account info  in ico shop
 */
public class AccountBalances {
    // account free icos . store with map
    private HashMap<String,Double> free;

    // account locked icos . store with map
    private HashMap<String,Double> lock;

    //last refresh data time;
    private Date lastTime;

    public HashMap<String, Double> getFree() {
        return free;
    }

    public void setFree(HashMap<String, Double> free) {
        this.free = free;
    }

    public HashMap<String, Double> getLock() {
        return lock;
    }

    public void setLock(HashMap<String, Double> lock) {
        this.lock = lock;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
}

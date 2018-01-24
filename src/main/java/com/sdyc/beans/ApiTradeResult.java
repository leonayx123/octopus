package com.sdyc.beans;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/24  17:59
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/24  17:59
 * Author:
 * </pre>
 */

public class ApiTradeResult {
    private Boolean success;
    private String orderId;
    private  String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

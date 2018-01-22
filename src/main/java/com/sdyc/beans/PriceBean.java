package com.sdyc.beans;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/16  19:17
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:
 * Discription:
 *
 * Modify:      2018/1/16  19:17
 * Author:
 * </pre>
 */

public class PriceBean {

    String name;

    double value;

    public PriceBean(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

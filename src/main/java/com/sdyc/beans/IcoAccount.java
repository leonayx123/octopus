package com.sdyc.beans;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/18  18:14
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/18  18:14
 * Author:
 * </pre>
 */

public class IcoAccount {

    long   seqkey;

    String userId;

    String userKey;

    String exchange;

    long batchNum;


    Double btc;
    Double eth;
    Double xrp;
    Double bch;
    Double ada;
    Double ltc;
    Double xem;
    Double neo;
    Double xlm;
    Double iota;
    Double eos;
    Double dash;
    Double trx;
    Double xmr;
    Double btg;
    Double etc;
    Double icx;
    Double lsk;
    Double qtum;
    Double xrb;
    Double omg;
    Double usdt;

    Date   groupDate;
    Date   createDate;
    Date   updateDate;




    private static String upfs(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;

    }

    //根据名称 反射获取某个ico的值
    public Double getIcoValue(String cpl)throws Exception{

        Method method= this.getClass().getDeclaredMethod("get"+upfs(cpl));

       return (Double)method.invoke(this);

    }

    //根据名称 反射设置某个ico的值
    public void setIcoValue(String cpl,Double value)throws Exception{

        Method method= this.getClass().getDeclaredMethod("set"+upfs(cpl),Double.class);

        method.invoke(this,value);

    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(long batchNum) {
        this.batchNum = batchNum;
    }

    public long getSeqkey() {
        return seqkey;
    }

    public void setSeqkey(long seqkey) {
        this.seqkey = seqkey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Double getBtc() {
        return btc;
    }

    public void setBtc(Double btc) {
        this.btc = btc;
    }

    public Double getEth() {
        return eth;
    }

    public void setEth(Double eth) {
        this.eth = eth;
    }

    public Double getXrp() {
        return xrp;
    }

    public void setXrp(Double xrp) {
        this.xrp = xrp;
    }

    public Double getBch() {
        return bch;
    }

    public void setBch(Double bch) {
        this.bch = bch;
    }

    public Double getAda() {
        return ada;
    }

    public void setAda(Double ada) {
        this.ada = ada;
    }

    public Double getLtc() {
        return ltc;
    }

    public void setLtc(Double ltc) {
        this.ltc = ltc;
    }

    public Double getXem() {
        return xem;
    }

    public void setXem(Double xem) {
        this.xem = xem;
    }

    public Double getNeo() {
        return neo;
    }

    public void setNeo(Double neo) {
        this.neo = neo;
    }

    public Double getXlm() {
        return xlm;
    }

    public void setXlm(Double xlm) {
        this.xlm = xlm;
    }

    public Double getIota() {
        return iota;
    }

    public void setIota(Double iota) {
        this.iota = iota;
    }

    public Double getEos() {
        return eos;
    }

    public void setEos(Double eos) {
        this.eos = eos;
    }

    public Double getDash() {
        return dash;
    }

    public void setDash(Double dash) {
        this.dash = dash;
    }

    public Double getTrx() {
        return trx;
    }

    public void setTrx(Double trx) {
        this.trx = trx;
    }

    public Double getXmr() {
        return xmr;
    }

    public void setXmr(Double xmr) {
        this.xmr = xmr;
    }

    public Double getBtg() {
        return btg;
    }

    public void setBtg(Double btg) {
        this.btg = btg;
    }

    public Double getEtc() {
        return etc;
    }

    public void setEtc(Double etc) {
        this.etc = etc;
    }

    public Double getIcx() {
        return icx;
    }

    public void setIcx(Double icx) {
        this.icx = icx;
    }

    public Double getLsk() {
        return lsk;
    }

    public void setLsk(Double lsk) {
        this.lsk = lsk;
    }

    public Double getQtum() {
        return qtum;
    }

    public void setQtum(Double qtum) {
        this.qtum = qtum;
    }

    public Double getXrb() {
        return xrb;
    }

    public void setXrb(Double xrb) {
        this.xrb = xrb;
    }

    public Double getOmg() {
        return omg;
    }

    public void setOmg(Double omg) {
        this.omg = omg;
    }

    public Double getUsdt() {
        return usdt;
    }

    public void setUsdt(Double usdt) {
        this.usdt = usdt;
    }

    public Date getGroupDate() {
        return groupDate;
    }

    public void setGroupDate(Date groupDate) {
        this.groupDate = groupDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


    @Override
    public int hashCode() {
        return this.userKey.hashCode();
    }


    @Override
    public String toString() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer sb=new StringBuffer()
         .append(exchange)
        .append(",").append(btc)
        .append(",").append(eth)
        .append(",").append(xrp)
        .append(",").append(bch)
        .append(",").append(ada)
        .append(",").append(ltc)
        .append(",").append(xem)
        .append(",").append(neo)
        .append(",").append(xlm)
        .append(",").append(iota)
        .append(",").append(eos)
        .append(",").append(dash)
        .append(",").append(trx)
        .append(",").append(xmr)
        .append(",").append(btg)
        .append(",").append(etc)
        .append(",").append(icx)
        .append(",").append(lsk)
        .append(",").append(qtum)
        .append(",").append(xrb)
        .append(",").append(omg)
        .append(",").append(usdt)
        .append(",").append(sdf.format(createDate))
        .append(",").append(sdf.format(updateDate));
        return sb.toString();
    }



}

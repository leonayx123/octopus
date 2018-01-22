package com.sdyc.service.exapi;

import com.alibaba.fastjson.JSONObject;
import com.sdyc.beans.AccountBalances;
import com.sdyc.beans.Depth;
import com.sdyc.beans.ExAccount;
import com.sdyc.beans.PriceBean;

import java.util.Map;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/11  19:15
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/11  19:15
 * Author:
 * </pre>
 */

public interface DataService {

    /**
     * 获取当前交易所的名称
     * @return
     */
    String getExchangeName();

    /**
     * 获取当前交易所的所有币对map
     * @return
     */
    Map<String,String> getExchangeCpMap();

    /**
     * 根据公共cp 拿到每一家的cp转换
     * @param comonCp
     * @return
     */
    String getExchangeCp(String comonCp);

    /**
     * get ico couple pirce data
     * @param  cp ico couple name like: btc_dbc
     * @return
     */
    PriceBean getPriceData(String cp) throws Exception;


    /**
     * get the deep data
     * @param  cp ico couple name like: btc_dbc
     *
     * @return
     */
    Depth[] getDeepData(String cp,String type) throws Exception;


    /**
     * get my  all ico balances;
     */
    public AccountBalances getBalances(ExAccount exAccount) throws Exception;

    /**
     * bug  ico
     * @param currencyPair
     * @param rate
     * @param amount
     * @return
     * @throws Exception
     */
    public JSONObject  buy(ExAccount exAccount,String currencyPair,Double rate, Double amount)throws Exception;


    /**
     * sell ico
     * @param currencyPair
     * @param rate
     * @param amount
     * @return
     * @throws Exception
     */
    public JSONObject sell(ExAccount exAccount,String currencyPair,Double rate, Double amount)throws  Exception;


    public JSONObject getOrder(ExAccount exAccount,String orderNumber,String currencyPair) throws Exception;


}

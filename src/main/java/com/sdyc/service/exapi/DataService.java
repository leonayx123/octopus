package com.sdyc.service.exapi;

import com.alibaba.fastjson.JSONObject;
import com.sdyc.beans.AccountBalances;
import com.sdyc.beans.Depth;
import com.sdyc.beans.PriceBean;

import java.util.Map;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/11  19:15
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     ����ʶ���˳���Ϣ�Ƽ����޹�˾
 * Discription:
 *
 * Modify:      2018/1/11  19:15
 * Author:
 * </pre>
 */

public interface DataService {

    /**
     * ��ȡ��ǰ������������
     * @return
     */
    String getExchangeName();

    /**
     * ��ȡ��ǰ�����������бҶ�map
     * @return
     */
    Map<String,String> getExchangeCpMap();

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
    public AccountBalances getBalances() throws Exception;

    /**
     * bug  ico
     * @param currencyPair
     * @param rate
     * @param amount
     * @return
     * @throws Exception
     */
    public JSONObject  buy(String currencyPair,Double rate, Double amount)throws Exception;


    /**
     * sell ico
     * @param currencyPair
     * @param rate
     * @param amount
     * @return
     * @throws Exception
     */
    public JSONObject sell(String currencyPair,Double rate, Double amount)throws  Exception;


    public JSONObject getOrder(String orderNumber,String currencyPair) throws Exception;


}

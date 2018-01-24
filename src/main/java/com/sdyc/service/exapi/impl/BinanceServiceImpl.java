package com.sdyc.service.exapi.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdyc.beans.*;
import com.sdyc.service.exapi.DataService;
import com.sdyc.sys.Config;
import com.sdyc.utils.HttpUtilManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/11  19:34
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     sdyc
 * Discription:
 *
 * Modify:      2018/1/11  19:34
 * Author:
 * </pre>
 */
@Component("binanceDataService")
public class BinanceServiceImpl implements DataService {

    private final static String exchangeName="binance";

    private  static HashMap<String,String> cpsMap=new HashMap<String, String>();

    static {
        String[] cps=   Config.get("icos.cps").split(",");
        String[] mycps=   Config.get("icos.binance").split(",");
        for(int i=0;i<cps.length;i++){
            cpsMap.put(cps[i],mycps[i]);
        }
    }

    private  String getMyCp( String cp){
        return  cpsMap.get(cp);
    }

    /**
     *获取当前交易所的名称
     * @param
     * @return
     */
    @Override
    public String getExchangeName(){
        return  exchangeName;
    }

    /**
     *获取当前交易所的币对map
     * @param
     * @return
     */
    @Override
    public Map<String,String> getExchangeCpMap(){

        return  cpsMap;

    }

    /**
     * 根据公共cp 拿到每一家的cp转换
     *
     * @param comonCp
     * @return
     */
    @Override
    public String getExchangeCp(String comonCp) {
        return cpsMap.get(comonCp);
    }

    /**
     * get  price data
     * @param cp  ico couple
     * @return
     */
    public PriceBean getPriceData(String cp) throws Exception{
        String mycp=getMyCp(cp);

        if(StringUtils.isBlank(mycp)||"NULL".equals(mycp)){
            return null;
        }

        String url="https://api.binance.com/api/v3/ticker/price?symbol="+mycp;

        Double rs=null;
        try {
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String res = httpUtil.requestHttpGet(url, "");

            JSONObject resObj= JSON.parseObject(res);
            if(resObj!=null){
                String price= resObj.getString("price");
                if(StringUtils.isBlank(price)){
                    return null;
                }else {
                    rs=Double.parseDouble( resObj.getString("price"));
                }

            }


        } catch (Exception e) {
            throw new Exception(cp+",get price exception ");
        }


        return new PriceBean(getExchangeName(),rs);
    }

    /**
     * get deep data
     * @param cp  ico couple name
     * @param type   the type of deep : ask or bid;
     * @return
     */
    public Depth[] getDeepData(String cp,String type)throws Exception{
        String mycp=getMyCp(cp);

        if("NULL".equals(mycp)){
            return null;
        }
        String url="https://api.binance.com/api/v1/depth?limit=5&symbol="+mycp;

        Depth[] rs=null;
        JSONArray arrs=null;
        try {

            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String res = httpUtil.requestHttpGet(url,"");

            JSONObject json= JSON.parseObject(res);

            if(type.equals("asks")){
                arrs =json.getJSONArray("asks");
            }else {
                arrs =json.getJSONArray("bids");
            }

            if(arrs==null||arrs.size()==0){
                return  null;
            }

            int size= Math.min(arrs.size(),5);
            rs=new Depth[size];

            for(int i=0;i< size; i++){

                JSONArray  oneData= (JSONArray)arrs.get(i);
                Depth depth=new Depth();
                //gate io  first is price  second is number
                depth.setPrice(Double.parseDouble((String)oneData.get(0)) );
                depth.setQuanatity(Double.parseDouble((String)oneData.get(1)));
                rs[i]=depth;
            }

        } catch (Exception e) {
            throw e;
        }


        return  rs;
    }

    /**
     * get my  all ico balances;
     */
    public AccountBalances getBalances(ExAccount exAccount) throws Exception {
        return null;
    }


    /**
     * bug  ico
     *
     * @param currencyPair
     * @param rate
     * @param amount
     * @return
     * @throws Exception
     */
    public ApiTradeResult buy(ExAccount exAccount,String currencyPair, Double rate, Double amount) throws Exception {
        return null;
    }

    /**
     * sell ico
     *
     * @param currencyPair
     * @param rate
     * @param amount
     * @return
     * @throws Exception
     */
    public ApiTradeResult sell(ExAccount exAccount,String currencyPair, Double rate, Double amount) throws Exception {
        return null;
    }

    public JSONObject getOrder(ExAccount exAccount,String orderNumber, String currencyPair) throws Exception {
        return null;
    }
}

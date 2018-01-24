package com.sdyc.service.exapi.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdyc.beans.*;
import com.sdyc.service.exapi.DataService;
import com.sdyc.sys.Config;
import com.sdyc.utils.HttpUtilManager;
import com.sdyc.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/17  16:54
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:
 * Discription:
 *
 * Modify:      2018/1/17  16:54
 * Author:
 * </pre>
 */

public class BitFinexServiceImpl implements DataService {



    private  static HashMap<String,String> cpsMap=new HashMap<String, String>();

    private final static String exchangeName="bitFinex";

    static {
        String[] cps=   Config.get("icos.cps").split(",");
        String[] mycps=   Config.get("icos.bitFinex").split(",");

        for(int i=0;i<cps.length;i++){
            cpsMap.put(cps[i],mycps[i]);
        }
    }

    private  String getMyCp( String cp){
        return  cpsMap.get(cp);
    }



    /**
     *
     * @param
     * @return
     */
    @Override
    public String getExchangeName(){
        return  exchangeName;
    }

    /**
     *
     * @param
     * @return
     */
    @Override
    public Map<String,String> getExchangeCpMap(){

        return  cpsMap;

    }

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
        String mycp=  getMyCp(cp);

        if(StringUtils.isBlank(mycp)||"NULL".equals(mycp)){
            return null;

        }

        String url="https://api.bitfinex.com/v2/ticker/t"+mycp;

        Double rs=null;
        try {
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();

            String result = httpUtil.requestHttpGet(url,"");

            JSONArray array= JSON.parseArray(result);
            /*
            [
              BID,
              BID_SIZE,
              ASK,
              ASK_SIZE,
              DAILY_CHANGE,
              DAILY_CHANGE_PERC,
              LAST_PRICE,
              VOLUME,
              HIGH,
              LOW
            ]
             */
            if(array!=null&&array.size()==10){
               rs=JsonUtils.getDouble(array.get(6));
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

        String mycp=  getMyCp(cp);

        if("NULL".equals(mycp)){
            return null;

        }

        String url="http://api.huobipro.com/market/depth?type=step1&symbol="+mycp;

        Depth[] rs=null;
        JSONArray arrs=null;
        try {

            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String res = httpUtil.requestHttpGet(url, "");

            JSONObject json= JSON.parseObject(res);

            JSONObject  tick =  json.getJSONObject("tick");

            if(type.equals("asks")){
                arrs =tick.getJSONArray("asks");
            }else {
                arrs =tick.getJSONArray("bids");
            }

            if(arrs==null||arrs.size()==0){
                return  null;
            }

            int size= Math.min(arrs.size(), 5);
            rs=new Depth[size];

            for(int i=0;i< size; i++){

                JSONArray  oneData= (JSONArray)arrs.get(i);
                Depth depth=new Depth();
                //gate io  first is price  second is number
                Object price=oneData.get(0);
                Object number=oneData.get(0);
                depth.setPrice(JsonUtils.getDouble(price));
                depth.setQuanatity(JsonUtils.getDouble(number));
                rs[i]=depth;
            }

        } catch (Exception e) {
            throw e;
        }
        return rs;
    }


    /**
     * get my  all ico balances;
     */
    public AccountBalances getBalances(ExAccount exAccount) throws Exception {


        return null;
    }


    public ApiTradeResult buy(ExAccount exAccount,String currencyPair,Double rate, Double amount)throws Exception{

        return null;
    }

    public ApiTradeResult sell(ExAccount exAccount,String currencyPair,Double rate, Double amount) throws Exception {

        return null;
    }

    public JSONObject getOrder(ExAccount exAccount,String orderNumber, String currencyPair) throws Exception {

        return null;
    }
}

package com.sdyc.service.exapi.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdyc.beans.*;
import com.sdyc.service.exapi.DataService;
import com.sdyc.sys.Config;
import com.sdyc.utils.HttpUtilManager;
import com.sdyc.utils.JsonUtils;
import com.sdyc.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/11  19:15
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:
 * Discription:
 *
 * Modify:      2018/1/11  19:15
 * Author:
 * </pre>
 */
@Component("gateIoDataService")
public class GateIoServiceImpl implements DataService{



    private  static HashMap<String,String> cpsMap=new HashMap<String, String>();

    private final static String exchangeName="gateIo";

    static {
        String[] cps=   Config.get("icos.cps").split(",");
        String[] mycps=   Config.get("icos.gate").split(",");
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

        String url="http://data.gate.io/api2/1/ticker/";

        Double rs=null;
        try {
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();

            String result = httpUtil.requestHttpGet(url+mycp,"");

            JSONObject resObj= JSON.parseObject(result);
            if(resObj!=null){
                String last= resObj.getString("last");
                if(StringUtils.isBlank(last)){
                    rs=0.0;
                }else{
                    rs=Double.parseDouble(last);
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
        String url="http://data.gate.io/api2/1/orderBook/";
        String mycp=  getMyCp(cp);

        if("NULL".equals(mycp)){
            return null;

        }

        Depth[] rs=null;
        try {
            JSONArray arrs=null;

            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String res = httpUtil.requestHttpGet(url + mycp, "");
              /* {
                    "result": "true",
                    "asks": [
                            [29500,    4.07172355],
                            [29499,    0.00203397],
                            [29495,    1],
                            [29488,    0.0672],
                            [29475,    0.001]
                        ],
                    "bids": [
                            [28001, 0.0477],
                            [28000, 0.35714018],
                            [28000, 2.56222976],
                            [27800, 0.0015],
                            [27777, 0.1]
            ] }
         */
            JSONObject json= JSON.parseObject(res);
            if(type.equals("asks")){
                arrs =json.getJSONArray("asks");

                //ask data  need reverse. we need  the  cheapest on the top
                if(arrs==null||arrs.size()==0){
                    return  null;
                }

                int size= Math.min(arrs.size(),5);

                rs=new Depth[size];
                // get cheapest price  from last one
                for(int i= 0;i<size ; i++){
                    int index=arrs.size()-1-i;
                    JSONArray  oneData= (JSONArray)arrs.get(index);
                    Depth depth=new Depth();

                    //gate io  first is price  second is number
                    Double price=null;
                    Double quantity=null;
                    Object priceJson= oneData.get(0);
                    Object qunatityJson= oneData.get(1);

                    price= JsonUtils.getDouble(priceJson);
                    quantity=JsonUtils.getDouble(qunatityJson);
                            //gate io  first is price  second is number
                    depth.setPrice(price);
                    depth.setQuanatity(quantity);
                    rs[i]=depth;
                }


            }else {
                arrs =json.getJSONArray("bids");

                if(arrs==null||arrs.size()==0){
                    return  null;
                }

                int size= Math.min(arrs.size(),5);
                rs=new Depth[size];

                for(int i=0;i< size; i++){

                    JSONArray  oneData= (JSONArray)arrs.get(i);
                    Depth depth=new Depth();
                    Double price=null;
                    Double quantity=null;
                    Object priceJson= oneData.get(0);
                    Object qunatityJson= oneData.get(1);

                    price= JsonUtils.getDouble(priceJson);
                    quantity=JsonUtils.getDouble(qunatityJson);
                    //gate io  first is price  second is number
                    depth.setPrice(price);
                    depth.setQuanatity(quantity);
                    rs[i]=depth;
                }
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
        String apiUrl="https://api.gate.io/api2/1/private/balances";

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key",exAccount.getKey());
        String sign = MD5Util.buildMysignV1(params, exAccount.getSecret());
        params.put("sign", sign);

        AccountBalances myBalance=new AccountBalances();
        try {
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();

            String result = httpUtil.doGateIoRequest("post", apiUrl, params,exAccount.getKey(), exAccount.getSecret());

            if(StringUtils.isBlank(result)){
                throw  new Exception("没有获取到账号数据");

            }
            /**
             *  {
             "result":
             "true",
                 "available": {
                     "BTC": "1000",
                     "ETH": "968.8",
                     "ETC": "0",
                 },
                 "locked": {
                     "ETH": "1"
                 }
             }
             */

            JSONObject banlance=  JSONObject.parseObject(result);
           // banlance.getJSONObject();
            JSONObject freeJson=banlance.getJSONObject("available");

            JSONObject freezedJson=banlance.getJSONObject("locked");

            HashMap<String,Double> free=null;
            if(freeJson!=null&&freeJson.size()>0){
                free=new HashMap<String, Double>();
                Iterator<String> it= freeJson.keySet().iterator();
                while(it.hasNext()){
                    String key=it.next();
                    free.put(key.trim().toLowerCase(), Double.parseDouble(freeJson.getString(key)) );
                }
            }

            HashMap<String,Double> lock=null;
            if(freezedJson!=null&&freezedJson.size()>0){
                lock=new HashMap<String, Double>();
                Iterator<String> itfz= freezedJson.keySet().iterator();
                while (itfz.hasNext()){
                    String key=itfz.next();
                    lock.put(key.trim().toLowerCase(),Double.parseDouble(freezedJson.getString(key)));
                }

            }
            myBalance.setFree(free);
            myBalance.setLock(lock);


        } catch (Exception e) {
           throw  e;
        }

        return myBalance;
    }


    public ApiTradeResult buy(ExAccount exAccount,String currencyPair,Double rate, Double amount)throws Exception{
        String mycp=  getMyCp(currencyPair);

        String BUY_URL = "https://api.gate.io/api2/1/private/buy";
        Map<String, String> params = new HashMap<String, String>();
        params.put("currencyPair", mycp);
        params.put("rate",String.valueOf(rate) );
        params.put("amount", String.valueOf(amount));

        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String result = httpUtil.doGateIoRequest("post", BUY_URL, params,exAccount.getKey(), exAccount.getSecret());
        /**
         *  {
         "result":"true",
         "orderNumber":"123456",
         "message":"Success"
          }
         */
         JSONObject resJson = JSON.parseObject(result);
        ApiTradeResult  apiTradeResult=new ApiTradeResult();
        apiTradeResult.setSuccess(Boolean.parseBoolean(resJson.getString("result")));
        apiTradeResult.setMsg(resJson.getString("message"));
        if(apiTradeResult.getSuccess()){
            apiTradeResult.setOrderId(resJson.getString("orderNumber"));
        }

        return apiTradeResult;
    }

    public ApiTradeResult sell(ExAccount exAccount,String currencyPair,Double rate, Double amount) throws Exception {
        String mycp=  getMyCp(currencyPair);
        String SELL_URL = "https://api.gate.io/api2/1/private/sell";

        Map<String, String> params = new HashMap<String, String>();
        params.put("currencyPair", mycp);
        params.put("rate",  String.valueOf(rate));
        params.put("amount",  String.valueOf(amount));

        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String result = httpUtil.doGateIoRequest("post", SELL_URL, params,exAccount.getKey(), exAccount.getSecret());

        JSONObject resJson=  JSON.parseObject(result);
        ApiTradeResult  apiTradeResult=new ApiTradeResult();
        apiTradeResult.setSuccess(Boolean.parseBoolean(resJson.getString("result")));
        apiTradeResult.setMsg(resJson.getString("message"));
        if(apiTradeResult.getSuccess()){
            apiTradeResult.setOrderId(resJson.getString("orderNumber"));
        }

        return apiTradeResult;
    }

    public JSONObject getOrder(ExAccount exAccount,String orderNumber, String currencyPair) throws Exception {
        String mycp=  getMyCp(currencyPair);
        String GETORDER_URL="https://api.gate.io/api2/1/private/getOrder";
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderNumber", orderNumber);
        params.put("currencyPair", mycp);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String result = httpUtil.doGateIoRequest("post", GETORDER_URL, params,exAccount.getKey(), exAccount.getSecret());
        //System.out.print(result);
        return JSON.parseObject(result);
    }


}

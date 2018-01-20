package com.sdyc.service.exapi.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdyc.beans.AccountBalances;
import com.sdyc.beans.Depth;
import com.sdyc.beans.PriceBean;
import com.sdyc.service.exapi.DataService;
import com.sdyc.sys.Config;
import com.sdyc.utils.HttpUtilManager;
import com.sdyc.utils.JsonUtils;
import com.sdyc.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    // gate.io  api_secret
    private static String SECRET = "c906812402f9c31d1d22779859697f36fa16d8202f5acd1cc4bbd65556ec43dc";
    //gate.io api key
    private static String KEY = "57D56DC2-0649-4610-AE20-B020BC2487C2";

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
    public AccountBalances getBalances() throws Exception {
        String apiUrl="https://api.gate.io/api2/1/private/balances";

        Map<String, String> params = new HashMap<String, String>();
        params.put("api_key", this.KEY);
        String sign = MD5Util.buildMysignV1(params, this.SECRET);
        params.put("sign", sign);


        try {
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();

            String result = httpUtil.doGateIoRequest("post", apiUrl, params, this.KEY, this.SECRET);

            if(StringUtils.isBlank(result)){
                throw  new Exception("没有获取到账号数据");

            }
            JSONObject banlance=  JSONObject.parseObject(result);
           // banlance.getJSONObject();
            AccountBalances myBalance=new AccountBalances();
           // myBalance.setFree();


        } catch (Exception e) {
           throw  e;
        }

        return null;
    }


    public JSONObject buy(String currencyPair,Double rate, Double amount)throws Exception{
        String mycp=  getMyCp(currencyPair);

        String BUY_URL = "https://api.gate.io/api2/1/private/buy";
        Map<String, String> params = new HashMap<String, String>();
        params.put("currencyPair", mycp);
        params.put("rate",String.valueOf(rate) );
        params.put("amount", String.valueOf(amount));

        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String result = httpUtil.doGateIoRequest("post", BUY_URL, params, this.KEY, this.SECRET);
        System.out.print(result);
        return JSON.parseObject(result);
    }

    public JSONObject sell(String currencyPair,Double rate, Double amount) throws Exception {
        String mycp=  getMyCp(currencyPair);
        String SELL_URL = "https://api.gate.io/api2/1/private/sell";

        Map<String, String> params = new HashMap<String, String>();
        params.put("currencyPair", mycp);
        params.put("rate",  String.valueOf(rate));
        params.put("amount",  String.valueOf(amount));

        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String result = httpUtil.doGateIoRequest("post", SELL_URL, params, this.KEY, this.SECRET);
        System.out.print(result);
        return JSON.parseObject(result);
    }

    public JSONObject getOrder(String orderNumber, String currencyPair) throws Exception {
        String mycp=  getMyCp(currencyPair);
        String GETORDER_URL="https://api.gate.io/api2/1/private/getOrder";
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderNumber", orderNumber);
        params.put("currencyPair", mycp);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String result = httpUtil.doGateIoRequest("post", GETORDER_URL, params, this.KEY, this.SECRET);
        //System.out.print(result);
        return JSON.parseObject(result);
    }


}

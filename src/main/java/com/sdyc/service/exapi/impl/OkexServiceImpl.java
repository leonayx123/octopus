package com.sdyc.service.exapi.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.rest.stock.IStockRestApi;
import com.okcoin.rest.stock.impl.StockRestApi;
import com.sdyc.beans.AccountBalances;
import com.sdyc.beans.Depth;
import com.sdyc.beans.PriceBean;
import com.sdyc.service.exapi.DataService;
import com.sdyc.sys.Config;
import com.sdyc.utils.HttpUtilManager;
import com.sdyc.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/16  14:58
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *  doc address  https://www.okex.com/rest_getStarted.html
 * Modify:      2018/1/16  14:58
 * Author:
 * </pre>
 */
@Component("okexDataService")
public class OkexServiceImpl implements DataService {

    // gate.io  api_secret
    private static String SECRET = "6C3EE8B5A262FAC423A1E40EAA4777EE";
    //gate.io api key
    private static String KEY = "c574641b-4047-41e9-b40f-6ecbda0c960c";

    private  static HashMap<String,String> cpsMap=new HashMap<String, String>();

    private final static String exchangeName="okex";


    private static  IStockRestApi stockPost = new StockRestApi("https://www.okex.com", KEY, SECRET);

    static {
        String[] cps=   Config.get("icos.cps").split(",");
        String[] mycps=   Config.get("icos.okex").split(",");
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
     * get  price data
     * @param cp  ico couple
     * @return
     */
    public PriceBean getPriceData(String cp) throws Exception{
        String mycp=getMyCp(cp);

        if(StringUtils.isBlank(mycp)||"NULL".equals(mycp)){
            return null;

        }

        String url="https://www.okex.com/api/v1/ticker.do?symbol=";

        Double rs=null;
        try {
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();

            String result = httpUtil.requestHttpGet(url+mycp,"");

            JSONObject resObj= JSON.parseObject(result);

            JSONObject tickerObj=  resObj.getJSONObject("ticker");

            if(resObj!=null){
                rs=Double.parseDouble( tickerObj.getString("last"));
            }

        } catch (Exception e) {
            throw e;
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
        String url="https://www.okex.com/api/v1/depth.do?size=5&symbol=";
        String mycp=getMyCp(cp);

        Depth[] rs=null;
        try {
            JSONArray arrs=null;

            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String res = httpUtil.requestHttpGet(url+mycp,"");
              /* {
                "asks": [
                    [792, 5],
                    [789.68, 0.018],
                    [788.99, 0.042],
                    [788.43, 0.036],
                    [787.27, 0.02]
                ],
                "bids": [
                    [787.1, 0.35],
                    [787, 12.071],
                    [786.5, 0.014],
                    [786.2, 0.38],
                    [786, 3.217],
                    [785.3, 5.322],
                    [785.04, 5.04]
                ]
            }
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
                    Object price=oneData.get(0);
                    Object number=oneData.get(1);
                    depth.setPrice(JsonUtils.getDouble(price));
                    depth.setQuanatity(JsonUtils.getDouble(number));

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
                    Object price=oneData.get(0);
                    Object number=oneData.get(1);
                    depth.setPrice(JsonUtils.getDouble(price));
                    depth.setQuanatity(JsonUtils.getDouble(number));
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


        IStockRestApi stockPost = new StockRestApi("https://www.okex.com", this.KEY, this.SECRET);




        try {
            String info  = stockPost.userinfo();

            System.out.print(info);

            AccountBalances myBalance=new AccountBalances();
            // myBalance.setFree();


        } catch (Exception e) {
            throw  e;
        }

        return null;
    }


    public JSONObject buy(String currencyPair,Double rate, Double amount)throws Exception{


        String mycp=  getMyCp(currencyPair);
        //现货下单交易
        String tradeResult = stockPost.trade(mycp, "buy",String.valueOf(rate), String.valueOf(amount));

        return JSON.parseObject(tradeResult);
    }

    public JSONObject sell(String currencyPair,Double rate, Double amount) throws Exception {


        String mycp=  getMyCp(currencyPair);
        //现货下单交易
        String tradeResult = stockPost.trade(mycp, "sell", String.valueOf(rate), String.valueOf(amount));

        return JSON.parseObject(tradeResult);
    }

    public JSONObject getOrder(String orderNumber, String currencyPair) throws Exception {
        String mycp=  getMyCp(currencyPair);
        //现货获取用户订单信息
        String result =  stockPost.order_info(mycp, orderNumber);
        //System.out.print(result);
        return JSON.parseObject(result);
    }
}

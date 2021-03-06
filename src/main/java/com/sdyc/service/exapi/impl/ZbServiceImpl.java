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

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/17  15:55
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     ����ʶ���˳���Ϣ�Ƽ����޹�˾
 * Discription:
 *  doc address   https://www.zb.com/i/developer/restApi#market
 *
 * Modify:      2018/1/17  15:55
 * Author:
 * </pre>
 */
@Component("zbDataService")
public class ZbServiceImpl  implements DataService{


    private  static HashMap<String,String> cpsMap=new HashMap<String, String>();

    private final static String exchangeName="zb";

    static {
        String[] cps=   Config.get("icos.cps").split(",");
        String[] mycps=   Config.get("icos.zb").split(",");

        for(int i=0;i<cps.length;i++){
            cpsMap.put(cps[i],mycps[i]);
        }
    }

    private  String getMyCp( String cp){
        return  cpsMap.get(cp);
    }



    /**
     *��ȡ��ǰ����������
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

        String url="http://api.zb.com/data/v1/ticker?market="+mycp;

        Double rs=null;
        try {
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();

            String result = httpUtil.requestHttpGet(url,"");

            JSONObject resObj= JSON.parseObject(result);

            if(resObj!=null){
                JSONObject tick=resObj.getJSONObject("ticker");

                if(tick!=null){
                    rs=Double.parseDouble(tick.getString("last"));
                }else{
                    rs=0.0;
                }

            }

        } catch (Exception e) {
            throw new Exception(cp+", get price exception ");
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

        String url="http://api.zb.com/data/v1/depth?size=5&market="+mycp;

        Depth[] rs=null;
        JSONArray arrs=null;
        try {

            HttpUtilManager httpUtil = HttpUtilManager.getInstance();
            String res = httpUtil.requestHttpGet(url, "");

            JSONObject tick= JSON.parseObject(res);


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
                Object number=oneData.get(1);
                depth.setPrice(JsonUtils.getDouble(price));
                depth.setQuanatity(JsonUtils.getDouble(number));
                rs[i]=depth;
            }

            if("asks".equals(type)){
                Arrays.sort(rs, new Comparator<Depth>() {
                    @Override
                    public int compare(Depth o1, Depth o2) {
                        return Double.compare(o1.getPrice(),o2.getPrice());
                    }
                });

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


        try {
            HttpUtilManager httpUtil = HttpUtilManager.getInstance();

            String result = httpUtil.doGateIoRequest("post", apiUrl, params,exAccount.getKey(),exAccount.getSecret() );

            if (StringUtils.isBlank(result)){
                throw  new Exception("û�л�ȡ���˺����");

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


    public ApiTradeResult buy(ExAccount exAccount,String currencyPair,Double rate, Double amount)throws Exception{
      return  null;
    }

    public ApiTradeResult sell(ExAccount exAccount,String currencyPair,Double rate, Double amount) throws Exception {
       return  null;
    }

    public JSONObject getOrder(ExAccount exAccount,String orderNumber, String currencyPair) throws Exception {
        String mycp=  getMyCp(currencyPair);
        String GETORDER_URL="https://api.gate.io/api2/1/private/getOrder";
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderNumber", orderNumber);
        params.put("currencyPair", mycp);
        HttpUtilManager httpUtil = HttpUtilManager.getInstance();
        String result = httpUtil.doGateIoRequest( "post", GETORDER_URL, params,exAccount.getKey(),exAccount.getSecret()  );
        //System.out.print(result);
        return JSON.parseObject(result);
    }
}

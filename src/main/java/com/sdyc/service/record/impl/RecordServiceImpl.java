package com.sdyc.service.record.impl;

import com.sdyc.beans.IcoAccount;
import com.sdyc.beans.TradeRecordBean;
import com.sdyc.service.record.RecordService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/18  18:52
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/18  18:52
 * Author:
 * </pre>
 */
@Component("recordService")
public class RecordServiceImpl implements RecordService {

    private static   List<IcoAccount>  accs=null;

    public static  Double allbtcCount=0.0;

    public static Double lastBtcCount=0.0;

    public static  Double currbtcCount=0.0;

    private static String upfs(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;

    }

    @Override
    public double[] getBtcChange() {
        return new double[]{allbtcCount,currbtcCount,lastBtcCount};
    }

    @Override
    public void setCurrBtc(double btc) {
        //记录一个上次的值 用来比较是否有增长
        this.lastBtcCount=this.currbtcCount;
        this.currbtcCount=btc;
    }

    /**
     * 获取个人的账号数据
     *
     * @param userId@return
     */
    @Override
    public List<IcoAccount> getAccountData(String userId)throws Exception{
        if(accs==null){
            accs=new ArrayList<IcoAccount>();
           String filePath=  this.getClass().getClassLoader().getResource("data/BTCTrader.data.csv").getPath();
           List<String> csv=  FileUtils.readLines(new File(filePath), "gb2312");

            //第一行是币名称
            //coin,btc,eth,xrp,bch,ada,ltc,xem,neo,xlm,iota,eos,dash,trx,xmr,btg,etc,icx,lsk,qtum,xrb,omg,usdt
            String[] conis= csv.get(0).split(",");



            String[] okexConis=csv.get(1).split(",");

            IcoAccount okex=new IcoAccount();
            accs.add(okex);
            okex.setExchange("okex");
            okex.setUpdateDate(new Date());
            for(int i=1;i<conis.length;i++){
                Double value=Double.parseDouble(okexConis[i]);
                Method method= okex.getClass().getDeclaredMethod("set"+upfs(conis[i]),Double.class);
                method.invoke(okex,value);
            }



            String[] gateIos=csv.get(2).split(",");
            IcoAccount gateIo=new IcoAccount();
            accs.add(gateIo);
            gateIo.setExchange("gateIo");
            gateIo.setUpdateDate(new Date());
            for(int i=1;i<conis.length;i++){
                Double value=Double.parseDouble(gateIos[i]);
                Method method= gateIo.getClass().getDeclaredMethod("set"+upfs(conis[i]),Double.class);
                method.invoke(gateIo,value);
            }

            String[] binances=csv.get(3).split(",");
            IcoAccount binance=new IcoAccount();
            accs.add(binance);
            binance.setExchange("binance");
            binance.setUpdateDate(new Date());
            for(int i=1;i<conis.length;i++){
                Double value=Double.parseDouble(binances[i]);
                Method method= binance.getClass().getDeclaredMethod("set"+upfs(conis[i]),Double.class);
                method.invoke(binance,value);
            }


            String[] huobi=csv.get(4).split(",");
            IcoAccount huobipro=new IcoAccount();
            accs.add(huobipro);
            huobipro.setExchange("huobipro");
            huobipro.setUpdateDate(new Date());
            for(int i=1;i<conis.length;i++){
                Double value=Double.parseDouble(huobi[i]);
                Method method= huobipro.getClass().getDeclaredMethod("set"+upfs(conis[i]),Double.class);
                method.invoke(huobipro,value);
            }


            String[] zbs=csv.get(5).split(",");
            IcoAccount zb=new IcoAccount();
            accs.add(zb);
            zb.setExchange("zb");
            zb.setUpdateDate(new Date());
            for(int i=1;i<conis.length;i++){
                Double value=Double.parseDouble(zbs[i]);
                Method method= zb.getClass().getDeclaredMethod("set"+upfs(conis[i]),Double.class);
                method.invoke(zb,value);
            }



            Date dt=new Date();
            for(IcoAccount ico:accs){
                ico.setUserId("111");

                ico.setSeqkey(1);
                ico.setBatchNum(1);
                ico.setUserKey("111");
                ico.setCreateDate(dt);
                allbtcCount=allbtcCount+ico.getBtc();
            }


        }
        return accs;
    }


    /**
     * 获取个人的账号数据
     *
     * @param userId
     * @param exName
     * @return
     */
    @Override
    public IcoAccount getUserExAccountData(String userId, String exName) throws Exception {
        if(exName.equals("gateIo")){
            return  accs.get(1);

        }else  if(exName.equals("binance")){
            return accs.get(2);

        }
        else  if(exName.equals("huobipro")){
            return accs.get(3);

        }
        else  if(exName.equals("okex")){
            return accs.get(0);

        }
        else  if(exName.equals("zb")){
            return accs.get(4);

        }
        return null;
    }

    /**
     * 修改一个account的信息
     *
     * @param account
     * @return
     */
    @Override
    public IcoAccount updateAccount(IcoAccount account) throws Exception{
        return null;
    }

    /**
     * 修改一个account的某个币的数值
     *
     * @param id
     * @param cp
     * @param num
     * @return
     */
    @Override
    public IcoAccount updateAccountIco(long id, String cp, double num) {
        return null;
    }

    /**
     * 记录交易流水
     *
     * @param recordBean
     * @return
     */
    @Override
    public TradeRecordBean saveTradeRecodr(TradeRecordBean recordBean) throws Exception {
        return null;
    }
}

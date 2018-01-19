package com.sdyc.core;

import com.sdyc.beans.Depth;
import com.sdyc.beans.IcoAccount;
import com.sdyc.beans.PriceBean;
import com.sdyc.service.exapi.DataService;
import com.sdyc.service.exapi.ExDataServiceFactory;
import com.sdyc.service.record.RecordService;
import com.sdyc.sys.Config;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/12  13:54
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/12  13:54
 * Author:
 * </pre>
 */
@Component("business")
public class Business  {


    @Resource
    private ExDataServiceFactory exServiceFactory;

    @Resource
    TraderCore traderCore;

    @Resource
    RecordService recordService;



    //所有需要判断的交易对
    private final  static  String[] cps=Config.get("icos.cps").split(",");

    private final  static  String[] exNames=Config.get("business.allEx").split(",");



    private final  static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final  static SimpleDateFormat fileNameSdf=new SimpleDateFormat("yyyyMMdd");



    //实际工作的业务逻辑
    public void  doWork(){

        List<IcoAccount>  accounts=null;

        try {
            accounts= recordService.getAccountData("111");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i=0;i<cps.length;i++){

             String cp=cps[i];

             try {
                 ArrayList<PriceBean> priceBeans =new ArrayList<PriceBean>();



                 //the date-time  at beginning
                String dateStr= sdf.format(new Date());

                 //遍历所有的交易所名称. 按名称找到交易所的数据服务类. 调用接口
                 for(String exName:exNames){

                     DataService exDataService=exServiceFactory.getService(exName);

                     PriceBean price=  exDataService.getPriceData(cp);
                     if(price!=null&&price.getValue()!=0.0){
                         priceBeans.add(new PriceBean(exName,price.getValue()));
                     }


                 }



                 //the date-time  at end
                String endDateStr= sdf.format(new Date());


                 if(priceBeans.size()<2){
                     //we need more than two exchane
                     continue;
                 }


                 //把每家交易所价格从小到大排序
                 Collections.sort(priceBeans, new Comparator<PriceBean>() {
                     public int compare(PriceBean o1, PriceBean o2) {
                     return Double.compare(o1.getValue(),o2.getValue());
                     }
                 });


                 //取最后一家为最大价格.
                 String  higherEx= priceBeans.get(priceBeans.size()-1).getName();
                 //取第一家为最小价格.
                 String  lowerEx= priceBeans.get(0).getName();

                 //最大价
                 Double   highPrice= priceBeans.get(priceBeans.size()-1).getValue();

                 //最小价
                 Double   lowPrice= priceBeans.get(0).getValue();


                 //get the The difference price  percent
                 double bili= Math.abs ((highPrice - lowPrice)/Math.min(highPrice,lowPrice)) * 100;

                 //if the difference price  lower than 2, do nothing  and continue
                 if(bili<1){
                     //System.out.println(gprice+"percent too low . continue");
                     continue;
                 }

                 Depth[] higherBids =null;
                 Depth[] lowerAsks=null;

                 //获取最高价的交易所的数据服务类
                 DataService highExDataService=exServiceFactory.getService(higherEx);

                 //获取最低价的交易所的数据服务类
                 DataService lowExDataService=exServiceFactory.getService(lowerEx);

                 higherBids=highExDataService.getDeepData(cp, "bids");
                 lowerAsks= lowExDataService.getDeepData(cp, "asks");


                 if(higherBids==null||higherBids==null){
                     continue;
                 }
                 System.out.println(cp);

                 traderCore.doTrade(higherBids,lowerAsks,0,0,cp ,higherEx,lowerEx);



//                 StringBuffer csvBuffer=new StringBuffer()
//                         .append("name:"+gateCp).append(",,,,\n")
//                         .append(dateStr).append(",")
//                         .append(endDateStr).append(",")
//                         .append(nf.format(gprice)).append(",")
//                         .append(nf.format(bprice)).append(",")
//                         .append(nf.format(bili)+"%").append("\n");
//
//                 String filePath=  this.getClass().getResource("/").getPath()+"/output";
//                 File outPut=new File(filePath);
//                 if(!outPut.exists()){
//                     outPut.mkdirs();
//                 }
                // File file=new File(filePath, fileNameSdf.format(new Date())+".csv");
                // FileUtils.write(file,csvBuffer.toString(),"gb2312",true);

                // System.out.println(csvBuffer);



             } catch (Exception e) {
                 e.printStackTrace();
                 continue;
             }

         }

        try {

            String logdir= Config.get("log.dir");
            File btcLog= new File(logdir+"/output/btclog.csv");
            File walletLog=new File(logdir+"/output/wallet.csv");

            if(!btcLog.exists()){
                FileUtils.write(btcLog,"date,init btc, current btc , percent%\n","gb2312");

            }

            //FileUtils.forceDeleteOnExit(walletLog);



            StringBuffer walletBf=new StringBuffer();
            Double currBtc=0.0;
            Date dt =new Date();


            walletBf.
                     append("exchange")
                    .append(",").append("btc")
                    .append(",").append("eth")
                    .append(",").append("xrp")
                    .append(",").append("bch")
                    .append(",").append("ada")
                    .append(",").append("ltc")
                    .append(",").append("xem")
                    .append(",").append("neo")
                    .append(",").append("xlm")
                    .append(",").append("iota")
                    .append(",").append("eos")
                    .append(",").append("dash")
                    .append(",").append("trx")
                    .append(",").append("xmr")
                    .append(",").append("btg")
                    .append(",").append("etc")
                    .append(",").append("icx")
                    .append(",").append("lsk")
                    .append(",").append("qtum")
                    .append(",").append("xrb")
                    .append(",").append("omg")
                    .append(",").append("usdt")
                    .append(",").append("createDate")
                    .append(",").append("updateDate")
                    .append("\n");

            for(IcoAccount  account: accounts){
                account.setUpdateDate(dt);

                walletBf.append(account.toString()).append("\n");
                currBtc=currBtc+account.getBtc();

             }
            recordService.setCurrBtc(currBtc);

            double[] btc=recordService.getBtcChange();

            System.out.print("****************wallet********************\n");

            //当前值和上次值不相同 才需要写文件
            if( btc[1]!=btc[2]){
                String btcstr=sdf.format(new Date())+","+btc[0]+","+btc[1]+","+( (btc[1]-btc[0])/btc[1]*100)+"%\n";
                FileUtils.write(btcLog,btcstr,"gb2312",true);
                System.out.print(btcstr);


                FileUtils.write(walletLog,walletBf.toString(),"gb2312",false);

                System.out.print(walletBf.toString());
            }






        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    //this method  for  test
    public void  doTest(){
        try {



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

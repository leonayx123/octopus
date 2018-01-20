package com.sdyc.core;

import com.sdyc.beans.Depth;
import com.sdyc.beans.IcoAccount;
import com.sdyc.beans.PriceBean;
import com.sdyc.dto.AccUserBtcDTO;
import com.sdyc.dto.AccUserExSetingDTO;
import com.sdyc.service.exapi.DataService;
import com.sdyc.service.exapi.ExDataServiceFactory;
import com.sdyc.service.record.RecordService;
import com.sdyc.service.wallet.CmdAdjustService;
import com.sdyc.service.wallet.WalletService;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    Log log = LogFactory.getLog(Business.class);


    @Resource
    private ExDataServiceFactory exServiceFactory;

    @Resource
    TraderCore traderCore;

    @Resource
    RecordService recordService;

    @Resource
    WalletService walletService;

    @Resource
    CmdAdjustService cmdAdjustService;





    //所有需要判断的交易对
    private final  static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final  static SimpleDateFormat fileNameSdf=new SimpleDateFormat("yyyyMMdd");



    //实际工作的业务逻辑
    public void  doWork(){

        String userId="1mil10coins";




        AccUserExSetingDTO userExSetingDTO=null;
        BuniessDataContext context=null;
        try {
            //开始调用 币数修改任务.进行币数调整
            cmdAdjustService.doAllCmds();


            //获取最新的钱包信息
            List<IcoAccount>  accounts = walletService.getAccount(userId);

            context=new BuniessDataContext(accounts);

            //查询用户的btc信息
            userExSetingDTO= walletService.getUserExSetting(userId);

            Assert.assertNotNull(userExSetingDTO);
            Assert.assertNotNull(userExSetingDTO.getUserCoins());
            Assert.assertNotNull(userExSetingDTO.getUserExchanges());


        } catch (Exception e) {

            log.error("钱包数据初始化出错",e);
            return;
        }
        //取到用户关注的 币对
        String[]cps=userExSetingDTO.getUserCoins().split(",");
        String[] exchanges=userExSetingDTO.getUserExchanges().split(",");


        for(int i=0;i<cps.length;i++){
            String cp=cps[i];
             try {
                 ArrayList<PriceBean> priceBeans =new ArrayList<PriceBean>();

                 //the date-time  at beginning
                String dateStr= sdf.format(new Date());

                 //遍历所有的交易所名称. 按名称找到交易所的数据服务类. 调用接口
                 for(String exName:exchanges){

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
                 if(bili<0.8){
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

                 //传入context. 数据修改都在Contex里操作
                 traderCore.doTrade(context,higherBids,lowerAsks,0,0,cp ,higherEx,lowerEx);


             } catch (Exception e) {
                 log.error("traderCore 调用捕获到异常");
                 continue;
             }

         }

        try {

            List <IcoAccount > contextAccs= context.getContextIcoAccountDatas();
            walletService.batchUpdateUserCoinAmt(userId,cps,contextAccs);

            Double currBtc=0.0;
            for(IcoAccount  account: contextAccs){
                currBtc=currBtc+account.getBtc();
            }

            AccUserBtcDTO btc=walletService.getUserBtc(userId);

            //当前值不等于 上次的btc值 才需要做记录 说明增加了.
            //如果是减少..那说明出bug了
            if(btc.getCurrBtc().doubleValue()<currBtc){
                //Core.lastBtc =btc.getCurrBtc().doubleValue();
                Double ratio=(currBtc - btc.getInitBtc().doubleValue())/ btc.getInvestBtc().doubleValue();
                AccUserBtcDTO btcup=new AccUserBtcDTO();

                btcup.setCurrBtc(new BigDecimal(currBtc));
                btcup.setAddRatio(ratio);
                btcup.setUserId(userId);
                //修改btc变化数据
                walletService.updateUserBtc(btcup);



            }else if(btc.getCurrBtc().doubleValue()>currBtc) {
                log.error("WTF !!! curr btc 居然减少了,下面是当前详情!!====> "+currBtc);
                for(IcoAccount  account: contextAccs){
                    log.error(account.toString());
                }

            }else {
                log.debug("当前btc 和 上次btc相同 不做记录了 ");
            }


        } catch (Exception e) {
           log.error("写buniess日志时出错",e);
        }


    }


}

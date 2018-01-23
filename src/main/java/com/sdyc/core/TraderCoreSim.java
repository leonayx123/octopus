package com.sdyc.core;

import com.sdyc.beans.Depth;
import com.sdyc.beans.IcoAccount;
import com.sdyc.dto.RecordTradeTurnoverDTO;
import com.sdyc.service.exapi.ExDataServiceFactory;
import com.sdyc.service.record.RecordService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;


/**
 * <pre>
 * User:        Zhejun li
 * Date:        version 0.1
 * Email:
 * Version      V1.1
 * Company:     NowledgeData
 * Discription:
 *
 * v.1.1 update -----------------
 * This time I added more robust logic, when the minimum volume is too small, the 3rd price is used to determine
 * if there is a better choice. another way is the calculate the average volume of 1st and 2nd price.
 *
 *
 *
 * </pre>
 */
@Component("traderCoreSim")
public class TraderCoreSim {

    Log log= LogFactory.getLog(TraderCoreSim.class);

    @Resource
    private ExDataServiceFactory exServiceFactory;

    @Resource
    RecordService  recordService;

    private final  static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final  static SimpleDateFormat fileNameSdf=new SimpleDateFormat("yyyyMMdd");

    //the minimum price different rate for bid and ask.
    private final static double minPriceDiff = 0.006;
    private final static double minTradableValue = 0.0005;
    private final static double maxTradableValue = 0.01;
    private final static double exchangeTradeCostRate = 0.002;

    // return code:
    // 0 : price diff too small
    // 1 : successful
    // 2 : tradable value too small
    // 3 : not enough bcoin on lower
    // 4 : sell failed;
    // 5 : buy failed;
    public  int  doTrade(BuniessDataContext context, Depth[] higherBids, Depth[] lowerAsks, double coinBalanceHigher, double bcoinBalanceLower,String icoCpl,String higherEx,String lowerEx){

        //这是交易流水bean
        RecordTradeTurnoverDTO tradeTurnover=(RecordTradeTurnoverDTO)context.getAttr("tradeTrunover");

        double higher_bid_1_val;
        double higher_bid_1_qtty;
        double higher_bid_2_val;
        double higher_bid_2_qtty;

        double lower_ask_1_val;
        double lower_ask_1_qtty;
        double lower_ask_2_val;
        double lower_ask_2_qtty;

        double priceDiff1;
        double minTradbleQtty;
        double tradeValueBuy;
        double tradeValueSell;
        double tradeValueMargin;
        double tradeValueMarginPct;

        double minQttyHigher;
        double minQttyLower;
        
        String strTxtFile = higherEx+"====>"+lowerEx+" coin:"+icoCpl+  "********************************* \n";


        higher_bid_1_val = higherBids[0].getPrice();
        higher_bid_1_qtty = higherBids[0].getQuanatity();
        higher_bid_2_val = higherBids[1].getPrice();
        higher_bid_2_qtty = higherBids[1].getQuanatity();



        lower_ask_1_val = lowerAsks[0].getPrice();
        lower_ask_1_qtty = lowerAsks[0].getQuanatity();
        lower_ask_2_val = lowerAsks[1].getPrice();
        lower_ask_2_qtty = lowerAsks[1].getQuanatity();



        //set a temp value for coinBalanceHigher and bcoinBbalanceLower
        double coinBalanceHigherTemp = 9999;
        double bcoinBalanceLowerTemp = 9999;


        IcoAccount highIcoAccount=null;
        IcoAccount lowIcoAccount=null;

        try {
            highIcoAccount=   context.getUserExWalletData(higherEx);

            lowIcoAccount=   context.getUserExWalletData(lowerEx);

            coinBalanceHigherTemp=highIcoAccount.getIcoValue(icoCpl);

            bcoinBalanceLowerTemp=lowIcoAccount.getIcoValue("btc");



        priceDiff1 = (higher_bid_2_val - lower_ask_2_val)/lower_ask_2_val;


            //必要的日志信息
            tradeTurnover.setHigherBidVal1(higher_bid_1_val);
            tradeTurnover.setHigherBidVal2(higher_bid_2_val);
            tradeTurnover.setHigherBidVal3(higherBids[2].getPrice());
            tradeTurnover.setLowerAskVal1(lower_ask_1_val);
            tradeTurnover.setLowerAskVal2(lower_ask_2_val);
            tradeTurnover.setLowerAskVal3( lowerAsks[2].getPrice());
            tradeTurnover.setPriceDiff(priceDiff1);
        if(priceDiff1 < minPriceDiff){
            String str = " price gap too small "+ priceDiff1 * 100 + "%, by" +" bid 2 :" + higher_bid_2_val+" ( bid1 "+higher_bid_1_val+") " + " and ask 2: " + lower_ask_2_val+" ( ask1 "+lower_ask_1_val+")\n";
            System.out.println(str);
            tradeTurnover.setStatus(0);
            tradeTurnover.setMsg(" price gap too small "+ priceDiff1 * 100 + "%");
            return 0;
        }


        // determine the miminum tradable quantity, which is the smallest among
        // higher bid 1, 2, lower ask 1, 2 and coin balance on higher.
        // v1.1 -----
        // which logic is better?  use the smallest among all bids and asks.
        // or use average volume between first 2? or first 3?
        // if volume is too small, we lost the chance. if volume set too large, we take the risk.
        // in version 1.1, I take the conservative path,
        //    which is to use the smaller number between the 60% volume of 2nd price and the average of 1st and 2nd price.
        //
        minQttyHigher = (higher_bid_1_qtty + higher_bid_2_qtty)/2;
        minQttyLower =  (lower_ask_1_qtty + lower_ask_2_qtty)/2;

        minTradbleQtty = Math.min(minQttyHigher, minQttyLower);
        
        //select the smaller one, either the minTradbleQtty, or the balance amount of the coin of the higher exchange. 
        minTradbleQtty = Math.min(coinBalanceHigherTemp, minTradbleQtty);

        minTradbleQtty = Math.min(bcoinBalanceLowerTemp*(1-exchangeTradeCostRate)/lower_ask_2_val, minTradbleQtty);

        minTradbleQtty = Math.min(maxTradableValue/lower_ask_2_val, minTradbleQtty);


        //calculate tradable value, which is the minimum tradable quantity * lower_ask_2_val;
        tradeValueBuy = minTradbleQtty * lower_ask_2_val;
        tradeValueSell = minTradbleQtty * higher_bid_2_val;
        String str1 = "minimum quantity selection : minQttyHigher: " + minQttyHigher + ", minQttyLower: " + minQttyLower +
                        ", coinBalanceHigher: " + coinBalanceHigherTemp + ", maxBuyCoinLower: " + bcoinBalanceLowerTemp/lower_ask_2_val + 
                        ", maxTradeValueCoin: " +maxTradableValue/lower_ask_2_val + " . \n" ;
        System.out.println(str1);

            tradeTurnover.setMinTradbleQtty(minTradbleQtty);
            tradeTurnover.setTradeValueBuy(tradeValueBuy);
            tradeTurnover.setTradeValueSell(tradeValueSell);


        if (tradeValueBuy < minTradableValue  ){
            String str = " , Tradble value too small as : " + tradeValueBuy + " <  " + minTradableValue + "\n" ;
            System.out.println(str);

            tradeTurnover.setMsg(" Tradble value too small as : " + tradeValueBuy + " <  " + minTradableValue );
            tradeTurnover.setStatus(2);
            return 2;
        }

            // calculate trade value margin
        // !! here I  substract the possible exchange cost. which would be 0.4% at most.

        tradeValueMargin = tradeValueSell - tradeValueBuy -
                    tradeValueBuy * exchangeTradeCostRate - tradeValueSell * exchangeTradeCostRate;
        // better to calcualte the percentage as well!
        tradeValueMarginPct = tradeValueMargin / tradeValueBuy;
      //  tradeValueMarginPct = Math.round(tradeValueMarginPct);

        String str = "WOW! Possible arbitrage :  "+icoCpl+","+ higherEx +"-->"+lowerEx+"  " + tradeValueMargin + " of selling " + tradeValueSell + " and buying "+ tradeValueBuy +"  of base coin !\n" ;
            System.out.println(str);



            tradeTurnover.setTradeValueMargin(tradeValueMargin);
            tradeTurnover.setTradeValueMarginPct(tradeValueMarginPct);
            tradeTurnover.setMsg(str);
            tradeTurnover.setStatus(1);



            Double coinNum= highIcoAccount.getIcoValue(icoCpl);
           Double btcNum=  highIcoAccount.getBtc();
           Double coinNumLow= lowIcoAccount.getIcoValue(icoCpl);
           Double btcNumLow=  lowIcoAccount.getBtc();



            // sell and  minus cost
            coinNum=coinNum-minTradbleQtty ;

            btcNum=btcNum+minTradbleQtty*higher_bid_2_val - minTradbleQtty*higher_bid_2_val*exchangeTradeCostRate;

            highIcoAccount.setIcoValue(icoCpl,coinNum);
            highIcoAccount.setBtc(btcNum);

            coinNumLow=coinNumLow+minTradbleQtty;
            btcNumLow=btcNumLow-minTradbleQtty*lower_ask_2_val- minTradbleQtty*lower_ask_2_val*exchangeTradeCostRate;

            lowIcoAccount.setIcoValue(icoCpl,coinNumLow);
            lowIcoAccount.setBtc(btcNumLow);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;

    }
}

package com.sdyc.core;

import com.sdyc.beans.Depth;
import com.sdyc.sys.Config;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
@Component("traderCore")
public class TraderCore {

    private final  static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final  static SimpleDateFormat fileNameSdf=new SimpleDateFormat("yyyyMMdd");

    //the minimum price different rate for bid and ask.
    private final static double minPriceDiff = 0.008;
    private final static double minTradableValue = 0.001;
    private final static double exchangeTradeCostRate = 0.002;

    // return code:
    // 0 : price diff too small
    // 1 : successful
    // 2 : tradable value too small
    // 3 : not enough bcoin on lower
    // 4 : sell failed;
    // 5 : buy failed;
    public  int  doTrade(Depth[] higherBids, Depth[] lowerAsks, double coinBalanceHigher, double bcoinBalanceLower,String icoCpl,String higherEx,String lowerEx){

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


        higher_bid_1_val = higherBids[0].getPrice();
        higher_bid_1_qtty = higherBids[0].getQuanatity();
        higher_bid_2_val = higherBids[1].getPrice();
        higher_bid_2_qtty = higherBids[1].getQuanatity();

        lower_ask_1_val = lowerAsks[0].getPrice();
        lower_ask_1_qtty = lowerAsks[0].getQuanatity();
        lower_ask_2_val = lowerAsks[1].getPrice();
        lower_ask_2_qtty = lowerAsks[1].getQuanatity();

        //set a temp value for coinBalanceHigher and bcoinBbalanceLower
        double coinBalanceHigherTemp = 99999;
        double bcoinBalanceLowerTemp = 99999;

        priceDiff1 = (higher_bid_2_val - lower_ask_2_val)/lower_ask_2_val;

        if(priceDiff1 < minPriceDiff){
            String str = " price gap too small as bid 2 :" + higher_bid_2_val+" ("+higher_bid_1_val+") " + " and ask 2: " + lower_ask_2_val+" ("+lower_ask_1_val+")";
            System.out.println(str);
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
        minQttyHigher = Math.min(higher_bid_2_qtty  * 0.6, (higher_bid_1_qtty + higher_bid_2_qtty)/2);
        minQttyLower = Math.min(lower_ask_2_qtty * 0.6, (lower_ask_1_qtty + lower_ask_2_qtty)/2);

        minTradbleQtty = Math.min(minQttyHigher, minQttyLower);
        minTradbleQtty = Math.min(coinBalanceHigherTemp, minTradbleQtty);

        //calculate tradable value, which is the minimum tradable quantity * lower_ask_2_val;
        tradeValueBuy = minTradbleQtty * lower_ask_2_val;
        tradeValueSell = minTradbleQtty * higher_bid_2_val;
        String str1 = "minimum quantity selection :" + higher_bid_1_qtty + "," + higher_bid_2_qtty +
                        "," + lower_ask_1_qtty + "," + lower_ask_2_qtty;
        System.out.println(str1);

        if (tradeValueBuy < minTradableValue ){
            String str = "Tradble value too small as : " + minTradbleQtty + " * " + lower_ask_2_val + " =  " + tradeValueBuy ;
            System.out.println(str);
            return 2;
        }

        // calculate trade value margin
        // !! here I  substract the possible exchange cost. which would be 0.4% at most.

        tradeValueMargin = tradeValueSell - tradeValueBuy -
                    tradeValueBuy * exchangeTradeCostRate - tradeValueSell * exchangeTradeCostRate;
        // better to calcualte the percentage as well!
        tradeValueMarginPct = tradeValueMargin / tradeValueBuy;
      //  tradeValueMarginPct = Math.round(tradeValueMarginPct);

        String str = "WOW! Possible arbitrage of :ico couple is:  "+icoCpl+","+ higherEx +"-->"+lowerEx+"  " + tradeValueMargin + " of base coin !" ;


        System.out.println(str);

        StringBuffer csvBuffer=new StringBuffer();

        csvBuffer.append(icoCpl)
                .append(",").append(sdf.format(new Date()))
                .append(",").append(higherEx)
                .append(",").append(lowerEx)
                .append(",").append(tradeValueMargin)
                .append(",").append(tradeValueMarginPct)
                .append(",").append(minTradbleQtty)
                .append(",").append(tradeValueBuy)
                .append(",").append(tradeValueSell)
                .append("\n");


        String filePath= Config.get("log.dir")+"/output";
                 File outPut=new File(filePath);
                 if(!outPut.exists()){
                     outPut.mkdirs();
                 }
        File file=new File(filePath, fileNameSdf.format(new Date())+".csv");
        try {
            if(!file.exists()){
                FileUtils.write(file, "ico couple,date,higherEx,lowerEx,tradeValueMargin,tradeValueMarginPct,minTradbleQtty,tradeValueBuy,tradeValueSell\n", "gb2312", true);
            }

            FileUtils.write(file, csvBuffer.toString(), "gb2312", true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(csvBuffer);


        return 1;

    }
}

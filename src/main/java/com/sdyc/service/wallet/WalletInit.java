package com.sdyc.service.wallet;

import com.sdyc.beans.IcoAccount;
import com.sdyc.beans.PriceBean;
import com.sdyc.service.exapi.DataService;
import com.sdyc.service.exapi.ExDataServiceFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/19  15:32
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/19  15:32
 * Author:
 * </pre>
 */
@Component("walletInit")
public class WalletInit {

    @Resource
    ExDataServiceFactory factory;

    @Resource
    WalletService walletService;


    /**
     * 对resource/data目录下的文件进行加载
     * @param fileName
     * @return
     */
    public List<IcoAccount> loadAccountFile(String fileName,String userId){
        String filePath=this.getClass().getResource("/data").getPath()+"/"+fileName;
        ArrayList <IcoAccount>  accs=new ArrayList<>();
        try {
            List<String> datas= FileUtils.readLines(new File(filePath), "gb2312");
            String[] cps=datas.get(0).toLowerCase().split(",");
            for(int i=1;i<datas.size();i++){
                //一行是一个交易所
                IcoAccount icoAccount=new IcoAccount();
                String[] coins=datas.get(i).split(",");
                icoAccount.setExchange(coins[0]);
                icoAccount.setUserId(userId);

                accs.add(icoAccount);

                //每一列是币值
                for(int j=1;j<coins.length;j++){
                    try {
                        icoAccount.setIcoValue(cps[j] ,Double.parseDouble(coins[j].trim()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accs;

    }
    /**
     * 根据已有的icon信息初始化
     * @param userId
     * @param icoAccounts
     */
    public void doInitByIcoAccount(String userId,Double investBtc,String[] iconCpls,String[]exchanes,List<IcoAccount> icoAccounts){
        try {
            //先清除历史数据
            walletService.deleteUserDate(userId);
            walletService.addUserExSetting(userId, StringUtils.join(iconCpls,",")  , StringUtils.join(exchanes,","));

            walletService.addUserExData(icoAccounts,iconCpls);
            Double initBtc=0.0;
            for(IcoAccount icoAccount:icoAccounts){
                initBtc=initBtc+icoAccount.getBtc();

            }

            walletService.addUserBtc(userId,investBtc,initBtc,initBtc);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void  doInit(String userId, Double btcNum,String[] exchanes,String[] iconCpls ){
        try {


            TreeMap<String,IcoAccount> accMap= initWallet( btcNum,exchanes,iconCpls );

            //先清除历史数据
            walletService.deleteUserDate(userId);


            walletService.addUserExSetting(userId, StringUtils.join(iconCpls,",")  , StringUtils.join(exchanes,","));


            ArrayList<IcoAccount> accs=new ArrayList<IcoAccount>();
            Double btc=0.0;
            for(String ex:exchanes){
                IcoAccount icoAccount=  accMap.get(ex);
                icoAccount.setUserId(userId);
                btc=btc+icoAccount.getBtc();
                accs.add(icoAccount);
            }

            walletService.addUserExData(accs,iconCpls);

            walletService.addUserBtc(userId,btcNum,btc,btc);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 初始化钱包数据.  根据传入的
     * @param btcNum
     * @param exchanes
     * @param iconCpls
     */
    public TreeMap<String,IcoAccount> initWallet(Double btcNum,String[] exchanes,String[] iconCpls )throws Exception{
        /**100
         *        btc  a  b  c  d  e  f  g  h  i
         *         10 10 10 10 10  10 10 10 10 10
         *  1       2
         *  2       2
         *  3       2
         *  4       2
         *  5       2
         */
        //先初始化所有的x交易所的bean用来存放数据
        TreeMap<String,IcoAccount>  accountTreeMap=new TreeMap<String,IcoAccount>();
        try {



            for (int i = 0; i < exchanes.length; i++) {
                String exName = exchanes[i];
                IcoAccount account = new IcoAccount();
                account.setUserId("sdyc");
                account.setExchange(exName);
                account.setCreateDate(new Date());
                account.setUpdateDate(new Date());

                accountTreeMap.put(exName, account);
            }

            //把总币数要给各个ex 和 coin
            //先除以币数  得到 一个币 给所有的交易均分有多少个
            Double oneCoinAllex=btcNum/iconCpls.length;


            //再判断交易所 有没有这个币的交易. 没有的话 不给他分了
            //把有的几家再计数 然后均分.

            for(int e=0;e<iconCpls.length;e++ ) {

                ArrayList<String> hasThisCoinExs = new ArrayList<String>();

                String icon = iconCpls[e];

                for (int i = 0; i < exchanes.length; i++) {

                    String exName = exchanes[i];

                    //如果是btc 直接均分...这是锚定币
                    if (icon.equals("btc")) {
                        hasThisCoinExs.add(exName);
                    } else {
                        DataService exDaservice = factory.getService(exName);
                        String exCp = exDaservice.getExchangeCp(icon);
                        //调用每一家ex的数据服务 取他们家的币对. 如果有的话 才需要给他分.
                        if (StringUtils.isNotBlank(exCp) && !exCp.equals("NULL")) {
                            hasThisCoinExs.add(exName);
                        }
                    }

                }


                //把有这个币的ex数量获取到 . 如果数量小于两家.则跳出 说明这个币不能用作双边交易
                //钱包就没法正常初始化..直接抛异常
                if(hasThisCoinExs.size()<2){
                    System.out.print("this iocn ["+icon+"], there is not has more than 2 ex got it's buniess " );

                    throw  new Exception("this iocn ["+icon+"], there is not has more than 2 ex got it's buniess " );

                }

                //剩余有这个币的ex 能各自分多少..用每币总btc量 除以有币的ex的格式
                Double everyCoinBtc  =oneCoinAllex/hasThisCoinExs.size();

                for(String hasThisCoinExName :hasThisCoinExs){

                   IcoAccount exAccount= accountTreeMap.get(hasThisCoinExName);
                    //如果是btc 直接换算即可
                    if(icon.equals("btc")){
                        exAccount.setBtc(everyCoinBtc);
                    }else{
                        //其他币  需要兑换一下.
                        //先查兑换率
                        PriceBean priceBean=null;

                        try {
                            priceBean=    factory.getService(hasThisCoinExName).getPriceData(icon);
                        } catch (Exception e1) {
                            Thread.sleep(2000);
                            priceBean=    factory.getService(hasThisCoinExName).getPriceData(icon);
                        }

                        if(priceBean==null){
                                throw new Exception(icon+","+hasThisCoinExName+" error");

                         }



                        if(priceBean.getValue()==0){
                            exAccount.setIcoValue(icon,-1.0);
                        }else {
                            double coinNun=  everyCoinBtc/ priceBean.getValue();
                            exAccount.setIcoValue(icon,coinNun);
                        }





                    }

                }

            }






        } catch (Exception e) {
            e.printStackTrace();
        }


        return  accountTreeMap;
    }
}

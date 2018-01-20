package com.sdyc.service.wallet;

import com.sdyc.beans.IcoAccount;
import com.sdyc.beans.PriceBean;
import com.sdyc.service.exapi.DataService;
import com.sdyc.service.exapi.ExDataServiceFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/19  15:32
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     ����ʶ���˳���Ϣ�Ƽ����޹�˾
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


    public void  doInit(String userId, Double btcNum,String[] exchanes,String[] iconCpls ){
        try {


            TreeMap<String,IcoAccount> accMap= initWallet( btcNum,exchanes,iconCpls );

            walletService.deleteUserDate(userId);

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
     * ��ʼ��Ǯ������.  ���ݴ����
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
        //�ȳ�ʼ�����е�x��������bean�����������
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

            //���ܱ���Ҫ������ex �� coin
            //�ȳ��Ա���  �õ� һ���� �����еĽ��׾����ж��ٸ�
            Double oneCoinAllex=btcNum/iconCpls.length;


            //���жϽ����� ��û������ҵĽ���. û�еĻ� ����������
            //���еļ����ټ��� Ȼ�����.

            for(int e=0;e<iconCpls.length;e++ ) {

                ArrayList<String> hasThisCoinExs = new ArrayList<String>();

                String icon = iconCpls[e];

                for (int i = 0; i < exchanes.length; i++) {

                    String exName = exchanes[i];

                    //�����btc ֱ�Ӿ���...����ê����
                    if (icon.equals("btc")) {
                        hasThisCoinExs.add(exName);
                    } else {
                        DataService exDaservice = factory.getService(exName);
                        String exCp = exDaservice.getExchangeCp(icon);
                        //����ÿһ��ex�����ݷ��� ȡ���ǼҵıҶ�. ����еĻ� ����Ҫ������.
                        if (StringUtils.isNotBlank(exCp) && !exCp.equals("NULL")) {
                            hasThisCoinExs.add(exName);
                        }
                    }

                }


                //��������ҵ�ex������ȡ�� . �������С������.������ ˵������Ҳ�������˫�߽���
                //Ǯ����û��������ʼ��..ֱ�����쳣
                if(hasThisCoinExs.size()<2){
                    System.out.print("this iocn ["+icon+"], there is not has more than 2 ex got it's buniess " );

                    throw  new Exception("this iocn ["+icon+"], there is not has more than 2 ex got it's buniess " );

                }

                //ʣ��������ҵ�ex �ܸ��Էֶ���..��ÿ����btc�� �����бҵ�ex�ĸ�ʽ
                Double everyCoinBtc  =oneCoinAllex/hasThisCoinExs.size();

                for(String hasThisCoinExName :hasThisCoinExs){

                   IcoAccount exAccount= accountTreeMap.get(hasThisCoinExName);
                    //�����btc ֱ�ӻ��㼴��
                    if(icon.equals("btc")){
                        exAccount.setBtc(everyCoinBtc);
                    }else{
                        //������  ��Ҫ�һ�һ��.
                        //�Ȳ�һ���
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

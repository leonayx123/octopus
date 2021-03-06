package com.sdyc.service.wallet;

import com.sdyc.beans.IcoAccount;
import com.sdyc.dto.AccUserExchangeDTO;
import com.sdyc.service.account.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/19  17:22
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/19  17:22
 * Author:
 * </pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class WalletInitTest {

    @Resource
    AccountService accountService;

    @Resource
    WalletInit  walletInit;
    @Test
    public void testIcoAccountInit(){
        String userId="litest";
        Double investBtc=3.54252588;
        String[]cpls="btc,eth,etc,bch,xrp,iota,bcd,bcx,btg,btm,eos,omg,qtum,zec".split(",");
        String[]exchanes="okex,gateIo".split(",");

        List<IcoAccount > assc= walletInit.loadAccountFile("litest.csv",userId);
        walletInit.doInitByIcoAccount(userId,investBtc,cpls,exchanes,assc);
    }
//

   @Test
    public void testInitWallet() throws Exception {
       //String userId="litest";
       //List<IcoAccount > assc= walletInit.loadAccountFile("litest.csv",userId);

       List<AccUserExchangeDTO> exs= accountService.getUserExchanges("litest");

       System.out.print(exs.size());
        //Double btcnum= 11.23608;
        //String[] exs="okex,gateIo,binance,huobipro,zb".split(",");
        //String[]iconCps="btc,eth,xrp,bch,ada,ltc,xem,xlm,neo,iota".split(",");
        //
        //
        //walletInit.doInit("1mil10coins",btcnum,exs,iconCps);

//        TreeMap<String,IcoAccount> accountTreeMap= walletInit.initWallet(btcnum,exs,iconCps);
//
//       String path= Config.get("log.dir")+"/output/walleinit.csv";
//        File wl=new File(path);
//        FileUtils.write(wl, "coin,btc,eth,xrp,bch,ada,ltc,xem,xlm,neo,iota\n");
//        for(String es:exs){
//            IcoAccount esAccount= accountTreeMap.get(es);


//
//            FileUtils.write(wl,esAccount.getExchange()+","+
//                    esAccount.getBtc()+","+
//                    esAccount.getEth()+","+
//                    esAccount.getXrp()+","+
//                    esAccount.getBch()+","+
//                    esAccount.getAda()+","+
//                    esAccount.getLtc()+","+
//                    esAccount.getXem()+","+
//                    esAccount.getXlm()+","+
//                    esAccount.getNeo()+","+
//                    esAccount.getIota()+"\n",
//                    true);


    }
}
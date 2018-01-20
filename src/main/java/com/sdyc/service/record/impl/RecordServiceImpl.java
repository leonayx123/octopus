package com.sdyc.service.record.impl;

import com.sdyc.beans.IcoAccount;
import com.sdyc.beans.TradeRecordBean;
import com.sdyc.dto.TUserBtcDTO;
import com.sdyc.service.record.RecordService;
import com.sdyc.service.wallet.WalletService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource
    WalletService walletService;

    private static   List<IcoAccount>  accs=null;




    private  static String userId="1mil10coins";


    private static String upfs(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;

    }

    @Override
    public TUserBtcDTO getBtcChange() {

       TUserBtcDTO  userBtcDTO= walletService.getUserBtc(userId);

        return  userBtcDTO;

    }



    /**
     * 获取个人的账号数据
     *
     * @param userId@return
     */
    @Override
    public List<IcoAccount> getAccountData(String userId,Boolean reload)throws Exception{

        if(reload){
            accs=  walletService.getAccount(userId);
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

        for(IcoAccount acc: accs){

            if(acc.getExchange().equals(exName)){
                return  acc;
            }

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

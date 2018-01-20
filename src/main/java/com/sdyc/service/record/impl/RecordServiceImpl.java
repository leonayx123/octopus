package com.sdyc.service.record.impl;

import com.sdyc.beans.IcoAccount;
import com.sdyc.beans.TradeRecordBean;
import com.sdyc.service.record.RecordService;
import com.sdyc.service.wallet.WalletService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    private static   List<IcoAccount>  icoAccounts=null;
    private static Map<String,Integer> icoAccMap=null;






    /**
     * 记录交易流水
     *
     * @param recordBean
     * @return
     */
    @Override
    public TradeRecordBean saveTradeRecord(TradeRecordBean recordBean) throws Exception {
        return null;
    }
}

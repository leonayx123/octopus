package com.sdyc.service.record;

import com.sdyc.beans.IcoAccount;
import com.sdyc.beans.TradeRecordBean;
import com.sdyc.dto.TUserBtcDTO;

import java.util.List;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/18  18:08
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 * 记录交易流水相关记录的服务
 *
 * Modify:      2018/1/18  18:08
 * Author:
 * </pre>
 */

public interface RecordService {

    public TUserBtcDTO getBtcChange();



    /**
     * 获取个人的账号数据
     * @param
     * @return
     */
    public List<IcoAccount>  getAccountData(String userId,Boolean reload)throws Exception;


    /**
     * 获取个人的账号数据
     * @param
     * @return
     */
    public IcoAccount  getUserExAccountData(String userId,String exName)throws Exception;

    /**
     * 修改一个account的信息
     * @return
     */
    public  IcoAccount updateAccount(IcoAccount account)throws Exception;


    /**
     * 修改一个account的某个币的数值
     * @return
     */
    public  IcoAccount updateAccountIco(long  id, String cp ,double num )throws Exception;


    /**
     * 记录交易流水
     * @return
     */
    public TradeRecordBean  saveTradeRecodr(TradeRecordBean recordBean) throws Exception;


}

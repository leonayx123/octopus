package com.sdyc.service.wallet;

import com.sdyc.beans.IcoAccount;
import com.sdyc.dto.TUserBtcDTO;

import java.util.List;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/20  17:54
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/20  17:54
 * Author:
 * </pre>
 */

public interface WalletService {

    /**
     * 删除用户数据
     * @throws Exception
     */
    public  void  deleteUserDate(String userId) throws Exception;

    /**
     * 添加用户交易所的数据
     * @throws Exception
     */
    public  void  addUserExData(List<IcoAccount> accounts,String[]icons) throws Exception;


    public List<IcoAccount> getAccount(String userId)throws Exception;
    /**
     * 修改用户的某个ex 的coin值
     * @param userId
     * @param coinId
     * @param amt
     */
    public void  updateUserCoinAmt(String userId,String ex,String coinId,Double amt);

    public TUserBtcDTO getUserBtc(String userid);

    public void  addUserBtc(String userId,Double amt1,Double amt2,Double amt3);

    public void  updateUserBtc(String userId,Double amt,String field);
}

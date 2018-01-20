package com.sdyc.service.wallet;

import com.sdyc.beans.IcoAccount;
import com.sdyc.dto.AccUserBtcDTO;
import com.sdyc.dto.AccUserExSetingDTO;

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
     * 添加用户关注的币对的信息
     * @param coins
     * @throws Exception
     */
    public void addUserExSetting(String userId, String  coins,String exNames)throws Exception;

    /**
     * 获取用户的币对数据
     * @param userId
     * @return
     * @throws Exception
     */
    public AccUserExSetingDTO getUserExSetting(String userId)throws Exception;



    /**
     * 添加用户交易所的数据
     * @throws Exception
     */
    public  void  addUserExData(List<IcoAccount> accounts,String[]icons) throws Exception;


    /**
     * 查询用户的总账号数据
     * @param userId
     * @return
     * @throws Exception
     */
    public List<IcoAccount> getAccount(String userId)throws Exception;


    /**
     * 查询用户的单个exchange账号数据
     * @param userId
     * @return
     * @throws Exception
     */
    public  IcoAccount getExAccount(String userId,String exchange)throws Exception;
    /**
     * 修改用户的某个ex 的coin值
     * @param userId
     * @param coinId
     * @param amt
     */
    public void  updateUserCoinAmt(String userId,String ex,String coinId,Double amt);


    /**
     * 修改用户的某个ex 的coin值
     * @param userId
     * @param cps
     * @param datas
     */
    public void  batchUpdateUserCoinAmt(String userId,String[]cps,List<IcoAccount> datas)throws Exception;

    public AccUserBtcDTO getUserBtc(String userid);

    public void  addUserBtc(String userId,Double amt1,Double amt2,Double amt3);

    public void  updateUserBtc(AccUserBtcDTO userBtc);
}

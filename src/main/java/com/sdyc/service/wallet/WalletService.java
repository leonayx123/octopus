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
 * Company:     ����ʶ���˳���Ϣ�Ƽ����޹�˾
 * Discription:
 *
 * Modify:      2018/1/20  17:54
 * Author:
 * </pre>
 */

public interface WalletService {

    /**
     * ɾ���û�����
     * @throws Exception
     */
    public  void  deleteUserDate(String userId) throws Exception;

    /**
     * ����û���ע�ıҶԵ���Ϣ
     * @param coins
     * @throws Exception
     */
    public void addUserExSetting(String userId, String  coins,String exNames)throws Exception;

    /**
     * ��ȡ�û��ıҶ�����
     * @param userId
     * @return
     * @throws Exception
     */
    public AccUserExSetingDTO getUserExSetting(String userId)throws Exception;



    /**
     * ����û�������������
     * @throws Exception
     */
    public  void  addUserExData(List<IcoAccount> accounts,String[]icons) throws Exception;


    /**
     * ��ѯ�û������˺�����
     * @param userId
     * @return
     * @throws Exception
     */
    public List<IcoAccount> getAccount(String userId)throws Exception;


    /**
     * ��ѯ�û��ĵ���exchange�˺�����
     * @param userId
     * @return
     * @throws Exception
     */
    public  IcoAccount getExAccount(String userId,String exchange)throws Exception;
    /**
     * �޸��û���ĳ��ex ��coinֵ
     * @param userId
     * @param coinId
     * @param amt
     */
    public void  updateUserCoinAmt(String userId,String ex,String coinId,Double amt);


    /**
     * �޸��û���ĳ��ex ��coinֵ
     * @param userId
     * @param cps
     * @param datas
     */
    public void  batchUpdateUserCoinAmt(String userId,String[]cps,List<IcoAccount> datas)throws Exception;

    public AccUserBtcDTO getUserBtc(String userid);

    public void  addUserBtc(String userId,Double amt1,Double amt2,Double amt3);

    public void  updateUserBtc(AccUserBtcDTO userBtc);
}

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
 * Company:     ����ʶ���˳���Ϣ�Ƽ����޹�˾
 * Discription:
 * ��¼������ˮ��ؼ�¼�ķ���
 *
 * Modify:      2018/1/18  18:08
 * Author:
 * </pre>
 */

public interface RecordService {

    public TUserBtcDTO getBtcChange();



    /**
     * ��ȡ���˵��˺�����
     * @param
     * @return
     */
    public List<IcoAccount>  getAccountData(String userId,Boolean reload)throws Exception;


    /**
     * ��ȡ���˵��˺�����
     * @param
     * @return
     */
    public IcoAccount  getUserExAccountData(String userId,String exName)throws Exception;

    /**
     * �޸�һ��account����Ϣ
     * @return
     */
    public  IcoAccount updateAccount(IcoAccount account)throws Exception;


    /**
     * �޸�һ��account��ĳ���ҵ���ֵ
     * @return
     */
    public  IcoAccount updateAccountIco(long  id, String cp ,double num )throws Exception;


    /**
     * ��¼������ˮ
     * @return
     */
    public TradeRecordBean  saveTradeRecodr(TradeRecordBean recordBean) throws Exception;


}

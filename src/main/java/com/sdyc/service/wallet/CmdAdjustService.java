package com.sdyc.service.wallet;

import com.sdyc.dto.CmdBalanceAdjustDTO;

import java.util.List;

/**
 * Created by yangxun on 2018-01-21.
 */
public interface CmdAdjustService {

    /**
     * ����ִ�������������
     * @return
     */
    public boolean   doAllCmds();

    /**
     * ִ��ÿһ������ ������
     * @return
     */
    public boolean  DoCmdAdjust(CmdBalanceAdjustDTO cmd) throws  Exception;



    /**
     * ��ȡ���д��������б�
     * @return
     */
    public List<CmdBalanceAdjustDTO>   getAllBalanceAdjust() throws  Exception;


    /**
     * ִ����������
     * @param
     */
    public void changeStatusForAdjust(CmdBalanceAdjustDTO cmd) throws  Exception;




}

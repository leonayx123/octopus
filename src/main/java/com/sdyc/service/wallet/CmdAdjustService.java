package com.sdyc.service.wallet;

import com.sdyc.dto.CmdBalanceAdjustDTO;

import java.util.List;

/**
 * Created by yangxun on 2018-01-21.
 */
public interface CmdAdjustService {

    /**
     * 这是执行命令的主程序
     * @return
     */
    public boolean   doAllCmds();

    /**
     * 执行每一条命令 带事务
     * @return
     */
    public boolean  DoCmdAdjust(CmdBalanceAdjustDTO cmd) throws  Exception;



    /**
     * 获取所有代办命令列表
     * @return
     */
    public List<CmdBalanceAdjustDTO>   getAllBalanceAdjust() throws  Exception;


    /**
     * 执行所有命令
     * @param
     */
    public void changeStatusForAdjust(CmdBalanceAdjustDTO cmd) throws  Exception;




}

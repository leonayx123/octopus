package com.sdyc.service.wallet.impl;

import com.sdyc.beans.IcoAccount;
import com.sdyc.dto.CmdBalanceAdjustDTO;
import com.sdyc.service.record.RecordService;
import com.sdyc.service.wallet.CmdAdjustService;
import com.sdyc.service.wallet.WalletService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by yangxun on 2018-01-21.
 */
@Component("cmdAdjustService")
public class CmdAdjustServiceImpl implements CmdAdjustService {
    Log log= LogFactory.getLog(CmdAdjustServiceImpl.class);

    @Resource
    JdbcTemplate jdbcTemplate;

    @Resource
    WalletService walletService;

    @Resource
    RecordService recordService;


    /**
     * ������ִ������������
     * @return
     */
    public  boolean doAllCmds(){
        List<CmdBalanceAdjustDTO> cmds= this.getAllBalanceAdjust();

        for(CmdBalanceAdjustDTO cmd:cmds){
            try {
                this.DoCmdAdjust(cmd);
            }
            catch (Exception e){

                log.error("CMD_ADJUST ִ�г���..seqkey is:"+ cmd.getSeqkey(), e);
                //�����쳣�ع���.��״̬ д��FAIL.
                cmd.setStatus("FAIL");
                this.changeStatusForAdjust(cmd);
            }

        }

        return  true;
    }
    /**
     * ִ������
     *
     * @return
     */
    @Override
    @Transactional
    public boolean DoCmdAdjust(CmdBalanceAdjustDTO cmd)throws Exception{

         //��ȡ��������Ӧ���˺�����
        IcoAccount account=  walletService.getExAccount(cmd.getUserId(), cmd.getExchangeId());

         //��ԭ�������ݼ������õ����ݻ�����µ�����
         Double newAmt=account.getIcoValue(cmd.getCoinId());
         newAmt=newAmt+cmd.getAmtChange().doubleValue();

         //ִ������
         walletService.updateUserCoinAmt(
                 cmd.getUserId(),
                 cmd.getExchangeId(),
                 cmd.getCoinId(),
                 newAmt
              );

        //ִ�гɹ��Ļ� ����״̬ΪSUCCESS
        cmd.setStatus("SUCCESS");
        this.changeStatusForAdjust(cmd);

        return true;
    }

    /**
     * ��ȡ���д��������б�
     *
     * @return
     */
    @Override
    public List<CmdBalanceAdjustDTO> getAllBalanceAdjust() {
        String sql="select * from cmd_balance_adjust where adjust_set_time <=? and status='TODO' ";

      List<CmdBalanceAdjustDTO>  cmds= jdbcTemplate.query(sql, new Object[]{new Date()},
                new BeanPropertyRowMapper<CmdBalanceAdjustDTO>(CmdBalanceAdjustDTO.class));

        return cmds;
    }

    /**
     * ִ����������,�޸�״̬
     *
     * @param
     */
    @Override
    public void changeStatusForAdjust(CmdBalanceAdjustDTO cmd) {

        String sql="update cmd_balance_adjust set status=?,adjust_time=? where seqkey=?  ";

        jdbcTemplate.update(sql,new Object[]{cmd.getStatus(),new Date(),cmd.getSeqkey()});

    }


}

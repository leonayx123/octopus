package com.sdyc.service.record.impl;

import com.sdyc.beans.IcoAccount;
import com.sdyc.service.TestBaseSetupService;
import com.sdyc.service.exapi.DataService;
import com.sdyc.service.record.RecordService;
import org.junit.Test;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/18  19:20
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/18  19:20
 * Author:
 * </pre>
 */

public class RecordServiceImplTest extends TestBaseSetupService{

    @Resource
    RecordService recordService;
    /**
     * 获取当前的测试类实例
     *
     * @return
     */
    @Override
    protected DataService getTestService() throws Exception {
        return null;
    }

    @Test
    public void testGetAccountData() throws Exception {

        IcoAccount icoAccount=new IcoAccount();

       Method mm= icoAccount.getClass().getDeclaredMethod("setXrp",Double.class);

       //List<IcoAccount>  accs= recordService.getAccountData(null);

       // System.out.print(accs);

    }

    @Test
    public void testUpdateAccount() throws Exception {

    }

    @Test
    public void testUpdateAccountIco() throws Exception {

    }

    @Test
    public void testSaveTradeRecodr() throws Exception {

    }
}
package com.sdyc.service;

import com.sdyc.service.exapi.DataService;
import com.sdyc.service.exapi.ExDataServiceFactory;
import com.sdyc.utils.HttpUtilManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/17  15:08
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/17  15:08
 * Author:
 * </pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public abstract class TestBaseSetupService {

    @Resource
    private ExDataServiceFactory exServiceFactory;


    protected ExDataServiceFactory getFactory(){
        return exServiceFactory;
    }

    @BeforeClass
    public static void setUp(){
        HttpUtilManager.getInstance();
    }

    /**
     * 获取当前的测试类实例
     * @return
     */
    protected abstract DataService getTestService()throws Exception;
}

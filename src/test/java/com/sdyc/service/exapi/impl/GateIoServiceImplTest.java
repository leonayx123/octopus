package com.sdyc.service.exapi.impl;

import com.sdyc.beans.Depth;
import com.sdyc.beans.PriceBean;
import com.sdyc.service.exapi.DataService;
import com.sdyc.service.TestBaseSetupService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/16  16:38
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/16  16:38
 * Author:
 * </pre>
 */

public class GateIoServiceImplTest extends TestBaseSetupService {


    /**
     * 获取当前的测试类实例
     *
     * @return
     */
    @Override
    protected DataService getTestService() throws Exception {
        return getFactory().getService("gateIo");
    }

    @Test
    public void testGetPriceData() throws Exception {
        PriceBean priceBean= getTestService().getPriceData("eth");
        Assert.assertNotNull(priceBean);


        System.out.println(getTestService().getExchangeName() + " testGetPriceData:" + priceBean.getName() + "," + priceBean.getValue());

    }

    @Test
    public void testGetDeepData() throws Exception {
        //卖 要从低到高拍
        Depth[] asks= getTestService().getDeepData("eth", "asks");
        Assert.assertNotNull(asks);
        Assert.assertTrue(asks.length>0);
        Assert.assertTrue(asks[0].getPrice()< asks[1].getPrice() );

        System.out.println(getTestService().getExchangeName()+" testGetDeepData  asks: "+ Arrays.toString(asks));

        //买 要从高到低拍
        Depth[] bids= getTestService().getDeepData("eth", "bids");
        Assert.assertNotNull(bids);
        Assert.assertTrue(bids.length>0);
        Assert.assertTrue(bids[0].getPrice() > bids[1].getPrice());

        System.out.println(getTestService().getExchangeName() + "  testGetDeepData  bids: " + Arrays.toString(bids));

    }



    @Test
    public void testGetBalances() throws Exception {

        getTestService().getBalances();

    }

    @Test
    public void testBuy() throws Exception {

    }

    @Test
    public void testSell() throws Exception {

    }

    @Test
    public void testGetOrder() throws Exception {

    }
}
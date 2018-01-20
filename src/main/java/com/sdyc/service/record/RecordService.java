package com.sdyc.service.record;

import com.sdyc.beans.TradeRecordBean;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/18  18:08
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *  这是一次任务周期的数据服务. 会查询出这次任务需要的数据.然后局部的修改
 *
 * Modify:      2018/1/18  18:08
 * Author:
 * </pre>
 */

public interface RecordService {

    /**
     * 记录交易流水
     *
     * @param recordBean
     * @return
     */
    public TradeRecordBean saveTradeRecord(TradeRecordBean recordBean) throws Exception ;





}

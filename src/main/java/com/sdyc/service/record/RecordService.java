package com.sdyc.service.record;

import com.sdyc.beans.TradeRecordBean;
import com.sdyc.dto.RecordBtcAddDTO;
import com.sdyc.dto.RecordTradeTurnoverDTO;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/18  18:08
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:    
 * Discription:  用来记录日志和流水
 *  
 *
 * Modify:      2018/1/18  18:08
 * Author:
 * </pre>
 */

public interface RecordService {

    /**
     *
     *保存交易对比记录
     * @param tradeTurnover
     * @return
     */
    public TradeRecordBean saveTradeRecord(RecordTradeTurnoverDTO tradeTurnover) throws Exception ;



    /**
     *
     *保存btc增长记录
     * @param
     * @return
     */
    public TradeRecordBean saveTradeRecord(RecordBtcAddDTO recordBtcAdd) throws Exception ;

}

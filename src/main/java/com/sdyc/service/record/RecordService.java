package com.sdyc.service.record;

import com.sdyc.dto.RecordBtcAddDTO;
import com.sdyc.dto.RecordTradeTurnoverDTO;
import com.sdyc.dto.RecordWalletSpotDTO;

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
    public RecordTradeTurnoverDTO saveTradeRecord(RecordTradeTurnoverDTO tradeTurnover) throws Exception ;



    /**
     *
     *保存btc增长记录
     * @param
     * @return
     */
    public RecordBtcAddDTO saveBtcAddRecord(RecordBtcAddDTO recordBtcAdd) throws Exception ;

    /**
     * 保存钱包快照数据
     * @param spotDTO
     * @return
     * @throws Exception
     */
    public RecordWalletSpotDTO saveWalletSpotRecord(RecordWalletSpotDTO spotDTO) throws Exception ;

}

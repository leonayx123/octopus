package com.sdyc.service.record.impl;

import com.sdyc.dto.RecordBtcAddDTO;
import com.sdyc.dto.RecordTradeTurnoverDTO;
import com.sdyc.dto.RecordWalletSpotDTO;
import com.sdyc.service.record.RecordService;
import com.sdyc.utils.DButil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/18  18:52
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:   
 * Discription:
 *
 * Modify:      2018/1/18  18:52
 * Author:
 * </pre>
 */
@Component("recordService")
public class RecordServiceImpl implements RecordService {
    @Resource
    JdbcTemplate jdbcTemplate;

    /**
     * 保存交易对比记录
     *
     * @param tradeTurnover
     * @return
     */
    @Override
    public RecordTradeTurnoverDTO saveTradeRecord(RecordTradeTurnoverDTO tradeTurnover) throws Exception {
        tradeTurnover.setTime(new Timestamp(new Date().getTime()));
        KeyHolder KEY= DButil.insertReturnPk("record_trade_turnover", tradeTurnover, jdbcTemplate);
        tradeTurnover.setSeqkey(KEY.getKey().longValue());
        return tradeTurnover;
    }

    /**
     * 保存btc增长记录
     *
     * @param recordBtcAdd@return
     */
    @Override
    public RecordBtcAddDTO saveBtcAddRecord(RecordBtcAddDTO recordBtcAdd) throws Exception {
        recordBtcAdd.setTime(new Timestamp(new Date().getTime()));
        KeyHolder KEY= DButil.insertReturnPk("record_btc_add", recordBtcAdd, jdbcTemplate);
        recordBtcAdd.setSeqkey(KEY.getKey().longValue());
        return recordBtcAdd;
    }

    /**
     * 保存钱包快照数据
     *
     * @param spotDTO
     * @return
     * @throws Exception
     */
    @Override
    public RecordWalletSpotDTO saveWalletSpotRecord(RecordWalletSpotDTO spotDTO) throws Exception {
        spotDTO.setUpdateDate(new Timestamp(new Date().getTime()));
        KeyHolder key= DButil.insertReturnPk("record_wallet_spot", spotDTO, jdbcTemplate);
        spotDTO.setSeqkey(key.getKey().longValue());
        return  spotDTO;
    }
}

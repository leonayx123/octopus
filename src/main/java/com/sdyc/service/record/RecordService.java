package com.sdyc.service.record;

import com.sdyc.beans.TradeRecordBean;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/18  18:08
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     ����ʶ���˳���Ϣ�Ƽ����޹�˾
 * Discription:
 *  ����һ���������ڵ����ݷ���. ���ѯ�����������Ҫ������.Ȼ��ֲ����޸�
 *
 * Modify:      2018/1/18  18:08
 * Author:
 * </pre>
 */

public interface RecordService {

    /**
     * ��¼������ˮ
     *
     * @param recordBean
     * @return
     */
    public TradeRecordBean saveTradeRecord(TradeRecordBean recordBean) throws Exception ;





}

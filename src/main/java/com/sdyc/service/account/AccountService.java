package com.sdyc.service.account;

import com.sdyc.dto.AccUserExchangeDTO;

import java.util.List;

/**
 * Created by yangxun on 2018-01-23.
 */
public interface AccountService {

    /**
     * 获取用户的交易所key之类的信息
     * @param userId
     * @return
     */
    public List<AccUserExchangeDTO> getUserExchanges(String userId);

}

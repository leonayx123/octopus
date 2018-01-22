package com.sdyc.service.account.impl;

import com.sdyc.dto.AccUserExchangeDTO;
import com.sdyc.service.account.AccountService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yangxun on 2018-01-23.
 */
@Component("accountService")
public class AccountServiceImpl implements AccountService {

    @Resource
    JdbcTemplate jdbcTemplate;
    /**
     * 获取用户的交易所key之类的信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<AccUserExchangeDTO> getUserExchanges(String userId) {

        String sql="select * from acc_user_exchange where userId=? ";

        return  jdbcTemplate.query(sql,new Object[]{userId},new BeanPropertyRowMapper<AccUserExchangeDTO>(AccUserExchangeDTO.class));
    }
}

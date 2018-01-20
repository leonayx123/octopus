package com.sdyc.service.wallet.impl;

import com.sdyc.beans.IcoAccount;
import com.sdyc.dto.AccCoinBalanceDTO;
import com.sdyc.dto.TUserBtcDTO;
import com.sdyc.service.wallet.WalletService;
import com.sdyc.utils.DButil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/20  17:55
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/20  17:55
 * Author:
 * </pre>
 */

@Component("walletService")
public class WalletServiceImpl implements WalletService {

    @Resource
    private JdbcTemplate jdbcTemplate;




    /**
     * 删除用户数据
     *
     * @param userId
     * @throws Exception
     */
    @Transactional
    public void deleteUserDate(String userId) throws Exception {

        String sql="delete from acc_user_exchange where userId=? ";
        String sql2="delete from acc_coin_balance where userId=? ";
        String sql3="delete from acc_excoin_spot where userId=? ";

        jdbcTemplate.update(sql,userId);
        jdbcTemplate.update(sql2,userId);
        jdbcTemplate.update(sql3,userId);

    }


    public List<IcoAccount> getAccount(String userId)throws Exception{
        HashMap<String,IcoAccount> bmap=new HashMap<String, IcoAccount>();
        String sql= "select * from acc_coin_balance where userId=? ";
        List<AccCoinBalanceDTO> bls=  jdbcTemplate.query(sql, new Object[]{userId},new BeanPropertyRowMapper<AccCoinBalanceDTO>(AccCoinBalanceDTO.class));

        for(AccCoinBalanceDTO acc:bls){
            IcoAccount  icoAccount=  bmap.get(acc.getExchangeId());
            if(icoAccount==null){
                icoAccount=new IcoAccount();
                icoAccount.setUserId(userId);
                bmap.put(acc.getExchangeId(),icoAccount);
            }

            try {
                icoAccount.setIcoValue(acc.getCoinId(),acc.getCoinAmt().doubleValue());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        Iterator<String> it= bmap.keySet().iterator();

        List<IcoAccount>  res=new ArrayList<IcoAccount>();
        while (it.hasNext()){
            String key=it.next();

            res.add(bmap.get(key));
        }

        return  res;

    }

    /**
     * 添加用户交易所的数据
     *
     * @param accounts
     * @throws Exception
     */
    public void addUserExData(List<IcoAccount> accounts,String[]icons) throws Exception {
        StringBuffer sql=
                new StringBuffer("insert into acc_user_exchange(guid,userId,exchangeId,createDate,isActive) ")
                .append(" values(?,?,?,?,?)");
        for(int i=0;i<accounts.size();i++){

            IcoAccount icoAccount= accounts.get(i);
            String userId=icoAccount.getUserId();
            jdbcTemplate.update(sql.toString(),
                    DButil.getUUId(),
                    icoAccount.getUserId(),
                    icoAccount.getExchange(),
                    new Date(),
                    1
             );

            StringBuffer bachsql=new StringBuffer("insert into acc_coin_balance ")
            .append("(guid,userId,exchangeId,coinId,coinAmt,createDate,updateDate) ")
            .append("values (?,?,?,?,?,?,?)");

            List<Object[]> ars=new ArrayList<Object[]>();
            for(String icon:icons){

                BigDecimal b   =   new   BigDecimal(icoAccount.getIcoValue(icon));
                double   f1   =   b.setScale(10,   BigDecimal.ROUND_HALF_UP).doubleValue();
//                ars.add(
//                );

                jdbcTemplate.update(bachsql.toString(),
                        new Object[]{
                                DButil.getUUId(),
                        userId,
                        icoAccount.getExchange(),
                        icon,
                        f1,
                        new Date(),
                        new Date()
                });
            }


        }


    }


    public void addUserBtc(String userId, Double amt1, Double amt2, Double amt3) {

        String sql3="delete from t_user_btc where userId=? ";
        jdbcTemplate.update(sql3,userId);

        String sql="insert into t_user_btc (userId,investBtc,initBtc,currBtc,updateDate) " +
                " VALUE (?,?,?,?,?)";

        jdbcTemplate.update(sql,userId,amt1,amt2,amt3,new Date());
    }


    @Override
    public TUserBtcDTO getUserBtc(String userid) {

        String sql= "select * from t_user_btc where userId=? ";
        List<TUserBtcDTO> rss= jdbcTemplate.query(sql, new BeanPropertyRowMapper<TUserBtcDTO>(TUserBtcDTO.class));

        if(rss!=null&&rss.size()>0){
            return  rss.get(0);

        }
        return null;
    }

    /**
     * 修改用户的某个ex 的coin值
     *
     * @param userId
     * @param coinId
     * @param amt
     */
    public void updateUserCoinAmt(String userId,String ex, String coinId, Double amt) {

        String sql="update acc_coin_balance set coinAmt=?,updateDate=? " +
                " where userId=? and exchangeId=? and coinId=? ";

        jdbcTemplate.update(sql,amt,new Date(),userId,ex,coinId );

    }

    public void updateUserBtc(String userId, Double amt, String field) {

        String sql="update t_user_btc set "+field+"=? where userId=? ";
        jdbcTemplate.update(sql,amt,userId);

    }
}

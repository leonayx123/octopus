package com.sdyc.service.wallet.impl;

import com.sdyc.beans.IcoAccount;
import com.sdyc.dto.AccCoinBalanceDTO;
import com.sdyc.dto.AccUserBtcDTO;
import com.sdyc.dto.AccUserExSetingDTO;
import com.sdyc.service.wallet.WalletService;
import com.sdyc.utils.DButil;
import junit.framework.Assert;
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
        String sql4="delete from acc_user_coins where userId=? ";

        jdbcTemplate.update(sql,userId);
        jdbcTemplate.update(sql2,userId);
        jdbcTemplate.update(sql3,userId);
        jdbcTemplate.update(sql4,userId);

    }






    @Override
    public List<IcoAccount> getAccount(String userId)throws Exception{
        HashMap<String,IcoAccount> bmap=new HashMap<String, IcoAccount>();
        String sql= "select * from acc_coin_balance where userId=? ";
        List<AccCoinBalanceDTO> bls=  jdbcTemplate.query(sql, new Object[]{userId},new BeanPropertyRowMapper<AccCoinBalanceDTO>(AccCoinBalanceDTO.class));

        for(AccCoinBalanceDTO acc:bls){
            IcoAccount  icoAccount=  bmap.get(acc.getExchangeId());
            if(icoAccount==null){
                icoAccount=new IcoAccount();
                icoAccount.setUserId(userId);
                icoAccount.setExchange(acc.getExchangeId());

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
     * 查询单个ex的账号数据
     * @param userId
     * @param exchange
     * @return
     * @throws Exception
     */
    @Override
    public IcoAccount getExAccount(String userId,String exchange)throws Exception{
        String sql= "select * from acc_coin_balance where userId=? and exchangeId=? ";
        List<AccCoinBalanceDTO> bls=  jdbcTemplate.query(sql, new Object[]{userId,exchange},new BeanPropertyRowMapper<AccCoinBalanceDTO>(AccCoinBalanceDTO.class));
        IcoAccount exAccount=new IcoAccount();

        for(AccCoinBalanceDTO acc:bls){
            try {
                exAccount.setIcoValue(acc.getCoinId(),acc.getCoinAmt().doubleValue());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return  exAccount;

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

        String sql3="delete from acc_user_btc where userId=? ";
        jdbcTemplate.update(sql3,userId);

        String sql="insert into acc_user_btc (userId,investBtc,initBtc,currBtc,updateDate) " +
                " VALUE (?,?,?,?,?)";

        jdbcTemplate.update(sql,userId,amt1,amt2,amt3,new Date());
    }


    @Override
    public AccUserBtcDTO getUserBtc(String userId) {

        String sql= "select * from acc_user_btc where userId=? ";
        List<AccUserBtcDTO> rss= jdbcTemplate.query(sql,new Object[]{userId}, new BeanPropertyRowMapper<AccUserBtcDTO>(AccUserBtcDTO.class));

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

    /**
     * 修改用户的某个ex 的coin值
     *
     * @param userId
     * @param cps
     * @param datas
     */
    @Override
    public void batchUpdateUserCoinAmt(String userId, String[] cps, List<IcoAccount> datas) throws Exception{
        String sql="update acc_coin_balance set coinAmt=?,updateDate=? where userId=? and exchangeId=? and coinId=?";
        List<Object[]> args=new ArrayList<Object[]>();
        for(IcoAccount icoAccount: datas){
            for(String cp:cps){
                args.add( new Object[]{
                        new BigDecimal(icoAccount.getIcoValue(cp)) ,
                        new Date(),
                        userId,
                        icoAccount.getExchange(),
                        cp
                });
            }
        }
        jdbcTemplate.batchUpdate(sql,args);
    }

    /**
     * 添加用户关注的币对的信息
     *
     * @param userId
     * @param coins
     * @param exNames
     * @throws Exception
     */
    @Override
    public void addUserExSetting(String userId, String coins, String exNames) throws Exception {


        String sql="insert into acc_user_ex_seting (userId,userCoins,userExchanges) values(?,?,?)";
        jdbcTemplate.update(sql,userId,coins,exNames);

    }

    /**
     * 获取用户的币对数据
     *
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public AccUserExSetingDTO getUserExSetting(String userId) throws Exception {
        String sql="select * from  acc_user_ex_seting where userId=? ";
        List<AccUserExSetingDTO> rss= jdbcTemplate.query(sql, new Object[]{userId},
                new BeanPropertyRowMapper<AccUserExSetingDTO>(AccUserExSetingDTO.class));

        if(rss!=null&&rss.size()>0){
            return rss.get(0);
        }
        return null;
    }

    @Override
    public void updateUserBtc(AccUserBtcDTO userBtc) {
        Assert.assertNotNull(userBtc.getUserId());

        ArrayList<Object> ars=new ArrayList<Object>();
        StringBuffer sql=new StringBuffer(" update acc_user_btc set updateDate =?  ");
        ars.add(new Date());

        if(userBtc.getInvestBtc()!=null){
            sql.append(" ,investBtc=?  ");
            ars.add(userBtc.getInvestBtc());
        }
        if(userBtc.getInitBtc()!=null){
            sql.append(" ,initBtc=? ");
            ars.add(userBtc.getInitBtc());
        }
        if(userBtc.getCurrBtc()!=null){
            sql.append(", currBtc =? ");
            ars.add(userBtc.getCurrBtc());
        }
        if(userBtc.getAddRatio()!=null){
            sql.append(", addRatio =? ");
            ars.add(userBtc.getAddRatio());
        }
        sql.append(" where userId=? ");
        ars.add(userBtc.getUserId());


        jdbcTemplate.update(sql.toString(),ars.toArray());

    }
}

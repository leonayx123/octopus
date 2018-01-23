package com.sdyc.core;

import com.sdyc.beans.ExAccount;
import com.sdyc.beans.IcoAccount;
import com.sdyc.dto.AccUserExchangeDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangxun on 2018-01-21.
 *
 * 用作在任务中传输数据用. 是数据的上下文. 需要的数据都可以往这里面放
 */
public class BuniessDataContext {

    private  Map<String,IcoAccount> taskIcoAccounts;

    private Map<String,ExAccount> exAccountMap;

    //存放一些其他业务变量
    private Map<String,Object>attrbuite=new HashMap<String, Object>();

    /**
     * 可以对这些业务变量增删改
     * @param k
     * @param o
     */
    public void setAttr(String k,Object o){
        this.attrbuite.put(k,o);
    }
    public Object getAttr(String key){
        return attrbuite.get(key);
    }
    public Object removeAttr(String key){
       return  attrbuite.remove(key);
    }

    /**
     * 初始化 需要账号信息 和 用户的key信息
     * @param accs
     * @param userExchangeDTOs
     */
    BuniessDataContext(List<IcoAccount> accs,List<AccUserExchangeDTO> userExchangeDTOs){
        this.taskIcoAccounts=new HashMap<String, IcoAccount>();
        for(int  i=0;i< accs.size();i++){
            IcoAccount icoAccount=accs.get(i);
            this.taskIcoAccounts.put(icoAccount.getExchange(),icoAccount);
        }

        this.exAccountMap=new HashMap<String, ExAccount>();
        for(AccUserExchangeDTO userExchangeDTO:userExchangeDTOs){
            ExAccount exAccount=new ExAccount();
            exAccount.setExchangeId(userExchangeDTO.getExchangeId());
            exAccount.setKey(userExchangeDTO.getKey());
            exAccount.setSecret(userExchangeDTO.getSecert());

            this.exAccountMap.put(userExchangeDTO.getExchangeId(),exAccount);
        }
    }


    /**
     * 返回最终的修改过的数据
     * @return
     */
    public List<IcoAccount> getContextIcoAccountDatas(){
        return  new ArrayList<IcoAccount>(taskIcoAccounts.values());
    }


    /**
     * 获取个人的账号数据
     * @param
     * @return
     */
    public IcoAccount  getUserExWalletData(String exName)throws Exception{
        return taskIcoAccounts.get(exName);
    }

    /**
     * 获取某个exchange的账号信息
     * @param exName
     * @return
     */
    public ExAccount getExchangeAccount(String exName){
        return this.exAccountMap.get(exName);
    }

    /**
     * 用完数据后 要销毁
     */
    public void  destory(){
        //taskIcoAccounts.clear();
        taskIcoAccounts=null;
    }

}

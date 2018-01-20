package com.sdyc.core;

import com.sdyc.beans.IcoAccount;

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

    private static Map<String,IcoAccount> taskIcoAccounts;

    BuniessDataContext(List<IcoAccount> accs){
        taskIcoAccounts=new HashMap<String, IcoAccount>();
        for(int  i=0;i< accs.size();i++){
            IcoAccount icoAccount=accs.get(i);
            taskIcoAccounts.put(icoAccount.getExchange(),icoAccount);
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
    public IcoAccount  getUserExAccountData(String exName)throws Exception{
        return taskIcoAccounts.get(exName);
    }

    /**
     * 用完数据后 要销毁
     */
    public void  destory(){
        //taskIcoAccounts.clear();
        taskIcoAccounts=null;
    }

}

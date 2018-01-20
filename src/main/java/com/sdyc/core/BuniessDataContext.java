package com.sdyc.core;

import com.sdyc.beans.IcoAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangxun on 2018-01-21.
 *
 * �����������д���������. �����ݵ�������. ��Ҫ�����ݶ��������������
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
     * �������յ��޸Ĺ�������
     * @return
     */
    public List<IcoAccount> getContextIcoAccountDatas(){
        return  new ArrayList<IcoAccount>(taskIcoAccounts.values());
    }


    /**
     * ��ȡ���˵��˺�����
     * @param
     * @return
     */
    public IcoAccount  getUserExAccountData(String exName)throws Exception{
        return taskIcoAccounts.get(exName);
    }

    /**
     * �������ݺ� Ҫ����
     */
    public void  destory(){
        //taskIcoAccounts.clear();
        taskIcoAccounts=null;
    }

}

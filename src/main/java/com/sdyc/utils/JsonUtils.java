package com.sdyc.utils;

import java.math.BigDecimal;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/17  15:45
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:     陕西识代运筹信息科技有限公司
 * Discription:
 *
 * Modify:      2018/1/17  15:45
 * Author:
 * </pre>
 */

public class JsonUtils {

    public  static  Double getDouble(Object obj){
        if(obj==null){
            return null;
        }
        Double res=null;
        if(obj instanceof String){
            res= Double.parseDouble( (String)obj);

        }else  if(obj instanceof BigDecimal){
            res=  ((BigDecimal)obj).doubleValue();

        }else  if(obj instanceof Integer){

            res=  ((Integer)obj).doubleValue();
        }

        return  res;

    }
}

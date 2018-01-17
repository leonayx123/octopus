package com.sdyc.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/17  13:33
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Company:
 * Discription: ����һ��������. ���ݽ���������.��ȡ��ǰ�����������ݷ�����
 *
 * Modify:      2018/1/17  13:33
 * Author:
 * </pre>
 */
@Component("exServiceFactory")
public class ExDataServiceFactory implements ApplicationContextAware {
      @Resource
      private ApplicationContext context=null;

      @Override
      public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.context=applicationContext;

      }

      /**
       * ��ȡһ�����׳��������ݷ���
       * @param exName
       * @return
       * @throws Exception
       */
      public DataService getService(String exName)throws Exception{
            if(StringUtils.isBlank(exName)){
                  return null;
            }

            String dataServiceName=exName+"DataService";

            DataService exService=context.getBean(dataServiceName,DataService.class);

            if(exService==null){
               throw  new  Exception("can't find your needed ExService by name:"+exName);
            }

            if(!exService.getExchangeName().equals(exName)){
                  throw  new  Exception("exname can't match with name in service:"+exName);
            }


            return  exService;
      }
}

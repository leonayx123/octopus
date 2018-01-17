package com.sdyc.core;

import com.sdyc.sys.Config;
import com.sdyc.utils.HttpUtilManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <pre>
 * User:        yangxun
 * Date:        2018/1/11  19:02
 * Email:       yangxun@nowledgedata.com.cn
 * Version      V1.0
 * Discription:
 *
 * Modify:      2018/1/11  19:02
 * Author:
 * </pre>
 */

public class Core {
    private static ApplicationContext ctx=null;



    public static   void  run(){

        Business business=ctx.getBean("business",Business.class);


        //this is our buniess run start
         business.doWork();

    }


    // system main entry,run this method
    public static  void  main(String args[]){

        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        Config.get("test");

        HttpUtilManager.getInstance();

        run();

    }


}

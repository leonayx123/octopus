package com.sdyc.core.task;

import com.sdyc.core.Business;
import com.sdyc.sys.Config;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 系统的主定时器。时间为分钟读取一次。可以做定时触发的任务
 * 修改频率可以改quartz.jobTrigger
 * @author yx
 * 
 */
@Component("systemQuartzHandle")
public class SystemQuartzHandle {

	@Resource
	Business business;


	/**
	 * 每分钟
	 */
	public void doProcess(){
		try {

			System.out.println("task is start now");
			System.out.println("============================================");
			if(Config.get("sys.runtask").trim().equals("on")){

				business.doWork();
			}


		} catch (Exception e) {
			
		}
	}
}

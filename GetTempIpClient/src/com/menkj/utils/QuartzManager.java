package com.menkj.utils;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzManager {
	   private static SchedulerFactory sf = new StdSchedulerFactory();  
	   private static String JOB_GROUP_NAME = "group1";  
	   private static String TRIGGER_GROUP_NAME = "trigger1";  
	   public static void main(String args[]){
//		   String job_name = "getTempIp";
//		   try {
//			QuartzManager.addJob(job_name, new QuartzJob(), "0/1 * * * * ?");
//			
//			Thread.sleep(5000);  
//		      System.out.println("���޸�ʱ�䡿��ʼ(ÿ2�����һ��)...");  
//		      QuartzManager.modifyJobTime(job_name, "*/2 * * * * ?");  
//		      Thread.sleep(60000);  
//		      System.out.println("���Ƴ���ʱ����ʼ...");  
//		      QuartzManager.removeJob(job_name);  
//		      System.out.println("���Ƴ���ʱ���ɹ�");  
//		      
//		      System.out.println("���ٴ���Ӷ�ʱ���񡿿�ʼ(ÿ10�����һ��)...");  
//		      QuartzManager.addJob(job_name, new QuartzJob(), "*/10 * * * * ?");  
//		      Thread.sleep(60000);  
//		      System.out.println("���Ƴ���ʱ����ʼ...");  
//		      QuartzManager.removeJob(job_name);  
//		      System.out.println("���Ƴ���ʱ���ɹ�");
//		      
//		} catch (SchedulerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
	   }
	     
	   /** *//** 
	    *  ���һ����ʱ����ʹ��Ĭ�ϵ������������������������������� 
	    * @param jobName ������ 
	    * @param job     ���� 
	    * @param time    ʱ�����ã��ο�quartz˵���ĵ� 
	    * @throws SchedulerException 
	    * @throws ParseException 
	    */  
	   public static void addJob(String jobName,Job job,String time) throws SchedulerException, ParseException{  
	       Scheduler sched = sf.getScheduler();  
	       JobDetail jobDetail = newJob(job.getClass()).withIdentity(jobName, JOB_GROUP_NAME).build();
	       CronTrigger trigger = newTrigger().withIdentity(jobName,TRIGGER_GROUP_NAME).withSchedule(cronSchedule(time)).build();//ָ��ÿ����Сʱִ��һ��
	       sched.scheduleJob(jobDetail,trigger);  
	       //����  
	       if(!sched.isShutdown())  
	          sched.start();  
	   }
	     
	   /** *//** 
	    * ���һ����ʱ���� 
	    * @param jobName ������ 
	    * @param jobGroupName �������� 
	    * @param triggerName  �������� 
	    * @param triggerGroupName ���������� 
	    * @param job     ���� 
	    * @param time    ʱ�����ã��ο�quartz˵���ĵ� 
	    * @throws SchedulerException 
	    * @throws ParseException 
	    */  
	   public static void addJob(String jobName,String jobGroupName, String triggerName,String triggerGroupName, Job job,String time)   
	                               throws SchedulerException, ParseException{  
	       Scheduler sched = sf.getScheduler();  
	       JobDetail jobDetail = newJob(job.getClass()).withIdentity(jobName, jobGroupName).build();
	       CronTrigger trigger = newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(cronSchedule(time)).build();//ָ��ÿ����Сʱִ��һ��
		   sched.scheduleJob(jobDetail, trigger); 
	       if(!sched.isShutdown())  
	          sched.start();  
	   }  
	     
	   /** *//** 
	    * �޸�һ������Ĵ���ʱ��(ʹ��Ĭ�ϵ�������������������������������) 
	    * @param jobName 
	    * @param time 
	    * @throws SchedulerException 
	    * @throws ParseException 
	    */  
	   public static void modifyJobTime(String jobName,String time) throws SchedulerException, ParseException{  
	       Scheduler sched = sf.getScheduler();  
	       TriggerKey triggerKey = new TriggerKey(jobName,TRIGGER_GROUP_NAME);
	       Trigger trigger =  sched.getTrigger(triggerKey);//.getTrigger(jobName,TRIGGER_GROUP_NAME);  
	       if(trigger != null){  
	           CronTrigger  ct = (CronTrigger)trigger;
	           ct.getTriggerBuilder().withSchedule(cronSchedule(time));
	           sched.resumeTrigger(triggerKey);  
	       }  
	   }  
	     
	   /** *//** 
	    * �޸�һ������Ĵ���ʱ�� 
	    * @param triggerName 
	    * @param triggerGroupName 
	    * @param time 
	    * @throws SchedulerException 
	    * @throws ParseException 
	    */  
	   public static void modifyJobTime(String triggerName,String triggerGroupName, String time) throws SchedulerException, ParseException{  
	       Scheduler sched = sf.getScheduler();
	       TriggerKey triggerKey = new TriggerKey(triggerName,triggerGroupName);
	       Trigger trigger =  sched.getTrigger(triggerKey);  
	       if(trigger != null){  
	           CronTrigger  ct = (CronTrigger)trigger;  
	           //�޸�ʱ��  
	           //ct.setCronExpression(time);  
	           ct.getTriggerBuilder().withSchedule(cronSchedule(time));
	           //����������  
	           sched.resumeTrigger(triggerKey);  
	       }  
	   }  
	     
	   /** *//** 
	    * �Ƴ�һ������(ʹ��Ĭ�ϵ�������������������������������) 
	    * @param jobName 
	    * @throws SchedulerException 
	    */  
	   public static void removeJob(String jobName) throws SchedulerException{  
	       Scheduler sched = sf.getScheduler();  
	       TriggerKey tk = new TriggerKey(jobName,TRIGGER_GROUP_NAME);
	       sched.pauseTrigger(tk);//ֹͣ������  
	       sched.unscheduleJob(tk);//�Ƴ�������  
	       JobKey jk = new JobKey(jobName,JOB_GROUP_NAME);
	       sched.deleteJob(jk);//ɾ������  
	   }  
	     
	   /** *//** 
	    * �Ƴ�һ������ 
	    * @param jobName 
	    * @param jobGroupName 
	    * @param triggerName 
	    * @param triggerGroupName 
	    * @throws SchedulerException 
	    */  
	   public static void removeJob(String jobName,String jobGroupName, String triggerName,String triggerGroupName)   
	                               throws SchedulerException{  
	       Scheduler sched = sf.getScheduler(); 
	       TriggerKey tk = new TriggerKey(triggerName,triggerGroupName);
	       sched.pauseTrigger(tk);//ֹͣ������  
	       sched.unscheduleJob(tk);//�Ƴ�������  
	       JobKey jk = new JobKey(jobName,jobGroupName);
	       sched.deleteJob(jk);//ɾ������  
	   } 
	   
	   public static void shutdownJobs() {
		    try {
		      Scheduler sched = sf.getScheduler();
		      if (!sched.isShutdown()) {
		        sched.shutdown();
		      }
		    } catch (Exception e) {
		      throw new RuntimeException(e);
		    }
		  }
}

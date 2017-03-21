package com.linkstec.raptor.eagle.tool;

import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

import com.linkstec.raptor.eagle.tool.job.Job;
import com.linkstec.raptor.eagle.tool.util.JobHelper;

public class Main {

	public static void main(String[] args) throws Exception {

//		ConfigHelper.init();
		if(args.length == 0 ){
			System.out.println("please eneter jobname witch want to excute");
			System.out.println("job list：");
			int i = 1;
			for(Job job : JobHelper.getJobs()){
				System.out.println(StringUtils.rightPad(i+++"", 3, " ")  +" " + StringUtils.rightPad(job.getName(), 20, " ") + " " +StringUtils.rightPad(job.getDescription(), 20, " "));
			}

			System.out.print("please enter jobnum or jobname witch want to excute.");

			Scanner sc = new Scanner(System.in);
			String select = sc.next();
			executeJob(select);
		}else{
			executeJob(args[0]);
		}

	}

	public static void executeJob(String select) throws Exception{
		JobExecutor executor = new JobExecutor();

		if(select.matches("\\d+")){
			int index= Integer.parseInt(select) -1;
			if(index >= 0 && index < JobHelper.getJobs().size()){
				executor.execute(JobHelper.getJobs().get(index));
			} else {
				System.out.println("error, cann't find the job definition!");
			}
		}else{
			Job job = JobHelper.getJob(select);
			if(job != null){
				executor.execute(job);
			}else{
				System.out.println("error, cann't find the job definition!");
			}
		}
	}
}

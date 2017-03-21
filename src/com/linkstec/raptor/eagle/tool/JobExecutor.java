package com.linkstec.raptor.eagle.tool;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.linkstec.raptor.eagle.tool.job.Job;
import com.linkstec.raptor.eagle.tool.job.Step;
import com.linkstec.raptor.eagle.tool.logger.GLog;
import com.linkstec.raptor.eagle.tool.step.StepExecutor;
import com.linkstec.raptor.eagle.tool.util.JobHelper;
import com.linkstec.raptor.eagle.tool.util.StepHelper;

public class JobExecutor {

	public void execute(Job job) throws Exception {
		GLog.info("执行Job({})-------------------开始", job.getName());
		List<Step> steps = job.getWorkflow().getStep();
		for(Step step : steps){
			if(StringUtils.isNotEmpty(step.getIncludeJob())){
				Job subJob = JobHelper.getJob(step.getIncludeJob());
				if(subJob != null){
					execute(subJob);
				}else{
					GLog.error("找不到Job定义, JobName={}", job.getName());
				}
			}else{
				StepExecutor executor = StepHelper.getStepExecutor(step.getClazz());
				if(executor != null){
					executor.doProcess(step);
				}
			}
		}
		GLog.info("执行Job({})-------------------结束", job.getName());
	}
}

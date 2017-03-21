package com.linkstec.raptor.eagle.tool.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.linkstec.raptor.eagle.tool.job.Job;
import com.linkstec.raptor.eagle.tool.logger.GLog;

public class JobHelper {
	private static Map<String, Job> jobMap;
	static{
		jobMap = new HashMap<String, Job>();
		URL url = Thread.currentThread().getContextClassLoader().getResource("job");
		File jobFolder = new File(url.getPath());
		for(File f : jobFolder.listFiles()){
			Job job= XmlUtil.loadXml(f, Job.class);
			jobMap.put(job.getName(), job);
		}
		GLog.debug("加载Job{}", getJobInfoList());
	}

	public static Job getJob(String jobName){
		return jobMap.get(jobName);
	}
	
	public static List<Job> getJobs(){
		List<Job> jobs = new ArrayList<Job>();
		jobs.addAll(jobMap.values());
		Collections.sort(jobs, new Comparator<Job>() {
			public int compare(Job o1, Job o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return jobs;
	}
	
	public static String getJobInfoList(String split){
		List<String> list = getJobInfoList();
		return StringUtils.join(list, split);
	}
	
	public static List<String> getJobInfoList(){
		List<String> list = new ArrayList<String>();
		Iterator<String> it = jobMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			Job job = jobMap.get(key);
			if(job.getDescription()!=null){
				list.add(key + "-" + job.getDescription());
			} else {
				list.add(key);
			}
		}
		Collections.sort(list);
		return list;
	}
	
//	public static String getJobInfoList(){
//		return getJobInfoList(",");
//	}
	
	public static void main(String[] args){
		System.out.println(jobMap);
	}
}

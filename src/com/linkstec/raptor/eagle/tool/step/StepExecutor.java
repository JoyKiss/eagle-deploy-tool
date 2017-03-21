package com.linkstec.raptor.eagle.tool.step;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.linkstec.raptor.eagle.tool.job.Step;
import com.linkstec.raptor.eagle.tool.logger.GLog;
import com.linkstec.raptor.eagle.tool.ssh.ToolSshClient;
import com.linkstec.raptor.eagle.tool.util.ConfigHelper;
import com.linkstec.raptor.eagle.tool.util.JavaShellUtil;
import com.linkstec.raptor.eagle.tool.util.RemoteServerHelper;

public abstract class StepExecutor {
	public abstract int validate(Step step);
	public abstract int execute(ToolSshClient client, Step step);
	
	public void doProcess(Step step){
		GLog.info("执行Step({})-------------------开始", step.getName());
		String idStr =  step.getServerRef().getName();
		String[] serverIds = idStr.split(",");
		String excutedServers = "";
		for(String id : serverIds){
			ToolSshClient client = RemoteServerHelper.getRemoteSshClient(id.trim());
			if(client!=null){
				if(!excutedServers.contains(client.getIp())){
					execute(client, step);
					excutedServers = excutedServers + "|" + client.getIp();
				}
			}else{
				GLog.error("找不到执行服务器,请确认JOB配置是否正确。ServerId={}", id);
			}
			
		}
		GLog.info("执行Step({})-------------------结束", step.getName());
	}
	
	public  String  repalceGlobleParam(String val) {
		String strCharSequence =val;
		String strRegex = "\\$\\{([\\w_\\.]+)\\}";
		Pattern p = Pattern.compile(strRegex);
		Matcher m = p.matcher(strCharSequence);

		StringBuffer sb = new StringBuffer(); 

		while(m.find()) { 
			String replaceVal = ConfigHelper.getConfig(m.group(1));
			if(StringUtils.isNotEmpty(replaceVal)){
				m.appendReplacement(sb, replaceVal); 
				GLog.debug("替换可变参数值 {} -> {}", m.group(1), replaceVal);
			}else{
				GLog.warn("找不到可变参数{}，替换参数失败 ", m.group(1));
			}
			
		} 
		m.appendTail(sb); 
		return sb.toString();
	}
	
	public  void executeLocal(String shellContent) {
		GLog.info("本机执行命令：{}", shellContent);
		String tmpShell = "/tmp/" + DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS") + ".sh";
		try {
			
			BufferedWriter writer;
			writer = new BufferedWriter(new FileWriter(new File(tmpShell)));
			writer.write("#!/bin/bash" + System.getProperty("line.separator"));
			writer.write(shellContent);
			writer.flush();
			writer.close();
			
			String cmdstring = "chmod +x " +tmpShell ;
			Process proc = Runtime.getRuntime().exec(cmdstring);
			proc.waitFor(); //阻塞，直到上述命令执行完
			JavaShellUtil.executeShell(tmpShell);
		} catch (IOException e) {
			GLog.error("shell文件生成出错 shell={}", tmpShell);
		} catch (InterruptedException e) {
			GLog.error("设置shell执行权限出错 shell={}", tmpShell);
		}
	}
}

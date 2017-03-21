package com.linkstec.raptor.eagle.tool.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.linkstec.raptor.eagle.tool.logger.GLog;

public class JavaShellUtil {
//	// 基本路径
//	private static final String basePath = "/root/";
//
//	// 记录Shell执行状况的日志文件的位置(绝对路径)
//	private static final String executeShellLogFile = basePath
//			+ "executeShell.log";
//
//	// 发送文件到Kondor系统的Shell的文件名(绝对路径)
//	private static final String sendKondorShellName = basePath
//			+ "songjy.sh";

	public static int executeShell(String shellCommand) throws IOException {
		System.out.println("shellCommand:"+shellCommand);
		int success = 0;
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = null;
		// 格式化日期时间，记录日志时使用
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS ");

		try {
			stringBuffer.append(dateFormat.format(new Date()))
					.append("准备执行Shell命令 ").append(shellCommand)
					.append(" \r\n");
			Process pid = null;
			String[] cmd = { "/bin/bash", "-c", shellCommand };
			// 执行Shell命令
			pid = Runtime.getRuntime().exec(cmd);
			if (pid != null) {
				stringBuffer.append("进程号：").append(pid.toString())
						.append("\r\n");
				// bufferedReader用于读取Shell的输出内容
				bufferedReader = new BufferedReader(new InputStreamReader(pid.getInputStream()), 1024);
				pid.waitFor();
			} else {
				stringBuffer.append("没有pid\r\n");
			}
			stringBuffer.append(dateFormat.format(new Date())).append(
					"Shell命令执行完毕\r\n执行结果为：\r\n");
			String line = null;
			// 读取Shell的输出内容，并添加到stringBuffer中
			while (bufferedReader != null
					&& (line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line).append("\r\n");
			}
//			System.out.println("stringBuffer:"+stringBuffer);
		} catch (Exception ioe) {
			stringBuffer.append("执行Shell命令时发生异常：\r\n").append(ioe.getMessage())
					.append("\r\n");
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
					GLog.info("{}", stringBuffer.toString());
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			success = 1;
		}
		return success;
	}
}
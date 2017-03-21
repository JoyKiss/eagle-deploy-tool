package com.linkstec.raptor.eagle.tool;

import java.io.PrintStream;

import jenkins.plugins.ssh2easy.gssh.client.JenkinsSshClient;

public class Test {

	static String stopNimbus = "pkill -9 -f storm\r" + "zkServer.sh stop\r" + "ps -ef |grep nimbus\r"
			+ "ps -ef |grep ui\r" + "rm -rf /usr/local/storm/logs/*.*\r" + "rm -rf /usr/local/storm/data\r"
			+ "rm -rf /usr/local/zookeeper/data/version-2/\r";

	static String stopWorker = "pkill -9 -f storm\r" + "zkServer.sh stop\r" + "ps -ef |grep storm\r"
			+ "rm -rf /usr/local/storm/logs/*.*\r" + "rm -rf /usr/local/storm/data\r"
			+ "rm -rf /usr/local/zookeeper/data/version-2/\r";

	static String startZk = "zkServer.sh start\r";
	static String statusZk = "zkServer.sh status\r";
	
	static String startNimbus = "nohup storm nimbus > /dev/null &\r" +
			"nohup storm ui > /dev/null &\r" +
			"ps -ef |grep nimbus\r" +
			"ps -ef |grep ui\r" ;
	
	static String startWorker =  "nohup storm supervisor > /dev/null &\r" +
			"ps -ef |grep supervisor\r" ;
	
	static String startTopology = "cd /usr/local/eagle/storm/\r" +
			"./start.sh\r" ;
	
	public static void main(String[] args) throws Exception {
		test();
	}

	public static void test() throws Exception{
		PrintStream output = new PrintStream(System.out);
		JenkinsSshClient client56 = new JenkinsSshClient("192.168.154.2", 22, "root", "root");
		// client.executeCommand(output, "ll");
		client56.executeShell(output, stopNimbus);
		JenkinsSshClient client54 = new JenkinsSshClient("192.168.9.54", 22, "root", "linkage@12345");
		client54.executeShell(output, stopWorker);

		JenkinsSshClient client55 = new JenkinsSshClient("192.168.9.55", 22, "root", "linkage@12345");
		client55.executeShell(output, stopWorker);
		client56.executeShell(output, startZk);
		client54.executeShell(output, startZk);
		client55.executeShell(output, startZk);
		
		client56.executeShell(output, statusZk);
		client54.executeShell(output, statusZk);
		client55.executeShell(output, statusZk);
		
		
		client56.executeShell(output, startNimbus);
		Thread.sleep(5000L);
		client54.executeShell(output, startWorker);
		client55.executeShell(output, startWorker);
		Thread.sleep(5000L);
		client56.executeShell(output, startTopology);
	}
}

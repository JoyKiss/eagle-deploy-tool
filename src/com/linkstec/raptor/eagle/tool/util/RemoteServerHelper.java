package com.linkstec.raptor.eagle.tool.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.linkstec.raptor.eagle.tool.ssh.ToolSshClient;



public class RemoteServerHelper {

	private static Map<String, ToolSshClient> serverMap;
	static{
		serverMap = new HashMap<String, ToolSshClient>();
	}
	
	public static ToolSshClient getRemoteSshClient(String serverId){
		if(serverMap.containsKey(serverId)){
			return serverMap.get(serverId);
		} else{
			if(StringUtils.isNotEmpty(ConfigHelper.getConfig(serverId))){
				String info = ConfigHelper.getConfig(serverId);
				String[] data = info.split(",");
				if(data.length == 3){
					String host ="";
					int port = 22;
					if(data[0].indexOf(":")>0){
						host = data[0].substring(0, data[0].indexOf(":"));
						port = Integer.parseInt(data[0].substring(data[0].indexOf(":")+1));
					}
					String user = data[1];
					String password = data[2];
					ToolSshClient client = new ToolSshClient(host, port, user, password);
					client.testConnection(System.out);
					serverMap.put(serverId, client);
					return client;
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args){
		System.out.println(getRemoteSshClient("storm.worker1"));
	}
}

package com.linkstec.raptor.eagle.tool.step;

import com.linkstec.raptor.eagle.tool.job.Step;
import com.linkstec.raptor.eagle.tool.logger.SSHLog;
import com.linkstec.raptor.eagle.tool.ssh.ToolSshClient;
import com.linkstec.raptor.eagle.tool.util.IpHelper;



public class ShellStepExecutor extends StepExecutor {

	public int execute(ToolSshClient client, Step step) {

		String shell =  repalceGlobleParam(step.getShell()).replace("\t", "");
	
		if(IpHelper.isLocal(client.getIp())){
			executeLocal(shell);
		}else{
			client.executeCommand(SSHLog.getInstance(), shell);
		}

		return 0;
	}

	public int validate(Step step) {
		return 0;
	}

}

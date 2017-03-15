package com.linkstec.raptor.eagle.tool.step;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.linkstec.raptor.eagle.tool.job.Step;
import com.linkstec.raptor.eagle.tool.job.Upload;
import com.linkstec.raptor.eagle.tool.logger.GLog;
import com.linkstec.raptor.eagle.tool.logger.SSHLog;
import com.linkstec.raptor.eagle.tool.ssh.ToolSshClient;
import com.linkstec.raptor.eagle.tool.util.CompressUtil;
import com.linkstec.raptor.eagle.tool.util.ConfigHelper;
import com.linkstec.raptor.eagle.tool.util.IpHelper;



public class UploadStepExecutor extends StepExecutor {

	public int execute(ToolSshClient client, Step step) {

		if(IpHelper.isLocal(client.getIp())){
			List<String> commands = new ArrayList<String>();
			for(Upload upload : step.getUpload()){
				String path = repalceGlobleParam(upload.getSrcFile());
				commands.add("mkdir -p " + upload.getDestPath());
				File f = new File(path);
				if(f.exists()){
					commands.add("rm -rf " + upload.getDestPath() + "/*");
					if(f.isDirectory()){
						if(upload.isCompress()){
							String tmpFile =f.getName() +".zip";
							String tmpPath = ConfigHelper.getConfig("workspace.tmp.path", "tmp");
							commands.add("zip -r " + tmpPath + "/" + tmpFile  + " " + path);
						} else {
							commands.add("cp -rf " + path + "/* " +  upload.getDestPath());
						}
					} else {
						commands.add("rm -rf " + path + " " + upload.getDestPath());
					}
				}
			}
			executeLocal(StringUtils.join(commands, System.getProperty("line.separator")));
		}else{
			for(Upload upload : step.getUpload()){
				String path = repalceGlobleParam(upload.getSrcFile());
				String destPath = repalceGlobleParam(upload.getDestPath());
				File f = new File(path);
				if(!f.exists()){
					GLog.error("上传文件或目录不存在，path={}", path);
					return -1;
				}
				
				if(upload.isCompress()){
					String tmpFile =f.getName() +".zip";
					String tmpPath = ConfigHelper.getConfig("workspace.tmp.path", "tmp");
					CompressUtil.zip(f.getAbsolutePath(),  tmpPath +  File.separator +  tmpFile , "");
					client.executeCommand(SSHLog.getInstance(), "mkdir -p " +  destPath);
					upload(client, new File(tmpPath +  File.separator +  tmpFile), upload.getSaveName(), destPath);
				} else{
//					client.executeCommand(SSHLog.getInstance(), "mkdir -p " +  destPath);
//					upload(client, f, step.getUpload().getSaveName(), destPath);
					if(f.isDirectory()){
						for(File subflie : f.listFiles()){
							upload(client, subflie, "", destPath);
						}
					}else{
						upload(client, f, upload.getSaveName(), destPath);
					}
				}
			}
		}
		
		
		return 0;
	}


	private void upload(ToolSshClient client ,File file, String saveName, String destPath){
		client.executeCommand(SSHLog.getInstance(), "mkdir -p " +  destPath);
		if(file.isDirectory()){
			for(File subflie : file.listFiles()){
				upload(client, subflie, "", destPath+ "/"  +subflie.getParentFile().getName());
			} 
		} else{
			if(StringUtils.isNotEmpty(saveName)){
				client.uploadFile(SSHLog.getInstance(), saveName, file, destPath);
			} else {
				client.uploadFile(SSHLog.getInstance(), file.getName(), file, destPath);
			}
		}
	}
	
	public int validate(Step step) {
		return 0;
	}

}

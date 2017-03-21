package com.linkstec.raptor.eagle.tool.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.commons.lang.StringEscapeUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import jenkins.plugins.ssh2easy.gssh.GsshPluginException;
import jenkins.plugins.ssh2easy.gssh.GsshUserInfo;
import jenkins.plugins.ssh2easy.gssh.ServerGroup;

public class DefaultSshClient extends AbstractSshClient {
	public static final String SSH_BEY = "\nexit $?";
	private String ip;
	private int port;
	private String username;
	private String password;

	public DefaultSshClient(String ip, int port, String username, String password) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public DefaultSshClient(ServerGroup serverGroup, String ip) {
		this.port = serverGroup.getPort();
		this.username = serverGroup.getUsername();
		this.password = serverGroup.getPassword();
		this.ip = ip;
	}

	public static SshClient newInstance(String ip, int port, String username, String password) {
		return new DefaultSshClient(ip, port, username, password);
	}

	public static SshClient newInstance(ServerGroup group, String ip) {
		return new DefaultSshClient(group, ip);
	}

	public Session createSession(PrintStream logger) {
		JSch jsch = new JSch();

		Session session = null;
		try {
			session = jsch.getSession(this.username, this.ip, this.port);
			session.setPassword(this.password);

			UserInfo ui = new GsshUserInfo(this.password);
			session.setUserInfo(ui);

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setDaemonThread(false);
			session.connect();
			logger.println("create ssh session success with ip=[" + this.ip + "],port=[" + this.port + "],username=["
					+ this.username + "],password=[*******]");
		} catch (Exception e) {
			logger.println("create ssh session failed with ip=[" + this.ip + "],port=[" + this.port + "],username=["
					+ this.username + "],password=[*******]");

			e.printStackTrace(logger);
			throw new GsshPluginException(e);
		}
		return session;
	}

	public int uploadFile(PrintStream logger, String fileName, InputStream fileContent, String serverLocation) {
		Session session = null;
		ChannelSftp sftp = null;
		OutputStream out = null;
		try {
			session = createSession(logger);
			Channel channel = session.openChannel("sftp");
			channel.setOutputStream(logger, true);
			channel.setExtOutputStream(logger, true);
			channel.connect();
			Thread.sleep(2000L);
			sftp = (ChannelSftp) channel;
			sftp.setFilenameEncoding("UTF-8");
			sftp.cd(serverLocation);
			out = sftp.put(fileName, 777);
			Thread.sleep(2000L);
			byte[] buffer = new byte[1024*1024];
			int n = -1;
			while ((n = fileContent.read(buffer, 0, 1024*1024)) != -1) {
				out.write(buffer, 0, n);
			}
			out.flush();
			logger.println("upload file [" + fileName + "] to remote [" + serverLocation + "]success");

			int i = 0;
			return i;
		} catch (Exception e) {
			logger.println("[GSSH - SFTP]  Exception:" + e.getMessage());
			e.printStackTrace(logger);
			throw new GsshPluginException(e);
		} finally {
			logger.println("[GSSH]-SFTP exit status is " + sftp.getExitStatus());
			if (null != out)
				try {
					out.close();
				} catch (IOException e) {
				}
			closeSession(session, sftp);
		}
	}

	public int downloadFile(PrintStream logger, String remoteFile, String localFolder, String fileName) {
		Session session = null;
		ChannelSftp sftp = null;
		OutputStream out = null;
		try {
			session = createSession(logger);
			Channel channel = session.openChannel("sftp");
			channel.connect();
			Thread.sleep(2000L);
			sftp = (ChannelSftp) channel;
			sftp.setFilenameEncoding("UTF-8");
			sftp.get(remoteFile, localFolder + "/" + fileName);
			logger.println("download remote file [" + remoteFile + "] to local [" + localFolder + "] with file name ["
					+ fileName + "]");

			int i = 0;
			return i;
		} catch (Exception e) {
			logger.println("[GSSH - SFTP]  Exception:" + e.getMessage());
			e.printStackTrace(logger);
			throw new GsshPluginException(e);
		} finally {
			logger.println("[GSSH]-SFTP exit status is " + sftp.getExitStatus());
			if (null != out)
				try {
					out.close();
				} catch (IOException e) {
				}
			closeSession(session, sftp);
		}
	}

	public int executeShell(PrintStream logger, String shell) {
		return executeCommand(logger, shell);
	}

	public int executeCommand(PrintStream logger, String command) {
		Session session = null;
		ChannelExec channel = null;
		InputStream in = null;
		try {
			String wrapperCommand = wrapperInput(command);
			logger.write("execute below commands:".getBytes());
			logger.write(wrapperCommand.getBytes());
			logger.flush();
			session = createSession(logger);
			channel = (ChannelExec) session.openChannel("exec");
			channel.setOutputStream(logger, true);
			channel.setExtOutputStream(logger, true);
			channel.setPty(Boolean.FALSE.booleanValue());
			channel.setCommand(wrapperCommand);
			channel.connect();
			Thread.sleep(1000L);
			while (true) {
				byte[] buffer = new byte[2048];
				int len = -1;
				in = channel.getInputStream();
				while (-1 != (len = in.read(buffer))) {
					logger.write(buffer, 0, len);
					logger.flush();
				}
				if (channel.isEOF()) {
					break;
				}
				if (!channel.isConnected()) {
					break;
				}
				if (channel.isClosed()) {
					break;
				}
				Thread.sleep(1000L);
			}
			int status = channel.getExitStatus();
			logger.println("shell exit status code -->" + status);
			int len = status;
			return len;
		} catch (Exception e) {
			logger.println("[GSSH]-cmd Exception:" + e.getMessage());
			e.printStackTrace(logger);
			closeSession(session, channel);
			throw new GsshPluginException(e);
		} finally {
			if (null != in)
				try {
					in.close();
				} catch (IOException e) {
				}
			closeSession(session, channel);
		}
	}

	public boolean testConnection(PrintStream logger) {
		try {
			Session session = createSession(logger);
			closeSession(session, null);
			return true;
		} catch (Exception e) {
			logger.println("test ssh connection failed !");
			e.printStackTrace(logger);
		}
		return false;
	}

	private void closeSession(Session session, Channel channel) {
		if (channel != null) {
			channel.disconnect();
			channel = null;
		}
		if (session != null) {
			session.disconnect();
			session = null;
		}
	}

	public String wrapperInput(String input) {
		String output = fixIEIssue(input);

		return output + "\nexit $?";
	}

	private String fixIEIssue(String input) {
		return StringEscapeUtils.unescapeHtml(input);
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return "Server Info [" + this.ip + " ," + this.port + "," + this.username + "," + this.password + "]";
	}
}

/*
 * Location: C:\Users\zhangzj\Desktop\shell\ssh2easy.jar Qualified Name:
 * jenkins.plugins.ssh2easy.gssh.client.DefaultSshClient JD-Core Version: 0.6.0
 */
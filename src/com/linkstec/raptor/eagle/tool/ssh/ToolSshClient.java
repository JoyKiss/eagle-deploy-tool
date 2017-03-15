package com.linkstec.raptor.eagle.tool.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.linkstec.raptor.eagle.tool.logger.GLog;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;

import jenkins.plugins.ssh2easy.gssh.GsshPluginException;
import jenkins.plugins.ssh2easy.gssh.ServerGroup;

public class ToolSshClient extends DefaultSshClient {

	private Connection conn = null;
	public ToolSshClient(String ip, int port, String username, String password) {
		super(ip, port, username, password);
	}
	
	public ToolSshClient(ServerGroup serverGroup, String ip) {
		super(serverGroup, ip);
	}

	public static SshClient newInstance(String ip, int port, String username, String password) {
		return new ToolSshClient(ip, port, username, password);
	}

	public static SshClient newInstance(ServerGroup group, String ip) {
		return new ToolSshClient(group, ip);
	}

	public Connection getConnection() throws IOException {
//		if(conn != null && conn.isAuthenticationComplete()){
//			return conn;
//		}
		
	    conn = new Connection(getIp(), getPort());
		conn.connect();
		boolean isAuthenticated = conn.authenticateWithPassword(getUsername(), getPassword());
		if (!isAuthenticated) {
			throw new IOException("Authentication failed.");
		}
		GLog.info("create ssh session success with ip=[" + getIp() + "],port=[" + getPort() + "],username=["
				+ getUsername() + "],password=[*******]");
		return conn;
	}

	public int executeCommand(PrintStream logger, String command) {
		Connection conn = null;
		try {
			conn = getConnection();
		} catch (Exception e) {
			GLog.info("create ssh session failed with ip=[" + getIp() + "],port=[" + getPort() + "],username=["
					+ getUsername() + "],password=[*******]");
			e.printStackTrace(logger);
			throw new GsshPluginException(e);
		}
		Session session = null;
		String wrappedCommand = wrapperInput(command);
		try {
			session = conn.openSession();
			session.requestPTY("dumb");
			session.startShell();
			ExecutorService exec = Executors.newSingleThreadExecutor();
			Future task = exec.submit(new OutputTask(session, logger));
			PrintWriter out = new PrintWriter(session.getStdin());
			String[] commands = wrappedCommand.split("\n");
			for (int i = 0; i < commands.length; i++) {
				String cmd = commands[i];
				if ("".equals(cmd.trim()))
					continue;
				out.println(cmd);
			}
			out.close();
			task.get();
			exec.shutdown();
			int status = session.getExitStatus().intValue();
			GLog.info("execute command exit status -->" + status);
			return status;
		} catch (Exception e) {
			String msg = "execute commds=[" + wrappedCommand + "]failed !";
			GLog.error(msg,e);
		
			throw new GsshPluginException(msg, e);
		} finally {
			if (null != session) {
				session.close();
			}
			if (null != conn)
				conn.close();
		}
	}

	class OutputTask implements Callable<Boolean> {
		private PrintStream logger;
		private Session session;

		public boolean execute() throws IOException, InterruptedException {
			InputStream stdout = this.session.getStdout();
			InputStream stderr = this.session.getStderr();
			byte[] buffer = new byte[8192];
			boolean result = true;
			while (true) {
				if ((stdout.available() == 0) && (stderr.available() == 0)) {
					int conditions = this.session.waitForCondition(44, 0L);

					if ((conditions & 0x1) != 0) {
						GLog.error("wait timeout and exit now !");
					} else if ((conditions & 0x20) != 0)
						break;
				} else {
					while (stdout.available() > 0) {
						int len = stdout.read(buffer);
						if (len > 0) {
							byte[] bs = new byte[len];
							System.arraycopy(buffer, 0, bs, 0, len);
//							this.logger.write(bs);
							GLog.info("{}", new String(bs));
						}
					}
					while (stderr.available() > 0) {
						int len = stderr.read(buffer);
						if (len > 0){
							byte[] bs = new byte[len];
							System.arraycopy(buffer, 0, bs, 0, len);
//							this.logger.write(bs);
							GLog.info("{}", new String(bs));
						}
						result = false;
					}
					if (!result) {
						break;
					}
				}
			}
//			GLog.info("####################################");
			return result;
		}

		public Boolean call() throws Exception {
			Thread.sleep(2000L);
			return Boolean.valueOf(execute());
		}

		public OutputTask(Session session, PrintStream logger) {
			this.session = session;
			this.logger = logger;
		}
	}
}

/*
 * Location: C:\Users\zhangzj\Desktop\shell\ssh2easy.jar Qualified Name:
 * jenkins.plugins.ssh2easy.gssh.client.JenkinsSshClient JD-Core Version: 0.6.0
 */
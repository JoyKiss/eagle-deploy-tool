package com.linkstec.raptor.eagle.tool.ssh;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;

import jenkins.plugins.ssh2easy.gssh.GsshPluginException;
import jenkins.plugins.ssh2easy.gssh.Utils;

public abstract class AbstractSshClient implements SshClient {
	public static final String TEMP_PATH = "/var";
	public static final String LATEEST_EXEC_SHELL_DEBUG = "/var/latest_exec_debug.sh";

	public int uploadFile(PrintStream logger, String fileName, File file, String serverLocation) {
		logger.println("sftp upload file [" + file + "] to target location [" + serverLocation + "] with file name is ["
				+ fileName + "]");
		InputStream fileContent = null;
		try {
			if (!file.exists()) {
				logger.println("[GSSH-FTP] ERROR as: sftp upload local file [" + file + "] can't find !");
			}

			fileContent = new FileInputStream(file);
			int i = uploadFile(logger, fileName, fileContent, serverLocation);
			return i;
		} catch (FileNotFoundException e) {
			String message = "[GSSH-FTP] ERROR as: sftp upload local file [" + file + "] can't find !";

			logger.println(message);
			e.printStackTrace(logger);
			throw new GsshPluginException(message, e);
		} catch (Exception e) {
			String message = "[GSSH-FTP] ERROR as with below errors logs:";
			logger.println(message);
			e.printStackTrace(logger);
			throw new GsshPluginException(message, e);
		} finally {
			if (null != fileContent)
				try {
					fileContent.close();
				} catch (IOException e) {
				}
		}
	}

	public int uploadFile(PrintStream logger, String fileName, String fileContent, String serverLocation) {
		InputStream bis = new ByteArrayInputStream(fileContent.getBytes());
		int status = uploadFile(logger, fileName, bis, serverLocation);
		if (null != bis)
			try {
				bis.close();
			} catch (IOException e) {
			}
		return status;
	}

	public int downloadFile(PrintStream logger, String remoteFile, String localFolder) {
		File rf = new File(remoteFile);
		return downloadFile(logger, remoteFile, localFolder, rf.getName());
	}

	public int executeShellByFTP(PrintStream logger, InputStream shell) {
		Random random = new Random();

		String shellName = "tempshell_" + System.currentTimeMillis() + random.nextInt() + ".sh";
		String shellFile = "/var/" + shellName;
		try {
			uploadFile(logger, shellName, shell, "/var");
			chmod(logger, 777, shellFile);
			int i = executeCommand(logger, ". " + shellFile);
			return i;
		} finally {
			rm_Rf(logger, "/var/latest_exec_debug.sh");
			mv(logger, shellFile, "/var/latest_exec_debug.sh");
		}

	}

	public int executeShellByFTP(PrintStream logger, String shell) {
		Random random = new Random();
		logger.println("execute shell as : ");
		logger.println(shell);

		String shellName = "tempshell_" + System.currentTimeMillis() + random.nextInt() + ".sh";

		String shellFile = "/var/" + shellName;
		try {
			uploadFile(logger, shellName, shell, "/var");
			chmod(logger, 777, shellFile);
			int i = executeCommand(logger, ". " + shellFile);
			return i;
		} finally {
			rm_Rf(logger, "/var/latest_exec_debug.sh");
			mv(logger, shellFile, "/var/latest_exec_debug.sh");
		}
	}

	public int chmod(PrintStream logger, int mode, String path) {
		return executeCommand(logger, "chmod " + mode + " " + path);
	}

	public int chown(PrintStream logger, String own, String path) {
		return executeCommand(logger, "chown " + own + " " + path);
	}

	public int mv(PrintStream logger, String source, String dest) {
		return executeCommand(logger, "mv " + source + " " + dest);
	}

	public int rm_Rf(PrintStream logger, String path) {
		return executeCommand(logger, "rm -rf " + path);
	}

	public int executeCommand(PrintStream logger, InputStream command) {
		String content = Utils.getStringFromStream(command);
		return executeCommand(logger, content);
	}
}

/*
 * Location: C:\Users\zhangzj\Desktop\shell\ssh2easy.jar Qualified Name:
 * jenkins.plugins.ssh2easy.gssh.client.AbstractSshClient JD-Core Version: 0.6.0
 */
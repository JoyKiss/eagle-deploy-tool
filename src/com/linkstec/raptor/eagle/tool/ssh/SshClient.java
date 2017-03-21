package com.linkstec.raptor.eagle.tool.ssh;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

public abstract interface SshClient
{
  public static final int STATUS_SUCCESS = 0;
  public static final int STATUS_FAILED = -1;

  public abstract int executeCommand(PrintStream paramPrintStream, String paramString);

  public abstract int executeShell(PrintStream paramPrintStream, String paramString);

  public abstract int executeShellByFTP(PrintStream paramPrintStream, String paramString);

  public abstract int uploadFile(PrintStream paramPrintStream, String paramString1, String paramString2, String paramString3);

  public abstract int uploadFile(PrintStream paramPrintStream, String paramString1, InputStream paramInputStream, String paramString2);

  public abstract int uploadFile(PrintStream paramPrintStream, String paramString1, File paramFile, String paramString2);

  public abstract int downloadFile(PrintStream paramPrintStream, String paramString1, String paramString2, String paramString3);

  public abstract int downloadFile(PrintStream paramPrintStream, String paramString1, String paramString2);

  public abstract int chmod(PrintStream paramPrintStream, int paramInt, String paramString);

  public abstract int chown(PrintStream paramPrintStream, String paramString1, String paramString2);

  public abstract int mv(PrintStream paramPrintStream, String paramString1, String paramString2);

  public abstract int rm_Rf(PrintStream paramPrintStream, String paramString);

  public abstract boolean testConnection(PrintStream paramPrintStream);
}

/* Location:           C:\Users\zhangzj\Desktop\shell\ssh2easy.jar
 * Qualified Name:     jenkins.plugins.ssh2easy.gssh.client.SshClient
 * JD-Core Version:    0.6.0
 */
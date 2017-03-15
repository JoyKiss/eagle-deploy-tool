package com.linkstec.raptor.eagle.tool.logger;

import java.io.IOException;
import java.io.PrintStream;

public class SSHLog extends PrintStream{
	
	private void log(Object info) {
		GLog.info(info.toString());
    }
	
	private static SSHLog instance;
	private SSHLog(){
		super(System.out);
	}
	public static SSHLog getInstance(){
		if(instance == null){
			instance = new SSHLog();
		}
		return instance;
	}
	
	  public void println(boolean x) {
          log(Boolean.valueOf(x));
      }
      public void println(char x) {
          log(Character.valueOf(x));
      }
      public void println(char[] x) {
          log(x == null ? null : new String(x));
      }
      public void println(double x) {
          log(Double.valueOf(x));
      }
      public void println(float x) {
          log(Float.valueOf(x));
      }
      public void println(int x) {
          log(Integer.valueOf(x));
      }
      public void println(long x) {
          log(x);
      }
      public void println(Object x) {
          log(x);
      }
      public void println(String x) {
          log(x);
      }
      public void write(String x) {
    	  log(x);
      }
      public void write(byte b[]) throws IOException {
          write(b, 0, b.length);
          String[] lines = new String(b).split("\r\n");
          for(String line : lines){
        	  log(line);
          }
        		
      }
}

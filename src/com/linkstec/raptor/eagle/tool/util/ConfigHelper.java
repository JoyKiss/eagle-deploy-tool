package com.linkstec.raptor.eagle.tool.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.linkstec.raptor.eagle.tool.logger.GLog;

public class ConfigHelper {

	private static String strRegex = "\\$\\{([\\w_\\.]+)\\}";
	private static Pattern p = Pattern.compile(strRegex);
	private static Properties prop;
	private static boolean initFlag = false;
	
	static{
		init();
	}
	
	public static void init(){
		if(initFlag){
			return;
		}
		
		prop = new Properties();
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("tool.properties");
			prop = new Properties();
			prop.load(in);
			prop.put("ymd", new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis()));
			prop.put("hms", new SimpleDateFormat("HHmmss").format(System.currentTimeMillis()));
			prop.put("basedir", System.getProperty("user.dir").replace("\\", "/"));
			Iterator it = prop.keySet().iterator();
			while(it.hasNext()){
				String key = it.next().toString();
			    if(StringUtils.isNotEmpty(prop.getProperty(key)) && p.matcher(prop.getProperty(key))!=null){
			    	prop.put(key, repalceGlobleParam(prop.getProperty(key)));
			    }
			}
			GLog.info("加载配置文件tool.properties成功{}", prop);
		} catch (IOException e) {
			GLog.error("加载配置文件server.properties失败", e);
		}
		initFlag = true;
	}
	
	public static String getConfig(String key, String defultVal){
		return prop.getProperty(key, defultVal);
	}
	
	public static String getConfig(String key){
		return prop.getProperty(key, "");
	}
	
	public static String  repalceGlobleParam(String val) {
		String strCharSequence =val;
		String strRegex = "\\$\\{([\\w_\\.]+)\\}";
		Pattern p = Pattern.compile(strRegex);
		Matcher m = p.matcher(strCharSequence);

		StringBuffer sb = new StringBuffer(); 

		while(m.find()) { 
			String replaceVal = prop.getProperty(m.group(1));
			if(StringUtils.isNotEmpty(replaceVal)){
				m.appendReplacement(sb, replaceVal); 
				GLog.debug("替换可变参数值${{}} -> {}", m.group(1), replaceVal);
			}else{
				GLog.warn("找不到可变参数${{}}，替换参数失败 ", m.group(1));
			}
		} 
		m.appendTail(sb); 
		return sb.toString();
	}
}

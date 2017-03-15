package com.linkstec.raptor.eagle.tool.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GLog {
	
	private static final Logger LOGGER = LoggerFactory.getLogger("EAGLE.TOOL");
	
	public static void error(String msg, Throwable t) {
		LOGGER.error(msg, t);
	}
	
	public static void info(String msg, Throwable t) {
		LOGGER.info(msg, t);
	}

	public static void warn(String msg, Throwable t) {
		LOGGER.warn(msg, t);
	}

	public static void debug(String msg, Throwable t) {
		LOGGER.debug(msg, t);
	}

	public static void error(String format, Object... arguments) {
		LOGGER.error(format, arguments);
	}	

	public static void warn(String format, Object... arguments) {
		LOGGER.warn(format, arguments);
	}	

	public static void info(String format, Object... arguments) {
		LOGGER.info(format, arguments);
	}
	
	public static void debug(String format, Object... arguments) {
		LOGGER.debug(format, arguments);
	}	

}

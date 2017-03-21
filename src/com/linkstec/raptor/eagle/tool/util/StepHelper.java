package com.linkstec.raptor.eagle.tool.util;

import java.util.HashMap;
import java.util.Map;

import com.linkstec.raptor.eagle.tool.step.StepExecutor;

public class StepHelper {

	private static Map<String, StepExecutor> stepMap;
	static{
		stepMap = new HashMap<String, StepExecutor>();
	}
	@SuppressWarnings("unchecked")
	public static StepExecutor getStepExecutor(String clsName){
		try {
			Class<StepExecutor> clazz = (Class<StepExecutor>) Class.forName(clsName);
			stepMap.put(clsName, clazz.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stepMap.get(clsName);
	}
}

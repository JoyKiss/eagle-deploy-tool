package com.linkstec.raptor.eagle.tool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXB;

import com.linkstec.raptor.eagle.tool.logger.GLog;

public class XmlUtil {

	public static <T> T loadXml(String folder, String fileName, Class<T> type) {
		File file = new File(folder, fileName);
		return loadXml(file,type);
	}

	public static <T> T loadXml(File file, Class<T> type) {

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			GLog.debug("加载Job文件{}", file.getName());
		    return JAXB.unmarshal(fis, type);
		} catch(IOException ex) {
			GLog.error("加载Job文件出错，path={}", file.getAbsolutePath());
			GLog.error(ex.getLocalizedMessage(),ex);
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch(Exception ex) {
					;
				}
			}
		}
	}

	public static <T> T loadXml(InputStream is, Class<T> type) {

		FileInputStream fis = null;
		try {
		    return JAXB.unmarshal(is, type);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch(Exception ex) {
					;
				}
			}
		}
	}
	
	public static boolean saveXml(String folder, String fileName, Object obj) {
		File file = new File(folder, fileName);
		return saveXml(file, obj);

	}

	public static boolean saveXml(File file, Object obj) {

		FileOutputStream fos = null;
	    try {
	    	fos = new FileOutputStream(file);
			JAXB.marshal(obj, fos);
	        fos.flush();
	        return true;
	    } catch(IOException ex) {
//	    	GLog.error(ex.getLocalizedMessage());
	    	return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch(Exception ex) {
					;
				}
			}
		}

	}

	public static void main(String[] args){

	}
}

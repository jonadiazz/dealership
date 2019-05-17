package services;

import org.apache.log4j.Logger;

public class LogUtil {

	public static void logException(Exception e, @SuppressWarnings("rawtypes") Class c) {
		Logger log = Logger.getLogger(c);
		
		log.error(e.getClass()+ ": " + e.getMessage());
		
		for(StackTraceElement s: e.getStackTrace()) {
			log.warn(s.getLineNumber() + " " + s.getClassName());
		}
		
	}
}

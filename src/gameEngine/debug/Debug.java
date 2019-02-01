package gameEngine.debug;

public class Debug {
	
	public static void log(Object toLog) {
		
		Throwable t = new Throwable(); 
		StackTraceElement[] elements = t.getStackTrace(); 
		
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(elements[1].getClassName());
		sBuilder.append(".");
		sBuilder.append(elements[1].getMethodName());
		sBuilder.append("() : ");
		sBuilder.append(toLog);
		System.out.println("| Debug | " + sBuilder);
	}
	
	public static void logWarning(Object toLog) {

		Throwable t = new Throwable(); 
		StackTraceElement[] elements = t.getStackTrace(); 
		
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(elements[1].getClassName());
		sBuilder.append(".");
		sBuilder.append(elements[1].getMethodName());
		sBuilder.append("() : ");
		sBuilder.append(toLog);
		System.out.println("! WARNING ! " + sBuilder);
	}
	
	public static void logError(Object toLog) {

		Throwable t = new Throwable(); 
		StackTraceElement[] elements = t.getStackTrace(); 
		
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(elements[1].getClassName());
		sBuilder.append(".");
		sBuilder.append(elements[1].getMethodName());
		sBuilder.append("() : ");
		sBuilder.append(toLog);
		System.err.println("!@! ERROR !@! " + sBuilder);
	}
}

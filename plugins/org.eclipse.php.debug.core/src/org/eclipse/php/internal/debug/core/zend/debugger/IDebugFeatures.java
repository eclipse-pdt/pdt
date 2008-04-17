package org.eclipse.php.internal.debug.core.zend.debugger;

public interface IDebugFeatures {

	public static final int START_PROCESS_FILE_NOTIFICATION = 1;
	public static final int GET_CWD = 2;
	public static final int GET_CALL_STACK_LITE = 3;
	
	/**
	 * Checks whether current debug session is capable of the given feature
	 * @param feature
	 * @return
	 */
	public boolean canDo(int feature);
}

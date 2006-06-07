package org.eclipse.php.debug.core.debugger.parameters;

import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.debug.core.ILaunch;

public abstract class AbstractDebugParametersInitializer implements IDebugParametersInitializer {

	// Parameters
	public static final String START_DEBUG = "start_debug=";
	public static final String DEBUG_PORT = "debug_port=";
	public static final String DEBUG_PASSIVE = "debug_passive=";
	public static final String DEBUG_HOST = "debug_host=";
	public static final String SEND_SESS_END = "send_sess_end=";
	public static final String DEBUG_NO_CACHE = "debug_no_cache=";
	public static final String DEBUG_STOP = "debug_stop=";
	public static final String DEBUG_PROTOCOL = "debug_protocol=";
	public static final String ORIGINAL_URL = "original_url=";
	public static final String DEBUG_SESSION_ID = "debug_session_id=";

	private String id = null;

	/* (non-Javadoc)
	 * @see org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer#getDebugHandler()
	 */
	public String getDebugHandler() {
		return id;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer#setDebugHandler(java.lang.String)
	 */
	public void setDebugHandler(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.debug.core.debugger.parameters.IDebugParametersInitializer#generateQuery(org.eclipse.debug.core.ILaunch)
	 */
	public String generateQuery(ILaunch launch) {
		StringBuffer buf = new StringBuffer();

		Hashtable parameters = generateQueryParameters(launch);
		Enumeration e = parameters.keys();

		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			buf.append(key);
			buf.append(parameters.get(key));
			if (e.hasMoreElements()) {
				buf.append('&'); //$NON-NLS-1$
			}
		}
		return buf.toString();
	}
}

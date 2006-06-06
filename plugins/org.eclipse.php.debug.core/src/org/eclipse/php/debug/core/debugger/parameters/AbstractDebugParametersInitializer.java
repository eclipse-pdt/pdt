package org.eclipse.php.debug.core.debugger.parameters;

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

	public String getDebugHandler() {
		return id;
	}

	public void setDebugHandler(String id) {
		this.id = id;
	}
}

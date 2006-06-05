package org.eclipse.php.debug.core.debugger.parameters;

public abstract class AbstractDebugParametersInitializer implements IDebugParametersInitializer {
	private String id = null;

	public String getDebugHandler() {
		return id;
	}

	public void setDebugHandler(String id) {
		this.id = id;
	}
}

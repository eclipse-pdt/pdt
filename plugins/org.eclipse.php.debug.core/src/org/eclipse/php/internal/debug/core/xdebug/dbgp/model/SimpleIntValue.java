package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

public class SimpleIntValue extends DBGpElement implements IValue {
	private int currentValue;
	private int wantedValue;
	private IVariable[] vars = new IVariable[0];
	
	public SimpleIntValue(int currentValue, int wantedValue, IDebugTarget debugTarget) {
		super(debugTarget);
		this.currentValue = currentValue;
		this.wantedValue = wantedValue;
	}
	
	public String getReferenceTypeName() throws DebugException {
		return "int";
	}

	public String getValueString() throws DebugException {
		//TODO: cache
		if (currentValue == wantedValue) {
			return Integer.toString(currentValue);
		}
		else {
			return Integer.toString(currentValue) + " (" + Integer.toString(wantedValue)+ ")";
		}
	}

	public IVariable[] getVariables() throws DebugException {
		return vars;
	}

	public boolean hasVariables() throws DebugException {
		return false;
	}

	public boolean isAllocated() throws DebugException {
		return false;
	}

	public ILaunch getLaunch() {
		return getDebugTarget().getLaunch();
	}
}

package org.eclipse.php.internal.debug.core.xdebug.dbgp.model;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

public class SimpleByteArrayValue extends DBGpElement implements IValue {
	private byte[] value;
	private int start;
	private int end;
	private IVariable[] elements;
	
	public SimpleByteArrayValue(byte[] value, int start, int end, IDebugTarget debugTarget) {
		super(debugTarget);
		this.value = value;
		this.start = start;
		this.end = end;
	}
	
	public String getReferenceTypeName() throws DebugException {
		return "byte[]";
	}

	public String getValueString() throws DebugException {
		return "";
	}

	public IVariable[] getVariables() throws DebugException {
		if (elements == null) {
			int count = end - start + 1;
			elements = new IVariable[count];
			for (int i = 0; i < count; i++) {
				IValue iv = new SimpleByteValue(value[start + i], getDebugTarget());
				elements[i] = new SimpleVariable(Integer.toString(start + i), iv, getDebugTarget());
			}
		}
		return elements;
	} 

	public boolean hasVariables() throws DebugException {
		return true;
	}

	public boolean isAllocated() throws DebugException {
		return false;
	}

	public ILaunch getLaunch() {
		return getDebugTarget().getLaunch();
	}
}

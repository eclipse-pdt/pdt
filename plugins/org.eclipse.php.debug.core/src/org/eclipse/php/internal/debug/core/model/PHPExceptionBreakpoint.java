/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.model;

import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.Logger;

/**
 * PHP exception breakpoint.
 * 
 * @author Bartlomiej Laczkowski
 */
public class PHPExceptionBreakpoint extends Breakpoint implements IPHPExceptionBreakpoint {

	public static final String MARKER_ID = "org.eclipse.php.debug.core.PHPExceptionBreakpointMarker"; //$NON-NLS-1$

	public static final String ATTR_TYPE = "org.eclipse.php.debug.core.PHPExceptionType"; //$NON-NLS-1$

	private String name;
	private Type type;
	private Map<IDebugTarget, Integer> lines = new WeakHashMap<IDebugTarget, Integer>();
	private Map<IDebugTarget, Integer> ids = new WeakHashMap<IDebugTarget, Integer>();

	/**
	 * Creates new "general" PHP exception breakpoint which internal data will
	 * be set with the use of related persistent marker, see
	 * {@link IBreakpoint#setMarker(IMarker)}.
	 */
	public PHPExceptionBreakpoint() {
		super();
	}

	/**
	 * Creates new "general" PHP exception breakpoint. This constructor should
	 * be used when new type of exception breakpoint should be added and
	 * registered by breakpoint manager.
	 * 
	 * @param name
	 *            exception or error name
	 * @param type
	 *            breakpoint type
	 */
	public PHPExceptionBreakpoint(String name, Type type) {
		this.name = name;
		this.type = type;
		try {
			IMarker marker = ResourcesPlugin.getWorkspace().getRoot().createMarker(MARKER_ID);
			marker.setAttribute(IBreakpoint.PERSISTED, Boolean.TRUE);
			marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
			marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
			marker.setAttribute(IMarker.MESSAGE, name);
			marker.setAttribute(ATTR_TYPE, type.name());
			setMarker(marker);
			setEnabled(true);
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.IBreakpoint#getModelIdentifier()
	 */
	@Override
	public String getModelIdentifier() {
		return IPHPDebugConstants.ID_PHP_DEBUG_CORE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint#
	 * getExceptionName()
	 */
	public String getExceptionName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint#getLine
	 * (org.eclipse.debug.core.model.IDebugTarget)
	 */
	public int getLine(IDebugTarget target) {
		Integer number = lines.get(target);
		return number != null ? number : -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint#getId(
	 * org.eclipse.debug.core.model.IDebugTarget)
	 */
	public int getId(IDebugTarget target) {
		Integer id = ids.get(target);
		return id != null ? id : -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint#getType
	 * ()
	 */
	public Type getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.core.model.Breakpoint#setMarker(org.eclipse.core.
	 * resources.IMarker)
	 */
	@Override
	public void setMarker(IMarker marker) throws CoreException {
		super.setMarker(marker);
		name = marker.getAttribute(IMarker.MESSAGE, null);
		type = Type.valueOf(marker.getAttribute(ATTR_TYPE, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint#setLine
	 * (org.eclipse.debug.core.model.IDebugTarget, int)
	 */
	public void setLine(IDebugTarget target, int line) {
		lines.put(target, line);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.model.IPHPExceptionBreakpoint#setId(
	 * org.eclipse.debug.core.model.IDebugTarget, int)
	 */
	public void setId(IDebugTarget target, int id) {
		ids.put(target, id);
	}

}

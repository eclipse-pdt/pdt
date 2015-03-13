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
package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import java.util.Map;

import org.eclipse.php.internal.core.IUniqueIdentityElement;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerSettingsProvider;
import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsKind;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings;

/**
 * XDebug debugger owner setting provider implementation.
 * 
 * @author Bartlomiej Laczkowski
 */
public class XDebugDebuggerSettingsProvider extends
		AbstractDebuggerSettingsProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerSettingsProvider
	 * #createSettings(org.eclipse.php.internal.debug.core.debugger.
	 * DebuggerSettingsKind,
	 * org.eclipse.php.internal.core.IUniqueIdentityElement)
	 */
	@Override
	protected IDebuggerSettings createSettings(DebuggerSettingsKind kind,
			IUniqueIdentityElement owner) {
		switch (kind) {
		case PHP_SERVER:
			return new XDebugDebuggerServerSettings(owner);
		case PHP_EXE:
			return new XDebugDebuggerExeSettings(owner);
		default:
			break;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerSettingsProvider
	 * #createSettings(org.eclipse.php.internal.debug.core.debugger.
	 * DebuggerSettingsKind,
	 * org.eclipse.php.internal.core.IUniqueIdentityElement, java.util.Map)
	 */
	@Override
	protected IDebuggerSettings createSettings(DebuggerSettingsKind kind,
			IUniqueIdentityElement owner, Map<String, String> attributes) {
		switch (kind) {
		case PHP_SERVER:
			return new XDebugDebuggerServerSettings(owner, attributes);
		case PHP_EXE:
			return new XDebugDebuggerExeSettings(owner, attributes);
		default:
			break;
		}
		return null;
	}

}

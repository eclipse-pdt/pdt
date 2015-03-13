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
package org.eclipse.php.internal.debug.core.zend.debugger;

import java.util.Map;

import org.eclipse.php.internal.core.IUniqueIdentityElement;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerSettingsProvider;
import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsKind;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings;

/**
 * 
 * 
 * @author Bartlomiej Laczkowski
 */
@SuppressWarnings("restriction")
public class ZendDebuggerSettingsProvider extends
		AbstractDebuggerSettingsProvider {

	@Override
	protected IDebuggerSettings createSettings(DebuggerSettingsKind kind,
			IUniqueIdentityElement owner) {
		switch (kind) {
		case PHP_SERVER:
			return new ZendDebuggerServerSettings(owner);
		case PHP_EXE:
			return new ZendDebuggerExeSettings(owner);
		default:
			break;
		}
		return null;
	}

	@Override
	protected IDebuggerSettings createSettings(DebuggerSettingsKind kind,
			IUniqueIdentityElement owner, Map<String, String> attributes) {
		switch (kind) {
		case PHP_SERVER:
			return new ZendDebuggerServerSettings(owner, attributes);
		case PHP_EXE:
			return new ZendDebuggerExeSettings(owner, attributes);
		default:
			break;
		}
		return null;
	}

}

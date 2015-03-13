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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.internal.core.IUniqueIdentityElement;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerSettingsProvider;
import org.eclipse.php.internal.debug.core.debugger.DebuggerSettingsKind;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsWorkingCopy;

/**
 * Zend debugger owner setting provider implementation.
 * 
 * @author Bartlomiej Laczkowski
 */
public class ZendDebuggerSettingsProvider extends
		AbstractDebuggerSettingsProvider {

	private class ServerSettingsWorkingCopy extends ZendDebuggerServerSettings
			implements IDebuggerSettingsWorkingCopy {

		private IDebuggerSettings original;
		private boolean dirty = false;

		protected ServerSettingsWorkingCopy(IDebuggerSettings original) {
			super(original.getOwner(), new HashMap<String, String>(
					original.getAttributes()));
			this.original = original;
		}

		@Override
		public void setAttribute(String key, String value) {
			attributes.put(key, value);
			dirty = true;
		}

		@Override
		public IDebuggerSettings getOriginal() {
			return original;
		}

		@Override
		public boolean isDirty() {
			return dirty;
		}

	}

	private class ExeSettingsWorkingCopy extends ZendDebuggerExeSettings
			implements IDebuggerSettingsWorkingCopy {

		private IDebuggerSettings original;
		private boolean dirty = false;

		protected ExeSettingsWorkingCopy(IDebuggerSettings original) {
			super(original.getOwner(), new HashMap<String, String>(
					original.getAttributes()));
			this.original = original;
		}

		@Override
		public void setAttribute(String key, String value) {
			attributes.put(key, value);
			dirty = true;
		}

		@Override
		public IDebuggerSettings getOriginal() {
			return original;
		}

		@Override
		public boolean isDirty() {
			return dirty;
		}

	}

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
			return new ZendDebuggerServerSettings(owner,
					Collections.unmodifiableMap(attributes));
		case PHP_EXE:
			return new ZendDebuggerExeSettings(owner,
					Collections.unmodifiableMap(attributes));
		default:
			break;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.debug.core.debugger.IDebuggerSettingsProvider
	 * #createWorkingCopy
	 * (org.eclipse.php.internal.debug.core.debugger.IDebuggerSettings)
	 */
	@Override
	public IDebuggerSettingsWorkingCopy createWorkingCopy(
			IDebuggerSettings settings) {
		switch (settings.getKind()) {
		case PHP_SERVER:
			return new ServerSettingsWorkingCopy(settings);
		case PHP_EXE:
			return new ExeSettingsWorkingCopy(settings);
		default:
			break;
		}
		return null;
	}

}

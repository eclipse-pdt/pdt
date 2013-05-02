/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.console;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.preferences.IDebugPreferenceConstants;
import org.eclipse.debug.ui.console.ConsoleColorProvider;
import org.eclipse.debug.ui.console.IConsole;
import org.eclipse.php.internal.debug.core.IPHPConsoleEventListener;
import org.eclipse.php.internal.debug.core.launching.DebugConsoleMonitor;
import org.eclipse.php.internal.debug.core.launching.PHPHyperLink;
import org.eclipse.php.internal.debug.core.launching.PHPProcess;
import org.eclipse.php.internal.debug.core.launching.PHPStreamsProxy;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.swt.graphics.Color;

/**
 * 
 * The default behavior of mapping stdin/stdout to the console doesn't apply to
 * PHP, since the process in the debug target is generally a web browser, and a
 * output view. Instead, create IStreamMonitors onto which we will map parser
 * errors.
 */
public class PHPConsoleColorProvider extends ConsoleColorProvider {

	private static IPHPConsoleEventListener[] fConsoleEventListeners;
	private PHPProcess fProcess;
	private IConsole fConsole;
	private ILaunch fLaunch;
	private PHPHyperLink fPHPHyperLink;
	private final static String PHP_DEBUG_STREAM = PHPDebugUIPlugin.getID()
			+ ".PHP_CONSOLE_STREAM"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.console.IConsoleColorProvider#connect(org.eclipse
	 * .debug.core.model.IProcess, org.eclipse.debug.ui.console.IConsole)
	 */
	public void connect(IProcess process, IConsole console) {
		fConsole = console;

		PHPStreamsProxy proxy = (PHPStreamsProxy) process.getStreamsProxy();
		if (process instanceof PHPProcess) {
			fProcess = (PHPProcess) process;
			fProcess.setConsole(fConsole);
			fPHPHyperLink = new PHPHyperLink();
			fProcess.setPHPHyperLink(fPHPHyperLink);
		} else {
			return;
		}

		DebugConsoleMonitor debugMonitor = (DebugConsoleMonitor) proxy
				.getConsoleStreamMonitor();
		if (proxy != null) {
			fConsole.connect(debugMonitor, PHP_DEBUG_STREAM);
		}

		fLaunch = process.getLaunch();

		PHPDebugTarget target = null;
		if (fLaunch.getDebugTarget() instanceof PHPDebugTarget) {
			target = (PHPDebugTarget) fLaunch.getDebugTarget();
		}
		if (target != null) {
			IPHPConsoleEventListener[] listeners = getConsoleEventListeners();
			for (IPHPConsoleEventListener eventListener : listeners) {
				eventListener.init(fLaunch, debugMonitor, fPHPHyperLink);
				target.addConsoleEventListener(eventListener);
			}
		}
		super.connect(process, fConsole);
	}

	private static IPHPConsoleEventListener[] getConsoleEventListeners() {
		if (fConsoleEventListeners == null) {
			Map<String, IPHPConsoleEventListener> listeners = new HashMap<String, IPHPConsoleEventListener>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IConfigurationElement[] elements = registry
					.getConfigurationElementsFor(PHPDebugUIPlugin.getID(),
							"phpConsoleListeners"); //$NON-NLS-1$
			for (IConfigurationElement element : elements) {
				if ("listener".equals(element.getName())) { //$NON-NLS-1$
					String id = element.getAttribute("id"); //$NON-NLS-1$
					if (!listeners.containsKey(id)) {
						String overridesIds = element
								.getAttribute("overridesId"); //$NON-NLS-1$
						if (overridesIds != null) {
							StringTokenizer st = new StringTokenizer(
									overridesIds, ", "); //$NON-NLS-1$
							while (st.hasMoreTokens()) {
								listeners.put(st.nextToken(), null);
							}
						}
						try {
							listeners
									.put(id, (IPHPConsoleEventListener) element
											.createExecutableExtension("class")); //$NON-NLS-1$
						} catch (CoreException e) {
							PHPDebugUIPlugin.log(e);
						}
					}
				}
			}
			Collection<IPHPConsoleEventListener> l = listeners.values();
			while (l.remove(null))
				; // remove null elements
			fConsoleEventListeners = l
					.toArray(new IPHPConsoleEventListener[listeners.size()]);
		}
		return fConsoleEventListeners;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.console.IConsoleColorProvider#disconnect()
	 */
	public void disconnect() {
		fConsole = null;
		fProcess = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.console.IConsoleColorProvider#isReadOnly()
	 */
	public boolean isReadOnly() {
		return true/* fProcess == null || fProcess.isTerminated() */;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.console.IConsoleColorProvider#getColor(java.lang
	 * .String)
	 */
	public Color getColor(String streamIdentifer) {
		if (PHP_DEBUG_STREAM.equals(streamIdentifer)) {
			// TODO: fix to use own preferences.
			return DebugUIPlugin
					.getPreferenceColor(IDebugPreferenceConstants.CONSOLE_SYS_ERR_COLOR);
		}
		return null;
	}

	/**
	 * Returns the process this color provider is providing color for, or
	 * <code>null</code> if none.
	 * 
	 * @return the process this color provider is providing color for, or
	 *         <code>null</code> if none
	 */
	protected IProcess getProcess() {
		return fProcess;
	}

	/**
	 * Returns the console this color provider is connected to, or
	 * <code>null</code> if none.
	 * 
	 * @return IConsole the console this color provider is connected to, or
	 *         <code>null</code> if none
	 */
	protected IConsole getConsole() {
		return fConsole;
	}
}

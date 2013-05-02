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
/**
 * 
 */
package org.eclipse.php.internal.debug.core.debugger;

import org.eclipse.swt.widgets.Shell;

/**
 * A generic debugger configuration that should be implemented by any debugger
 * that is attached to the phpDebuggers extension point. The debugger
 * configuration supplies specific debugger attributes as well as the option to
 * obtain other attributes. Also, the debugger configuration can contribute to
 * the UI by creating a Composite widget for setting its attributes.
 * 
 * The IDebuggerConfiguration is connected to the PHP debug plug-in in a way
 * that any attribute that exists in the preferences is returned when requested.
 * Attributes that do not exist in the preferences are held in this runtime
 * instance of the debugger configuration and can be accessed as needed.
 * 
 * @author Shalom Gibly
 * @since PDT 1.0
 */
public interface IDebuggerConfiguration {

	/**
	 * Attribute key for the debugger's name.
	 */
	public String DEBUGGER_NAME = "name"; //$NON-NLS-1$
	/**
	 * Attribute key for the debugger's id.
	 */
	public String DEBUGGER_ID = "id"; //$NON-NLS-1$
	/**
	 * Attribute key for the debugger's delegate for executing PHP scripts using
	 * a local PHP executable.
	 */
	public String SCRIPT_LAUNCH_DELEGATE_CLASS = "scriptDelegateClass"; //$NON-NLS-1$
	/**
	 * Attribute key for the debugger's delegate for executing PHP using a web
	 * server.
	 */
	public String WEB_LAUNCH_DELEGATE_CLASS = "webDelegateClass"; //$NON-NLS-1$

	/**
	 * Opens a dialog for editing/displaying the debugger's configuration.
	 * 
	 * @param parentShell
	 *            A parent Shell, or null (use the default display Shell).
	 */
	public void openConfigurationDialog(Shell parentShell);

	/**
	 * Returns the attribute value for the given id.
	 * 
	 * @param id
	 *            The attribute id.
	 * @return The attribute's value. Null, if non exists.
	 */
	public String getAttribute(String id);

	/**
	 * Returns the debugger's port number.
	 * 
	 * @return The debugger's port number. -1, if none is defined.
	 */
	public int getPort();

	/**
	 * Returns the debugger's display name.
	 * 
	 * @return The name of the debugger.
	 */
	public String getName();

	/**
	 * Returns the name of the delegate class for PHP script launching.
	 * 
	 * @return The delegate class name for PHP script launching.
	 */
	public String getScriptLaunchDelegateClass();

	/**
	 * Returns the name of the delegate class for PHP web page launching.
	 * 
	 * @return The delegate class name for PHP web page launching.
	 */
	public String getWebLaunchDelegateClass();

	/**
	 * Returns the debugger's identification string.
	 * 
	 * @return The debugger's id.
	 */
	public String getDebuggerId();

	/**
	 * Save any changes that were made to this configuration.
	 */
	public void save();

}

/*******************************************************************************
 * Copyright (c) 2009,2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [339547]
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.php.internal.core.IUniqueIdentityElement;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.UniqueIdentityElementUtil;
import org.eclipse.php.internal.debug.core.Logger;
import org.eclipse.php.internal.debug.core.PHPDebugPlugin;
import org.eclipse.php.internal.debug.core.PHPExeException;
import org.eclipse.php.internal.debug.core.PHPExeUtil;
import org.eclipse.php.internal.debug.core.PHPExeUtil.PHPExeInfo;

/**
 * A PHP executable item. An item has a name, version, path, configuration file
 * path and a debugger ID. It can be editable or non-editable in case it was
 * loaded from an extension point.
 * 
 * @author shalom, michael
 */
public class PHPexeItem implements IUniqueIdentityElement, IPHPexeItemProperties {

	public static final String SAPI_CLI = "CLI"; //$NON-NLS-1$
	public static final String SAPI_CGI = "CGI"; //$NON-NLS-1$
	public static final String ID_PREFIX = "php-exe"; //$NON-NLS-1$

	private final class EventNotifier {

		void notify(String key, Object oldValue, Object newValue) {
			PHPexeItemEvent event = new PHPexeItemEvent(PHPexeItem.this, key, oldValue, newValue);
			for (Object listener : listeners.getListeners()) {
				((IPHPexeItemListener) listener).phpExeChanged(event);
			}
		}

	}

	private String uniqueId;
	private boolean editable = true;
	/**
	 * store the php version list that use this PHPexeItem as default PHPexeItem
	 */
	private List<PHPVersion> defaultForPHPVersionList = new ArrayList<PHPVersion>();
	private final Map<String, Object> properties;
	private final ListenerList listeners = new ListenerList();
	private final EventNotifier notifier;

	/**
	 * Constructs a new PHP executable item.
	 */
	public PHPexeItem() {
		setUniqueId(UniqueIdentityElementUtil.generateId(ID_PREFIX));
		properties = new HashMap<String, Object>();
		notifier = new EventNotifier();
		setDebuggerID(PHPDebuggersRegistry.NONE_DEBUGGER_ID);
	}

	/**
	 * Constructs a new PHP executable item.
	 * 
	 * @param name
	 * @param executable
	 * @param iniLocation
	 * @param debuggerID
	 * @param editable
	 */
	public PHPexeItem(String name, File executable, File iniLocation, String debuggerID, boolean editable) {
		this();
		this.editable = editable;
		setName(name);
		setExecutable(executable);
		setINILocation(iniLocation);
		setDebuggerID(debuggerID);
		// Detect other properties from PHP executable
		detectFromPHPExe();
	}

	/**
	 * Constructs a new PHP executable item.
	 * 
	 * @param name
	 *            PHP executable nice name (like: PHP 5.3 CGI)
	 * @param executable
	 *            PHP executable file
	 * @param config
	 *            The configuration file (php.ini) location (can be null)
	 * @param debuggerID
	 *            ID of debugger (see org.eclipse.php.debug.core.phpDebuggers
	 *            extension point)
	 * @param loadDefaultINI
	 *            Disable php "-n" usage
	 */
	PHPexeItem(String name, String executable, String config, String debuggerID, boolean loadDefaultINI) {
		this();
		setName(name);
		setDebuggerID(debuggerID);
		setExecutable(new File(executable));
		if (config != null && config.length() > 0) {
			setINILocation(new File(config));
		}
		setLoadDefaultINI(loadDefaultINI);
		// Detect other properties from PHP executable
		detectFromPHPExe();
	}

	final void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	/**
	 * Adds PHP exe item listener.
	 * 
	 * @param listener
	 */
	public void addPHPexeListener(IPHPexeItemListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes PHP exe item listener.
	 * 
	 * @param listener
	 */
	public void removePHPexeListener(IPHPexeItemListener listener) {
		listeners.remove(listener);
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * Returns the name of this PHP executable item.
	 * 
	 * @return The name of the item.
	 */
	public String getName() {
		return (String) properties.get(PROP_NAME);
	}

	/**
	 * Returns the php executable file.
	 * 
	 * @return The php executable file.iniLocation
	 */
	public File getExecutable() {
		return (File) properties.get(PROP_EXE_LOCATION);
	}

	/**
	 * Returns the configuration file path. The returned value can be null in
	 * case the value was not set. In this case, the ini location is assumed to
	 * be next to the php executable.
	 * 
	 * @return The configuration file location.
	 */
	public File getINILocation() {
		return (File) properties.get(PROP_INI_LOCATION);
	}

	/**
	 * Returns the debugger ID set for this item.
	 * 
	 * @return The debugger ID.
	 */
	public String getDebuggerID() {
		return (String) properties.get(PROP_DEBUGGER_ID);
	}

	/**
	 * Returns SAPI type of this PHP executable
	 * 
	 * @return
	 */
	public String getSapiType() {
		return (String) properties.get(PROP_SAPI_TYPE);
	}

	/**
	 * Returns the version of the item.
	 * 
	 * @return The item's version.
	 */
	public String getVersion() {
		return (String) properties.get(PROP_VERSION);
	}

	/**
	 * Set the debugger ID that can use this item.
	 * 
	 * @param debuggerID
	 *            A debugger ID.
	 */
	public void setDebuggerID(String debuggerID) {
		setProperty(PROP_DEBUGGER_ID, debuggerID);
	}

	/**
	 * Set the PHP ini location.
	 * 
	 * @param location
	 *            The ini location (can be null).
	 */
	public void setINILocation(File location) {
		setProperty(PROP_INI_LOCATION, location);
	}

	/**
	 * If true PHPLaunchUtilities ignore "-n"
	 * 
	 * @param loadDefaultINI
	 */
	public void setLoadDefaultINI(boolean loadDefaultINI) {
		setProperty(PROP_USE_DEFAULT_INI, loadDefaultINI);
	}

	/**
	 * Sets SAPI type of this PHP executable
	 * 
	 * @param sapiType
	 */
	public void setSapiType(String sapiType) {
		setProperty(PROP_SAPI_TYPE, sapiType);
	}

	/**
	 * Sets the name of this item.
	 * 
	 * @param name
	 *            The name of the item.
	 */
	public void setName(String name) {
		setProperty(PROP_NAME, name);
	}

	/**
	 * Sets the php executable path. Setting the path also sets the executable
	 * directory and reset the ini location to null.
	 * 
	 * @return The php executable file.
	 * @throws IllegalArgumentException
	 *             in case the file is null.
	 */
	public void setExecutable(File executable) {
		if (executable != null && executable.equals(getExecutable())) {
			return;
		}
		setProperty(PROP_EXE_LOCATION, executable);
		setProperty(PROP_INI_LOCATION, null);
	}

	/**
	 * Sets the version of the item.
	 * 
	 * @param version
	 *            The item's version.
	 */
	public void setVersion(String version) {
		setProperty(PROP_VERSION, version);
	}

	/**
	 * Returns if this item is editable (e.g. a user defined item).
	 * 
	 * @return True, if this item can be edited.
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * Returns if this item is the default item.
	 * 
	 * @return if this item is the default item.
	 */
	public boolean isDefault() {
		return getName().equals(
				InstanceScope.INSTANCE.getNode(PHPDebugPlugin.ID).get(PHPDebugCorePreferenceNames.DEFAULT_PHP, null));
	}

	/**
	 * @return return loadDefault
	 */
	public boolean isLoadDefaultINI() {
		Boolean isLoadDefaultINI = (Boolean) properties.get(PROP_USE_DEFAULT_INI);
		return isLoadDefaultINI != null ? isLoadDefaultINI : false;
	}

	/**
	 * Creates editable working copy of this item.
	 * 
	 * @return working copy of this item
	 */
	public PHPexeItem makeCopy() {
		PHPexeItem copy = new PHPexeItem();
		// Unique ID should always be the same for copy
		copy.uniqueId = uniqueId;
		copy.editable = editable;
		copy.defaultForPHPVersionList = new ArrayList<PHPVersion>(defaultForPHPVersionList);
		copy.setExecutable(getExecutable());
		copy.setSapiType(getSapiType());
		copy.setName(getName());
		copy.setINILocation(getINILocation());
		copy.setVersion(getVersion());
		copy.setLoadDefaultINI(isLoadDefaultINI());
		copy.setDebuggerID(getDebuggerID());
		return copy;
	}

	void addPHPVersionToDefaultList(PHPVersion phpVersion) {
		defaultForPHPVersionList.add(phpVersion);
	}

	void removePHPVersionToDefaultList(PHPVersion phpVersion) {
		defaultForPHPVersionList.remove(phpVersion);
	}

	int geDefaultForPHPVersionSize() {
		return defaultForPHPVersionList.size();
	}

	PHPVersion getPHPVersionAtDefaultList(int index) {
		assert geDefaultForPHPVersionSize() > index;
		return defaultForPHPVersionList.get(index);
	}

	void setDefaultForPHPVersion(PHPexes phpexes, PHPVersion phpVersion) {
		phpexes.setItemDefaultForPHPVersion(this, phpVersion);
	}

	protected void detectFromPHPExe() {
		PHPExeInfo phpInfo;
		try {
			phpInfo = PHPExeUtil.getPHPInfo(getExecutable(), false);
			if (phpInfo == null)
				return;
		} catch (PHPExeException e) {
			Logger.logException("Could not obtain PHP executable info.", //$NON-NLS-1$
					e);
			return;
		}
		if (getName() == null)
			setName(phpInfo.getName());
		if (getSapiType() == null)
			setSapiType(phpInfo.getSapiType());
		if (getVersion() == null)
			setVersion(phpInfo.getVersion());
	}

	private void setProperty(String key, Object newValue) {
		Object oldValue = properties.put(key, newValue);
		if (oldValue == null && newValue == null) {
			return;
		}
		if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null)
				|| (oldValue != null && !oldValue.equals(newValue))) {
			fireEvent(key, oldValue, newValue);
		}
	}

	private void fireEvent(String key, Object oldValue, Object newValue) {
		notifier.notify(key, oldValue, newValue);
	}

}

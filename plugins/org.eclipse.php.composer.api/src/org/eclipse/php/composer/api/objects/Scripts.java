/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.composer.api.collection.JsonArray;

/**
 * Represents the scripts entry in a composer package
 * 
 * @see http://getcomposer.org/doc/articles/scripts.md
 * @see http://getcomposer.org/doc/04-schema.md#scripts
 * @author Thomas Gossmann <gos.si>
 */
public class Scripts extends JsonObject {

	@SuppressWarnings("rawtypes")
	protected void doParse(Object obj) {
		if (obj instanceof LinkedHashMap) {

			LinkedHashMap json = (LinkedHashMap)obj;

			for (String event : getEvents()) {
				parseScripts(json, event);
			}
		}
	}
	
	public static String[] getEvents() {
		return new String[] {
				"pre-install-cmd", "post-install-cmd", 
				"pre-update-cmd", "post-update-cmd",
				"pre-package-install", "post-package-install", 
				"pre-package-update", "post-package-update", 
				"pre-package-uninstall", "post-package-uninstall"};
	}
	
	@Override
	protected List<String> getOwnProperties() {
		List<String> list = new ArrayList<String>(Arrays.asList(getEvents()));
		list.addAll(super.getOwnProperties());
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	private void parseScripts(LinkedHashMap json, String property) {
		if (json.containsKey(property)) {
			JsonArray values;
			Object value = json.get(property);
			
			if (value instanceof LinkedList) {
				values = new JsonArray(value);
			} else {
				values = new JsonArray();
				values.add(value);
			}
			
			set(property, values);
			json.remove(property);
		}
	}
	
	
	/**
	 * Gets scripts that will occur before the 
	 * <pre>install</pre> command is executed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPreInstallCmd() {
		return getAsArray("pre-install-cmd");
	}
	
	/**
	 * Gets scripts that will occur after the 
	 * <pre>install</pre> command is executed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPostInstallCmd() {
		return getAsArray("post-install-cmd");
	}
	
	/**
	 * Gets scripts that will occur before the 
	 * <pre>update</pre> command is executed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPreUpdateCmd() {
		return getAsArray("pre-update-cmd");
	}
	
	/**
	 * Gets scripts that will occur after the 
	 * <pre>update</pre> command is executed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPostUpdateCmd() {
		return getAsArray("post-update-cmd");
	}
	
	/**
	 * Gets scripts that will occur before a package is installed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPrePackageInstall() {
		return getAsArray("pre-package-install");
	}
	
	/**
	 * Gets scripts that will occur after a package is installed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPostPackageInstall() {
		return getAsArray("post-package-install");
	}
	
	/**
	 * Gets scripts that will occur before a package is updateed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPrePackageUpdate() {
		return getAsArray("pre-package-update");
	}
	
	/**
	 * Gets scripts that will occur after a package is updateed.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPostPackageUpdate() {
		return getAsArray("post-package-update");
	}
	
	/**
	 * Gets scripts that will occur before a package is uninstalled.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPrePackageUninstall() {
		return getAsArray("pre-package-uninstall");
	}
	
	/**
	 * Gets scripts that will occur after a package is uninstalled.
	 * 
	 * @return the scripts
	 */
	public JsonArray getPostPackageUninstall() {
		return getAsArray("post-package-uninstall");
	}
}

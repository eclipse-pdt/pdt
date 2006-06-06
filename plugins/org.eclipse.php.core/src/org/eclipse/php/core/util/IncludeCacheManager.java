/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.PHPCorePlugin;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.xml.sax.InputSource;

/**
 * A class that manage the mapping between the projects and their include paths.
 * This class is also responsible of removing any un-needed cache files from the shared cache
 * folder.
 */
public class IncludeCacheManager {

	private static final String INCLUDES_MAP_XML = "includes.xml";//$NON-NLS-1$
	private Object lock = new Object();
	private File includeXMLFile;
	private DefaultCacheManager cacheManager;

	/**
	 * Constructs a new IncludeCacheManager.
	 */
	public IncludeCacheManager(DefaultCacheManager cacheManager) {
		this.cacheManager = cacheManager;
		includeXMLFile = new File(cacheManager.getSharedCacheDirectory(), INCLUDES_MAP_XML);
	}

	/**
	 * Update the include-paths XML with a new list of paths for the given project. 
	 * This method also check for any un-used cache files and remove them from the disk.
	 * 
	 * @param project The modified IProject.
	 * @param includePaths The new list of include-paths (a list of string paths).
	 * @return The cached Map of IProject name to List of cache files names
	 */
	public Map includePathChanged(IProject project, List includePaths) {
		Map cachedMap = null;
		synchronized (lock) {
			String key = project.getName();
			cachedMap = getCachedMap();
			List oldPaths = (List) cachedMap.put(key, includePaths);
			if (oldPaths != null) {
				checkPaths(oldPaths, cachedMap);
				deletePaths(oldPaths);
			}
			saveMap(cachedMap);
		}
		return cachedMap;
	}

	/**
	 * Update the include-paths XML when a project is removed. 
	 * This method also check for any un-used cache files and remove them from the disk.
	 * 
	 * @param project The IProject that was removed.
	 * @return The cached Map of IProject name to List of cache files names
	 */
	public Map projectRemoved(IProject project) {
		Map cachedMap = null;
		synchronized (lock) {
			String key = project.getName();
			cachedMap = getCachedMap();
			List pathsToRemove = (List) cachedMap.remove(key);
			if (pathsToRemove != null) {
				checkPaths(pathsToRemove, cachedMap);
				deletePaths(pathsToRemove);
			}
			saveMap(cachedMap);
		}
		return cachedMap;
	}

	/**
	 * Update the include-paths XML when a project PHP version is changed.
	 * Note: This method assumes that any cache file ends-up with the PHP version string.
	 * 
	 * 
	 * @param project
	 * @param oldVersion
	 * @param newVersion 
	 * @return The cached Map of IProject name to List of cache files names; Null if the old or the new phpVersion
	 * are null.
	 */
	public Map phpVersionChanged(IProject project, String oldVersion, String newVersion) {
		if (oldVersion == null || newVersion == null) {
			return null;
		}
		Map cachedMap = null;
		synchronized (lock) {
			String key = project.getName();
			cachedMap = getCachedMap();
			List oldPaths = (List)cachedMap.get(key);
			if (oldPaths != null) {
				// Go over the names and change each on of them to end with the new PHP version.
				List newNames = new ArrayList(oldPaths.size());
				for (int i = 0; i < oldPaths.size(); i++) {
					newNames.add(((String)oldPaths.get(i)).replaceAll(oldVersion, newVersion));
				}
				cachedMap.put(key, newNames);
				checkPaths(oldPaths, cachedMap);
				deletePaths(oldPaths);
			}
			saveMap(cachedMap);
		}
		return cachedMap;
	}
	
	/**
	 * Check that all the projects in the map actually exists in the workspace.
	 * If a project is missing (probably as a result of a renaming) remove it from the map and check 
	 * if any of its cache files can also be removed.
	 */
	public void checkCache() {
		Map cachedMap = getCachedMap();
		IProject[] projects = PHPWorkspaceModelManager.getInstance().listProjects();
		HashMap projectsMap = new HashMap();
		for (int i = 0; i < projects.length; i++) {
			projectsMap.put(projects[i].getName(), projects[i]);
		}
		List pathsToRemove = new ArrayList();
		Set keysSet = cachedMap.keySet();
		Object[] keys = new Object[keysSet.size()];
		keysSet.toArray(keys);
		for (int i = 0; i < keys.length; i++) {
			if (!projectsMap.containsKey(keys[i])) {
				List list = (List)cachedMap.remove(keys[i]);
				pathsToRemove.addAll(list);
			}
		}
		// Delete any cache file that is not related to other projects
		checkPaths(pathsToRemove, cachedMap);
		deletePaths(pathsToRemove);
	}
	
	/*
	 * Deletes a list of files from the disk.
	 * The given List contains the cached file names.
	 */
	private void deletePaths(List pathsToRemove) {
		Iterator paths = pathsToRemove.iterator();
		File sharedDirectory = cacheManager.getSharedCacheDirectory();
		while (paths.hasNext()) {
			// Note: Since we are given with the file names, we attach the cache directory and then delete the file.
			File file = new File(sharedDirectory, (String) paths.next());
			file.delete();
		}
	}

	/*
	 * Check the given List of paths and remove any path that exists in the given map as a value
	 * for another key.
	 * In that way we avoid deleting any path that has a reference in another project.
	 */
	private void checkPaths(List pathsToRemove, Map cachedMap) {
		HashMap tempMap = new HashMap();
		Iterator values = cachedMap.values().iterator();
		while (values.hasNext()) {
			Iterator paths = ((List) values.next()).iterator();
			while (paths.hasNext()) {
				tempMap.put(paths.next(), "dummy");//$NON-NLS-1$
			}
		}
		// Filter out any paths that exists in the tempMap
		for (int i = pathsToRemove.size() - 1; i >= 0; i--) {
			if (tempMap.containsKey(pathsToRemove.get(i))) {
				pathsToRemove.remove(i);
			}
		}
	}

	/*
	 * Saves the include-paths mapping to the XML.
	 */
	private void saveMap(Map cachedMap) {
		MapXMLWriter xmlWriter = null;
		try {
			xmlWriter = new MapXMLWriter(new FileOutputStream(includeXMLFile));
			xmlWriter.writeMap(cachedMap);
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		} finally {
			if (xmlWriter != null) {
				xmlWriter.flush();
				xmlWriter.close();
			}
		}
	}

	/*
	 * Reads and returns a Map from the include-paths XML.
	 */
	private Map getCachedMap() {
		Map map = null;
		try {
			if (includeXMLFile.exists()) {
				map = MapXMLReader.readMap(new InputSource(new InputStreamReader(new FileInputStream(includeXMLFile), "UTF8")));
				// The stream is closed by the parser...
			}
		} catch (Exception e) {
			PHPCorePlugin.log(e);
		}
		if (map == null) {
			map = new HashMap();
		}
		return map;
	}

}

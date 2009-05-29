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
package org.eclipse.php.internal.core.language.keywords;

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.ProjectOptions;

/**
 * This class is used for retrieval PHP keywords information for the given PHP version 
 */
public class PHPKeywords {

	/** Keyword may be used in class body */
	public static final int CLASS_BODY = (1 << 0);

	/** Keyword may be used in global context */
	public static final int GLOBAL = (1 << 1);

	/**
	 * This class contains code assist auto-complete information about keyword
	 */
	public static class KeywordData implements Comparable<KeywordData> {
		public String name;
		public String suffix;
		public int suffixOffset;
		public int context = GLOBAL;

		/**
		 * Constructs keyword data with default context: {@link #GLOBAL}
		 * @param name
		 * @param suffix
		 * @param suffixOffset
		 */
		public KeywordData(String name, String suffix, int suffixOffset) {
			this.name = name;
			this.suffix = suffix;
			this.suffixOffset = suffixOffset;
		}

		public KeywordData(String name, String suffix, int suffixOffset, int context) {
			this.name = name;
			this.suffix = suffix;
			this.suffixOffset = suffixOffset;
			this.context = context;
		}

		public int hashCode() {
			return name.hashCode();
		}

		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			KeywordData other = (KeywordData) obj;
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}

		public int compareTo(KeywordData o) {
			return this.name.compareTo(o.name);
		}
	}

	private static final Map<PHPVersion, PHPKeywords> instances = new HashMap<PHPVersion, PHPKeywords>();
	private Collection<KeywordData> keywordData;

	private PHPKeywords(IPHPKeywordsInitializer keywordsInitializer) {
		keywordData = new TreeSet<KeywordData>();
		keywordsInitializer.initialize(keywordData);
		keywordsInitializer.initializeSpecific(keywordData);
	}

	public static PHPKeywords getInstance(IProject project) {
		PHPVersion version = ProjectOptions.getPhpVersion(project);
		synchronized (instances) {
			if (!instances.containsKey(version)) {
				PHPKeywords instance;
				if (PHPVersion.PHP4 == version) {
					instance = new PHPKeywords(new KeywordInitializerPHP_4());
				} else if (PHPVersion.PHP5 == version) {
					instance = new PHPKeywords(new KeywordInitializerPHP_5());
				} else if (PHPVersion.PHP5_3 == version) {
					instance = new PHPKeywords(new KeywordInitializerPHP_5_3());
				} else {
					throw new IllegalArgumentException("No PHP version defined for project!");
				}
				instances.put(version, instance);
			}
		}
		return instances.get(version);
	}

	/**
	 * Returns a list of keyword code assist auto-complete information by prefix
	 * @param prefix
	 * @return keywords info list
	 */
	public Collection<KeywordData> findByPrefix(String prefix) {
		List<KeywordData> result = new LinkedList<KeywordData>();
		for (KeywordData data : keywordData) {
			if (data.name.startsWith(prefix)) {
				result.add(data);
			}
		}
		return result;
	}

	/**
	 * Returns a list of keywords by prefix
	 * @param prefix
	 * @return keywords info list
	 */
	public Collection<String> findNamesByPrefix(String prefix) {
		List<String> result = new LinkedList<String>();
		for (KeywordData data : keywordData) {
			if (data.name.startsWith(prefix)) {
				result.add(data.name);
			}
		}
		return result;
	}
}

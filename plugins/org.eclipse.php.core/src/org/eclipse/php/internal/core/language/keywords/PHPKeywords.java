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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.search.Messages;

/**
 * This class is used for retrieval PHP keywords information for the given PHP
 * version
 */
public class PHPKeywords {

	/** Keyword may be used in class body */
	public static final int CLASS_BODY = (1 << 0);

	/** Keyword may be used in method parameters context */
	public static final int METHOD_BODY = (1 << 1);

	/** Keyword may be used in global context */
	public static final int GLOBAL = (1 << 2);

	/** Keyword may be used in method parameters context */
	public static final int METHOD_PARAM = (1 << 3);

	/**
	 * This class contains code assist auto-complete information about keyword
	 */
	public static class KeywordData implements Comparable<KeywordData> {
		public String name;
		public String suffix;
		public int suffixOffset;
		public int context = PHPKeywords.GLOBAL | PHPKeywords.METHOD_BODY;
		public boolean ignoreCase = false;

		/**
		 * Constructs keyword data with default context: {@link #GLOBAL}
		 * 
		 * @param name
		 * @param suffix
		 * @param suffixOffset
		 */
		public KeywordData(String name, String suffix, int suffixOffset, boolean ignoreCase) {
			this.name = name;
			this.suffix = suffix;
			this.suffixOffset = suffixOffset;
			this.ignoreCase = ignoreCase;
		}

		public KeywordData(String name, String suffix, int suffixOffset) {
			this(name, suffix, suffixOffset, false);
		}

		public KeywordData(String name, String suffix, int suffixOffset, int context, boolean ignoreCase) {
			this.name = name;
			this.suffix = suffix;
			this.suffixOffset = suffixOffset;
			this.context = context;
			this.ignoreCase = ignoreCase;
		}

		public KeywordData(String name, String suffix, int suffixOffset, int context) {
			this(name, suffix, suffixOffset, context, false);
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
			return this.ignoreCase ? this.name.compareToIgnoreCase(o.name) : this.name.compareTo(o.name);
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
		PHPVersion version = ProjectOptions.getPHPVersion(project);
		synchronized (instances) {
			if (!instances.containsKey(version)) {
				PHPKeywords instance;
				if (PHPVersion.PHP5 == version) {
					instance = new PHPKeywords(new KeywordInitializerPHP_5());
				} else if (PHPVersion.PHP5_3 == version) {
					instance = new PHPKeywords(new KeywordInitializerPHP_5_3());
				} else if (PHPVersion.PHP5_4 == version) {
					instance = new PHPKeywords(new KeywordInitializerPHP_5_4());
				} else if (PHPVersion.PHP5_5 == version) {
					instance = new PHPKeywords(new KeywordInitializerPHP_5_5());
				} else if (PHPVersion.PHP5_6 == version) {
					instance = new PHPKeywords(new KeywordInitializerPHP_5_6());
				} else if (PHPVersion.PHP7_0 == version) {
					instance = new PHPKeywords(new KeywordInitializerPHP_7());
				} else if (PHPVersion.PHP7_1 == version) {
					instance = new PHPKeywords(new KeywordInitializerPHP_7_1());
				} else {
					if (version == null) {
						throw new IllegalArgumentException(CoreMessages.getString("UnknownPHPVersion_0")); //$NON-NLS-1$
					} else {
						throw new IllegalArgumentException(
								Messages.format(CoreMessages.getString("UnknownPHPVersion_1"), version)); //$NON-NLS-1$
					}
				}
				instances.put(version, instance);
			}
		}
		return instances.get(version);
	}

	/**
	 * Returns a list of keyword code assist auto-complete information by prefix
	 * 
	 * @param prefix
	 * @return keywords info list
	 */
	public Collection<KeywordData> findByPrefix(String prefix) {
		List<KeywordData> result = new LinkedList<KeywordData>();
		if (prefix == null) {
			return result;
		}
		for (KeywordData data : keywordData) {
			if (data.name.startsWith(prefix)
					|| (data.ignoreCase && StringUtils.startsWithIgnoreCase(data.name, prefix))) {
				result.add(data);
			}
		}
		return result;
	}

	/**
	 * Returns a list of keywords by prefix
	 * 
	 * @param prefix
	 * @return keywords info list
	 */
	public Collection<String> findNamesByPrefix(String prefix) {
		List<String> result = new LinkedList<String>();
		if (prefix == null) {
			return result;
		}
		for (KeywordData data : keywordData) {
			if (data.name.startsWith(prefix)
					|| (data.ignoreCase && StringUtils.startsWithIgnoreCase(data.name, prefix))) {
				result.add(data.name);
			}
		}
		return result;
	}
}

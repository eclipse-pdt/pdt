/*******************************************************************************
 * Copyright (c) 2014, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.php.composer.core.model.IPackage;

/**
 * This class is based on Composer\Package\Version\VersionParser.php class.
 * Parser functionality is simplified to validation rules. Class should be able
 * to detect version constraints that will broke composer.json configuration.
 * Actually it's detects only error level problems, not warnings.
 * 
 * @author Michal Niewrzal, 2014
 * 
 */
public class VersionConstraintValidator {

	private static final String MODIFIER_REGEX = "[._-]?(?:(stable|beta|b|RC|alpha|a|patch|pl|p)(?:[.-]?(\\d+))?)?([.-]?dev)?"; //$NON-NLS-1$

	private static final String STABILITIES = "stable|RC|beta|alpha|dev"; //$NON-NLS-1$

	public String validate(IPackage p) {
		return validate(p.getVersionConstraint());
	}

	public String validate(String constraint) {
		constraint = constraint.trim();
		if (constraint == null || constraint.isEmpty()) {
			return "Version cannot be empty"; //$NON-NLS-1$
		}
		constraint = constraint.replaceAll("[ ,|]+", ","); //$NON-NLS-1$ //$NON-NLS-2$
		String[] split = constraint.split(","); //$NON-NLS-1$
		for (String part : split) {
			if (!part.isEmpty()) {
				String result = validateConstraint(part);
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}

	private String validateConstraint(String constraint) {
		String[] match = null;

		if (constraint.equalsIgnoreCase("self.version")) { //$NON-NLS-1$
			return null;
		}

		if (preg_match("^@(" + STABILITIES + ")$", constraint) != null) { //$NON-NLS-1$ //$NON-NLS-2$
			return null;
		}

		if ((match = preg_match("^([^,\\s]+?)@(" + STABILITIES + ")$", //$NON-NLS-1$ //$NON-NLS-2$
				constraint)) != null) {
			constraint = match[1];
		}

		if (preg_match("^[x*](\\.[x*])*$", constraint) != null) { //$NON-NLS-1$
			return null;
		}

		if (preg_match("^~>?(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?(?:\\.(\\d+))?" //$NON-NLS-1$
				+ MODIFIER_REGEX + "?$", constraint) != null) { //$NON-NLS-1$
			if (constraint.substring(0, 2).equals("~>")) { //$NON-NLS-1$
				return "Invalid operator \"~>\", you probably meant to use the \"~\" operator"; //$NON-NLS-1$
			}

			return null;
		}

		// match wildcard constraints
		if (preg_match("^(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?\\.[x*]$", //$NON-NLS-1$
				constraint) != null) {
			return null;
		}

		// match operators constraints
		if ((match = preg_match("^(<>|!=|>=?|<=?|==?)?\\s*(.*)", constraint)) != null) { //$NON-NLS-1$
			String result = normalize(match[2]);
			if (result != null) {
				return result;
			}

			return null;
		}
		return "Invalid version constraint: " + constraint; //$NON-NLS-1$
	}

	private String normalize(String version) {
		version = version.trim();

		// ignore aliases and just assume the alias is required instead of the
		// source
		String[] matches = null;
		if ((matches = preg_match("^([^,\\s]+) +as +([^,\\s]+)$", version)) != null) { //$NON-NLS-1$
			version = matches[1];
		}

		// match master-like branches
		if (preg_match("^(?:dev-)?(?:master|trunk|default)$", version) != null) { //$NON-NLS-1$
			return null;// "9999999-dev";
		}

		if (version.length() >= 4 && "dev-".equals(version.toLowerCase().substring(0, 4))) { //$NON-NLS-1$
			return null;
		}

		// match classical versioning
		if ((matches = preg_match("^v?(\\d{1,3})(\\.\\d+)?(\\.\\d+)?(\\.\\d+)?" //$NON-NLS-1$
				+ MODIFIER_REGEX + "$", version)) != null) { //$NON-NLS-1$

			return null;
		} else if ((matches = preg_match("^v?(\\d{4}(?:[.:-]?\\d{2}){1,6}(?:[.:-]?\\d{1,3})?)" //$NON-NLS-1$
				+ MODIFIER_REGEX + "$", version)) != null) { // match //$NON-NLS-1$
																// date-based
																// versioning
			return null;
		}

		if (preg_match("(.*?)[.-]?dev$", version) != null) { //$NON-NLS-1$
			return null;
		}

		if (version.startsWith("^")) {
			return normalize(version.substring(1));
		}

		return "Invalid version string \"" + version + "\""; //$NON-NLS-1$ //$NON-NLS-2$
	}

	private String[] preg_match(String regex, String text) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		List<String> results = new ArrayList<String>();
		while (matcher.find()) {
			// Matcher always have groups count + 1 (all groups combined)
			for (int i = 0; i <= matcher.groupCount(); i++) {
				results.add(matcher.group(i));
			}
		}
		return results.isEmpty() ? null : results.toArray(new String[0]);
	}

}

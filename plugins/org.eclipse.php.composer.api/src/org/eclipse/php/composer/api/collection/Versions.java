/*******************************************************************************
 * Copyright (c) 2012, 2016 PDT Extension Group and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.api.collection;

import java.util.*;
import java.util.Map.Entry;

import org.eclipse.php.composer.api.ComposerConstants;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.entities.AbstractIterableJsonObject;
import org.eclipse.php.composer.api.entities.Version;

/**
 * Represents a version property in a composer package or version collection in
 * a composer repository or packagist package.
 * 
 * @see http://getcomposer.org/doc/04-schema.md#version
 * @see http://getcomposer.org/doc/05-repositories.md#packages
 * @author Thomas Gossmann <gos.si>
 *
 */
public class Versions extends AbstractIterableJsonObject<ComposerPackage> {

	private Map<String, Version> detailedVersions = null;
	private SortedMap<String, SortedSet<Integer>> majors = new TreeMap<>();

	public Versions() {
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doParse(Object obj) {
		clear();
		if (obj instanceof LinkedHashMap) {
			for (Entry<String, Object> entry : ((Map<String, Object>) obj).entrySet()) {
				ComposerPackage pkg = new ComposerPackage(entry.getValue());
				set(entry.getKey(), pkg);
			}
		}
	}

	/**
	 * Returns the most recent version based on version number and stability.
	 * 
	 * @return
	 */
	public String getDefaultVersion() {
		Set<String> sortedVersions = new TreeSet<>(properties.keySet()).descendingSet();

		for (String stability : ComposerConstants.STABILITIES) {
			for (String version : sortedVersions) {
				Version v = getDetailedVersion(version);
				if (v != null && Objects.equals(stability, v.getStability())) {
					return version;
				}
			}
		}

		// if no version matches any of the known stabilities, return the first
		// one
		return (sortedVersions.isEmpty()) ? null : sortedVersions.iterator().next();
	}

	public Set<String> toSet() {
		return properties.keySet();
	}

	public String[] toArray() {
		return properties.keySet().toArray(new String[0]);
	}

	private void compileDetailedVersions() {
		if (detailedVersions == null) {
			detailedVersions = new HashMap<>();

			for (String version : toArray()) {
				compileDetailedVersion(version);
			}
		}
	}

	private void compileDetailedVersion(String version) {
		Version v = new Version(version);
		detailedVersions.put(version, v);

		// hierarchy
		if (v.getStability() == ComposerConstants.STABLE) {
			String major = v.getMajor();
			if (major != null) {
				if (!majors.containsKey(major)) {
					majors.put(major, new TreeSet<Integer>(Comparator.reverseOrder()));
				}

				// reverse ordered
				SortedSet<Integer> majorList = majors.get(major);

				String minor = v.getMinor();
				if (minor != null) {
					try {
						Integer minorInt = Integer.parseInt(minor);
						// add if not present
						majorList.add(minorInt);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void prepareDetailedVersions() {
		if (detailedVersions == null) {
			compileDetailedVersions();
		}
	}

	public List<Version> getDetailedVersions() {
		prepareDetailedVersions();

		List<Version> all = new ArrayList<>();
		all.addAll(detailedVersions.values());

		return all;
	}

	public String[] getMajors() {
		prepareDetailedVersions();

		return majors.keySet().toArray(new String[] {});
	}

	public String getRecentMajor() {
		prepareDetailedVersions();

		if (majors.size() > 0) {
			return Collections.max(majors.keySet(), new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
				}
			});
		}

		return null;
	}

	/**
	 * Returns all minor versions for the given major version or null if major
	 * version does not exist.
	 * 
	 * @param major
	 * @return
	 */
	public String[] getMinors(String major) {
		prepareDetailedVersions();

		if (majors.containsKey(major)) {
			return majors.get(major).toArray(new String[] {});
		}

		return null;
	}

	/**
	 * Returns the recent minor version for the given major version or null if
	 * neither major version or no minor version exists.
	 * 
	 * @param major
	 * @return
	 */
	public String getRecentMinor(String major) {
		if (major == null) {
			return null;
		}
		prepareDetailedVersions();

		if (majors.containsKey(major) && majors.get(major).size() > 0) {
			return majors.get(major).toArray()[0].toString();
		}

		return null;
	}

	public void set(String version, ComposerPackage composerPackage) {
		if (detailedVersions != null) {
			compileDetailedVersion(version);
		}

		super.set(version, composerPackage);

		Collections.sort(sortOrder);
	}

	@Override
	public void remove(String version) {
		if (detailedVersions != null) {
			Version v = getDetailedVersion(version);
			detailedVersions.remove(version);

			// remove hierarchy
			if (v.getStability() == ComposerConstants.STABLE) {
				String major = v.getMajor();
				if (major != null) {
					if (majors.containsKey(major)) {
						// reverse ordered
						SortedSet<Integer> majorList = majors.get(major);

						String minor = v.getMinor();
						if (minor != null) {
							try {
								Integer minorInt = Integer.parseInt(minor);
								// remove if present
								majorList.remove(minorInt);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}

						if (majorList.size() == 0) {
							majors.remove(major);
						}
					}
				}
			}
		}

		super.remove(version);
	}

	/**
	 * Returns the detailed version for a given string version or null if the
	 * version doesn't exist in this version collection
	 * 
	 * @param version
	 * @return
	 */
	public Version getDetailedVersion(String version) {
		prepareDetailedVersions();

		if (detailedVersions.containsKey(version)) {
			return detailedVersions.get(version);
		}

		return null;
	}
}

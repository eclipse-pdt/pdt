/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.api;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.php.composer.api.annotation.Name;
import org.eclipse.php.composer.api.collection.*;
import org.eclipse.php.composer.api.json.ParseException;
import org.eclipse.php.composer.api.objects.Config;
import org.eclipse.php.composer.api.objects.Extra;
import org.eclipse.php.composer.api.objects.Support;

/**
 * Represents a composer package. The source can either be a composer.json file
 * or a json response from packagist.org.
 * 
 * See {@link #fromJson} / {@link #fromPackagist} for details.
 * 
 * @see http://getcomposer.org
 * @author Robert Gruendler <r.gruendler@gmail.com>
 * @author Thomas Gossmann <gos.si>
 * 
 */
public class ComposerPackage extends DistributedPackage {

	private Dependencies require = new Dependencies();
	@Name("require-dev")
	private Dependencies requireDev = new Dependencies();

	private Repositories repositories = new Repositories();

	private Support support = new Support();
	private License license = new License();

	private Persons authors = new Persons();

	private Extra extra = new Extra();
	private Config config = new Config();

	private Scripts scripts = new Scripts();

	public ComposerPackage() {
		super();
		listen();
	}

	public ComposerPackage(Object json) {
		this();
		fromJson(json);
	}

	public ComposerPackage(String json) throws ParseException {
		this();
		fromJson(json);
	}

	public ComposerPackage(File file) throws IOException, ParseException {
		this();
		fromJson(file);
	}

	public ComposerPackage(Reader reader) throws IOException, ParseException {
		this();
		fromJson(reader);
	}

	@Override
	protected List<String> getOwnProperties() {
		String[] props = new String[] { "keywords", "bin" }; //$NON-NLS-1$ //$NON-NLS-2$
		List<String> list = new ArrayList<String>(Arrays.asList(props));
		list.addAll(super.getOwnProperties());
		return list;
	}

	/**
	 * Returns the homepage
	 * 
	 * @return the homepage
	 */
	public String getHomepage() {
		return getAsString("homepage"); //$NON-NLS-1$
	}

	/**
	 * Sets the homepage
	 * 
	 * @param homepage
	 *            the homepage to set
	 */
	public void setHomepage(String homepage) {
		set("homepage", homepage); //$NON-NLS-1$
	}

	/**
	 * Returns the require dependencies
	 * 
	 * @return
	 */
	public Dependencies getRequire() {
		return require;
	}

	/**
	 * Returns the require-dev dependencies
	 * 
	 * @return
	 */
	public Dependencies getRequireDev() {
		return requireDev;
	}

	/**
	 * Returns the target-dir
	 * 
	 * @return the target-dir
	 */
	public String getTargetDir() {
		return getAsString("target-dir"); //$NON-NLS-1$
	}

	/**
	 * Sets the target-dir
	 * 
	 * @param targetDir
	 *            the target-dir to set
	 */
	public void setTargetDir(String targetDir) {
		set("target-dir", targetDir); //$NON-NLS-1$
	}

	/**
	 * Returns the normalized version
	 * 
	 * @return the normalized version
	 */
	public String getVersionNormalized() {
		return getAsString("version_normalized"); //$NON-NLS-1$
	}

	/**
	 * Returns the license section
	 * 
	 * @return the license section
	 */
	public License getLicense() {
		return license;
	}

	/**
	 * Returns the keywords
	 *
	 * @return the keywords
	 */
	public JsonArray getKeywords() {
		return getAsArray("keywords"); //$NON-NLS-1$
	}

	/**
	 * Returns the minimum-stability property
	 * 
	 * @return the minimum-stability
	 */
	public String getMinimumStability() {
		String stabi = getAsString("minimum-stability"); //$NON-NLS-1$
		if (stabi == null) {
			return ComposerConstants.STABILITIES[0];
		} else {
			return stabi;
		}
	}

	/**
	 * Sets the minimum-stability property
	 * 
	 * @param minimumStability
	 *            the minimum-stability to set
	 */
	public void setMinimumStability(String minimumStability) {
		set("minimum-stability", minimumStability); //$NON-NLS-1$
	}

	/**
	 * Returns the authors
	 * 
	 * @return the authors
	 */
	public Persons getAuthors() {
		return authors;
	}

	/**
	 * Returns the support section
	 * 
	 * @return the support section
	 */
	public Support getSupport() {
		return support;
	}

	/**
	 * @return the repositories
	 */
	public Repositories getRepositories() {
		return repositories;
	}

	/**
	 * Returns the extra entity
	 * 
	 * @return the extra
	 */
	public Extra getExtra() {
		return extra;
	}

	/**
	 * Returns the config entity
	 * 
	 * @return the config
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * Returns the scripts entity
	 * 
	 * @return the scripts
	 */
	public Scripts getScripts() {
		return scripts;
	}

	/**
	 * Returns the <code>bin</code> collection.
	 * 
	 * @return the <code>bin</code> collection
	 */
	public JsonArray getBin() {
		return getAsArray("bin"); //$NON-NLS-1$
	}

	public String toString() {
		return getName();
	}
}

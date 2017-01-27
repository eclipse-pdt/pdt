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

import org.eclipse.php.composer.api.collection.Persons;
import org.eclipse.php.composer.api.collection.Versions;
import org.eclipse.php.composer.api.json.ParseException;
import org.eclipse.php.composer.api.packages.PackagistDownloader;
import org.eclipse.php.composer.api.repositories.PackageRepository;

public class RepositoryPackage extends DistributedPackage {

	private Versions versions = new Versions();
	private Persons maintainers = new Persons();

	public RepositoryPackage() {
		super();
		listen();
	}

	public RepositoryPackage(Object json) {
		this();
		fromJson(json);
	}

	public RepositoryPackage(String json) throws ParseException {
		this();
		fromJson(json);
	}

	public RepositoryPackage(File file) throws IOException, ParseException {
		this();
		fromJson(file);
	}

	public RepositoryPackage(Reader reader) throws IOException, ParseException {
		this();
		fromJson(reader);
	}

	/**
	 * Deserializes packages from packagist.org, e.g.
	 * http://packagist.org/packages/react/react.json
	 * 
	 * @param input
	 * @return the deserialized package
	 * @throws IOException
	 * @throws ParseException
	 */
	public static RepositoryPackage fromPackageRepository(File input) throws IOException, ParseException {
		PackageRepository repo = new PackageRepository(input);
		return repo.getPackage();
	}

	/**
	 * Deserializes packages from packagist.org, e.g.
	 * http://packagist.org/packages/react/react.json
	 * 
	 * @param input
	 * @return the deserialized package
	 * @throws IOException
	 * @throws ParseException
	 */
	public static RepositoryPackage fromPackageRepository(Reader input) throws IOException, ParseException {
		PackageRepository repo = new PackageRepository(input);
		return repo.getPackage();
	}

	/**
	 * Deserializes packages from packagist.org, e.g.
	 * http://packagist.org/packages/Symfony/Router.json
	 * 
	 * @param name
	 *            the package name, such as Symfony/Router
	 * @return the deserialized package
	 * @throws IOException
	 */
	public static RepositoryPackage fromPackagist(String name) throws Exception {

		PackagistDownloader downloader = new PackagistDownloader();
		return downloader.loadPackage(name);
	}

	/**
	 * Returns the versions
	 * 
	 * @return the versions
	 */
	public Versions getVersions() {
		return versions;
	}

	/**
	 *
	 * Returns the package name suitable for passing it to "composer.phar
	 * require"
	 *
	 * @param version
	 * @return String the package/version combination
	 * @throws Exception
	 */
	public String getPackageName(String version) throws Exception {
		if (!this.versions.has(version)) {
			throw new Exception("Invalid version " + version + " for package " + getName()); //$NON-NLS-1$ //$NON-NLS-2$
		}

		return String.format("%s:%s", getName(), version); //$NON-NLS-1$
	}

	/**
	 * Returns the maintainers
	 * 
	 * @return the maintainers
	 */
	public Persons getMaintainers() {
		return maintainers;
	}

	/**
	 * Returns the repository url
	 * 
	 * @return the repository
	 */
	public String getRepository() {
		return getAsString("repository"); //$NON-NLS-1$
	}

	/**
	 * @param repository
	 *            the repository to set
	 */
	public void setRepository(String repository) {
		set("repository", repository); //$NON-NLS-1$
	}
}

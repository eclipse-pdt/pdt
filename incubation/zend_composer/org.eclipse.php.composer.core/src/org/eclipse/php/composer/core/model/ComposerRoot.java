/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.core.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.core.internal.model.Packages;
import org.eclipse.php.composer.core.internal.model.Property;
import org.eclipse.php.composer.core.internal.model.Repositories;
import org.eclipse.php.composer.core.internal.model.adapters.ComposerJacksonMapper;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class ComposerRoot {

	private IProperty name;

	private IProperty description;

	private IProperty version;

	private IPackages require;

	private IPackages requireDev;

	private IPackages conflict;

	private IPackages replace;

	private IPackages provide;

	private IRepositories repositories;

	protected Map<String, Object> additionalAttributes = new HashMap<String, Object>();

	private transient boolean dirty;

	private transient IFile file;

	public ComposerRoot() {
		this.name = new Property();
		this.description = new Property();
		this.version = new Property();
		this.require = new Packages();
		this.requireDev = new Packages();
		this.conflict = new Packages();
		this.replace = new Packages();
		this.provide = new Packages();
		this.repositories = new Repositories();
	}

	public IProperty getName() {
		return ignoreNullProperty(name);
	}

	public IProperty getDescription() {
		return ignoreNullProperty(description);
	}

	public IProperty getVersion() {
		return ignoreNullProperty(version);
	}

	public IPackages getRequire() {
		return ignoreNullPackages(require);
	}

	public IPackages getConflict() {
		return ignoreNullPackages(conflict);
	}

	public IPackages getProvide() {
		return ignoreNullPackages(provide);
	}

	public IPackages getReplace() {
		return ignoreNullPackages(replace);
	}

	public IRepositories getRepositories() {
		return repositories.getRepositories().size() > 0 ? repositories : null;
	}

	@JsonProperty("require-dev")
	public IPackages getRequireDev() {
		return ignoreNullPackages(requireDev);
	}

	@JsonAnyGetter
	public Map<String, Object> any() {
		return additionalAttributes;
	}

	@JsonAnySetter
	public void set(String name, Object value) {
		additionalAttributes.put(name, value);
	}

	@JsonIgnore
	public IProperty getNameProperty() {
		return name;
	}

	@JsonIgnore
	public IProperty getVersionProperty() {
		return version;
	}

	@JsonIgnore
	public IProperty getDescriptionProperty() {
		return description;
	}

	@JsonIgnore
	public IPackages getRequireProperty() {
		return require;
	}

	@JsonIgnore
	public IPackages getRequireDevProperty() {
		return requireDev;
	}

	@JsonIgnore
	public IPackages getConflictProperty() {
		return conflict;
	}

	@JsonIgnore
	public IPackages getReplaceProperty() {
		return replace;
	}

	@JsonIgnore
	public IPackages getProvideProperty() {
		return provide;
	}

	@JsonIgnore
	public IRepositories getRepositoriesProperty() {
		return repositories;
	}

	@JsonIgnore
	public IFile getFile() {
		return file;
	}

	@JsonIgnore
	public boolean isDirty() {
		return dirty;
	}

	@JsonIgnore
	public void setDirty(boolean value) {
		this.dirty = value;
	}

	protected void setName(IProperty name) {
		this.name.setValue(name.getValue(), false);
	}

	protected void setDescription(IProperty description) {
		this.description.setValue(description.getValue(), false);
	}

	protected void setVersion(IProperty version) {
		this.version.setValue(version.getValue(), false);
	}

	protected void setRequire(IPackages require) {
		this.require.setPackages(require.getPackages());
	}

	protected void setRequireDev(IPackages requireDev) {
		this.requireDev.setPackages(requireDev.getPackages());
	}

	protected void setConflict(IPackages conflict) {
		this.conflict.setPackages(conflict.getPackages());
	}

	protected void setReplace(IPackages replace) {
		this.replace.setPackages(replace.getPackages());
	}

	protected void setProvide(IPackages provide) {
		this.provide.setPackages(provide.getPackages());
	}

	protected void setRepositories(IRepositories repositories) {
		this.repositories.setRepositories(repositories.getRepositories());
	}

	protected void setFile(IFile file) {
		this.file = file;
	}

	public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
		this.additionalAttributes = additionalAttributes;
	}

	private IProperty ignoreNullProperty(IProperty p) {
		return p.getValue() != null ? p : null;
	}

	private IPackages ignoreNullPackages(IPackages p) {
		return p.getPackages().size() > 0 ? p : null;
	}

	/**
	 * Returns the model of the root <code>composer.json</code> file for the given project.
	 * 
	 * @param project
	 *            a project
	 *            
	 * @return a reference to a <code>ComposerRoot</code> object, or
	 *         <code>null</code> if there is no root composer.json file
	 *         
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static ComposerRoot parse(IProject project)
			throws JsonParseException, JsonMappingException, IOException {
		IFile file = project.getFile(ComposerService.COMPOSER_JSON);

		if (file.exists()) {
			return parse(file);
		}

		return null;
	}

	/**
	 * Returns the model of the given <code>composer.json</code> file.
	 * 
	 * @param composerJson
	 *            an <code>IFile</code> reference to the composer.json file
	 * 
	 * @return a reference to a <code>ComposerRoot</code> object
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static ComposerRoot parse(IFile composerJson)
			throws JsonParseException, JsonMappingException, IOException {
		return parse(composerJson.getRawLocation().makeAbsolute().toFile());
	}

	/**
	 * Returns the model of the given <code>composer.json</code> file.
	 * 
	 * @param composerJson
	 *            a <code>File</code> reference to the composer.json file
	 * 
	 * @return a reference to a <code>ComposerRoot</code> object
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static ComposerRoot parse(File composerJson)
			throws JsonParseException, JsonMappingException, IOException {
		return ComposerJacksonMapper.getMapper().readValue(composerJson,
				ComposerRoot.class);
	}

}

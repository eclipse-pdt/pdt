/*******************************************************************************
 * Copyright (c) 2009 Zhao and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zhao - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phar;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class PharPackage {

	private IPath stubLocation = new Path(""); // internal location //$NON-NLS-1$

	private String stubVersion;
	private String alias;

	/*
	 * What to export - internal locations The list fExported* is null if
	 * fExport* is false)
	 */

	private int exportType;
	private int compressType;

	/*
	 * Leaf elements (no containers) to export
	 */
	private Object[] fElements; // inside workspace

	private IPath fPharLocation; // external location
	private boolean fOverwrite;

	private String signature; // the JAR comment

	// Add directory entries to the jar
	private boolean fIncludeDirectoryEntries;
	private boolean stubGenerated = true;

	public boolean isUseSignature() {
		return true;
	}

	public void setStubGenerated(boolean stubGenerated) {
		this.stubGenerated = stubGenerated;
	}

	public IPath getStubLocation() {
		return stubLocation;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setStubLocation(IPath stubLocation) {
		this.stubLocation = stubLocation;
	}

	public void setPharLocation(IPath path) {
		this.fPharLocation = path;
	}

	public IPath getPharLocation() {
		return fPharLocation;
	}

	public IPath getAbsolutePharLocation() {
		if (!fPharLocation.isAbsolute()) {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			if (fPharLocation.segmentCount() >= 2) {
				// reverse of
				// AbstractJarDestinationWizardPage#handleDestinationBrowseButtonPressed()
				IFile file = root.getFile(fPharLocation);
				IPath absolutePath = file.getLocation();
				if (absolutePath != null) {
					return absolutePath;
				}
			}
			// The path does not exist in the workspace (e.g. because there's no
			// such project).
			// Fallback is to just append the path to the workspace root.
			return root.getLocation().append(fPharLocation);
		}
		return fPharLocation;
	}

	public boolean isStubGenerated() {
		return stubGenerated;
	}

	public int getExportType() {
		return exportType;
	}

	public void setExportType(int exportType) {
		this.exportType = exportType;
	}

	public int getCompressType() {
		return compressType;
	}

	public void setCompressType(int compressType) {
		this.compressType = compressType;
	}

	/**
	 * Gets the Stub file (as workspace resource).
	 * 
	 * @return a file which points to the Stub
	 */
	public IFile getStubFile() {
		IPath path = getStubLocation();
		if (path != null && path.isValidPath(path.toString())
				&& path.segmentCount() >= 2)
			return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		else
			return null;
	}

	/**
	 * Creates a new Jar Package Data structure
	 */
	public PharPackage() {
		setPharLocation(Path.EMPTY);
		setIncludeDirectoryEntries(false);
	}

	// ----------- Accessors -----------

	/**
	 * Tells whether files can be overwritten without warning.
	 * 
	 * @return <code>true</code> if files can be overwritten without warning
	 */
	public boolean allowOverwrite() {
		return fOverwrite;
	}

	/**
	 * Sets whether files can be overwritten without warning.
	 * 
	 * @param state
	 *            a boolean indicating the new state
	 */
	public void setOverwrite(boolean state) {
		fOverwrite = state;
	}

	public void setStubVersion(String stubVersion) {
		this.stubVersion = stubVersion;
	}

	/**
	 * Gets the manifest version.
	 * 
	 * @return a string containing the manifest version
	 */
	public String getStubVersion() {
		if (stubVersion == null)
			return "1.1.0"; //$NON-NLS-1$
		return stubVersion;
	}

	/**
	 * Returns the elements which will be exported. These elements are leaf
	 * objects e.g. <code>IFile</code> and not containers.
	 * 
	 * @return an array of leaf objects
	 */
	public Object[] getElements() {
		if (fElements == null)
			setElements(new Object[0]);
		return fElements;
	}

	/**
	 * Set the elements which will be exported.
	 * 
	 * These elements are leaf objects e.g. <code>IFile</code>. and not
	 * containers.
	 * 
	 * @param elements
	 *            an array with leaf objects
	 */
	public void setElements(Object[] elements) {
		fElements = elements;
	}

	// ----------- Utility methods -----------

	/**
	 * Finds the class files for the given java file and returns them.
	 * <p>
	 * This is a hook for subclasses which want to implement a different
	 * strategy for finding the class files. The default strategy is to query
	 * the class files for the source file name attribute. If this attribute is
	 * missing then all class files in the corresponding output folder are
	 * exported.
	 * </p>
	 * <p>
	 * A CoreException can be thrown if an error occurs during this operation.
	 * The <code>CoreException</code> will not stop the export process but adds
	 * the status object to the status of the export runnable.
	 * </p>
	 * 
	 * @param javaFile
	 *            a .java file
	 * @return an array with class files or <code>null</code> to used the
	 *         default strategy
	 * @throws CoreException
	 *             if find failed, e.g. I/O error or resource out of sync
	 * @see IJarExportRunnable#getStatus()
	 */
	public IFile[] findClassfilesFor(IFile javaFile) throws CoreException {
		return null;
	}

	/**
	 * Tells whether this JAR package data can be used to generate a valid JAR.
	 * 
	 * @return <code>true</code> if the JAR Package info is valid
	 */
	public boolean isValid() {
		return getElements() != null && getElements().length > 0
				&& getAbsolutePharLocation() != null && isStubAccessible();
	}

	/**
	 * Tells whether a manifest is available.
	 * 
	 * @return <code>true</code> if the manifest is generated or the provided
	 *         one is accessible
	 */
	public boolean isStubAccessible() {
		if (isStubGenerated())
			return true;
		IFile file = getStubFile();
		return file != null && file.isAccessible();
	}

	/**
	 * Tells whether directory entries are added to the jar.
	 * 
	 * @return <code>true</code> if directory entries are to be included
	 * 
	 * @since 3.1
	 */
	public boolean areDirectoryEntriesIncluded() {
		return fIncludeDirectoryEntries;
	}

	/**
	 * Sets the option to include directory entries into the jar.
	 * 
	 * @param includeDirectoryEntries
	 *            <code>true</code> to include directory entries
	 *            <code>false</code> otherwise
	 * 
	 * @since 3.1
	 */
	public void setIncludeDirectoryEntries(boolean includeDirectoryEntries) {
		fIncludeDirectoryEntries = includeDirectoryEntries;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignature() {
		return signature;
	}

}

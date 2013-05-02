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
package org.eclipse.php.internal.core.includepath;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;

/**
 * Include path entry
 */
public class IncludePath implements IAdaptable {

	protected boolean isBuildpath;
	private Object entry;
	private IProject fProject;

	public IncludePath(Object entry, IProject project) {
		isBuildpath = (entry instanceof IBuildpathEntry);
		this.entry = entry;
		this.fProject = project;
	}

	public IncludePath(Object entry, IScriptProject scriptProject) {
		this(entry, scriptProject.getProject());
	}

	/**
	 * @return Include path entry. It's either {@link IBuildpathEntry} or
	 *         {@link IResource} depending on kind
	 */
	public Object getEntry() {
		return entry;
	}

	/**
	 * Returns whether this include path entry represents
	 * {@link IBuildpathEntry}
	 */
	public boolean isBuildpath() {
		return isBuildpath;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entry == null) ? 0 : entry.hashCode());
		result = prime * result + (isBuildpath ? 1231 : 1237);
		return result;
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
		IncludePath other = (IncludePath) obj;
		if (entry == null) {
			if (other.entry != null) {
				return false;
			}
		} else if (!entry.equals(other.entry)) {
			return false;
		}
		if (isBuildpath != other.isBuildpath) {
			return false;
		}
		return true;
	}

	public String toString() {
		return new StringBuilder("Include Path [").append(entry.toString()) //$NON-NLS-1$
				.append(']').toString();
	}

	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the scriptProject
	 */
	public IProject getProject() {
		return fProject;
	}

}

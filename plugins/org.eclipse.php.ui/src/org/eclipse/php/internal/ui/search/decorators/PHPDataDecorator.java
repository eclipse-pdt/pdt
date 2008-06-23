/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.search.decorators;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.core.util.Visitor;

/**
 * PHP code data decorator that added a check for leaf state functionallity and can also
 * hold the project that contains it.
 *
 * This class is not intended to be instantiate.
 *
 * @author shalom
 */
public abstract class PHPDataDecorator implements IPHPDataLeafMarker {

	private PHPCodeData source;
	private IProject project;
	private boolean isLeaf;

	/**
	 * Constructs a new PHPDataDecorator.
	 *
	 * @param source
	 * @param project
	 * @param isLeaf
	 */
	public PHPDataDecorator(PHPCodeData source, IProject project, boolean isLeaf) {
		this.source = source;
		this.project = project;
		this.isLeaf = isLeaf;
	}

	/**
	 * Constructs a new PHPDataDecorator.
	 * The isLeaf is set to true.
	 *
	 * @param source
	 * @param project
	 */
	public PHPDataDecorator(PHPCodeData source, IProject project) {
		this(source, project,true);
	}

	public void setContainer(PHPCodeData container) {
		source.setContainer(container);
	}

	/**
	 * Returns the container wrapped with it's appropriate decorator.
	 * The returned wrapped container is never marked as leaf.
	 */
	public PHPCodeData getContainer() {
		PHPCodeData sourceContainer = source.getContainer();
		if (sourceContainer instanceof PHPClassData) {
			return new PHPClassDataDecorator((PHPClassData)sourceContainer, project, false);
		} else if (sourceContainer instanceof PHPFileData) {
			return new PHPFileDataDecorator((PHPFileData)sourceContainer, project, false);
		}
		return sourceContainer;
	}

	public PHPDocBlock getDocBlock() {
		return source.getDocBlock();
	}

	public String getName() {
		return source.getName();
	}

	public String getDescription() {
		return source.getDescription();
	}

	public boolean isUserCode() {
		return source.isUserCode();
	}

	public UserData getUserData() {
		return source.getUserData();
	}

	public void accept(Visitor v) {
		source.accept(v);
	}

	public int compareTo(CodeData arg0) {
		return source.compareTo(arg0);
	}

	public Object getAdapter(Class adapter) {
		return source.getAdapter(adapter);
	}

	public PHPCodeData getSource() {
		return source;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public IProject getProject() {
		return project;
	}

	public boolean equals(Object other) {
		if (other instanceof PHPDataDecorator) {
			return this.hashCode() == ((PHPDataDecorator)other).hashCode();
		}
		return false;
	}

	public int hashCode() {
		UserData userData = getUserData();
		return (userData.getStartPosition() + userData.getFileName()).hashCode() + getClass().hashCode();
	}
}

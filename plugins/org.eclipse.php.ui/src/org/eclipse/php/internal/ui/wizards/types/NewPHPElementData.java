/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.wizards.types;

import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IType;
import org.eclipse.php.core.PHPVersion;

/**
 * This class represents a Data Object that contains all the necessary data from
 * the UI for generating a new PHP element
 * 
 */
public class NewPHPElementData {
	public boolean isExistingFile = false;
	public String className = ""; //$NON-NLS-1$
	public boolean isFinal = false;;
	public boolean isAbstract = false;
	public IType superClass = null;
	public IType[] interfaces = new IType[0];
	public IType[] traits = new IType[0];
	public IMethod[] funcsToAdd = new IMethod[0];
	public String[] requiredToAdd = new String[0];
	public boolean isGenerateConstructor = false;
	public boolean isGenerateDestructor = false;
	public boolean isGeneratePHPDoc = false;
	public boolean isGenerateTODOs = false;
	public boolean isInFirstBlock = false;
	public boolean hasFirstBlock = false;
	public PHPVersion phpVersion = PHPVersion.getLatestVersion();
	public String namespace = ""; //$NON-NLS-1$
	public String realNamespace = ""; //$NON-NLS-1$
	public String[] imports = new String[0];
	public String[] existingImports = new String[0];
}

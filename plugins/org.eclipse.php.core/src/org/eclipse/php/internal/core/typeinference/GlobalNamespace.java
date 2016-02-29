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
package org.eclipse.php.internal.core.typeinference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.model.PhpModelAccess;

/**
 * Fake model element representing global namespace type
 * 
 * @author michael
 */
public class GlobalNamespace extends SourceType {

	public static final String NAME = "<global>"; //$NON-NLS-1$

	public GlobalNamespace(IModelElement project) {
		super((ModelElement) project, NAME);
	}

	public IField[] getFields() throws ModelException {
		IDLTKSearchScope scope = SearchEngine.createSearchScope(getParent(), IDLTKSearchScope.SOURCES);
		return PhpModelAccess.getDefault().findFileFields(PHPCoreConstants.GLOBAL_NAMESPACE, null, MatchRule.PREFIX, 0,
				0, scope, null);
	}

	public IMethod[] getMethods() throws ModelException {
		IDLTKSearchScope scope = SearchEngine.createSearchScope(getParent(), IDLTKSearchScope.SOURCES);
		return PhpModelAccess.getDefault().findFunctions(PHPCoreConstants.GLOBAL_NAMESPACE, null, MatchRule.PREFIX, 0,
				0, scope, null);
	}

	public IType[] getTypes() throws ModelException {
		IDLTKSearchScope scope = SearchEngine.createSearchScope(getParent(), IDLTKSearchScope.SOURCES);
		List<IType> types = new ArrayList<IType>();
		types.addAll(Arrays.asList(PhpModelAccess.getDefault().findTypes(PHPCoreConstants.GLOBAL_NAMESPACE, null,
				MatchRule.PREFIX, 0, 0, scope, null)));

		types.addAll(Arrays.asList(PhpModelAccess.getDefault().findTraits(PHPCoreConstants.GLOBAL_NAMESPACE, null,
				MatchRule.PREFIX, 0, 0, scope, null)));
		return types.toArray(new IType[types.size()]);
	}

	public IModelElement[] getChildren(IProgressMonitor monitor) throws ModelException {
		List<IModelElement> children = new LinkedList<IModelElement>();
		children.addAll(Arrays.asList(getFields()));
		children.addAll(Arrays.asList(getMethods()));
		children.addAll(Arrays.asList(getTypes()));
		return children.toArray(new IModelElement[children.size()]);
	}

	public int getFlags() throws ModelException {
		return Modifiers.AccNameSpace;
	}

	public boolean exists() {
		return true;
	}
}
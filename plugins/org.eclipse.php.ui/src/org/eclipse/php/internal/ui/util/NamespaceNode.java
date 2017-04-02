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
package org.eclipse.php.internal.ui.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.php.internal.core.model.PhpModelAccess;

public class NamespaceNode extends SourceType {
	public NamespaceNode(IModelElement modelElement, String name) {
		super((ModelElement) modelElement, name);
	}

	@Override
	public IModelElement[] getChildren(IProgressMonitor monitor) throws ModelException {
		List<IModelElement> children = new LinkedList<IModelElement>();
		IDLTKSearchScope scope = SearchEngine.createSearchScope(getParent(), IDLTKSearchScope.SOURCES);
		PhpModelAccess modelAccess = PhpModelAccess.getDefault();
		children.addAll(Arrays.asList(modelAccess.findTypes(name, null, MatchRule.PREFIX, 0, 0, scope, monitor)));
		children.addAll(Arrays.asList(modelAccess.findTraits(name, null, MatchRule.PREFIX, 0, 0, scope, monitor)));
		children.addAll(Arrays.asList(modelAccess.findFunctions(name, null, MatchRule.PREFIX, 0, 0, scope, monitor)));
		children.addAll(Arrays.asList(modelAccess.findFileFields(name, null, MatchRule.PREFIX, 0, 0, scope, monitor)));
		return children.toArray(new IModelElement[children.size()]);
	}

	@Override
	public int getFlags() throws ModelException {
		return Modifiers.AccNameSpace;
	}

	@Override
	public boolean exists() {
		return true;
	}
}
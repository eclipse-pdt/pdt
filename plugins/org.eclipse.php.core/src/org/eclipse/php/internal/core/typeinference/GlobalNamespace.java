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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceType;
import org.eclipse.php.internal.core.PHPLanguageToolkit;

/**
 * Fake model element representing global namespace type
 * @author michael
 */
public class GlobalNamespace extends SourceType {

	public static final String NAME = "<global>";

	public GlobalNamespace(IScriptProject project) {
		super((ModelElement) project, NAME);
	}

	protected <T extends IModelElement> void searchGlobalElements(final Collection<T> result, int searchFor) throws ModelException {
		SearchEngine engine = new SearchEngine();
		IDLTKSearchScope scope = SearchEngine.createSearchScope(getParent(), IDLTKSearchScope.SOURCES);
		SearchParticipant[] participants = new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() };
		SearchPattern pattern = SearchPattern.createPattern("*", searchFor, IDLTKSearchConstants.DECLARATIONS, SearchPattern.R_PATTERN_MATCH, PHPLanguageToolkit.getDefault());
		try {
			engine.search(pattern, participants, scope, new SearchRequestor() {
				@SuppressWarnings("unchecked")
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					T element = (T) match.getElement();
					if (PHPModelUtils.getCurrentNamespace(element) != null) {
						return;
					}
					IModelElement parent = element.getParent();
					if (parent instanceof IType) {
						return; // The element is a class/interface member
					}
					if (parent instanceof IMethod && element instanceof IField) {
						return; // The element is a local function variable
					}
					result.add(element);
				}
			}, new NullProgressMonitor());
		} catch (CoreException e) {
			throw new ModelException(e);
		}
	}

	public IField[] getFields() throws ModelException {
		Collection<IField> result = new LinkedList<IField>();
		searchGlobalElements(result, IDLTKSearchConstants.FIELD);
		return result.toArray(new IField[result.size()]);
	}

	public IMethod[] getMethods() throws ModelException {
		Collection<IMethod> result = new LinkedList<IMethod>();
		searchGlobalElements(result, IDLTKSearchConstants.METHOD);
		return result.toArray(new IMethod[result.size()]);
	}

	public IType[] getTypes() throws ModelException {
		Collection<IType> result = new LinkedList<IType>();
		searchGlobalElements(result, IDLTKSearchConstants.TYPE);
		return result.toArray(new IType[result.size()]);
	}

	public IModelElement[] getChildren() throws ModelException {
		List<IModelElement> result = new LinkedList<IModelElement>();
		searchGlobalElements(result, IDLTKSearchConstants.FIELD);
		searchGlobalElements(result, IDLTKSearchConstants.METHOD);
		searchGlobalElements(result, IDLTKSearchConstants.TYPE);
		return result.toArray(new IModelElement[result.size()]);
	}

	public int getFlags() throws ModelException {
		return Modifiers.AccNameSpace;
	}

	public boolean exists() {
		return true;
	}
}
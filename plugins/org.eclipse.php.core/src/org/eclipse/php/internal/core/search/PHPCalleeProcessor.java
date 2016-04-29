/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.compiler.SourceElementRequestorAdaptor;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.*;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.project.PHPNature;

public class PHPCalleeProcessor implements ICalleeProcessor {

	private IMethod method;
	private IDLTKSearchScope scope;

	private Map<Object, Object> fSearchResults = new HashMap<Object, Object>();

	private class RequestorAdaptor extends SourceElementRequestorAdaptor {
		@Override
		public void acceptMethodReference(String methodName, int argCount, int sourcePosition, int sourceEndPosition) {
			try {
				if (sourcePosition < method.getSourceRange().getOffset()
						|| sourceEndPosition > method.getSourceRange().getLength()
								+ method.getSourceRange().getOffset()) {
					return;
				}
			} catch (ModelException e1) {
				Logger.logException(e1);
			}

			SimpleReference ref = new SimpleReference(sourcePosition, sourceEndPosition, methodName);
			IMethod[] methods = findMethods(methodName, argCount, sourcePosition);
			fSearchResults.put(ref, methods);
		}

	}

	public PHPCalleeProcessor(IMethod method, IProgressMonitor monitor, IDLTKSearchScope scope) {
		this.method = method;
		this.scope = scope;
	}

	@Override
	public Map doOperation() {
		try {
			if (method.getSource() != null) {
				RequestorAdaptor requestor = new RequestorAdaptor();
				ISourceElementParser parser = DLTKLanguageManager.getSourceElementParser(PHPNature.ID);
				parser.setRequestor(requestor);
				parser.parseSourceModule((IModuleSource) method.getAncestor(IModelElement.SOURCE_MODULE));
			} else {
			}
			return fSearchResults;
		} catch (ModelException e) {
			Logger.logException(e);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		return fSearchResults;
	}

	public IMethod[] findMethods(final String methodName, int argCount, int sourcePosition) {
		final List<IMethod> methods = new ArrayList<IMethod>();
		ISourceModule module = this.method.getSourceModule();
		try {
			IModelElement[] elements = module.codeSelect(sourcePosition, methodName.length());
			for (int i = 0; i < elements.length; ++i) {
				if (elements[i] instanceof IMethod) {
					methods.add((IMethod) elements[i]);
				}
			}
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return methods.toArray(new IMethod[methods.size()]);
	}

	protected void search(String patternString, int searchFor, int limitTo, IDLTKSearchScope scope,
			SearchRequestor resultCollector) throws CoreException {
		search(patternString, searchFor, limitTo, SearchPattern.R_EXACT_MATCH, scope, resultCollector);
	}

	protected void search(String patternString, int searchFor, int limitTo, int matchRule, IDLTKSearchScope scope,
			SearchRequestor requestor) throws CoreException {
		if (patternString.indexOf('*') != -1 || patternString.indexOf('?') != -1) {
			matchRule |= SearchPattern.R_PATTERN_MATCH;
		}
		SearchPattern pattern = SearchPattern.createPattern(patternString, searchFor, limitTo, matchRule,
				scope.getLanguageToolkit());
		SearchEngine searchEngine = new SearchEngine();
		searchEngine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope,
				requestor, null);
	}

}

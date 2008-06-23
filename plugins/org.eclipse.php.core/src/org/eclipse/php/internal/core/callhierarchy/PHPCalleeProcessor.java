/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.callhierarchy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.ICalleeProcessor;
import org.eclipse.dltk.core.IMethod;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceElementParser;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchParticipant;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.SearchRequestor;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.wst.sse.core.internal.Logger;

public class PHPCalleeProcessor implements ICalleeProcessor {

	private static final String PHP_START = "<?php "; //$NON-NLS-1$

	protected static int EXACT_RULE = SearchPattern.R_EXACT_MATCH | SearchPattern.R_CASE_SENSITIVE;

	private Map<SimpleReference, IMethod[]> fSearchResults = new HashMap<SimpleReference, IMethod[]>();
	private IMethod method;

	public PHPCalleeProcessor(IMethod method, IProgressMonitor monitor, IDLTKSearchScope scope) {
		this.method = method;
	}

	private class CaleeSourceElementRequestor implements ISourceElementRequestor {
		
		public void acceptFieldReference(char[] fieldName, int sourcePosition) {
		}

		public void acceptMethodReference(char[] methodName, int argCount, int sourcePosition, int sourceEndPosition) {
			String name = new String(methodName);
			int off = 0;
			try {
				off = method.getSourceRange().getOffset() - PHP_START.length();
			} catch (ModelException e) {
				Logger.logException(e);
			}
			SimpleReference ref = new SimpleReference(off + sourcePosition, off + sourceEndPosition, name);
			IMethod[] methods = findMethods(name, argCount, off + sourceEndPosition - 1);
			fSearchResults.put(ref, methods);
		}

		public void acceptPackage(int declarationStart, int declarationEnd, char[] name) {
		}

		public void acceptTypeReference(char[][] typeName, int sourceStart, int sourceEnd) {
		}

		public void acceptTypeReference(char[] typeName, int sourcePosition) {
		}

		public void enterField(FieldInfo info) {
		}

		public boolean enterFieldCheckDuplicates(FieldInfo info) {
			return false;
		}

		public void enterMethod(MethodInfo info) {
		}

		public void enterMethodRemoveSame(MethodInfo info) {
		}

		public boolean enterMethodWithParentType(MethodInfo info, String parentName, String delimiter) {
			return false;
		}

		public boolean enterFieldWithParentType(FieldInfo info, String parentName, String delimiter) {
			return false;
		}

		public void enterModule() {
		}

		public void enterType(TypeInfo info) {
		}

		public boolean enterTypeAppend(TypeInfo info, String fullName, String delimiter) {
			return false;
		}

		public void exitField(int declarationEnd) {
		}

		public void exitMethod(int declarationEnd) {
		}

		public void exitModule(int declarationEnd) {
		}

		public void exitType(int declarationEnd) {
		}

		public void enterModuleRoot() {
		}

		public boolean enterTypeAppend(String fullName, String delimiter) {
			return false;
		}

		public void exitModuleRoot() {
		}
	}

	public Map<SimpleReference, IMethod[]> doOperation() {
		try {
			String methodSource = PHP_START + method.getSource();
			CaleeSourceElementRequestor requestor = new CaleeSourceElementRequestor();
			ISourceElementParser parser = null;

			parser = DLTKLanguageManager.getSourceElementParser(PHPNature.ID);

			parser.setRequestor(requestor);

			parser.parseSourceModule(methodSource.toCharArray(), null, method.getSourceModule().getPath().toString().toCharArray());

			return fSearchResults;
		} catch (ModelException e) {
			Logger.logException(e);
		}
		return fSearchResults;
	}

	public IMethod[] findMethods(final String methodName, int argCount, int sourcePosition) {
		final List<IMethod> methods = new ArrayList<IMethod>();
		
		ISourceModule module = this.method.getSourceModule();
		try {
			IModelElement[] elements = module.codeSelect(sourcePosition, /* methodName.length() */1);
			for (IModelElement element : elements) {
				methods.add((IMethod) element);
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}
		return (IMethod[]) methods.toArray(new IMethod[methods.size()]);
	}

	protected void search(String patternString, int searchFor, int limitTo, IDLTKSearchScope scope, SearchRequestor resultCollector) throws CoreException {
		search(patternString, searchFor, limitTo, EXACT_RULE, scope, resultCollector);
	}

	protected void search(String patternString, int searchFor, int limitTo, int matchRule, IDLTKSearchScope scope, SearchRequestor requestor) throws CoreException {
		if (patternString.indexOf('*') != -1 || patternString.indexOf('?') != -1) {
			matchRule |= SearchPattern.R_PATTERN_MATCH;
		}
		SearchPattern pattern = SearchPattern.createPattern(patternString, searchFor, limitTo, matchRule, scope.getLanguageToolkit());
		new SearchEngine().search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, requestor, null);
	}
}

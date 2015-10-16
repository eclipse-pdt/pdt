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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.core.ICallProcessor;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.search.*;
import org.eclipse.php.internal.core.Logger;

public class PHPCallProcessor implements ICallProcessor {
	private final static String EMPTY_STRING = ""; //$NON-NLS-1$
	private SearchEngine engine = new SearchEngine();

	private class Requestor extends SearchRequestor {

		public Map<Object, Object> result = new HashMap<Object, Object>();
		private IModelElement search;

		public Requestor(IModelElement member) {
			this.search = member;
		}

		@Override
		public void acceptSearchMatch(SearchMatch match) throws CoreException {
			if ((match.getAccuracy() != SearchMatch.A_ACCURATE)) {
				return;
			}

			if (match.isInsideDocComment()) {
				return;
			}

			if (match.getElement() != null && match.getElement() instanceof IModelElement) {
				IModelElement member = (IModelElement) match.getElement();

				SimpleReference ref = new SimpleReference(match.getOffset(), match.getOffset() + match.getLength(),
						EMPTY_STRING);
				result.put(ref, member);
			}
		}
	}

	@Override
	public Map process(IModelElement parent, IModelElement member, IDLTKSearchScope scope, IProgressMonitor monitor) {

		SearchPattern pattern = SearchPattern.createPattern(member, IDLTKSearchConstants.REFERENCES,
				SearchPattern.R_EXACT_MATCH | SearchPattern.R_ERASURE_MATCH, scope.getLanguageToolkit());
		Requestor req = new Requestor(member);
		try {
			engine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope, req,
					monitor);
		} catch (CoreException e) {
			Logger.logException(e);
		}

		return req.result;
	}

}

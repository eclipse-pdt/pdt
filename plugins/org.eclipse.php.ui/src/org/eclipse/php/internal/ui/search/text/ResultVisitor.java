/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.search.text;
        
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.search.core.text.TextSearchMatchAccess;
import org.eclipse.search.core.text.TextSearchRequestor;
import org.eclipse.search.internal.ui.text.FileMatch;

/**
 * Description: This is a visitor on the text results  
 * @author Roy, 2007
 */
public class ResultVisitor extends TextSearchRequestor {
	private final List fResult = new LinkedList();
	private final boolean fIsFileSearchOnly;
	private final boolean fSearchInBinaries;
	private final List fCachedMatches = new ArrayList();

	public ResultVisitor(boolean isFileSearchOnly, boolean searchInBinaries) {
		fIsFileSearchOnly = isFileSearchOnly;
		fSearchInBinaries = searchInBinaries;
	}

	public ResultVisitor() {
		this (false, false);
	}

	public boolean acceptFile(IFile file) throws CoreException {
		if (fIsFileSearchOnly) {
			fResult.add(new FileMatch(file, 0, 0));
		}
		flushMatches();
		return true;
	}

	public boolean reportBinaryFile(IFile file) {
		return fSearchInBinaries;
	}

	public boolean acceptPatternMatch(TextSearchMatchAccess matchRequestor) throws CoreException {
		fCachedMatches.add(new FileMatch(matchRequestor.getFile(), matchRequestor.getMatchOffset(), matchRequestor.getMatchLength()));
		return true;
	}

	public void beginReporting() {
		fCachedMatches.clear();
	}

	public void endReporting() {
		flushMatches();
	}
	
	/**
	 * @return a list of {@link FileMatch}
	 */
	public List getResult() {
		return fResult;
	}
	
	/**
	 * Clears resuls 
	 */
	public void clear() {
		fResult.clear();
	}
	
	private void flushMatches() {
		if (!fCachedMatches.isEmpty()) {
			fResult.addAll(fCachedMatches);
		}
	}

}

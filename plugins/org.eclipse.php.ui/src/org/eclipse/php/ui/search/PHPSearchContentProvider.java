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
package org.eclipse.php.ui.search;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public abstract class PHPSearchContentProvider  implements IStructuredContentProvider {

	
	protected final Object[] EMPTY_ARR= new Object[0];
	protected PHPSearchResult fResult;
	private PHPSearchResultPage fPage;

	PHPSearchContentProvider(PHPSearchResultPage page) {
		fPage= page;
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		initialize((PHPSearchResult) newInput);
	}
	
	protected void initialize(PHPSearchResult result) {
		fResult= result;
	}
	
	public abstract void elementsChanged(Object[] updatedElements);
	
	public abstract void clear();
	
	public void dispose() {
		// nothing to do
	}

	PHPSearchResultPage getPage() {
		return fPage;
	}
}
/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.wizard;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ltk.core.refactoring.MultiStateTextFileChange;
import org.eclipse.ltk.core.refactoring.TextEditBasedChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.ui.refactoring.TextEditChangeNode;
import org.eclipse.php.refactoring.core.changes.ProgramDocumentChange;

/**
 * Description: Used as an adapter to the view
 * 
 * @author Roy, 2007
 */
public class RefactoringAdapterFactory implements IAdapterFactory {

	private static final Class<?>[] ADAPTER_LIST = new Class<?>[] { TextEditChangeNode.class };

	@Override
	public Class<?>[] getAdapterList() {
		return ADAPTER_LIST;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(Object object, Class key) {
		if (!TextEditChangeNode.class.equals(key)) {
			return null;
		}
		if ((object instanceof TextFileChange) || (object instanceof MultiStateTextFileChange)
				|| (object instanceof ProgramDocumentChange)) {
			return new PHPRefactoringChangeNode((TextEditBasedChange) object);
		}
		return null;

	}
}

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
package org.eclipse.php.internal.core.model;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.index2.search.ModelAccess;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;

public class PhpModelAccess extends ModelAccess {

	private static final PhpModelAccess instance = new PhpModelAccess();

	public static PhpModelAccess getDefault() {
		return instance;
	}

	public IField[] findIncludes(String name, MatchRule matchRule,
			IDLTKSearchScope scope, IProgressMonitor monitor) {

		List<IField> result = new LinkedList<IField>();
		if (!findElements(IModelElement.IMPORT_DECLARATION, name, matchRule, 0,
				0, scope, result, monitor)) {
			return null;
		}
		return (IField[]) result.toArray(new IField[result.size()]);
	}
}

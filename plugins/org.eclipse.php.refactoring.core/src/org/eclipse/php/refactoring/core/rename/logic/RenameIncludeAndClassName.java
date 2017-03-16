/*******************************************************************************
 * Copyright (c) 2005, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.core.rename.logic;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEditGroup;

public class RenameIncludeAndClassName extends RenameClass {

	private boolean updateClassName;
	private boolean updateReference;
	private IResource changingResouce;

	public RenameIncludeAndClassName(IFile file, String oldName,
			String newName, boolean searchTextual, boolean updateClassName,
			boolean updateReference, IResource resource) {
		super(file, null, oldName, newName, searchTextual, null);
		this.updateClassName = updateClassName;
		this.updateReference = updateReference;
		this.changingResouce = resource;
	}

	public boolean visit(Include include) {
		if (updateReference) {
			Expression exp = include.getExpression();
			Scalar scalar = null;
			if (exp instanceof ParenthesisExpression) {
				Expression expression = ((ParenthesisExpression) exp)
						.getExpression();
				// The expression can be other type.
				if (expression instanceof Scalar) {
					scalar = (Scalar) expression;
				}
			}
			if (exp instanceof Scalar) {
				scalar = (Scalar) exp;
			}
			if (scalar == null) {
				return super.visit(include);
			}

			String stringValue = scalar.getStringValue();
			if (isScalarNeedChange(scalar, stringValue)) {
				addChange(scalar,
						PhpRefactoringCoreMessages
								.getString("RenameIncludeAndClassName.1")); //$NON-NLS-1$
			}
		}

		return false;
	}

	/**
	 * @param identifier
	 */
	protected void checkIdentifier(Identifier identifier) {
		if (updateClassName) {
			super.checkIdentifier(identifier);
		}
	}

	private void addChange(Scalar scalar, String description) {
		String stringValue = scalar.getStringValue();
		int index = stringValue.lastIndexOf(oldName); //$NON-NLS-1$

		addChange(scalar.getStart() + index, description);
	}

	/**
	 * Adds the scalar to the list
	 * 
	 * @param scalar
	 */
	protected void addChange(int start, String description) {
		final TextEditGroup textEditGroup = new TextEditGroup(description);
		final ReplaceEdit replaceEdit = new ReplaceEdit(start,
				oldName.length(), this.newName);
		textEditGroup.addTextEdit(replaceEdit);
		groups.add(textEditGroup);
	}

	private boolean isScalarNeedChange(Scalar scalar, final String stringValue) {
		IPath path = getPath(stringValue);

		return scalar.getScalarType() == Scalar.TYPE_STRING
				&& (changingResouce.getFullPath().equals(path));
		// return false;
	}

	/**
	 * Searches for a file given by the include statement
	 * 
	 * @param includeString
	 * @return path to the actual file
	 */
	private IPath getPath(String includeString) {
		// remove "" or ''
		if (includeString.startsWith("\"") || includeString.startsWith("'")) { //$NON-NLS-1$ //$NON-NLS-2$
			includeString = includeString.substring(1,
					includeString.length() - 1);
		}

		// Check for two cases:
		// 1. include path starts with ../ or ./
		// 2. a relatove include path
		IPath path = null;
		if (includeString.startsWith(".")) { //$NON-NLS-1$
			path = new Path(includeString);
			if (!path.isAbsolute()) {
				path = changedFile.getParent().getFullPath().append(path);
			}

		} else {
			final ISourceModule findSourceModule = FileNetworkUtility
					.findSourceModule(
							DLTKCore.createSourceModuleFrom(this.changedFile),
							includeString);

			if (findSourceModule != null) {
				path = findSourceModule.getPath();
			}
		}
		return path;
	}
}

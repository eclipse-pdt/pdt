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
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.ast.nodes.Expression;
import org.eclipse.php.core.ast.nodes.Include;
import org.eclipse.php.core.ast.nodes.ParenthesisExpression;
import org.eclipse.php.core.ast.nodes.Scalar;
import org.eclipse.php.internal.core.filenetwork.FileNetworkUtility;
import org.eclipse.php.refactoring.core.PhpRefactoringCoreMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEditGroup;

public class RenameIncludeFolder extends AbstractRename {

	private boolean updateReference;
	private IPath folderPath;

	public RenameIncludeFolder(IFile file, String oldName, String newName,
			IPath folderPath, boolean searchTextual, boolean updateReference) {
		super(file, oldName, newName, searchTextual);
		this.updateReference = updateReference;
		this.folderPath = folderPath;
	}

	public boolean visit(Include include) {
		if (updateReference) {
			Expression exp = include.getExpression();
			Scalar scalar = null;
			if (exp instanceof ParenthesisExpression) {
				Expression object = ((ParenthesisExpression) exp)
						.getExpression();
				if (object instanceof Scalar) {
					scalar = (Scalar) object;
				} else {
					// Ignore other cases.
				}
			}
			if (exp instanceof Scalar) {
				scalar = (Scalar) exp;
			}
			if (scalar == null) {
				return super.visit(include);
			}

			if (isScalarNeedChange(scalar)) {
				String includeString = scalar.getStringValue();
				// remove "" or ''
				int len = 0;
				if (includeString.startsWith("\"") || includeString.startsWith("'")) { //$NON-NLS-1$ //$NON-NLS-2$
					includeString = includeString.substring(1,
							includeString.length() - 1);
					len = 1;
				}

				if (includeString.startsWith(".")) { //$NON-NLS-1$
					IPath path = new Path(includeString);
					if (!path.isAbsolute()) {
						path = changedFile.getParent().getFullPath()
								.append(path);
					}

					String lastString = path.makeRelativeTo(folderPath)
							.toString();
					len = len + includeString.indexOf(lastString)
							- oldName.length() - 1;

					addChange(scalar.getStart() + len,
							PhpRefactoringCoreMessages
									.getString("RenameIncludeAndClassName.1")); //$NON-NLS-1$
				} else {

					IPath path = getPath(includeString);
					String lastString = path.makeRelativeTo(folderPath)
							.toString();

					// if no change is required - skip
					if (!lastString.equalsIgnoreCase(includeString)) {
						len = len + includeString.indexOf(lastString)
								- oldName.length() - 1;

						addChange(
								scalar.getStart() + len,
								PhpRefactoringCoreMessages
										.getString("RenameIncludeAndClassName.1")); //$NON-NLS-1$
					}
				}

			}
		}

		return false;
	}

	/**
	 * Adds the scalar to the list
	 * 
	 * @param scalar
	 */
	protected void addChange(int start, String description) {
		final TextEditGroup textEditGroup = new TextEditGroup(description);

		final ReplaceEdit replaceEdit = new ReplaceEdit(start,
				oldName.length(), newName);
		textEditGroup.addTextEdit(replaceEdit);
		groups.add(textEditGroup);
	}

	private boolean isScalarNeedChange(Scalar scalar) {
		String includeString = scalar.getStringValue();

		boolean needChange = false;
		IPath path = getPath(includeString);

		if (path != null
				&& changedFile.getWorkspace().getRoot().getFile(path).exists()
				&& folderPath.isPrefixOf(path)) {

			if ((includeString.startsWith("'./") //$NON-NLS-1$
					|| includeString.startsWith("'../") || (includeString //$NON-NLS-1$
					.indexOf("/") == -1 && includeString.indexOf("\\") == -1)) //$NON-NLS-1$ //$NON-NLS-2$
					&& folderPath.isPrefixOf(changedFile.getFullPath())) {
				needChange = false;
			} else {
				IPath relPath = path.makeRelativeTo(folderPath);
				String relativePath = relPath.toString();

				if (relativePath.length() != includeString.length()) {
					needChange = true;
				}
			}

		}
		return scalar.getScalarType() == Scalar.TYPE_STRING && needChange;
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

	@Override
	public String getRenameDescription() {
		return PhpRefactoringCoreMessages.getString("RenameIncludeFolder.4"); //$NON-NLS-1$
	}

}

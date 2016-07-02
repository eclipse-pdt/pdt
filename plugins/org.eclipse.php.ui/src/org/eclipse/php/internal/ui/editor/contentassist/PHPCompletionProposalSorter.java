/*******************************************************************************
 * Copyright (c) 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.ui.text.completion.AbstractScriptCompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalSorter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.ui.Logger;

public class PHPCompletionProposalSorter implements ICompletionProposalSorter {

	@Override
	public int compare(ICompletionProposal p1, ICompletionProposal p2) {
		if (p1 instanceof AbstractScriptCompletionProposal && p2 instanceof AbstractScriptCompletionProposal) {
			AbstractScriptCompletionProposal cp1 = (AbstractScriptCompletionProposal) p1;
			AbstractScriptCompletionProposal cp2 = (AbstractScriptCompletionProposal) p2;

			IModelElement el1 = cp1.getModelElement();
			IModelElement el2 = cp2.getModelElement();

			if (el1.getElementType() == IModelElement.TYPE && el2.getElementType() == IModelElement.TYPE) {
				try {
					int result = Boolean.compare(PHPFlags.isNamespace(((IType) el1).getFlags()),
							PHPFlags.isNamespace(((IType) el2).getFlags()));
					if (result != 0) {
						return result;
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}

			if (el1.getElementName().equals(el2.getElementName())) {
				String parent1 = getParentQualifier(el1.getParent());
				String parent2 = getParentQualifier(el2.getParent());

				if (parent1 == null && parent2 == null) {
					return 0;
				} else if (parent1 == null) {
					return -1;
				} else if (parent2 == null) {
					return 1;
				}

				int segments1 = StringUtils.countMatches(parent1, "\\"); //$NON-NLS-1$
				int segments2 = StringUtils.countMatches(parent2, "\\"); //$NON-NLS-1$
				// bump up elements in global namespace
				if (segments1 == 0 || segments2 == 0) {
					return Integer.compare(segments1, segments2);
				}
				return parent1.compareToIgnoreCase(parent2);
			}
			return 0;
		}
		return 0;
	}

	private String getParentQualifier(IModelElement parent) {
		if (parent instanceof IType) {
			IType type = (IType) parent;
			return type.getTypeQualifiedName("\\"); //$NON-NLS-1$
		}
		return null;
	}

}

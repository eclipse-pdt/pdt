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
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.text.hover.CompletionHoverControlCreator;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.completion.ScriptOverrideCompletionProposal;
import org.eclipse.jface.internal.text.html.BrowserInformationControl;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension4;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("restriction")
public class PHPOverrideCompletionProposal extends
		ScriptOverrideCompletionProposal implements
		ICompletionProposalExtension4 {
	/**
	 * The control creator.
	 */
	private IInformationControlCreator fCreator;

	public PHPOverrideCompletionProposal(IScriptProject jproject,
			ISourceModule cu, String methodName, String[] paramTypes,
			int start, int length, String displayName, String completionProposal) {
		super(jproject, cu, methodName, paramTypes, start, length, displayName,
				completionProposal);
	}

	public void apply(IDocument document, char trigger, int offset) {
		UseStatementInjector injector = new UseStatementInjector(this);
		offset = injector.inject(document, getTextViewer(), offset);

		super.apply(document, trigger, offset);

		calculateCursorPosition(document, offset);
	}

	public boolean isAutoInsertable() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
				PHPCoreConstants.CODEASSIST_AUTOINSERT, false, null);
	}

	protected boolean insertCompletion() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
				PHPCoreConstants.CODEASSIST_INSERT_COMPLETION, true, null);
	}

	protected void calculateCursorPosition(IDocument document, int offset) {
		try {
			while (Character.isJavaIdentifierPart(document.getChar(offset))
					|| document.getChar(offset) == NamespaceReference.NAMESPACE_SEPARATOR) {
				++offset;
			}
			if (document.getChar(offset) == '(') {
				boolean hasArguments = false;
				IModelElement modelElement = getModelElement();
				if (modelElement.getElementType() == IModelElement.METHOD) {
					IMethod method = (IMethod) modelElement;
					try {
						String[] parameters = method.getParameterNames();
						if (parameters != null && parameters.length > 0) {
							hasArguments = true;
						}
					} catch (ModelException e) {
					}
				}
				if (!hasArguments) {
					setCursorPosition(offset - getReplacementOffset() + 2);
				} else {
					setCursorPosition(offset - getReplacementOffset() + 1);
				}
			}
		} catch (BadLocationException e) {
		}
	}

	public IContextInformation getContextInformation() {
		String displayString = getDisplayString();

		// * ZSTD-335
		IModelElement modelElement = getModelElement();
		if (modelElement instanceof IMethod) {
			IMethod method = (IMethod) modelElement;
			IParameter[] parameters;
			try {
				parameters = method.getParameters();
				if (parameters != null) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < parameters.length; i++) {
						IParameter parameter = parameters[i];
						if (parameter.getType() != null) {
							sb.append(parameter.getType()).append(" "); //$NON-NLS-1$
						}
						sb.append(parameter.getName());
						if (parameter.getDefaultValue() != null) {
							sb.append("=").append(parameter.getDefaultValue()); //$NON-NLS-1$
						}
						sb.append(","); //$NON-NLS-1$
					}
					String infoDisplayString = sb.toString();
					if (infoDisplayString.length() > 0) {
						infoDisplayString = infoDisplayString.substring(0,
								infoDisplayString.length() - 1);
						return new ContextInformation(displayString,
								infoDisplayString);
					}

				}
			} catch (ModelException e) {
				e.printStackTrace();
			}

		}

		String infoDisplayString = displayString;

		int i = infoDisplayString.indexOf('(');
		if (i != -1) {
			infoDisplayString = infoDisplayString.substring(i + 1);
		}
		i = infoDisplayString.indexOf(')');
		if (i != -1) {
			infoDisplayString = infoDisplayString.substring(0, i);
		}
		if (infoDisplayString.length() == 0) {
			return null;
		}
		return new ContextInformation(displayString, infoDisplayString);
	}

	protected boolean isCamelCaseMatching() {
		return true;
	}

	protected ScriptTextTools getTextTools() {
		return PHPUiPlugin.getDefault().getTextTools();
	}

	public IInformationControlCreator getInformationControlCreator() {
		if (fCreator == null) {
			fCreator = new CompletionHoverControlCreator(
					new IInformationControlCreator() {
						public IInformationControl createInformationControl(
								Shell parent) {
							if (BrowserInformationControl.isAvailable(parent)) {
								return new BrowserInformationControl(
										parent,
										PreferenceConstants.APPEARANCE_DOCUMENTATION_FONT,
										true);
							} else {
								return new DefaultInformationControl(parent,
										true);
							}
						}
					}, true);
		}
		return fCreator;
	}

}

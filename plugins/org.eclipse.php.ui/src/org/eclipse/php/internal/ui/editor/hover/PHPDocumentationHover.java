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
package org.eclipse.php.internal.ui.editor.hover;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.core.resources.IStorage;
import org.eclipse.dltk.core.ICodeAssist;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.ExternalSourceModule;
import org.eclipse.dltk.internal.ui.editor.ExternalStorageEditorInput;
import org.eclipse.dltk.internal.ui.text.HTMLPrinter;
import org.eclipse.dltk.internal.ui.text.hover.DocumentationHover;
import org.eclipse.dltk.internal.ui.text.hover.ScriptHoverMessages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.IWorkingCopyManager;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.documentation.ScriptDocumentationAccess;
import org.eclipse.dltk.utils.TextUtils;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

public class PHPDocumentationHover extends DocumentationHover implements
		IPHPTextHover {

	private final long LABEL_FLAGS = // ScriptElementLabels.ALL_FULLY_QUALIFIED
	ScriptElementLabels.M_APP_TYPE_PARAMETERS
			| ScriptElementLabels.M_PARAMETER_NAMES
			| ScriptElementLabels.M_EXCEPTIONS
			| ScriptElementLabels.F_PRE_TYPE_SIGNATURE
			| ScriptElementLabels.M_PRE_TYPE_PARAMETERS
			| ScriptElementLabels.T_TYPE_PARAMETERS
			| ScriptElementLabels.USE_RESOLVED;
	private final long LOCAL_VARIABLE_FLAGS = LABEL_FLAGS
			& ~ScriptElementLabels.F_FULLY_QUALIFIED
			| ScriptElementLabels.F_POST_QUALIFIED;

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}

	protected ICodeAssist getCodeAssist() {
		IEditorPart editor = getEditor();
		if (editor != null) {
			IEditorInput input = editor.getEditorInput();

			if (input instanceof ExternalStorageEditorInput) {
				ExternalStorageEditorInput external = (ExternalStorageEditorInput) input;
				IStorage storage = external.getStorage();
				if (storage != null) {
					if (storage instanceof ExternalSourceModule) {
						ExternalSourceModule externalSourceModule = (ExternalSourceModule) storage;
						return externalSourceModule;
					}
				}
			}

			IWorkingCopyManager manager = DLTKUIPlugin.getDefault()
					.getWorkingCopyManager();
			return manager.getWorkingCopy(input, false);
		}

		return null;
	}

	@Override
	protected String getHoverInfo(String nature, IModelElement[] result) {
		StringBuffer buffer = new StringBuffer();
		int nResults = result.length;
		if (nResults == 0)
			return null;

		boolean hasContents = false;
		if (nResults > 1) {
			HTMLPrinter.addSmallHeader(buffer, getInfoText(result[0]));
			HTMLPrinter.addParagraph(buffer, "<hr>"); //$NON-NLS-1$
			for (int i = 0; i < result.length; i++) {
				// HTMLPrinter.startBulletList(buffer);
				IModelElement curr = result[i];
				if (curr instanceof IMember) {
					IMember member = (IMember) curr;

					Reader reader;
					try {
						reader = ScriptDocumentationAccess
								.getHTMLContentReader(nature, member, true,
										true);

						// Provide hint why there's no doc
						if (reader == null) {
							// reader= new
							// StringReader(DLTKHoverMessages.ScriptdocHover_noAttachedInformation);
							continue;
						}

					} catch (ModelException ex) {
						return null;
					}

					if (reader != null) {
						// HTMLPrinter.addBullet(buffer, getInfoText(curr));
						// HTMLPrinter.addParagraph(buffer, "<br>");
						if (hasContents) {
							HTMLPrinter.addParagraph(buffer, "<hr>"); //$NON-NLS-1$
						}
						HTMLPrinter.addParagraph(buffer, reader);
					}
					hasContents = true;
				}
				// HTMLPrinter.endBulletList(buffer);
			}

		} else {

			IModelElement curr = result[0];
			if (curr instanceof IMember) {
				IMember member = (IMember) curr;
				HTMLPrinter.addSmallHeader(buffer, getInfoText(member));
				Reader reader;
				try {
					reader = ScriptDocumentationAccess.getHTMLContentReader(
							nature, member, true, true);

					// Provide hint why there's no doc
					if (reader == null) {
						reader = new StringReader(
								ScriptHoverMessages.ScriptdocHover_noAttachedInformation);
					}

				} catch (ModelException ex) {
					return null;
				}

				if (reader != null) {
					HTMLPrinter.addParagraph(buffer, reader);
				}
				hasContents = true;
			}/*
			 * else if (curr.getElementType() == IModelElement.LOCAL_VARIABLE ||
			 * curr.getElementType() == IModelElement.TYPE_PARAMETER) {
			 * HTMLPrinter.addSmallHeader(buffer, getInfoText(curr));
			 * hasContents= true; }
			 */
		}

		if (!hasContents)
			return null;

		if (buffer.length() > 0) {
			HTMLPrinter.insertPageProlog(buffer, 0, getStyleSheet());
			HTMLPrinter.addPageEpilog(buffer);
			return buffer.toString();
		}

		return null;
	}

	private String getInfoText(IModelElement member) {
		long flags = member.getElementType() == IModelElement.FIELD ? LOCAL_VARIABLE_FLAGS
				: LABEL_FLAGS;
		String label = ScriptElementLabels.getDefault().getElementLabel(member,
				flags);
		return TextUtils.escapeHTML(label);
	}

}

/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.formatter.core.profiles.CodeFormatterPreferences;
import org.eclipse.php.internal.core.format.FormatPreferencesSupport;
import org.eclipse.php.internal.core.format.IFormatterCommonPrferences;
import org.eclipse.php.internal.formatter.core.Logger;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public class PHPCodeFormatterCommonPreferences implements IFormatterCommonPrferences {

	@Override
	public int getIndentationWrappedLineSize(IDocument document) {
		CodeFormatterPreferences preferences = getPreferences(document);
		if (preferences == null) {
			return FormatPreferencesSupport.getInstance().getIndentationWrappedLineSize(document);
		} else {
			return preferences.line_wrap_wrapped_lines_indentation;
		}
	}

	@Override
	public int getIndentationArrayInitSize(IDocument document) {
		CodeFormatterPreferences preferences = getPreferences(document);
		if (preferences == null) {
			return FormatPreferencesSupport.getInstance().getIndentationArrayInitSize(document);
		} else {
			return preferences.line_wrap_array_init_indentation;
		}
	}

	@Override
	public int getIndentationSize(IDocument document) {
		CodeFormatterPreferences preferences = getPreferences(document);
		if (preferences == null) {
			return FormatPreferencesSupport.getInstance().getIndentationSize(document);
		} else {
			return preferences.indentationSize;
		}
	}

	@Override
	public char getIndentationChar(IDocument document) {
		CodeFormatterPreferences preferences = getPreferences(document);
		if (preferences == null) {
			return FormatPreferencesSupport.getInstance().getIndentationChar(document);
		} else {
			return preferences.indentationChar;
		}
	}

	@Override
	public int getTabSize(IDocument document) {
		CodeFormatterPreferences preferences = getPreferences(document);
		if (preferences == null) {
			return FormatPreferencesSupport.getInstance().getIndentationSize(document);
		} else {
			return preferences.tabSize;
		}
	}

	@Override
	public boolean useTab(IDocument document) {
		return getIndentationChar(document) == '\t';
	}

	private CodeFormatterPreferences getPreferences(IDocument document) {
		IStructuredModel structuredModel = null;
		try {
			if (document instanceof IStructuredDocument) {
				structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(document);
				if (structuredModel == null) {
					try {
						CodeFormatterPreferences preferences = PHPCodeFormatter.getPreferences((IProject) null);
						return preferences;
					} catch (Exception e) {
					}
					return null;
				}

				IProject project = getProject(structuredModel);
				if (project == null) {
					Logger.logException(new IllegalStateException("Cann't resolve file name")); //$NON-NLS-1$
				}
				try {
					CodeFormatterPreferences preferences = PHPCodeFormatter.getPreferences(project);
					return preferences;
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			if (structuredModel != null) {
				structuredModel.releaseFromRead();
			}
		}
		return null;
	}

	/**
	 * @param doModelForPHP
	 * @return project from document
	 */
	private final IProject getProject(IStructuredModel doModelForPHP) {
		final String id = doModelForPHP.getId();
		if (id != null) {
			final IFile file = getFile(id);
			if (file != null) {
				return file.getProject();
			}
		}
		return null;
	}

	/**
	 * @param id
	 * @return the file from document
	 */
	private IFile getFile(final String id) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(id));
	}

}

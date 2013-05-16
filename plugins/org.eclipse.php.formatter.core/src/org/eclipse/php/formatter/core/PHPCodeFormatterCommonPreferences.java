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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.format.FormatPreferencesSupport;
import org.eclipse.php.internal.core.format.IFormatterCommonPrferences;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public class PHPCodeFormatterCommonPreferences implements
		IFormatterCommonPrferences {

	private CodeFormatterPreferences fCodeFormatterPreferences = CodeFormatterPreferences
			.getDefaultPreferences();
	private static final Map<String, Object> defaultPrefrencesValues = CodeFormatterPreferences
			.getDefaultPreferences().getMap();

	public int getIndentationWrappedLineSize(IDocument document) {
		CodeFormatterPreferences preferences = getPreferences(document);
		if (preferences == null) {
			return FormatPreferencesSupport.getInstance()
					.getIndentationWrappedLineSize(document);
		} else {
			return preferences.line_wrap_wrapped_lines_indentation;
		}
	}

	public int getIndentationArrayInitSize(IDocument document) {
		CodeFormatterPreferences preferences = getPreferences(document);
		if (preferences == null) {
			return FormatPreferencesSupport.getInstance()
					.getIndentationArrayInitSize(document);
		} else {
			return preferences.line_wrap_array_init_indentation;
		}
	}

	public int getIndentationSize(IDocument document) {
		CodeFormatterPreferences preferences = getPreferences(document);
		if (preferences == null) {
			return FormatPreferencesSupport.getInstance().getIndentationSize(
					document);
		} else {
			return preferences.indentationSize;
		}
	}

	public char getIndentationChar(IDocument document) {
		CodeFormatterPreferences preferences = getPreferences(document);
		if (preferences == null) {
			return FormatPreferencesSupport.getInstance().getIndentationChar(
					document);
		} else {
			return preferences.indentationChar;
		}
	}

	private CodeFormatterPreferences getPreferences(IDocument document) {
		IStructuredModel structuredModel = null;
		try {
			if (document instanceof IStructuredDocument) {
				IStructuredDocument structuredDocument = (IStructuredDocument) document;
				structuredModel = StructuredModelManager.getModelManager()
						.getExistingModelForRead(document);
				if (structuredModel == null) {
					try {
						CodeFormatterPreferences preferences = getPreferences((IProject) null);
						return preferences;
					} catch (Exception e) {
					}
					return null;
				}
				DOMModelForPHP doModelForPHP = (DOMModelForPHP) structuredModel;

				IProject project = getProject(doModelForPHP);
				if (project == null) {
					Logger.logException(new IllegalStateException(
							"Cann't resolve file name")); //$NON-NLS-1$
				}
				try {
					CodeFormatterPreferences preferences = getPreferences(project);
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

	private CodeFormatterPreferences getPreferences(IProject project)
			throws Exception {

		IEclipsePreferences node = null;
		if (project != null) {
			ProjectScope scope = (ProjectScope) new ProjectScope(project);
			node = scope.getNode(FormatterCorePlugin.PLUGIN_ID);
		}

		Map<String, Object> p = new HashMap<String, Object>(
				defaultPrefrencesValues);
		if (node != null && node.keys().length > 0) {
			Set<String> propetiesNames = p.keySet();
			for (Iterator<String> iter = propetiesNames.iterator(); iter
					.hasNext();) {
				String property = (String) iter.next();
				String value = node.get(property, null);
				if (value != null) {
					p.put(property, value);
				}
			}
		} else {
			Preferences preferences = FormatterCorePlugin.getDefault()
					.getPluginPreferences();
			String[] propetiesNames = preferences.propertyNames();
			for (int i = 0; i < propetiesNames.length; i++) {
				String property = propetiesNames[i];
				String value = preferences.getString(property);
				if (value != null) {
					p.put(property, value);
				}
			}
		}

		fCodeFormatterPreferences.setPreferencesValues(p);

		return fCodeFormatterPreferences;
	}

	/**
	 * @param doModelForPHP
	 * @return project from document
	 */
	private final static IProject getProject(DOMModelForPHP doModelForPHP) {
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
	private static IFile getFile(final String id) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(id));
	}

	public int getTabSize(IDocument document) {
		CodeFormatterPreferences preferences = getPreferences(document);
		if (preferences == null) {
			return FormatPreferencesSupport.getInstance().getIndentationSize(
					document);
		} else {
			return preferences.tabSize;
		}
	}

	public boolean useTab(IDocument document) {
		return getIndentationChar(document) == '\t';
	}

}

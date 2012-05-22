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
package org.eclipse.php.internal.core.format;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.preferences.*;
import org.eclipse.wst.sse.core.StructuredModelManager;

/**
 * 
 * @author guy.g
 * 
 */
public class FormatPreferencesSupport implements IFormatterCommonPrferences {

	private IDocument fLastDocument = null;
	private IProject fLastProject = null;

	private char indentationChar;
	private int indentationSize;
	private int tabSize;
	private boolean useTab;
	private int fIndentationWrappedLineSize;
	private int fIndentationArrayInitSize;

	private PreferencesSupport preferencesSupport = null;
	private PreferencesPropagatorListener listener = null;

	private boolean preferencesChanged = false;

	private PreferencesPropagator preferencesPropagator;

	private static final String NODES_QUALIFIER = PHPCorePlugin.ID;
	private static final Preferences store = PHPCorePlugin.getDefault()
			.getPluginPreferences();

	private FormatPreferencesSupport() {

		preferencesPropagator = PreferencePropagatorFactory
				.getPreferencePropagator(NODES_QUALIFIER, store);
		preferencesSupport = new PreferencesSupport(PHPCorePlugin.ID, store);
	}

	private static FormatPreferencesSupport instance = null;

	public static FormatPreferencesSupport getInstance() {
		if (instance == null) {
			instance = new FormatPreferencesSupport();
		}
		return instance;
	}

	public int getIndentationWrappedLineSize(IDocument document) {
		if (!verifyValidity(document)) {
			String indentSize = preferencesSupport
					.getWorkspacePreferencesValue(PHPCoreConstants.FORMATTER_INDENTATION_WRAPPED_LINE_SIZE);
			if (indentSize == null || indentSize.length() == 0) {
				return fIndentationWrappedLineSize;
			}
			return Integer.valueOf(indentSize).intValue();
		}
		return fIndentationWrappedLineSize;
	}

	public int getIndentationArrayInitSize(IDocument document) {
		if (!verifyValidity(document)) {
			String indentSize = preferencesSupport
					.getWorkspacePreferencesValue(PHPCoreConstants.FORMATTER_INDENTATION_ARRAY_INIT_SIZE);
			if (indentSize == null || indentSize.length() == 0) {
				return fIndentationArrayInitSize;
			}
			return Integer.valueOf(indentSize).intValue();
		}
		return fIndentationArrayInitSize;
	}

	public int getIndentationSize(IDocument document) {
		if (!verifyValidity(document)) {
			String indentSize = preferencesSupport
					.getWorkspacePreferencesValue(PHPCoreConstants.FORMATTER_INDENTATION_SIZE);
			if (indentSize == null || indentSize.length() == 0) {
				return indentationSize;
			}
			return Integer.valueOf(indentSize).intValue();
		}
		return indentationSize;
	}

	public int getTabSize(IDocument document) {
		if (!verifyValidity(document)) {
			String tabSizeStr = preferencesSupport
					.getWorkspacePreferencesValue(PHPCoreConstants.FORMATTER_TAB_SIZE);
			if (tabSizeStr == null || tabSizeStr.length() == 0) {
				return tabSize;
			}
			return Integer.valueOf(tabSizeStr).intValue();
		}
		return tabSize;
	}

	public char getIndentationChar(IDocument document) {
		if (!verifyValidity(document)) {
			String useTab = preferencesSupport
					.getWorkspacePreferencesValue(PHPCoreConstants.FORMATTER_USE_TABS);
			if (useTab == null) {
				return '\t';
			}
			return (Boolean.valueOf(useTab).booleanValue()) ? '\t' : ' ';
		}
		return indentationChar;
	}

	public boolean useTab(IDocument document) {
		if (!verifyValidity(document)) {
			String useTab = preferencesSupport
					.getWorkspacePreferencesValue(PHPCoreConstants.FORMATTER_USE_TABS);
			if (useTab == null) {
				return true;
			}
			return Boolean.valueOf(useTab).booleanValue();
		}
		return useTab;
	}

	private boolean verifyValidity(IDocument document) {
		if (fLastDocument != document) {
			DOMModelForPHP editorModel = null;
			try {
				editorModel = (DOMModelForPHP) StructuredModelManager
						.getModelManager().getExistingModelForRead(document);

				// The PHPMergeViewer can be used outside Editor.
				// E.g. the preview page.
				// In those cases, the editroModel is null.
				// Do the check and return in null case.
				if (editorModel == null) {
					return false;
				}

				String baseLocation = editorModel.getBaseLocation();
				// The baseLocation may be a path on disk or relative to the
				// workspace root. Don't translate on-disk paths to
				// in-workspace resources.
				IPath basePath = new Path(baseLocation);
				IFile file = null;
				if (basePath.segmentCount() > 1) {
					file = ResourcesPlugin.getWorkspace().getRoot()
							.getFile(basePath);
					if (!file.exists()) {
						file = null;
					}
				}
				if (file == null) {
					return false;
				}

				IProject project = file.getProject();
				if (fLastProject != project) {
					fLastProject = project;
					verifyListening();
				}
			} finally {
				if (editorModel != null)
					editorModel.releaseFromRead();
			}
		}

		if (fLastDocument != document || preferencesChanged) {
			String useTab = preferencesSupport.getPreferencesValue(
					PHPCoreConstants.FORMATTER_USE_TABS, null, fLastProject);
			String indentSize = preferencesSupport.getPreferencesValue(
					PHPCoreConstants.FORMATTER_INDENTATION_SIZE, null,
					fLastProject);
			String tabSize = preferencesSupport.getPreferencesValue(
					PHPCoreConstants.FORMATTER_TAB_SIZE, null, fLastProject);
			String indentationWrappedLineSize = preferencesSupport
					.getPreferencesValue(
							PHPCoreConstants.FORMATTER_INDENTATION_WRAPPED_LINE_SIZE,
							null, fLastProject);
			if (indentationWrappedLineSize == null
					|| indentationWrappedLineSize.trim().length() == 0) {
				indentationWrappedLineSize = PHPCoreConstants.DEFAULT_INDENTATION_WRAPPED_LINE_SIZE;
			}

			String indentationArrayInitSize = preferencesSupport
					.getPreferencesValue(
							PHPCoreConstants.FORMATTER_INDENTATION_ARRAY_INIT_SIZE,
							null, fLastProject);
			if (indentationArrayInitSize == null
					|| indentationArrayInitSize.trim().length() == 0) {
				indentationArrayInitSize = PHPCoreConstants.DEFAULT_INDENTATION_ARRAY_INIT_SIZE;
			}

			indentationChar = (Boolean.valueOf(useTab).booleanValue()) ? '\t'
					: ' ';
			this.useTab = Boolean.valueOf(useTab).booleanValue();
			indentationSize = Integer.valueOf(indentSize).intValue();
			this.tabSize = Integer.valueOf(tabSize).intValue();
			fIndentationWrappedLineSize = Integer.valueOf(
					indentationWrappedLineSize).intValue();
			fIndentationArrayInitSize = Integer.valueOf(
					indentationArrayInitSize).intValue();

			preferencesChanged = false;
			fLastDocument = document;
		}
		return true;
	}

	private void verifyListening() {
		if (listener != null) {
			preferencesPropagator.removePropagatorListener(listener,
					PHPCoreConstants.FORMATTER_INDENTATION_WRAPPED_LINE_SIZE);
			preferencesPropagator.removePropagatorListener(listener,
					PHPCoreConstants.FORMATTER_INDENTATION_ARRAY_INIT_SIZE);
			preferencesPropagator.removePropagatorListener(listener,
					PHPCoreConstants.FORMATTER_USE_TABS);
			preferencesPropagator.removePropagatorListener(listener,
					PHPCoreConstants.FORMATTER_INDENTATION_SIZE);
		}

		listener = new PreferencesPropagatorListener(fLastProject);
		preferencesPropagator.addPropagatorListener(listener,
				PHPCoreConstants.FORMATTER_INDENTATION_WRAPPED_LINE_SIZE);
		preferencesPropagator.addPropagatorListener(listener,
				PHPCoreConstants.FORMATTER_INDENTATION_ARRAY_INIT_SIZE);
		preferencesPropagator.addPropagatorListener(listener,
				PHPCoreConstants.FORMATTER_USE_TABS);
		preferencesPropagator.addPropagatorListener(listener,
				PHPCoreConstants.FORMATTER_INDENTATION_SIZE);
	}

	private class PreferencesPropagatorListener implements
			IPreferencesPropagatorListener {

		private IProject project;

		public PreferencesPropagatorListener(IProject project) {
			this.project = project;
		}

		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			preferencesChanged = true;
		}

		public IProject getProject() {
			return project;
		}

	}

}

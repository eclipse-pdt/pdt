/*******************************************************************************
 * Copyright (c) 2009, 2010, 2017 Zend Technologies Ltd. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.ui.format;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.core.format.IFormatterProcessorFactory;
import org.eclipse.php.internal.core.preferences.PreferencesSupport;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.wst.html.core.text.IHTMLPartitions;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.format.IStructuredFormatProcessor;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredPartitioning;
import org.w3c.dom.Node;

/**
 * @author vadim.p
 * 
 */
public class PHPFormatProcessorProxy implements IStructuredFormatProcessor {

	private static final String CLASS_ATTR = "class"; //$NON-NLS-1$

	private static IConfigurationElement phpFormatterElement;
	// private static String phpFormatterClassName;
	private static IContentFormatter phpFormatter;

	static {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(PHPUiPlugin.FORMATTER_PROCESSOR_ID);
		for (IConfigurationElement element : elements) {
			String name = element.getName();
			if (PHPUiPlugin.FORMATTER_PROCESSOR.equals(name)) {
				// phpFormatterClassName = element.getAttribute(CLASS_ATTR);
				phpFormatterElement = element;
			}
		}
	}

	private boolean removeTrailingWhitespacesEnabled = false;
	private boolean ignoreEmptyLines = false;

	/*
	 * Gets the preferences set for this editor in the Save Actions section
	 */
	public void updateSaveActionsState(@Nullable IProject project) {
		PreferencesSupport prefSupport = new PreferencesSupport(PHPUiPlugin.ID);
		String doCleanupPref = prefSupport.getPreferencesValue(PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES,
				null, project);
		String ignoreEmptyPref = prefSupport.getPreferencesValue(
				PreferenceConstants.FORMAT_REMOVE_TRAILING_WHITESPACES_IGNORE_EMPTY, null, project);

		removeTrailingWhitespacesEnabled = Boolean.parseBoolean(doCleanupPref);
		ignoreEmptyLines = Boolean.parseBoolean(ignoreEmptyPref);
	}

	public static IContentFormatter getFormatter() {
		if (phpFormatter == null && phpFormatterElement != null) {
			try {
				phpFormatter = (IContentFormatter) phpFormatterElement.createExecutableExtension(CLASS_ATTR);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (phpFormatter == null) {
			phpFormatter = new MultiPassContentFormatter(IStructuredPartitioning.DEFAULT_STRUCTURED_PARTITIONING,
					IHTMLPartitions.HTML_DEFAULT);
		}
		return phpFormatter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.wst.sse.core.internal.format.IStructuredFormatProcessor#
	 * formatDocument(org.eclipse.jface.text.IDocument, int, int)
	 */
	public void formatDocument(IDocument document, int start, int length) throws IOException, CoreException {
		IRegion region = new Region(start, length);
		getFormatter().format(document, region);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.wst.sse.core.internal.format.IStructuredFormatProcessor#
	 * formatFile(org.eclipse.core.resources.IFile)
	 */
	public void formatFile(IFile file) throws IOException, CoreException {
		IStructuredDocument document = null;
		IStructuredModel structuredModel = null;
		try {
			if (file != null) {
				if (file.exists()) {
					structuredModel = StructuredModelManager.getModelManager().getModelForRead(file);
					if (structuredModel != null) {
						document = structuredModel.getStructuredDocument();
					} else {
						document = StructuredModelManager.getModelManager().createStructuredDocumentFor(file);
					}
				}
			}

			if (document != null) {
				Region region = new Region(0, document.getLength());
				IContentFormatter formatter = getFormatter();
				if (formatter != null) {
					formatter.format(document, region);
				}
				if (formatter instanceof IFormatterProcessorFactory) {
					updateSaveActionsState(file != null ? file.getProject() : null);
					if (removeTrailingWhitespacesEnabled) {
						DeleteEdit[] deletes = FormatterUtils.getTrailingWhitespaces(document, ignoreEmptyLines);
						FormatterUtils.removeTrailingWhitespaces(document, deletes);
					}
				}
			}

		} catch (Exception e) {
			PHPCorePlugin.log(e);
		} finally {
			// release model after formatting
			if (structuredModel != null) {
				structuredModel.save();
				structuredModel.releaseFromRead();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.wst.sse.core.internal.format.IStructuredFormatProcessor#
	 * formatModel
	 * (org.eclipse.wst.sse.core.internal.provisional.IStructuredModel)
	 */
	public void formatModel(IStructuredModel structuredModel) {
		IStructuredDocument document = structuredModel.getStructuredDocument();
		IContentFormatter formatter = getFormatter();
		if (formatter != null)
			formatter.format(document, new Region(0, document.getLength()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.wst.sse.core.internal.format.IStructuredFormatProcessor#
	 * formatModel
	 * (org.eclipse.wst.sse.core.internal.provisional.IStructuredModel, int,
	 * int)
	 */
	public void formatModel(IStructuredModel structuredModel, int start, int length) {
		IStructuredDocument document = structuredModel.getStructuredDocument();
		Region region = new Region(start, length);
		IContentFormatter formatter = getFormatter();
		if (formatter != null)
			formatter.format(document, region);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.wst.sse.core.internal.format.IStructuredFormatProcessor#
	 * formatNode(org.w3c.dom.Node)
	 */
	public void formatNode(Node node) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.wst.sse.core.internal.format.IStructuredFormatProcessor#
	 * setProgressMonitor(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setProgressMonitor(IProgressMonitor monitor) {

	}

}

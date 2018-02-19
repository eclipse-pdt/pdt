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

import java.util.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.formatter.core.profiles.CodeFormatterPreferences;
import org.eclipse.php.internal.core.format.ICodeFormattingProcessor;
import org.eclipse.php.internal.core.format.IFormatterProcessorFactory;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.php.internal.formatter.core.FormatterCorePlugin;
import org.eclipse.php.internal.formatter.core.HtmlFormatterForPHPCode;
import org.eclipse.php.internal.formatter.core.Logger;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.wst.html.core.internal.format.HTMLFormatProcessorImpl;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.undo.IStructuredTextUndoManager;
import org.osgi.service.prefs.Preferences;

public class PHPCodeFormatter implements IContentFormatter, IFormatterProcessorFactory {

	public PHPCodeFormatter() {
	}

	@Override
	public void format(IDocument document, IRegion region) {
		IStructuredTextUndoManager undoManager = null;
		IStructuredModel structuredModel = null;
		try {
			if (document instanceof IStructuredDocument) {
				IStructuredDocument structuredDocument = (IStructuredDocument) document;
				structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(document);

				IProject project = null;
				if (structuredModel != null) {
					project = getProject(structuredModel);
				}
				if (project == null) {
					Logger.logException(new IllegalStateException("Cann't resolve file name")); //$NON-NLS-1$
					return;
				}

				undoManager = structuredDocument.getUndoManager();
				undoManager.beginRecording(this, "php format document", //$NON-NLS-1$
						"format PHP document", 0, document.getLength()); //$NON-NLS-1$

				// html format
				HTMLFormatProcessorImpl htmlFormatter = new HtmlFormatterForPHPCode();
				try {
					htmlFormatter.formatDocument(document, region.getOffset(), region.getLength());
				} catch (Exception e) {
					Logger.logException(e);
				}

				// php format
				PHPVersion version = ProjectOptions.getPHPVersion(project);
				boolean useShortTags = ProjectOptions.useShortTags(project);
				ICodeFormattingProcessor codeFormatterVisitor = getCodeFormattingProcessor(project, document, version,
						useShortTags, region);
				if (codeFormatterVisitor instanceof CodeFormatterVisitor) {
					List<ReplaceEdit> changes = ((CodeFormatterVisitor) codeFormatterVisitor).getChanges();
					if (changes.size() > 0) {
						replaceAll(document, changes, structuredModel);
					}
				}

			} else {
				// TODO: how to handle other document types?
			}

		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			if (undoManager != null) {
				undoManager.endRecording(this);
			}
			if (structuredModel != null) {
				structuredModel.releaseFromRead();
			}
		}
	}

	@Override
	public ICodeFormattingProcessor getCodeFormattingProcessor(IDocument document, PHPVersion phpVersion,
			boolean useShortTags, IRegion region) throws Exception {
		IProject project = getProject(document);
		return getCodeFormattingProcessor(project, document, phpVersion, useShortTags, region);
	}

	private ICodeFormattingProcessor getCodeFormattingProcessor(IProject project, IDocument document,
			PHPVersion phpVersion, boolean useShortTags, IRegion region) throws Exception {
		CodeFormatterPreferences fCodeFormatterPreferences = getPreferences(project);

		ICodeFormattingProcessor codeFormattingProcessor = new CodeFormatterVisitor(document, fCodeFormatterPreferences,
				PHPModelUtils.getLineSeparator(project), phpVersion, useShortTags, region);

		return codeFormattingProcessor;
	}

	private IProject getProject(IDocument document) {
		IProject project = null;
		IStructuredModel structuredModel = null;
		if (document instanceof IStructuredDocument) {
			try {
				structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(document);
				project = getProject(structuredModel);
			} finally {
				if (structuredModel != null) {
					structuredModel.releaseFromRead();
				}
			}
		}
		return project;
	}

	/**
	 * @param doModelForPHP
	 * @return project from document
	 */
	private final IProject getProject(IStructuredModel doModelForPHP) {
		if (doModelForPHP != null) {
			final String id = doModelForPHP.getId();
			if (id != null) {
				final IFile file = getFile(id);
				if (file != null) {
					return file.getProject();
				}
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

	private void replaceAll(IDocument document, List<ReplaceEdit> changes, IStructuredModel domModelForPHP)
			throws MalformedTreeException, BadLocationException {
		TextEdit multiEdit = new MultiTextEdit();
		multiEdit.addChildren(changes.toArray(new ReplaceEdit[0]));

		// On large edits, merge all edits into a single one to speed up
		// the edit processor.
		if (RewriteSessionEditProcessor.isLargeEdit(multiEdit)) {
			IDocument finalDocument = new Document(document.get());
			multiEdit.apply(finalDocument);
			multiEdit = new ReplaceEdit(0, document.getLength(), finalDocument.get());
		}

		Map<String, IDocumentPartitioner> partitioners = null;
		try {
			if (multiEdit.getChildrenSize() > 20) {
				partitioners = TextUtilities.removeDocumentPartitioners(document);
			}
			RewriteSessionEditProcessor editProcessor = new RewriteSessionEditProcessor(document, multiEdit,
					TextEdit.CREATE_UNDO | TextEdit.UPDATE_REGIONS);
			editProcessor.performEdits();
		} finally {
			if (partitioners != null) {
				TextUtilities.addDocumentPartitioners(document, partitioners);
			}
		}
	}

	@Override
	public IFormattingStrategy getFormattingStrategy(String contentType) {
		return null;
	}

	public static CodeFormatterPreferences getPreferences(IProject project) throws Exception {
		IEclipsePreferences node = null;
		if (project != null) {
			ProjectScope scope = new ProjectScope(project);
			node = scope.getNode(FormatterCorePlugin.PLUGIN_ID);
		}
		if (node == null || node.get(CodeFormatterConstants.FORMATTER_PROFILE, null) == null) {
			IScopeContext context = InstanceScope.INSTANCE;
			node = context.getNode(FormatterCorePlugin.PLUGIN_ID);
		}

		Map<String, Object> p = new HashMap<>(CodeFormatterPreferences.getDefaultPreferences().getMap());
		if (node != null && node.keys().length > 0) {
			Set<String> propertiesNames = p.keySet();
			for (Iterator<String> iter = propertiesNames.iterator(); iter.hasNext();) {
				String property = iter.next();
				String value = node.get(property, null);
				if (value != null) {
					p.put(property, value);
				}
			}
		} else {
			IPreferencesService service = Platform.getPreferencesService();
			String[] lookup = service.getLookupOrder(FormatterCorePlugin.PLUGIN_ID, null);
			Preferences[] nodes = new Preferences[lookup.length];
			for (int i = 0; i < lookup.length; i++) {
				nodes[i] = service.getRootNode().node(lookup[i]).node(FormatterCorePlugin.PLUGIN_ID);
			}
			for (String property : p.keySet()) {
				String value = service.get(property, null, nodes);
				if (value != null) {
					p.put(property, value);
				}
			}
		}

		return new CodeFormatterPreferences(p);
	}

}

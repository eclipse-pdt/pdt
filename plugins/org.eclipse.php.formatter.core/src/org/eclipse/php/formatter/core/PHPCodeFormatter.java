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

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.format.ICodeFormattingProcessor;
import org.eclipse.php.internal.core.format.IFormatterProcessorFactory;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.wst.html.core.internal.format.HTMLFormatProcessorImpl;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.undo.IStructuredTextUndoManager;

/**
 * 
 * @author moshe, 2007
 */
public class PHPCodeFormatter implements IContentFormatter,
		IFormatterProcessorFactory {

	private CodeFormatterPreferences fCodeFormatterPreferences = CodeFormatterPreferences
			.getDefaultPreferences();
	private static final Map<String, Object> defaultPrefrencesValues = CodeFormatterPreferences
			.getDefaultPreferences().getMap();

	public PHPCodeFormatter() {
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
		if (node != null
				&& node.keys().length > 0
				&& node.get(PreferenceConstants.FORMATTER_PROFILE, null) != null) {
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

	public void format(IDocument document, IRegion region) {
		IStructuredTextUndoManager undoManager = null;
		IStructuredModel structuredModel = null;
		try {
			if (document instanceof IStructuredDocument) {
				IStructuredDocument structuredDocument = (IStructuredDocument) document;
				structuredModel = StructuredModelManager.getModelManager()
						.getExistingModelForRead(document);
				DOMModelForPHP doModelForPHP = (DOMModelForPHP) structuredModel;

				IProject project = this.project;
				if (doModelForPHP != null) {
					project = getProject(doModelForPHP);
				}
				if (project == null) {
					Logger.logException(new IllegalStateException(
							"Cann't resolve file name")); //$NON-NLS-1$
					return;
				}

				undoManager = structuredDocument.getUndoManager();
				undoManager.beginRecording(this, "php format document", //$NON-NLS-1$
						"format PHP document", 0, document.getLength()); //$NON-NLS-1$

				// html format
				HTMLFormatProcessorImpl htmlFormatter = new HtmlFormatterForPhpCode();

				try {
					htmlFormatter.formatDocument(document);
				} catch (Exception e) {
					// TODO: handle exception
				}
				// php format
				PHPVersion version = ProjectOptions.getPhpVersion(project);
				boolean useShortTags = ProjectOptions.useShortTags(project);
				ICodeFormattingProcessor codeFormatterVisitor = getCodeFormattingProcessor(
						project, document, version, useShortTags, region);
				if (codeFormatterVisitor instanceof CodeFormatterVisitor) {
					List<ReplaceEdit> changes = ((CodeFormatterVisitor) codeFormatterVisitor)
							.getChanges();
					if (changes.size() > 0) {
						replaceAll(document, changes, doModelForPHP);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.core.format.IFormatterProcessorFactory#
	 * getCodeFormattingProcessor(org.eclipse.jface.text.IDocument,
	 * java.lang.Object, java.lang.String, org.eclipse.jface.text.IRegion)
	 */
	public ICodeFormattingProcessor getCodeFormattingProcessor(
			IDocument document, PHPVersion phpVersion, boolean useShortTags,
			IRegion region) throws Exception {
		IProject project = getProject(document);
		return getCodeFormattingProcessor(project, document, phpVersion,
				useShortTags, region);
	}

	private ICodeFormattingProcessor getCodeFormattingProcessor(
			IProject project, IDocument document, PHPVersion phpVersion,
			boolean useShortTags, IRegion region) throws Exception {
		CodeFormatterPreferences fCodeFormatterPreferences = getPreferences(project);
		int oldCommentLength = fCodeFormatterPreferences.comment_line_length;
		boolean forceSplit = fCodeFormatterPreferences.line_wrap_expressions_in_array_init_force_split;
		boolean insertSpaceAfterComma = fCodeFormatterPreferences.insert_space_after_list_comma_in_array;
		boolean insertNewLineBeforeCloseArray = fCodeFormatterPreferences.new_line_before_close_array_parenthesis_array;
		int lineWrapPolicy = fCodeFormatterPreferences.line_wrap_expressions_in_array_init_line_wrap_policy;
		if (isPasting) {
			fCodeFormatterPreferences.comment_line_length = 400;
			fCodeFormatterPreferences.line_wrap_expressions_in_array_init_force_split = true;
			fCodeFormatterPreferences.line_wrap_expressions_in_array_init_line_wrap_policy = CodeFormatterVisitor.WRAP_ALL_ELEMENTS;
			fCodeFormatterPreferences.insert_space_after_list_comma_in_array = false;
			fCodeFormatterPreferences.new_line_before_close_array_parenthesis_array = true;
		}
		ICodeFormattingProcessor codeFormattingProcessor = new CodeFormatterVisitor(
				document, fCodeFormatterPreferences, getLineSeparator(project),
				phpVersion, useShortTags, region);

		if (isPasting) {
			fCodeFormatterPreferences.comment_line_length = oldCommentLength;
			fCodeFormatterPreferences.line_wrap_expressions_in_array_init_force_split = forceSplit;
			fCodeFormatterPreferences.line_wrap_expressions_in_array_init_line_wrap_policy = lineWrapPolicy;
			fCodeFormatterPreferences.insert_space_after_list_comma_in_array = insertSpaceAfterComma;
			fCodeFormatterPreferences.new_line_before_close_array_parenthesis_array = insertNewLineBeforeCloseArray;
		}
		return codeFormattingProcessor;
	}

	private String getLineSeparator(IProject project) {
		String lineSeparator = null;
		if (project != null) {
			lineSeparator = Platform.getPreferencesService().getString(
					Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null,
					new IScopeContext[] { new ProjectScope(project) });
		}
		if (lineSeparator == null) {
			lineSeparator = Platform.getPreferencesService().getString(
					Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null,
					new IScopeContext[] { new InstanceScope() });
		}
		if (lineSeparator == null) {
			lineSeparator = System.getProperty(Platform.PREF_LINE_SEPARATOR);
		}
		return lineSeparator;
	}

	private IProject getProject(IDocument document) {
		IProject project = null;
		IStructuredModel structuredModel = null;
		if (document instanceof IStructuredDocument) {
			try {
				structuredModel = StructuredModelManager.getModelManager()
						.getExistingModelForRead(document);
				DOMModelForPHP doModelForPHP = (DOMModelForPHP) structuredModel;
				project = getProject(doModelForPHP);
			} finally {
				if (structuredModel != null) {
					structuredModel.releaseFromRead();
				}
			}
		}
		return project;
	}

	private void replaceAll(IDocument document, List<ReplaceEdit> changes,
			DOMModelForPHP domModelForPHP) throws BadLocationException {
		// Collect the markers before the content of the document is replaced.
		IFile file = null;
		IMarker[] allMarkers = null;
		if (domModelForPHP != null) {
			file = getFile(domModelForPHP.getId());
			try {
				if (file != null) {
					// collect and then delete
					allMarkers = file.findMarkers(null, true,
							IResource.DEPTH_INFINITE);
				} else {
					return; // no need to save breakpoints when no file was
					// detected
				}
			} catch (CoreException e) {
			}
		}

		// Replace the content of the document
		StringBuffer buffer = new StringBuffer(document.get());
		for (int i = changes.size() - 1; i >= 0; i--) {
			ReplaceEdit replace = (ReplaceEdit) changes.get(i);
			buffer.replace(replace.offset, replace.getEnd(), replace.content);
		}
		document.set(buffer.toString());

		try {
			if (file != null) {
				reinsertMarkers(allMarkers, file);
			}
		} catch (CoreException e) {
		}
	}

	// Return the markers
	// TODO - This is buggy since the lines might be different now.
	private void reinsertMarkers(IMarker[] allMarkers, IFile file)
			throws CoreException {
		final IBreakpointManager breakpointManager = DebugPlugin.getDefault()
				.getBreakpointManager();
		if (allMarkers != null) {
			for (IMarker marker : allMarkers) {
				String markerType = MarkerUtilities.getMarkerType(marker);
				if (markerType != null) {
					IBreakpoint breakpoint = breakpointManager
							.getBreakpoint(marker);
					if (breakpoint != null) {
						IMarker createdMarker = file.createMarker(markerType);
						createdMarker.setAttributes(breakpoint.getMarker()
								.getAttributes());
						breakpointManager.removeBreakpoint(breakpoint, true);
						breakpoint.setMarker(createdMarker);
						breakpointManager.addBreakpoint(breakpoint);
					} else {
						MarkerUtilities.createMarker(file,
								marker.getAttributes(), markerType);
					}
				}
				marker.delete();
			}
		}
	}

	public IFormattingStrategy getFormattingStrategy(String contentType) {
		return null;
	}

	private IProject project;
	private boolean isPasting = false;

	public void setDefaultProject(IProject project) {
		this.project = project;
	}

	public void setIsPasting(boolean isPasting) {
		this.isPasting = isPasting;
	}

	public void format(IDocument document, IRegion region, PHPVersion version) {
		// TODO Auto-generated method stub

		IStructuredTextUndoManager undoManager = null;
		IStructuredModel structuredModel = null;
		try {
			if (document instanceof IStructuredDocument) {
				IStructuredDocument structuredDocument = (IStructuredDocument) document;
				structuredModel = StructuredModelManager.getModelManager()
						.getExistingModelForRead(document);
				DOMModelForPHP doModelForPHP = (DOMModelForPHP) structuredModel;

				IProject project = this.project;
				if (doModelForPHP != null) {
					project = getProject(doModelForPHP);
				}
				if (project == null) {
					Logger.logException(new IllegalStateException(
							"Cann't resolve file name")); //$NON-NLS-1$
					return;
				}

				undoManager = structuredDocument.getUndoManager();
				undoManager.beginRecording(this, "php format document", //$NON-NLS-1$
						"format PHP document", 0, document.getLength()); //$NON-NLS-1$

				// html format
				HTMLFormatProcessorImpl htmlFormatter = new HtmlFormatterForPhpCode();

				try {
					htmlFormatter.formatDocument(document);
				} catch (Exception e) {
					// TODO: handle exception
				}
				// php format
				// PHPVersion version = ProjectOptions.getPhpVersion(project);
				boolean useShortTags = ProjectOptions.useShortTags(project);
				ICodeFormattingProcessor codeFormatterVisitor = getCodeFormattingProcessor(
						project, document, version, useShortTags, region);
				if (codeFormatterVisitor instanceof CodeFormatterVisitor) {
					List<ReplaceEdit> changes = ((CodeFormatterVisitor) codeFormatterVisitor)
							.getChanges();
					if (changes.size() > 0) {
						replaceAll(document, changes, doModelForPHP);
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

}

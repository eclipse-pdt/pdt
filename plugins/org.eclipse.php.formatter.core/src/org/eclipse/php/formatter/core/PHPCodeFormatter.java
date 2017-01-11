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
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.format.ICodeFormattingProcessor;
import org.eclipse.php.internal.core.format.IContentFormatter2;
import org.eclipse.php.internal.core.format.IFormatterProcessorFactory;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.html.core.internal.format.HTMLFormatProcessorImpl;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.undo.IStructuredTextUndoManager;
import org.osgi.service.prefs.Preferences;

/**
 * 
 * @author moshe, 2007
 */
public class PHPCodeFormatter implements IContentFormatter, IContentFormatter2, IFormatterProcessorFactory {

	private CodeFormatterPreferences fCodeFormatterPreferences = CodeFormatterPreferences.getDefaultPreferences();
	private static final Map<String, Object> defaultPrefrencesValues = CodeFormatterPreferences.getDefaultPreferences()
			.getMap();

	public PHPCodeFormatter() {
	}

	private CodeFormatterPreferences getPreferences(IProject project) throws Exception {

		IEclipsePreferences node = null;
		if (project != null) {
			ProjectScope scope = (ProjectScope) new ProjectScope(project);
			node = scope.getNode(FormatterCorePlugin.PLUGIN_ID);
		}

		Map<String, Object> p = new HashMap<String, Object>(defaultPrefrencesValues);
		if (node != null && node.keys().length > 0
				&& node.get(CodeFormatterConstants.FORMATTER_PROFILE, null) != null) {
			Set<String> propetiesNames = p.keySet();
			for (Iterator<String> iter = propetiesNames.iterator(); iter.hasNext();) {
				String property = (String) iter.next();
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

		fCodeFormatterPreferences.setPreferencesValues(p);

		return fCodeFormatterPreferences;
	}

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
				HTMLFormatProcessorImpl htmlFormatter = new HtmlFormatterForPhpCode();
				try {
					htmlFormatter.formatDocument(document, region.getOffset(), region.getLength());
				} catch (Exception e) {
					Logger.logException(e);
				}

				// php format
				PHPVersion version = ProjectOptions.getPhpVersion(project);
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

	/**
	 * @param doModelForPHP
	 * @return project from document
	 */
	private final static IProject getProject(IStructuredModel doModelForPHP) {
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

	private void replaceAll(IDocument document, List<ReplaceEdit> changes, IStructuredModel domModelForPHP)
			throws BadLocationException {
		/*
		 * Disabled because of
		 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=500292
		 */
		// // Collect the markers before the content of the document is
		// replaced.
		// IFile file = null;
		// IMarker[] allMarkers = null;
		// if (domModelForPHP != null) {
		// file = getFile(domModelForPHP.getId());
		// try {
		// if (file != null) {
		// // collect and then delete
		// allMarkers = file.findMarkers(null, true, IResource.DEPTH_INFINITE);
		// } else {
		// return; // no need to save breakpoints when no file was
		// // detected
		// }
		// } catch (CoreException e) {
		// }
		// }

		// Replace the content of the document
		StringBuilder buffer = new StringBuilder(document.get());
		for (int i = changes.size() - 1; i >= 0; i--) {
			ReplaceEdit replace = (ReplaceEdit) changes.get(i);
			buffer.replace(replace.getOffset(), replace.getExclusiveEnd(), replace.getText());
		}
		document.set(buffer.toString());

		/*
		 * Disabled because of
		 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=500292
		 */
		// try {
		// if (file != null) {
		// reinsertMarkers(allMarkers, file);
		// }
		// } catch (CoreException e) {
		// }
	}

	// Return the markers
	// TODO - This is buggy since the lines might be different now.
	@Deprecated
	private void reinsertMarkers(IMarker[] allMarkers, IFile file) throws CoreException {
		final IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
		if (allMarkers != null) {
			for (IMarker marker : allMarkers) {
				String markerType = MarkerUtilities.getMarkerType(marker);
				if (markerType != null) {
					IBreakpoint breakpoint = breakpointManager.getBreakpoint(marker);
					if (breakpoint != null) {
						IMarker createdMarker = file.createMarker(markerType);
						createdMarker.setAttributes(breakpoint.getMarker().getAttributes());
						breakpointManager.removeBreakpoint(breakpoint, true);
						breakpoint.setMarker(createdMarker);
						breakpointManager.addBreakpoint(breakpoint);
					} else {
						MarkerUtilities.createMarker(file, marker.getAttributes(), markerType);
					}
				}
				marker.delete();
			}
		}
	}

	public IFormattingStrategy getFormattingStrategy(String contentType) {
		return null;
	}

	@Deprecated
	public void format(IDocument document, IRegion region, PHPVersion version) {
		// TODO Auto-generated method stub

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
				HTMLFormatProcessorImpl htmlFormatter = new HtmlFormatterForPhpCode();

				try {
					htmlFormatter.formatDocument(document);
				} catch (Exception e) {
					// TODO: handle exception
				}
				// php format
				// PHPVersion version = ProjectOptions.getPhpVersion(project);
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

}

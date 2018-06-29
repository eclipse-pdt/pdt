/*******************************************************************************
 * Copyright (c) 2016, 2018 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.util;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.documentModel.parser.PHPSourceParser;
import org.eclipse.php.internal.core.documentModel.parser.PHPTokenizer;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPHPScriptRegion;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

/**
 * @author blind
 */
public class DocumentModelUtils {
	/**
	 * Only useful if we do a full document reparse afterwards (using methods
	 * below), since we left content of the document PhpScriptRegions untouched.
	 * 
	 * @param document
	 * @param project
	 */
	@SuppressWarnings("null")
	private static void resetDocumentParser(@Nullable IStructuredDocument document, @Nullable IProject project) {
		if (document != null && document.getParser() instanceof PHPSourceParser) {
			PHPSourceParser.projectThreadLocal.set(project);

			PHPSourceParser sourceParser = (PHPSourceParser) document.getParser();
			if (sourceParser.getTokenizer() instanceof PHPTokenizer) {
				PHPTokenizer tokenizer = (PHPTokenizer) sourceParser.getTokenizer();
				tokenizer.setProjectSettings(ProjectOptions.getPHPVersion(project),
						ProjectOptions.isSupportingASPTags(project), ProjectOptions.useShortTags(project));
			}
		}
	}

	/**
	 * Reparse PhpScriptRegions content of document attached to textViewer, assuming
	 * that project settings haven't changed. Also do a document reconcile
	 * afterwards.
	 * 
	 * @param textViewer
	 */
	@Deprecated
	public static void reparseAndReconcileDocument(@NonNull PHPStructuredTextViewer textViewer) {
		IDocument document = textViewer.getDocument();
		if (document instanceof IStructuredDocument) {
			reparseDocument(document);
		}
	}

	/**
	 * Reparse PhpScriptRegions content of document attached to textViewer, by using
	 * new project settings. Also do a document reconcile afterwards. If parameter
	 * project is not null, be sure that the document really belongs to this
	 * project!
	 * 
	 * @param textViewer
	 * @param project
	 */
	public static void reparseAndReconcileDocument(@NonNull PHPStructuredTextViewer textViewer,
			@Nullable IProject project) {
		IDocument document = textViewer.getDocument();
		if (document instanceof IStructuredDocument) {
			reparseDocument(document, project);
		}
	}

	/**
	 * Reparse content of all document PhpScriptRegions, assuming that project
	 * settings haven't changed.
	 * 
	 * @param document
	 */
	@SuppressWarnings("null")
	@Deprecated
	public static void reparseDocument(@Nullable IDocument document) {
		if (!(document instanceof IStructuredDocument)) {
			return;
		}
		IStructuredDocument structuredDocument = (IStructuredDocument) document;

		IStructuredDocumentRegion[] sdRegions = structuredDocument.getStructuredDocumentRegions();
		for (IStructuredDocumentRegion element : sdRegions) {
			Iterator<?> regionsIt = element.getRegions().iterator();
			reparseRegion(document, regionsIt, element.getStartOffset(), null, false, false, false);
		}
	}

	/**
	 * Reparse content of all document PhpScriptRegions, by using new project
	 * settings. If parameter project is not null, be sure that the document really
	 * belongs to this project!
	 * 
	 * @param document
	 * @param project
	 */
	@SuppressWarnings("null")
	public static void reparseDocument(@Nullable IDocument document, @Nullable IProject project) {
		if (!(document instanceof IStructuredDocument)) {
			return;
		}
		IStructuredDocument structuredDocument = (IStructuredDocument) document;

		resetDocumentParser((IStructuredDocument) document, project);

		PHPVersion phpVersion = ProjectOptions.getPHPVersion(project);
		boolean isSupportingASPTags = ProjectOptions.isSupportingASPTags(project);
		boolean useShortTags = ProjectOptions.useShortTags(project);
		IStructuredDocumentRegion[] sdRegions = structuredDocument.getStructuredDocumentRegions();
		for (IStructuredDocumentRegion element : sdRegions) {
			Iterator<?> regionsIt = element.getRegions().iterator();
			reparseRegion(document, regionsIt, element.getStartOffset(), phpVersion, isSupportingASPTags, useShortTags,
					true);
		}
	}

	/**
	 * iterate over regions in case of PhpScriptRegion. In case of region container
	 * iterate over the container regions.
	 * 
	 * @param document
	 *            document
	 * @param regionsIt
	 *            regions iterator
	 * @param offset
	 *            the container region start offset
	 * @param phpVersion
	 *            the new php version to use (only if setNewProject is set to true;
	 *            in this case phpVersion must be non-null)
	 * @param isSupportingASPTags
	 *            the new isSupportingASPTags value to use (only if setNewProject is
	 *            set to true)
	 * @param useShortTags
	 *            the new useShortTags value to use (only if setNewProject is set to
	 *            true)
	 * @param setNewProject
	 *            use the new project settings when setNewProject is set to true
	 */
	@SuppressWarnings("null")
	private static void reparseRegion(@NonNull IDocument document, @NonNull Iterator<?> regionsIt, int offset,
			PHPVersion phpVersion, boolean isSupportingASPTags, boolean useShortTags, boolean setNewProject) {
		assert !(setNewProject && phpVersion == null);
		while (regionsIt.hasNext()) {
			ITextRegion region = (ITextRegion) regionsIt.next();
			if (region instanceof ITextRegionContainer) {
				reparseRegion(document, ((ITextRegionContainer) region).getRegions().iterator(),
						offset + region.getStart(), phpVersion, isSupportingASPTags, useShortTags, setNewProject);
			}
			if (region instanceof IPHPScriptRegion) {
				final IPHPScriptRegion phpRegion = (IPHPScriptRegion) region;
				try {
					if (setNewProject) {
						phpRegion.completeReparse(document, offset + region.getStart(), region.getLength(), phpVersion,
								isSupportingASPTags, useShortTags);
					} else {
						phpRegion.completeReparse(document, offset + region.getStart(), region.getLength());
					}
				} catch (Error e) {
					// catch Error from PhpLexer.zzScanError
					// without doing this, the editor will behavior unnormal
					PHPUiPlugin.log(e);
				}
			}
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
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
	private static void resetDocumentReParser(@Nullable IStructuredDocument document, @Nullable IProject project) {
		if (document != null && document.getReParser() instanceof PhpSourceParser) {
			((PhpSourceParser) document.getReParser()).setProject(project);
		}
	}

	/**
	 * Reparse PhpScriptRegions content of document attached to textViewer. Also
	 * do a document reconcile afterwards.
	 * 
	 * @param textViewer
	 */
	public static void reparseAndReconcileDocument(@NonNull PHPStructuredTextViewer textViewer) {
		IDocument document = textViewer.getDocument();
		if (document instanceof IStructuredDocument) {
			reparseDocument((IStructuredDocument) document);
			textViewer.reconcile();
		}
	}

	/**
	 * Reparse PhpScriptRegions content of document attached to textViewer, by
	 * using new project settings. Also do a document reconcile afterwards. If
	 * parameter project is not null, be sure that the document really belongs
	 * to this project!
	 * 
	 * @param textViewer
	 * @param project
	 */
	public static void reparseAndReconcileDocument(@NonNull PHPStructuredTextViewer textViewer,
			@Nullable IProject project) {
		IDocument document = textViewer.getDocument();
		if (document instanceof IStructuredDocument) {
			reparseDocument((IStructuredDocument) document, project);
			textViewer.reconcile();
		}
	}

	/**
	 * Reparse content of all document PhpScriptRegions.
	 * 
	 * @param document
	 */
	public static void reparseDocument(@Nullable IDocument document) {
		if (!(document instanceof IStructuredDocument)) {
			return;
		}
		IStructuredDocument structuredDocument = (IStructuredDocument) document;

		IStructuredDocumentRegion[] sdRegions = structuredDocument.getStructuredDocumentRegions();
		for (IStructuredDocumentRegion element : sdRegions) {
			Iterator regionsIt = element.getRegions().iterator();
			reparseRegion(document, regionsIt, element.getStartOffset(), null, false);
		}
	}

	/**
	 * Reparse content of all document PhpScriptRegions, by using new project
	 * settings. If parameter project is not null, be sure that the document
	 * really belongs to this project!
	 * 
	 * @param document
	 * @param project
	 */
	public static void reparseDocument(@Nullable IDocument document, @Nullable IProject project) {
		if (!(document instanceof IStructuredDocument)) {
			return;
		}
		IStructuredDocument structuredDocument = (IStructuredDocument) document;

		resetDocumentReParser((IStructuredDocument) document, project);

		IStructuredDocumentRegion[] sdRegions = structuredDocument.getStructuredDocumentRegions();
		for (IStructuredDocumentRegion element : sdRegions) {
			Iterator regionsIt = element.getRegions().iterator();
			reparseRegion(document, regionsIt, element.getStartOffset(), project, true);
		}
	}

	/**
	 * iterate over regions in case of PhpScriptRegion. In case of region
	 * container iterate over the container regions.
	 * 
	 * @param document
	 *            document
	 * @param regionsIt
	 *            regions iterator
	 * @param offset
	 *            the container region start offset
	 * @param project
	 *            the new project to use (only if setNewProject is set to true)
	 * @param setNewProject
	 *            use the new project value (even if null) when setNewProject is
	 *            set to true
	 */
	private static void reparseRegion(@NonNull IDocument document, @NonNull Iterator<?> regionsIt, int offset,
			@Nullable IProject project, boolean setNewProject) {
		while (regionsIt.hasNext()) {
			ITextRegion region = (ITextRegion) regionsIt.next();
			if (region instanceof ITextRegionContainer) {
				reparseRegion(document, ((ITextRegionContainer) region).getRegions().iterator(),
						offset + region.getStart(), project, setNewProject);
			}
			if (region instanceof IPhpScriptRegion) {
				final IPhpScriptRegion phpRegion = (IPhpScriptRegion) region;
				try {
					if (setNewProject) {
						phpRegion.completeReparse(document, offset + region.getStart(), region.getLength(), project);
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

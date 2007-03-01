/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.util.CodeDataResolver;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.hover.PHPCodeHyperLink;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.php.ui.editor.hover.IHyperlinkDetectorForPHP;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

public class PHPCodeHyperlinkDetector implements IHyperlinkDetectorForPHP {

	private static final Pattern QUOTES_PATTERN = Pattern.compile("^[\"\']|[\"\']$");

	/**
	 * Creates a new PHP element hyperlink detector.
	 */
	public PHPCodeHyperlinkDetector() {
	}

	private boolean isIncludeType(String regionType) {
		return PHPRegionTypes.PHP_INCLUDE.equals(regionType) || PHPRegionTypes.PHP_INCLUDE_ONCE.equals(regionType) || PHPRegionTypes.PHP_REQUIRE.equals(regionType) || PHPRegionTypes.PHP_REQUIRE_ONCE.equals(regionType);
	}

	/*
	 * @see org.eclipse.jface.text.hyperlink.IHyperlinkDetector#detectHyperlinks(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion, boolean)
	 */
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		if (region == null || canShowMultipleHyperlinks)
			return null;

		try {
			IDocument document = textViewer.getDocument();
			if (document instanceof IStructuredDocument) {

				// Find the PHP token region:
				IStructuredDocument sDoc = (IStructuredDocument) document;
				IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(region.getOffset());
				ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(region.getOffset());

				ITextRegionCollection container = sdRegion;
				if (textRegion instanceof ITextRegionContainer) {
					container = (ITextRegionContainer) textRegion;
					textRegion = container.getRegionAtCharacterOffset(region.getOffset());
				}

				if (textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
					PhpScriptRegion phpScriptRegion = (PhpScriptRegion) textRegion;
					ITextRegion phpToken = phpScriptRegion.getPhpToken(region.getOffset() - container.getStartOffset() - phpScriptRegion.getStart());

					// Check whether the current token is string, if it is - try to resolve file name from this string:
					if (PHPPartitionTypes.isPHPQuotesState(phpToken.getType())) {

						// Skip left parenthesis:
						ITextRegion prevRegion = phpScriptRegion.getPhpToken(phpToken.getStart() - 1);
						if (prevRegion != null) {

							// Check whether this string belongs to some kind of include/require structure:
							prevRegion = phpScriptRegion.getPhpToken(prevRegion.getStart() - 1);
							if (prevRegion != null && isIncludeType(prevRegion.getType())) {

								int startOffset = container.getStartOffset() + phpScriptRegion.getStart() + phpToken.getStart();
								int offsetLength = phpToken.getLength();

								// Get the string contents - it's an included file name:
								String fileName = sDoc.get(startOffset, offsetLength);

								// Trim quotes around the filename:
								Matcher quotesMatcher = QUOTES_PATTERN.matcher(fileName);
								fileName = quotesMatcher.replaceAll("");

								// Find the file in the same project:
								IPath path = new Path(fileName);
								IWorkbenchPage page = PHPUiPlugin.getActivePage();
								if (page != null) {
									IEditorPart editor = page.getActiveEditor();
									final PHPStructuredEditor phpEditor = EditorUtility.getPHPStructuredEditor(editor);
									if (phpEditor != null) {
										IFile currentFile = phpEditor.getFile();
										IProject currentProject = currentFile.getProject();
										IFile file = currentProject.getFile(path);
										if (file != null && file.exists()) {
											return new IHyperlink[] { new WorkspaceFileHyperlink(new Region(startOffset, offsetLength), file) };
										}
									}
								}

								// Look for the file in other projects:
								IWorkspace workspace = ResourcesPlugin.getWorkspace();
								IWorkspaceRoot root = workspace.getRoot();
								IProject[] projects = root.getProjects();
								for (int i = 0; i < projects.length; ++i) {
									IFile file = projects[i].getFile(path);
									if (file != null && file.exists()) {
										return new IHyperlink[] { new WorkspaceFileHyperlink(new Region(startOffset, offsetLength), file) };
									}
								}

								// Try to open external file:
								File file = new File(fileName);
								if (file.exists()) {
									return new IHyperlink[] { new ExternalFileHyperlink(new Region(startOffset, offsetLength), file) };
								}
							}
						}
					} else { // The current token is not a string - resolve this PHP element
						CodeData[] codeDatas = CodeDataResolver.getInstance().resolve(sDoc, region.getOffset());
						if (codeDatas != null && codeDatas.length != 0) {
							return new IHyperlink[] { new PHPCodeHyperLink(selectWord(document, region.getOffset()), codeDatas) };
						}
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private IRegion selectWord(IDocument document, int anchor) {
		try {
			int offset = anchor;
			char c;

			while (offset >= 0) {
				c = document.getChar(offset);
				if (!Character.isJavaIdentifierPart(c))
					break;
				--offset;
			}

			int start = offset;

			offset = anchor;
			int length = document.getLength();

			while (offset < length) {
				c = document.getChar(offset);
				if (!Character.isJavaIdentifierPart(c))
					break;
				++offset;
			}

			int end = offset;

			if (start == end)
				return new Region(start, 0);
			return new Region(start + 1, end - start - 1);

		} catch (BadLocationException x) {
			return null;
		}
	}
}

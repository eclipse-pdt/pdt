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
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.hover.PHPCodeHyperLink;
import org.eclipse.php.internal.ui.util.CodeDataResolver;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.text.rules.StructuredTextPartitioner;

public class PHPCodeHyperlinkDetector implements IHyperlinkDetector {

	private static final Pattern QUOTES_PATTERN = Pattern.compile("^[\"\']|[\"\']$");
	private StructuredTextPartitioner fTextPartitioner;

	/**
	 * Creates a new PHP element hyperlink detector.
	 */
	public PHPCodeHyperlinkDetector() {
		fTextPartitioner = new PHPStructuredTextPartitioner();
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
				IStructuredDocument sDoc = (IStructuredDocument) document;
				IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(region.getOffset());
				ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(region.getOffset());
				if (PHPPartitionTypes.PHP_QUOTED_STRING.equals(fTextPartitioner.getPartitionType(textRegion, region.getOffset()))) {
					ITextRegion prevRegion = sdRegion.getRegionAtCharacterOffset(sdRegion.getStartOffset() + textRegion.getStart() - 1);
					if (prevRegion != null) {
						prevRegion = sdRegion.getRegionAtCharacterOffset(sdRegion.getStartOffset() + prevRegion.getStart() - 1);
						if (prevRegion != null && isIncludeType(prevRegion.getType())) {
							String fileName = sdRegion.getText(textRegion);
							Matcher quotesMatcher = QUOTES_PATTERN.matcher(fileName);
							fileName = quotesMatcher.replaceAll("");
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
										return new IHyperlink[] { new WorkspaceFileHyperlink(new Region(sdRegion.getStartOffset(textRegion), textRegion.getTextLength()), file) };
									}
								}
							}
							// look into other projects
							IWorkspace workspace = ResourcesPlugin.getWorkspace();
							IWorkspaceRoot root = workspace.getRoot();
							IProject[] projects = root.getProjects();
							for (int i = 0; i < projects.length; ++i) {
								IFile file = projects[i].getFile(path);
								if (file != null && file.exists()) {
									return new IHyperlink[] { new WorkspaceFileHyperlink(new Region(sdRegion.getStartOffset(textRegion), textRegion.getTextLength()), file) };
								}
							}
							
							// Try to open external file:
							File file = new File(fileName);
							if (file.exists()) {
								return new IHyperlink[] { new ExternalFileHyperlink(new Region(sdRegion.getStartOffset(textRegion), textRegion.getTextLength()), file) };
							}
						}
					}
				} else {
					if (sdRegion.getStartOffset() + textRegion.getTextEnd() >= region.getOffset()) {
						CodeData codeData = CodeDataResolver.getCodeData(textViewer, sdRegion.getStartOffset() + textRegion.getTextEnd());
						if (codeData != null && codeData.isUserCode()) {
							return new IHyperlink[] { new PHPCodeHyperLink(selectWord(document, region.getOffset()), codeData) };
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
			else
				return new Region(start + 1, end - start - 1);

		} catch (BadLocationException x) {
			return null;
		}
	}
}

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
package org.eclipse.php.internal.ui.doubleclick;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultTextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.w3c.dom.Node;

/**
 * This class was added in order to solve the problem of selecting variable name in the editor.
 * The default behaviour when double-clicking a variable, for instance $myVar, is to make only myVar selected - without the dollar sign.
 * This class also fixes selection of PHPdoc tags.
 * This class fixes this behaviour.
 * 
 * @author guy.g
 *
 */
public class PHPDoubleClickStrategy extends DefaultTextDoubleClickStrategy {

	public void doubleClicked(ITextViewer textViewer) {
		if (textViewer instanceof StructuredTextViewer) {
			StructuredTextViewer structuredTextViewer = (StructuredTextViewer) textViewer;
			IStructuredModel structuredModel = null;
			try {
				structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(structuredTextViewer.getDocument());
				if (structuredModel != null) {
					int caretPosition = textViewer.getSelectedRange().x;
					if (caretPosition > 0) {
						Node node = (Node) structuredModel.getIndexedRegion(caretPosition);
						if (node != null) {
							IStructuredDocumentRegion sdRegion = structuredModel.getStructuredDocument().getRegionAtCharacterOffset(caretPosition);
							if (sdRegion != null) {
								ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(caretPosition);

								// We should always hit the PhpScriptRegion:
								if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
									PhpScriptRegion phpScriptRegion = (PhpScriptRegion) tRegion;
									tRegion = phpScriptRegion.getPhpToken(caretPosition - sdRegion.getStartOffset() - phpScriptRegion.getStart());

									// Handle double-click on PHPDoc tags:
									if (PHPPartitionTypes.isPHPDocTagState(tRegion.getType())) {
										structuredTextViewer.setSelectedRange(sdRegion.getStartOffset() + phpScriptRegion.getStart() + tRegion.getStart(), tRegion.getTextLength());
										return; // Stop processing
									}
									
									
									// Handle double-click on variable:
									if (tRegion.getType() == PHPRegionTypes.PHP_VARIABLE) {
										int varStart = sdRegion.getStartOffset() + phpScriptRegion.getStart() + tRegion.getStart();
										int varLength = tRegion.getTextLength();
										
										// Check whether the variable is of kind "$var->name":
										ITextRegion nextRegion = phpScriptRegion.getPhpToken(tRegion.getEnd());
										if (nextRegion != null && nextRegion.getType() == PHPRegionTypes.PHP_OBJECT_OPERATOR) {
											nextRegion = phpScriptRegion.getPhpToken(nextRegion.getEnd());
											if (nextRegion != null && nextRegion.getType() == PHPRegionTypes.PHP_STRING) {
												varLength = nextRegion.getEnd() - tRegion.getStart();
											}
										}
										structuredTextViewer.setSelectedRange(varStart, varLength);
										return; // Stop processing
									}
									
									// Handle double-click on the name of variable construction of type "$var->name":
									if (tRegion.getType() == PHPRegionTypes.PHP_STRING) {
										tRegion = phpScriptRegion.getPhpToken(tRegion.getStart() - 1);
									}
									// Handle double-click on the operator of variable construction of type "$var->name":
									if (tRegion.getType() == PHPRegionTypes.PHP_OBJECT_OPERATOR) {
										ITextRegion prevRegion = phpScriptRegion.getPhpToken(tRegion.getStart() - 1);
										ITextRegion nextRegion = phpScriptRegion.getPhpToken(tRegion.getEnd());
										if (prevRegion != null && prevRegion.getType() == PHPRegionTypes.PHP_VARIABLE && nextRegion != null && nextRegion.getType() == PHPRegionTypes.PHP_STRING) {
											structuredTextViewer.setSelectedRange(sdRegion.getStartOffset() + phpScriptRegion.getStart() + prevRegion.getStart(), nextRegion.getEnd() - prevRegion.getStart());
											return; // Stop processing
										}
									}
								}
							}
						}
					}
				}
			} catch (BadLocationException e) {
				PHPUiPlugin.log(e);
			} finally {
				if (structuredModel != null) {
					structuredModel.releaseFromRead();
				}
			}
		}

		// We reach here only if there was an error or one of conditions hasn't met our requirements:
		super.doubleClicked(textViewer);
	}
}

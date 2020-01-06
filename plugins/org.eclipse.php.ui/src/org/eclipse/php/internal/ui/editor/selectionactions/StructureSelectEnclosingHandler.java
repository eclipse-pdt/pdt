/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.selectionactions;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.Region;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.corext.dom.Selection;
import org.eclipse.php.internal.core.corext.dom.SelectionAnalyzer;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.xml.ui.internal.handlers.StructuredSelectEnclosingXMLHandler;

public class StructureSelectEnclosingHandler extends StructuredSelectEnclosingXMLHandler {
	private ISourceModule sourceModule = null;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		sourceModule = StructureSelectUtil.getSourceModule(event);
		super.execute(event);
		sourceModule = null;

		return null;
	}

	protected Region getNewSelectionRegion(IndexedRegion indexedRegion, ITextSelection textSelection) {
		if (sourceModule != null && StructureSelectUtil.isPHP(indexedRegion)) {
			Selection selection = Selection.createFromStartLength(textSelection.getOffset(), textSelection.getLength());
			SelectionAnalyzer selAnalyzer = new SelectionAnalyzer(selection, true);
			try {
				Program ast = StructureSelectUtil.getAST(sourceModule);
				ast.accept(selAnalyzer);
				ASTNode first = selAnalyzer.getFirstSelectedNode();

				if (first == null || first.getParent() == null)
					return StructureSelectUtil.getLastCoveringNodeRange(
							new Region(textSelection.getOffset(), textSelection.getLength()), sourceModule,
							selAnalyzer);

				return StructureSelectUtil.getSelectedNodeSourceRange(sourceModule, first.getParent());
			} catch (ModelException e) {
			} catch (IOException e) {
			}
		}
		return super.getNewSelectionRegion(indexedRegion, textSelection);
	}

}
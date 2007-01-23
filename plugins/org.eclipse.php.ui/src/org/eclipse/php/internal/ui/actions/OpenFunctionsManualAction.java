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
package org.eclipse.php.internal.ui.actions;

import java.util.ResourceBundle;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFunctionData;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.CodeDataResolver;
import org.eclipse.php.internal.ui.util.PHPManualFactory;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.ui.texteditor.TextEditorAction;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

public class OpenFunctionsManualAction extends TextEditorAction implements IUpdate {

	private PHPFunctionData functionData;

	public OpenFunctionsManualAction(ResourceBundle resourceBundle, PHPStructuredEditor editor) {
		super(resourceBundle, "OpenFunctionsManualAction_", editor);
		this.setEnabled(true);
	}

	public void run() {
		if (!isEnabled()) {
			return;
		}
		if (validAction()) {
			PHPManualFactory.getManual().showFunctionHelp(functionData.getName());
			return;
		}
	}

	protected ITextSelection getCurrentSelection() {
		ITextEditor editor= getTextEditor();
		if (editor == null) {
			return null;
		}
		ISelectionProvider provider= editor.getSelectionProvider();
		if (provider == null) {
			return null;
		}
		ISelection selection= provider.getSelection();
		if (selection instanceof ITextSelection) {
			return (ITextSelection) selection;
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.TextEditorAction#update()
	 */
	public void update() {
		super.update();
		
		if (validAction()) {
			setEnabled(true);
		}else {
			setEnabled(false);
		}
	}
	
	private boolean validAction() {
			
		ITextEditor editor= getTextEditor();
		if (editor == null) {
			return false;
		}
			
		
		IDocumentProvider docProvider= editor.getDocumentProvider();
		IEditorInput input= editor.getEditorInput();
		if (docProvider == null || input == null) {
			return false;
		}
		
		IDocument document= docProvider.getDocument(input);
		if (document == null) {
			return false;
		}

		if (!(document instanceof IStructuredDocument)) {
			return false;
		}
		
		ITextSelection currentSelection = getCurrentSelection();
		int offset = currentSelection.getOffset();
		IStructuredDocument structuredDocument = (IStructuredDocument)document;
		String partitionType;
		try {
			partitionType = structuredDocument.getPartition(offset).getType();
		} catch (BadLocationException e1) {
			Logger.logException(e1);
			return false;
		}
		if (!partitionType.equals(PHPPartitionTypes.PHP_DEFAULT)) {
			return false;
		}
		
		IStructuredDocumentRegion structuredDocumentRegion = structuredDocument.getRegionAtCharacterOffset(offset);
		
		ITextRegion textRegion = structuredDocumentRegion.getRegionAtCharacterOffset(offset);
		if (textRegion == null) {
			return false;
		}
		
		offset = textRegion.getEnd() + structuredDocumentRegion.getStartOffset();
		
		StructuredTextEditor structuredTextEditor = (StructuredTextEditor)editor;
		CodeData codeData;
		try {
			codeData = CodeDataResolver.getCodeData(structuredTextEditor.getTextViewer(), offset);
		} catch (BadLocationException e) {
			Logger.logException(e);
			return false;
		}
		if (codeData == null) {
			return false;
		}
		if (codeData.getUserData() != null) {
			return false;
		}

		if (codeData.isUserCode()) {
			return false;
		}

		if (!(codeData instanceof PHPFunctionData)) {
			return false;
		}
		
		functionData = (PHPFunctionData)codeData;
		return true;
	}

}

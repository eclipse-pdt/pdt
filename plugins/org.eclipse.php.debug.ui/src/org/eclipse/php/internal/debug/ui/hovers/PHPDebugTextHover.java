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
package org.eclipse.php.internal.debug.ui.hovers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.zend.debugger.DefaultExpressionsManager;
import org.eclipse.php.internal.debug.core.zend.debugger.Expression;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPStackFrame;
import org.eclipse.php.internal.ui.editor.hover.AbstractPHPTextHover;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

public class PHPDebugTextHover extends AbstractPHPTextHover {

	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		PHPDebugTarget debugTarget = getDebugTarget();
		if (debugTarget == null || textViewer == null || textViewer.getDocument() == null) {
			return null;
		}
		int offset = hoverRegion.getOffset();
		IStructuredDocumentRegion flatNode = ((IStructuredDocument) textViewer.getDocument()).getRegionAtCharacterOffset(offset);
		ITextRegion region = null;
		if (flatNode != null) {
			region = flatNode.getRegionAtCharacterOffset(offset);
		}

		ITextRegionCollection container = flatNode;
		if (region instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) region;
			region = container.getRegionAtCharacterOffset(offset);
		}

		if (region.getType() == PHPRegionContext.PHP_CONTENT) {
			IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) region;
			try {
				region = phpScriptRegion.getPhpToken(offset - container.getStartOffset() - region.getStart());
			} catch (BadLocationException e) {
				region = null;
			}

			if (region != null) {
				String regionType = region.getType();
				if (regionType == PHPRegionTypes.PHP_VARIABLE) {
					String variable = null;
					try {
						int[] variableRange = getVariableRange(textViewer, hoverRegion.getOffset(), hoverRegion.getLength());
						variable = textViewer.getDocument().get(variableRange[0], variableRange[1]);
						variable = "<B>" + variable + " = </B>" + getValue(debugTarget, variable);
					} catch (BadLocationException e) {
						Logger.logException("Error retrieving the value\n", e);
					}
					return variable;
				}
			}
		}
		return null;
	}

	/**
	 * In case the user selected a text in the document and then hover over it, we would like to evaluate the selected text and not
	 * only the hover region that is under the mouse pointer. 
	 * In this case a we check for the selected text area and if the hover region contains in the selection, we evaluate the entire selection.
	 * In case that we hover over a different code area, the original hover region is used for the evaluation.
	 * 
	 * Note that this kind of behavior allows evaluation of arrays content such as $array[0] evaluation.
	 * 
	 * @param textViewer
	 * @param offset The original hover region offset.
	 * @param length The original hover region length.
	 * @return An array of integers that contains the offset and the length of the evaluation request.
	 */
	protected int[] getVariableRange(final ITextViewer textViewer, final int offset, final int length) {
		final int[] variableRange = new int[] { offset, length };
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				TextSelection selection = (TextSelection) textViewer.getSelectionProvider().getSelection();
				if (selection.isEmpty()) {
					return;
				}
				// Check if the selection contains the hover region
				int selectionStart = selection.getOffset();
				int selectionEnd = selectionStart + selection.getLength();
				int hoverRegionEnd = offset + length;
				if (offset >= selectionStart && offset < selectionEnd && hoverRegionEnd <= selectionEnd) {
					variableRange[0] = selection.getOffset();
					variableRange[1] = selection.getLength();
				}
			}
		});
		return variableRange;
	}

	/**
	 * Returns the variable value.
	 * 
	 * @param variable The variable name
	 * @return
	 */
	protected String getValue(PHPDebugTarget debugTarget, String variable) {
		DefaultExpressionsManager expressionManager = debugTarget.getExpressionManager();
		Expression expression = expressionManager.buildExpression(variable);

		// Get the value from the debugger
		debugTarget.getExpressionManager().getExpressionValue(expression, 1);
		expressionManager.update(expression, 1);
		String value = expression.getValue().getValueAsString();

		if (value != null && value.length() == 0) {
			value = "Empty";
			return value;
		}

		if (value != null) {
			tab.matcher(value).replaceAll("    ");
		}

		return value;
	}

	// Returns the php debug target that is in contex. 
	// In case that 
	protected PHPDebugTarget getDebugTarget() {
		try {
			IAdaptable adaptable = DebugUITools.getDebugContext();
			if (adaptable instanceof PHPStackFrame) {
				PHPStackFrame stackFrame = (PHPStackFrame) adaptable;
				IEditorInput ei = getEditorPart().getEditorInput();
				if (ei instanceof FileEditorInput) {
					FileEditorInput fi = (FileEditorInput) getEditorPart().getEditorInput();
					String launchProjectName = stackFrame.getLaunch().getLaunchConfiguration().getAttribute(IPHPConstants.PHP_Project, (String) null);
					String fileProjectName = '/' + fi.getFile().getProject().getName();

					// First, check if the project name is the same.
					if (!launchProjectName.equals(fileProjectName)) {
						return null;
					}
					// Check for the file path within the project
					String fileInDebug = stackFrame.getSourceName();
					String fileInProject = fi.getFile().getProjectRelativePath().toString();
					if (fileInDebug != null && fileInDebug.endsWith('/' + fileInProject) || fileInDebug.equals(fileInProject)) {
						PHPDebugTarget debugTarget = (PHPDebugTarget) stackFrame.getDebugTarget();
						return debugTarget;
					}
				} else {
					// File on the include Path
					PHPDebugTarget debugTarget = (PHPDebugTarget) stackFrame.getDebugTarget();
					return debugTarget;
				}

			}
		} catch (CoreException e) {
			Logger.logException("Error retrieving the PHPDebugTarget.\n", e);
		}
		return null;
	}
}

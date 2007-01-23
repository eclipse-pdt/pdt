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
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.debugger.DefaultExpressionsManager;
import org.eclipse.php.internal.debug.core.debugger.Expression;
import org.eclipse.php.internal.debug.core.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.model.PHPStackFrame;
import org.eclipse.php.internal.ui.editor.hover.AbstractPHPTextHover;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

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
		if (region != null) {
			String regionType = region.getType();
			if (regionType.equalsIgnoreCase(PHPRegionTypes.PHP_VARIABLE)) {
				String variable = null;
				try {
					variable = textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
					variable = "<B>" + variable + " = </B>" + getValue(debugTarget, variable);
				} catch (BadLocationException e) {
					Logger.logException("Error retrieving the value\n", e);
				}
				return variable;
			}
		}
		return null;
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
                if (ei instanceof FileEditorInput){
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

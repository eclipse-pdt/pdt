/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.hovers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.dltk.internal.ui.text.hover.AbstractScriptEditorTextHover;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpEvalVariable;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStackFrame;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStackVariable;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.protocol.DBGpResponse;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.wst.sse.core.internal.provisional.text.*;
import org.w3c.dom.Node;

public class XDebugTextHover extends AbstractScriptEditorTextHover implements IPHPTextHover {

	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (hoverRegion == null || textViewer == null || textViewer.getDocument() == null) {
			return null;
		}

		IAdaptable adaptable = DebugUITools.getDebugContext();
		if (adaptable instanceof DBGpStackFrame) {
			DBGpStackFrame context = (DBGpStackFrame) adaptable;

			int offset = hoverRegion.getOffset();
			IStructuredDocumentRegion flatNode = ((IStructuredDocument) textViewer.getDocument())
					.getRegionAtCharacterOffset(offset);
			ITextRegion region = null;
			if (flatNode != null) {
				region = flatNode.getRegionAtCharacterOffset(offset);
			}

			ITextRegionCollection container = flatNode;
			if (region instanceof ITextRegionContainer) {
				container = (ITextRegionContainer) region;
				region = container.getRegionAtCharacterOffset(offset);
			}

			if (region != null && container != null && region.getType() == PHPRegionContext.PHP_CONTENT) {
				IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) region;
				try {
					region = phpScriptRegion.getPhpToken(offset - container.getStartOffset() - region.getStart());
				} catch (BadLocationException e) {
					region = null;
				}

				if (region != null) {
					String regionType = region.getType();
					if (regionType == PHPRegionTypes.PHP_VARIABLE || regionType == PHPRegionTypes.PHP_ENCAPSED_VARIABLE
							|| regionType == PHPRegionTypes.PHP_THIS) {
						String variable = null;
						try {
							variable = textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
							if (variable != null) {
								variable = variable.trim();
								if (regionType == PHPRegionTypes.PHP_ENCAPSED_VARIABLE) {
									variable = "$" + variable; //$NON-NLS-1$
								}
								variable = "<B>" + variable + " = </B>" + getByProperty(context, variable); //$NON-NLS-1$ //$NON-NLS-2$
							}
						} catch (BadLocationException e) {
							Logger.logException("Error retrieving the value\n", e); //$NON-NLS-1$
						}
						return variable;
					}
				}
			}
		}
		return null;
	}

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}

	/**
	 * Returns the variable value.
	 * 
	 * @param expression
	 *            The variable name
	 * @return
	 */
	protected String getByEval(DBGpTarget debugTarget, String expression) {
		String value = null;
		Node resp = debugTarget.eval(expression);
		if (resp == null) {
			return ""; //$NON-NLS-1$
		}
		IVariable tempVar = new DBGpEvalVariable(debugTarget, expression, resp);
		IValue val = null;
		try {
			val = tempVar.getValue();
			if (val != null) {
				value = val.getValueString();
			}
		} catch (DebugException e) {
		}
		if (value != null && value.length() == 0) {
			value = PHPDebugUIMessages.XDebugHover_empty;
			return value;
		}
		if (value != null) {
			value = value.replaceAll("\t", "    ").replaceAll("&", "&amp;") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					.replaceAll("<", "&lt;").replaceAll(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		return value;
	}

	protected String getByProperty(DBGpStackFrame context, String variable) {
		String value = null;
		DBGpTarget debugTarget = (DBGpTarget) context.getDebugTarget();
		String stackLevel = context.getStackLevel();
		Node resp = debugTarget.getProperty(variable, stackLevel, 0);
		if (resp == null || DBGpResponse.REASON_ERROR.equals(resp.getNodeName())) {
			// Check if it is not super global property
			stackLevel = "-1"; //$NON-NLS-1$
			resp = debugTarget.getProperty(variable, stackLevel, 0);
		}
		if (resp == null) {
			return ""; //$NON-NLS-1$
		}
		IVariable tempVar = new DBGpStackVariable(debugTarget, resp, Integer.valueOf(stackLevel));
		IValue val = null;
		try {
			val = tempVar.getValue();
			if (val != null) {
				value = val.getValueString();
			}
		} catch (DebugException e) {
		}
		if (value != null && value.length() == 0) {
			value = PHPDebugUIMessages.XDebugHover_empty;
			return value;
		}
		if (value != null) {
			value = value.replaceAll("\t", "    ").replaceAll("&", "&amp;") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					.replaceAll("<", "&lt;").replaceAll(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		return value;
	}

}

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
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStackFrame;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpTarget;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpVariable;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.wst.sse.core.internal.provisional.text.*;
import org.w3c.dom.Node;

public class XDebugTextHover extends AbstractScriptEditorTextHover implements
		IPHPTextHover {

	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (hoverRegion == null || textViewer == null
				|| textViewer.getDocument() == null) {
			return null;
		}

		IAdaptable adaptable = DebugUITools.getDebugContext();
		if (adaptable instanceof DBGpStackFrame) {
			DBGpStackFrame context = (DBGpStackFrame) adaptable;

			int offset = hoverRegion.getOffset();
			IStructuredDocumentRegion flatNode = ((IStructuredDocument) textViewer
					.getDocument()).getRegionAtCharacterOffset(offset);
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
					region = phpScriptRegion.getPhpToken(offset
							- container.getStartOffset() - region.getStart());
				} catch (BadLocationException e) {
					region = null;
				}

				if (region != null) {
					String regionType = region.getType();
					if (regionType == PHPRegionTypes.PHP_VARIABLE) {
						String variable = null;
						try {
							variable = textViewer.getDocument().get(
									hoverRegion.getOffset(),
									hoverRegion.getLength());
							if (variable != null) {
								variable = variable.trim();
								variable = "<B>" + variable + " = </B>" + getPropertyValue(context, variable); //$NON-NLS-1$ //$NON-NLS-2$
							}
						} catch (BadLocationException e) {
							Logger.logException(
									"Error retrieving the value\n", e); //$NON-NLS-1$
						}
						return variable;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Returns the variable value.
	 * 
	 * @param variable
	 *            The variable name
	 * @return
	 */
	protected String getValueByEval(DBGpTarget debugTarget, String variable) {
		String value = null;
		Node resp = debugTarget.eval(variable); // note this is a synchronous
												// call
		if (resp == null) {
			return ""; //$NON-NLS-1$
		}
		IVariable tempVar = new DBGpVariable(debugTarget, resp, "-2"); //$NON-NLS-1$

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
			// value = value.replaceAll("\n", "<br>");
		}

		return value;
	}

	protected String getPropertyValue(DBGpStackFrame context, String variable) {
		String value = null;
		DBGpTarget debugTarget = (DBGpTarget) context.getDebugTarget();
		Node resp = debugTarget.getProperty(variable, context.getStackLevel(),
				0);
		if (resp == null) {
			return ""; //$NON-NLS-1$
		}
		IVariable tempVar = new DBGpVariable(debugTarget, resp, "-2"); //$NON-NLS-1$

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
			// value = value.replaceAll("\n", "<br>");
		}
		return value;
	}

	public IHoverMessageDecorator getMessageDecorator() {
		return null;
	}
}

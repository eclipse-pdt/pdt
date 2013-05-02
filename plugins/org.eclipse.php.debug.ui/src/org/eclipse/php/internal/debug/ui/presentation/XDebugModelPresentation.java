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
package org.eclipse.php.internal.debug.ui.presentation;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpStackFrame;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.model.DBGpThread;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;

/**
 * Renders PHP debug elements
 */
public class XDebugModelPresentation extends PHPModelPresentation implements
		IDebugModelPresentation {

	protected String getStackFrameText(IStackFrame frame) {
		if (frame instanceof DBGpStackFrame) {
			try {
				// Fix bug #160443 (Stack frames line numbers update).
				// Synchronize the top frame with the given values.
				DBGpThread thread = (DBGpThread) frame.getThread();
				DBGpStackFrame topFrame = (DBGpStackFrame) thread
						.getTopStackFrame();
				if (topFrame != null && topFrame.equals(frame)) {
					frame = topFrame;
				} // end fix

				StringBuffer buffer = new StringBuffer();
				String frameName = frame.getName();
				if (frameName != null && frameName.length() > 0) {
					buffer.append(frame.getName());
					//					buffer.append("() "); 
				} else {
					buffer.append(((DBGpStackFrame) frame).getSourceName());
					buffer.append(PHPDebugUIMessages.MPresentation_ATLine_1
							+ (frame.getLineNumber()));
				}
				return buffer.toString();

			} catch (DebugException e) {
				Logger.logException(e);
			}
		}
		return ""; //$NON-NLS-1$

	}
}

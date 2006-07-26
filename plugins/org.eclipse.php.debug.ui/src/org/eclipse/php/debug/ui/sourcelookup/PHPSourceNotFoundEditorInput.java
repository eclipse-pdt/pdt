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
package org.eclipse.php.debug.ui.sourcelookup;

import java.text.MessageFormat;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.php.debug.core.sourcelookup.PHPSourceNotFoundInput;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * Editor input for a stack frame for which source could not be located.
 * 
 */
public class PHPSourceNotFoundEditorInput extends PlatformObject implements IEditorInput {

	/**
	 * Associated stack frame
	 */
	private IStackFrame fFrame;

	/**
	 * Stack frame text (cached on creation)
	 */
	private String fFrameText;
	private String fTooltipText;

	/**
	 * Constructs an editor input for the given stack frame,
	 * to indicate source could not be found.
	 * A default tooltip and text will appear in the editor. 
	 * 
	 * @param frame stack frame
	 * @see #PHPSourceNotFoundEditorInput(PHPSourceNotFoundInput, String)
	 */
	public PHPSourceNotFoundEditorInput(PHPSourceNotFoundInput input) {
		fFrame = input.getStackFrame();
		IDebugModelPresentation pres = DebugUITools.newDebugModelPresentation(fFrame.getModelIdentifier());
		fFrameText = pres.getText(fFrame);
		pres.dispose();
	}

	/**
	 * Constructs an editor input for the given stack frame,
	 * to indicate source could not be found.
	 * 
	 * @param frame stack frame
	 * @param tooltipText The text that will appear in the editor.
	 */
	public PHPSourceNotFoundEditorInput(PHPSourceNotFoundInput input, String tooltipText) {
		fFrame = input.getStackFrame();
		IDebugModelPresentation pres = DebugUITools.newDebugModelPresentation(fFrame.getModelIdentifier());
		fFrameText = pres.getText(fFrame);
		pres.dispose();
		fTooltipText = tooltipText;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		return false;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return DebugUITools.getDefaultImageDescriptor(fFrame);
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		try {
			return fFrame.getName();
		} catch (DebugException e) {
			return PHPDebugUIMessages.SourceNotFoundEditorInput_Source_Not_Found_1; //$NON-NLS-1$
		}
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		return null;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		if (fTooltipText == null) {
			return MessageFormat.format(PHPDebugUIMessages.SourceNotFoundEditorInput_Source_not_found_for__0__2, new String[] { fFrameText }); //$NON-NLS-1$
		}
		return fTooltipText;
	}

}

/*******************************************************************************
 * Copyright (c) 2005, 2009 Zend Technologies.
 * All rights reserved. This program and the accompanying materials
 * are the copyright of Zend Technologies and is protected under
 * copyright laws of the United States.
 * You must not copy, adapt or redistribute this document for 
 * any use. 
 *
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.breakpoint;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IWorkbenchPart;

public class PHPToggleBreakpointsTargetFactory implements IToggleBreakpointsTargetFactory {

	private static final String ID = "org.eclipse.php.debug.ui.PHPtoggleTargetFactory"; //$NON-NLS-1$

	public PHPToggleBreakpointsTargetFactory() {
	}

	@Override
	public IToggleBreakpointsTarget createToggleTarget(String targetID) {
		return new ScriptLineBreakpointAdapter();
	}

	@Override
	public String getDefaultToggleTarget(IWorkbenchPart part, ISelection selection) {
		return ID;
	}

	@Override
	public String getToggleTargetDescription(String targetID) {
		return Messages.PHPToggleBreakpointsTargetFactory_1;
	}

	@Override
	public String getToggleTargetName(String targetID) {
		return Messages.PHPToggleBreakpointsTargetFactory_2;
	}

	@Override
	public Set<String> getToggleTargets(IWorkbenchPart part, ISelection selection) {

		if (getEditor(part) != null) {
			Set<String> targets = new HashSet<>();
			targets.add(ID);
			return targets;
		}
		return Collections.emptySet();
	}

	private PHPStructuredEditor getEditor(IWorkbenchPart part) {
		if (part instanceof PHPStructuredEditor) {
			return (PHPStructuredEditor) part;
		}
		return null;
	}

}

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
package org.eclipse.php.debug.ui.presentation;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.core.containers.LocalFileStorage;
import org.eclipse.php.core.containers.ZipEntryStorage;
import org.eclipse.php.debug.core.IPHPConstants;
import org.eclipse.php.debug.core.model.PHPDebugTarget;
import org.eclipse.php.debug.core.model.PHPLineBreakpoint;
import org.eclipse.php.debug.core.model.PHPStackFrame;
import org.eclipse.php.debug.core.model.PHPThread;
import org.eclipse.php.debug.core.sourcelookup.PHPSourceNotFoundInput;
import org.eclipse.php.debug.ui.Logger;
import org.eclipse.php.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.debug.ui.sourcelookup.PHPSourceNotFoundEditorInput;
import org.eclipse.php.ui.containers.LocalFileStorageEditorInput;
import org.eclipse.php.ui.containers.ZipEntryStorageEditorInput;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;

/**
 * Renders PHP debug elements
 */
public class PHPModelPresentation extends LabelProvider implements IDebugModelPresentation {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */

	public void setAttribute(String attribute, Object value) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof PHPDebugTarget) {
			return getTargetText((PHPDebugTarget) element);
		} else if (element instanceof PHPThread) {
			return getThreadText((PHPThread) element);
		} else if (element instanceof PHPStackFrame) {
			return getStackFrameText((PHPStackFrame) element);
		} else if (element instanceof PHPLineBreakpoint) {
			PHPLineBreakpoint breakpoint = (PHPLineBreakpoint) element;
			IMarker marker = breakpoint.getMarker();
			IResource resource = marker.getResource();
			if (resource instanceof IFile) {
				return null;
			} else if (resource instanceof IWorkspaceRoot) {
				try {
					String filename = (String) marker.getAttribute(IPHPConstants.Include_Storage);
					Integer lineNumber = (Integer) marker.getAttribute(IMarker.LINE_NUMBER);
					return filename + " [line: " + lineNumber.toString() + "]";
				} catch (CoreException e) {
					Logger.logException("Unexpected error in PHPModelPresentation", e);
				}
			}
		}
		return null;

	}

	private String getTargetText(PHPDebugTarget target) {
		String label = ""; //$NON-NLS-1$
		if (target.isTerminated()) {
			label = MessageFormat.format(PHPDebugUIMessages.MPresentation_Terminated_1, new Object[] {});
		}
		return label + PHPDebugUIMessages.MPresentation_PHP_APP_1;
	}

	private String getThreadText(PHPThread thread) {
		PHPDebugTarget target = (PHPDebugTarget) thread.getDebugTarget();
		String label = "";
		try {
			label = target.getName();
		} catch (DebugException e1) {
			// Just log should never happen
			Logger.logException("PHPModelPresentation error getting target name", e1);
		}
		if (thread.isStepping()) {
			label += PHPDebugUIMessages.MPresentation_Stepping_1;
		} else if (thread.isSuspended()) {
			IBreakpoint[] breakpoints = thread.getBreakpoints();
			if (breakpoints.length == 0) {
				label += PHPDebugUIMessages.MPresentation_Suspended_1;
			} else {
				IBreakpoint breakpoint = breakpoints[0]; // there can only be
				// one in PHP
				if (breakpoint instanceof PHPLineBreakpoint) {
					label += PHPDebugUIMessages.MPresentation_SLineBreakpoint_1;
				}
			}
		} else if (thread.isTerminated()) {
			label = PHPDebugUIMessages.MPresentation_Terminated_1 + label; //$NON-NLS-1$
		}
		return label;
	}

	private String getStackFrameText(PHPStackFrame frame) {
		try {
			StringBuffer buffer = new StringBuffer(PHPDebugUIMessages.MPresentation_File_1 + frame.getSourceName());
			buffer.append(PHPDebugUIMessages.MPresentation_ATLine_1 + (frame.getLineNumber()));
			return buffer.toString();

		} catch (DebugException e) {
			Logger.logException("Unexpected error in PHPModelPresentation", e);
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#computeDetail(org.eclipse.debug.core.model.IValue,
	 *      org.eclipse.debug.ui.IValueDetailListener)
	 */
	public void computeDetail(IValue value, IValueDetailListener listener) {
		String detail = ""; //$NON-NLS-1$
		try {
			detail = value.getValueString();
		} catch (DebugException e) {
		}
		listener.detailComputed(value, detail);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ISourcePresentation#getEditorInput(java.lang.Object)
	 */
	public IEditorInput getEditorInput(Object element) {
		if (element instanceof IFile) {
			return new FileEditorInput((IFile) element);
		}
		if (element instanceof PHPLineBreakpoint) {
			PHPLineBreakpoint breakpoint = (PHPLineBreakpoint) element;
			IMarker marker = breakpoint.getMarker();
			IResource resource = marker.getResource();
			if (resource instanceof IFile) {
				return new FileEditorInput((IFile) resource);
			} else if (resource instanceof IWorkspaceRoot) {
				try {
					String filename = (String) marker.getAttribute(IPHPConstants.Include_Storage);
					String type = (String) marker.getAttribute(IPHPConstants.Include_Storage_type);
					String id = (String) marker.getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY);
					String projectName = (String) marker.getAttribute(IPHPConstants.Include_Storage_Project, "");
					IProject project = PHPDebugUIPlugin.getProject(projectName);
					if (IPHPConstants.Include_Storage_LFile.equals(type)) {
						File file = new File(id);
						LocalFileStorage lfs = new LocalFileStorage(file);
						lfs.setProject(project);
						return new LocalFileStorageEditorInput(lfs);
					} else if (IPHPConstants.Include_Storage_zip.equals(type)) {
						int index = id.lastIndexOf(filename);
						String archive = id.substring(0, index - 1);
						ZipFile zip = new ZipFile(archive);
						ZipEntry entry = new ZipEntry(filename);
						ZipEntryStorage zipStore = new ZipEntryStorage(zip, entry);
						zipStore.setProject(project);
						return new ZipEntryStorageEditorInput(zipStore);

					}
				} catch (CoreException e) {
					Logger.logException("Unexpected error in PHPModelPresentation", e);
				} catch (IOException e) {
					Logger.logException("Unexpected error in PHPModelPresentation", e);
				}
			}

			//            return new FileEditorInput((IFile) ((ILineBreakpoint) element).getMarker().getResource());
		}
		if (element instanceof ZipEntryStorage) {
			return new ZipEntryStorageEditorInput((ZipEntryStorage) element);
		}
		if (element instanceof LocalFileStorage) {
			return new LocalFileStorageEditorInput((LocalFileStorage) element);
		}
		if (element instanceof PHPSourceNotFoundInput) {
			return new PHPSourceNotFoundEditorInput((PHPSourceNotFoundInput) element);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ISourcePresentation#getEditorId(org.eclipse.ui.IEditorInput,
	 *      java.lang.Object)
	 */
	public String getEditorId(IEditorInput input, Object element) {
		if (input instanceof PHPSourceNotFoundEditorInput) {
			return "org.eclipse.php.debug.SourceNotFoundEditor";
		}
		if (element instanceof IFile || element instanceof ILineBreakpoint || element instanceof ZipEntryStorage || element instanceof LocalFileStorage) {
			return "org.eclipse.php.editor"; //$NON-NLS-1$
		}
		return null;
	}
}

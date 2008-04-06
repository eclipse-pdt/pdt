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
package org.eclipse.php.internal.debug.ui.presentation;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.php.internal.core.containers.LocalFileStorage;
import org.eclipse.php.internal.core.containers.ZipEntryStorage;
import org.eclipse.php.internal.core.filesystem.FileStoreFactory;
import org.eclipse.php.internal.debug.core.IPHPDebugConstants;
import org.eclipse.php.internal.debug.core.model.*;
import org.eclipse.php.internal.debug.core.sourcelookup.PHPSourceNotFoundInput;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.core.zend.model.PHPStackFrame;
import org.eclipse.php.internal.debug.core.zend.model.PHPThread;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.debug.ui.breakpoint.PHPBreakpointImageDescriptor;
import org.eclipse.php.internal.debug.ui.sourcelookup.PHPSourceNotFoundEditorInput;
import org.eclipse.php.internal.ui.containers.LocalFileStorageEditorInput;
import org.eclipse.php.internal.ui.containers.ZipEntryStorageEditorInput;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.php.internal.ui.util.ImageDescriptorRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;

/**
 * Renders PHP debug elements
 */
public class PHPModelPresentation extends LabelProvider implements IDebugModelPresentation {
	protected final static String UNTITLED_FOLDER_PATH = "Untitled_Documents";
	
	private ImageDescriptorRegistry fDebugImageRegistry;

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
		if (element instanceof PHPConditionalBreakpoint) {
			return getBreakpointImage((PHPConditionalBreakpoint) element);
		}
		return null;
	}

	// Returns the conditional breakpoint icon (enabled / disabled).
	// In case the breakpoint is not conditional, return null and let the default breakpoint
	// icon.
	private Image getBreakpointImage(PHPConditionalBreakpoint breakpoint) {
		try {
			if (breakpoint.isConditionEnabled()) {
				PHPBreakpointImageDescriptor descriptor;
				if (breakpoint.isEnabled()) {
					descriptor = new PHPBreakpointImageDescriptor(DebugUITools.getImageDescriptor(IDebugUIConstants.IMG_OBJS_BREAKPOINT), PHPBreakpointImageDescriptor.CONDITIONAL | PHPBreakpointImageDescriptor.ENABLED);
				} else {
					descriptor = new PHPBreakpointImageDescriptor(DebugUITools.getImageDescriptor(IDebugUIConstants.IMG_OBJS_BREAKPOINT_DISABLED), PHPBreakpointImageDescriptor.CONDITIONAL);
				}
				return getDebugImageRegistry().get(descriptor);
			}
		} catch (CoreException e) {
			return null;
		}
		return null;
	}

	protected ImageDescriptorRegistry getDebugImageRegistry() {
		if (fDebugImageRegistry == null) {
			fDebugImageRegistry = PHPDebugUIPlugin.getImageDescriptorRegistry();
		}
		return fDebugImageRegistry;
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
					String filename = (String) marker.getAttribute(IPHPDebugConstants.STORAGE_FILE);
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
			// Fix bug #160443 (Stack frames line numbers update).
			// Synchronize the top frame with the given values.
			PHPThread thread = (PHPThread) frame.getThread();
			PHPStackFrame topFrame = (PHPStackFrame) thread.getTopStackFrame();
			if (topFrame != null && topFrame.equals(frame)) {
				frame = topFrame;
			} // end fix

			StringBuffer buffer = new StringBuffer();
			String frameName = frame.getName();
			if (frameName != null && frameName.length() > 0) {
				buffer.append(frame.getName());
				buffer.append("() ");
			}
			buffer.append(frame.getSourceName());
			buffer.append(PHPDebugUIMessages.MPresentation_ATLine_1 + (frame.getLineNumber()));
			return buffer.toString();

		} catch (DebugException e) {
			Logger.logException("Unexpected error in PHPModelPresentation", e);
		} catch (NullPointerException npe) {
			// This is here for debug purpose. Figure out why do we get nulls.
			StringBuffer errorMessage = new StringBuffer("NPE in getStackFrameText(). Frame = ");
			errorMessage.append(frame);
			if (frame != null) {
				errorMessage.append(", Thread = ");
				errorMessage.append(frame.getThread());
			}
			Logger.logException(errorMessage.toString(), npe);
		}
		return ""; //$NON-NLS-1$

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
			}
			// Breakpoints for external files are stored in workspace root:
			else if (resource instanceof IWorkspaceRoot) {
				try {
					String filename = (String) marker.getAttribute(IPHPDebugConstants.STORAGE_FILE);
					String type = (String) marker.getAttribute(IPHPDebugConstants.STORAGE_TYPE);
					
					if (IPHPDebugConstants.STORAGE_TYPE_INCLUDE.equals(type)) {
						String projectName = (String) marker.getAttribute(IPHPDebugConstants.STORAGE_PROJECT, "");
						IProject project = PHPDebugUIPlugin.getProject(projectName);
						String includeBaseDir = (String) marker.getAttribute(IPHPDebugConstants.STORAGE_INC_BASEDIR, "");
						filename = marker.getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY, filename);
						
						File file = new File(filename);
						LocalFileStorage lfs = new LocalFileStorage(file);
						lfs.setProject(project);
						lfs.setIncBaseDirName(includeBaseDir);
						return new LocalFileStorageEditorInput(lfs);
					} else if (IPHPDebugConstants.STORAGE_TYPE_EXTERNAL.equals(type) || IPHPDebugConstants.STORAGE_TYPE_REMOTE.equals(type)) {
						File file = new File(filename);
						return new FileStoreEditorInput(FileStoreFactory.createFileStore(file));
					}
				} catch (CoreException e) {
					Logger.logException("Unexpected error in PHPModelPresentation", e);
				}
			}
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
		if (element instanceof IFileStore) {
			if (isUntitled(element)) {
				String path = ((IFileStore)element).toString();
				return new NonExistingPHPFileEditorInput(new Path(path));
			} 
			return new FileStoreEditorInput((IFileStore)element);
		}
		Logger.log(Logger.WARNING_DEBUG, "Unknown editor input type: " + element.getClass().getName());
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ISourcePresentation#getEditorId(org.eclipse.ui.IEditorInput,
	 *      java.lang.Object)
	 */
	public String getEditorId(IEditorInput input, Object element) {
		if (isUntitled(element)) {
			return "org.eclipse.php.untitledPhpEditor"; //$NON-NLS-1$
		}
		if (input instanceof PHPSourceNotFoundEditorInput) {
			return "org.eclipse.php.debug.SourceNotFoundEditor";
		}
		if (element instanceof IFile || element instanceof ILineBreakpoint || element instanceof ZipEntryStorage || element instanceof LocalFileStorage || element instanceof LocalFile) {
			return "org.eclipse.php.editor"; //$NON-NLS-1$
		}
		return null;
	}
	
	protected boolean isUntitled(Object element) {
		if (element instanceof IFileStore) {
			final IFileStore localFile = (IFileStore)element;
			IFileStore parentDir = localFile.getParent();
			if (parentDir != null && UNTITLED_FOLDER_PATH.equals(parentDir.getName())) {
				return true;
			}
		}
		return false;
	}
}

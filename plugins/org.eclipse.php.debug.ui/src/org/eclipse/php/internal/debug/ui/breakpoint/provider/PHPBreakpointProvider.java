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
package org.eclipse.php.internal.debug.ui.breakpoint.provider;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.php.internal.core.containers.LocalFileStorage;
import org.eclipse.php.internal.core.containers.ZipEntryStorage;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.debug.core.IPHPConstants;
import org.eclipse.php.internal.debug.core.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.model.PHPDebugTarget;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.ui.editor.input.PHPFileEditorInput;
import org.eclipse.php.internal.ui.util.StatusLineMessageTimerManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.ISourceEditingTextTools;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.breakpoint.IBreakpointProvider;

public class PHPBreakpointProvider implements IBreakpointProvider, IExecutableExtension {

	public IStatus addBreakpoint(IDocument document, IEditorInput input, int editorLineNumber, int offset) throws CoreException {
		// check if there is a valid position to set breakpoint
		int pos = getValidPosition(document, editorLineNumber);
		IStatus status = null;
		IBreakpoint point = null;
		if (pos >= 0) {
			IResource res = getResourceFromInput(input);
			if (res != null && (input instanceof IFileEditorInput)) {
				try {
					editorLineNumber = document.getLineOfOffset(pos) + 1;
					Integer lineNumberInt = new Integer(editorLineNumber);
					IMarker[] breakpoints = res.findMarkers(IBreakpoint.LINE_BREAKPOINT_MARKER, true, IResource.DEPTH_ZERO);
					for (int i = 0; i < breakpoints.length; ++i) {
						if (breakpoints[i].getAttributes().get("lineNumber").equals(lineNumberInt)) {
							throw new BadLocationException();
						}
					}
					point = PHPDebugTarget.createBreakpoint(res, editorLineNumber);
				} catch (BadLocationException e) {
				}
			} else if (input instanceof IStorageEditorInput) {
				// For non-resources, use the workspace root and a coordinated
				// attribute that is used to
				// prevent unwanted (breakpoint) markers from being loaded
				// into the editors.
				res = ResourcesPlugin.getWorkspace().getRoot();
				String id = input.getName();
				IStorage storage = ((IStorageEditorInput) input).getStorage();
				if (input instanceof IStorageEditorInput && ((IStorageEditorInput) input).getStorage() != null) {
					id = storage.getFullPath().toString();
				}
				Map attributes = new HashMap();
				attributes.put(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY, id);
				String fileName = "";
				IProject project = null;
				if (storage instanceof ZipEntryStorage) {
					fileName = RemoteDebugger.convertToSystemIndependentFileName(((ZipEntryStorage) storage).getZipEntry().getName());
					attributes.put(IPHPConstants.Include_Storage_type, IPHPConstants.Include_Storage_zip);
					project = ((ZipEntryStorage) storage).getProject();
				} else if (storage instanceof LocalFileStorage) {
					attributes.put(IPHPConstants.Include_Storage_type, IPHPConstants.Include_Storage_LFile);
					fileName = RemoteDebugger.convertToSystemIndependentFileName(((LocalFileStorage) storage).getName());
					String incDir = ((LocalFileStorage) storage).getIncBaseDirName();
					incDir = RemoteDebugger.convertToSystemIndependentFileName(incDir);
					if (incDir != null) {
						fileName = id.substring(incDir.length() + 1);
					}
					project = ((LocalFileStorage) storage).getProject();
				} else if (input instanceof PHPFileEditorInput) {
					// we have a JavaFileEditorStorage
					attributes.put(IPHPConstants.Include_Storage_type, IPHPConstants.Include_Storage_LFile);
					attributes.put(IPHPConstants.Non_Workspace_Breakpoint, Boolean.TRUE);
					fileName = id;
				} else {
					attributes.put(IPHPConstants.Include_Storage_type, IPHPConstants.Include_Storage_RFile);
					fileName = storage.getName();
				}
				attributes.put(IPHPConstants.Include_Storage, fileName);
				String projectName = "";
				if (project != null)
					projectName = project.getName();
				attributes.put(IPHPConstants.Include_Storage_Project, projectName);
				point = PHPDebugTarget.createBreakpoint(res, editorLineNumber, attributes);
			}
		}
		if (point == null) {
			StatusLineMessageTimerManager.setErrorMessage(PHPDebugUIMessages.ErrorCreatingBreakpoint_1, 1000, true); // hide message after 1 second
		}
		if (status == null) {
			status = new Status(IStatus.OK, PHPDebugUIPlugin.getID(), IStatus.OK, MessageFormat.format(PHPDebugUIMessages.BreakpointCreated_1, new Object[] {}), null);
		}
		return status;
	}

	public IResource getResource(IEditorInput input) {
		return getResourceFromInput(input);
	}

	private IResource getResourceFromInput(IEditorInput input) {
		IResource resource = (IResource) input.getAdapter(IFile.class);
		/*        if (resource == null && input instanceof IStorageEditorInput) {
		 resource = ResourcesPlugin.getWorkspace().getRoot();
		 } else {
		 resource = (IResource) input.getAdapter(IResource.class);
		 }*/
//		if (resource == null && input instanceof PHPFileEditorInput) {
//			resource = ((IWorkspaceRoot) ResourcesPlugin.getWorkspace().getRoot()).getFile(((PHPFileEditorInput)input).getPath());
//		}
		if (resource == null) {
			resource = ResourcesPlugin.getWorkspace().getRoot();
		}
		return resource;
	}

	/**
	 * Finds a valid position somewhere on lineNumber in document, idoc, where a
	 * breakpoint can be set and returns that position. -1 is returned if a
	 * position could not be found.
	 * 
	 * @param idoc
	 * @param editorLineNumber
	 * @return position to set breakpoint or -1 if no position could be found
	 */
	public static int getValidPosition(IDocument idoc, int editorLineNumber) {
		int result = -1;
		if (idoc != null) {

			int startOffset = 0;
			int endOffset = 0;
			try {
				String partitionType = null;
				IRegion line;
				String linePart;
				boolean phpPartitionVisited = false;

				do {
					line = idoc.getLineInformation(editorLineNumber - 1);
					startOffset = line.getOffset();
					endOffset = Math.max(line.getOffset(), line.getOffset() + line.getLength());

					ITypedRegion[] partitions = null;

					partitions = idoc.computePartitioning(startOffset, endOffset - startOffset);

					for (int i = 0; i < partitions.length; ++i) {
						partitionType = partitions[i].getType();
						if (partitionType.equals(PHPPartitionTypes.PHP_DEFAULT)) {
							phpPartitionVisited = true;
							startOffset = partitions[i].getOffset();
							linePart = idoc.get(startOffset, partitions[i].getLength()).trim();
							if (Pattern.matches(".*[a-zA-Z0-0_]+.*", linePart) && !linePart.trim().toLowerCase().equals("<?php")) {
								result = startOffset;
								break;
							}
						}
					}
					++editorLineNumber;
				} while ((!phpPartitionVisited || PHPStructuredTextPartitioner.isPHPPartitionType(partitionType)) && result == -1);
			} catch (BadLocationException e) {
				result = -1;
			}
		}
		return result;
	}

	/**
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
	 *      java.lang.String, java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) {
		// not used
	}

	public void setSourceEditingTextTools(ISourceEditingTextTools tools) {
		// not used
	}

}

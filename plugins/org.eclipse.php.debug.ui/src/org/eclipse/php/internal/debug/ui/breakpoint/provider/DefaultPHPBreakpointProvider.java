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
package org.eclipse.php.internal.debug.ui.breakpoint.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.debug.core.model.PHPConditionalBreakpoint;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.ui.editor.input.IPlatformIndependentPathEditorInput;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.php.internal.ui.util.StatusLineMessageTimerManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.wst.sse.ui.internal.StructuredResourceMarkerAnnotationModel;
import org.eclipse.wst.sse.ui.internal.provisional.extensions.ISourceEditingTextTools;

import com.ibm.icu.text.MessageFormat;

public class DefaultPHPBreakpointProvider implements IPHPBreakpointProvider,
		IExecutableExtension {

	public IStatus addBreakpoint(IDocument document, IEditorInput input,
			int lineNumber, int offset) throws CoreException {

		// check if there is a valid position to set breakpoint
		int pos = getValidPosition(document, lineNumber);

		// calculate the line number here so both workspace files AND externals
		// will get it
		try {
			lineNumber = document.getLineOfOffset(pos) + 1;
		} catch (BadLocationException e) {
			Logger.logException(e);
			return new Status(IStatus.ERROR, PHPDebugUIPlugin.getID(),
					PHPDebugUIMessages.DefaultPHPBreakpointProvider_0);
		}

		IStatus status = null;
		IBreakpoint point = null;
		if (pos >= 0) {

			IResource resource = getResourceFromInput(input);

			Map<String, String> attributes = new HashMap<String, String>();
			ISourceModule modelElement = DLTKUIPlugin
					.getEditorInputModelElement(input);

			if (modelElement != null) {
				attributes.put(IMarker.LOCATION, modelElement.getPath()
						.toString());
			}

			// Calculate secondary ID
			String secondaryId = null;
			if (input instanceof IFileEditorInput) {
				IFileEditorInput fileEditorInput = (IFileEditorInput) input;
				if (fileEditorInput.getFile().isLinked()) {
					secondaryId = fileEditorInput.getFile().getRawLocation()
							.toString();
				}
			} else if (input instanceof IURIEditorInput
					|| (input instanceof NonExistingPHPFileEditorInput)) {

				if (input instanceof IPlatformIndependentPathEditorInput) {
					secondaryId = ((IPlatformIndependentPathEditorInput) input)
							.getPath();
				} else if (input instanceof IURIEditorInput) {
					secondaryId = URIUtil.toPath(
							((IURIEditorInput) input).getURI()).toOSString();
				} else {
					secondaryId = ((NonExistingPHPFileEditorInput) input)
							.getPath(input).toString();
				}

			} else if (input instanceof IStorageEditorInput) {
				IStorage storage = ((IStorageEditorInput) input).getStorage();
				if (storage instanceof IModelElement) {
					IModelElement element = (IModelElement) storage;
					secondaryId = EnvironmentPathUtils.getFile(element)
							.getFullPath().toPortableString();
				}
			}

			if (secondaryId != null) {
				attributes
						.put(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY,
								secondaryId);
			}

			if (findBreakpointMarker(secondaryId, resource, lineNumber) == null) {
				point = createBreakpoint(input, resource, lineNumber,
						attributes);
			}
		}

		if (point == null) {
			showErrorMessage();
		}

		if (status == null) {
			status = new Status(IStatus.OK, PHPDebugUIPlugin.getID(),
					IStatus.OK, MessageFormat.format(
							PHPDebugUIMessages.BreakpointCreated_1,
							new Object[] {}), null);
		}
		return status;
	}

	protected void showErrorMessage() {
		// hide message after one second:
		StatusLineMessageTimerManager.setErrorMessage(
				PHPDebugUIMessages.ErrorCreatingBreakpoint_1, 1000, true);
	}

	protected IMarker findBreakpointMarker(String secondaryId,
			IResource resource, int lineNumber) throws CoreException {
		IMarker[] breakpoints = resource.findMarkers(
				IBreakpoint.LINE_BREAKPOINT_MARKER, true, IResource.DEPTH_ZERO);
		for (IMarker breakpoint : breakpoints) {
			if (breakpoint.getAttribute(IMarker.LINE_NUMBER).equals(
					new Integer(lineNumber))
					&& (secondaryId == null || secondaryId
							.equals(breakpoint
									.getAttribute(StructuredResourceMarkerAnnotationModel.SECONDARY_ID_KEY)))) {
				return breakpoint;
			}
		}
		return null;
	}

	public IBreakpoint createBreakpoint(IEditorInput input, IResource resource,
			int lineNumber, Map<String, String> attributes)
			throws CoreException {
		return new PHPConditionalBreakpoint(resource, lineNumber, attributes);
	}

	public IResource getResource(IEditorInput input) {
		return getResourceFromInput(input);
	}

	private IResource getResourceFromInput(IEditorInput input) {
		IResource resource = (IResource) input.getAdapter(IFile.class);
		if (resource == null || !resource.exists()) {
			// for non-workspace resources - use workspace root for storing
			// breakpoints
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
					endOffset = Math.max(line.getOffset(), line.getOffset()
							+ line.getLength());

					ITypedRegion[] partitions = null;

					partitions = idoc.computePartitioning(startOffset,
							endOffset - startOffset);

					for (int i = 0; i < partitions.length; ++i) {
						partitionType = partitions[i].getType();
						if (partitionType.equals(PHPPartitionTypes.PHP_DEFAULT)) {
							phpPartitionVisited = true;
							startOffset = partitions[i].getOffset();
							linePart = idoc.get(startOffset,
									partitions[i].getLength()).trim();
							if (Pattern.matches(".*[a-zA-Z0-0_]+.*", linePart) //$NON-NLS-1$
									&& !linePart.trim().toLowerCase()
											.equals("<?php")) { //$NON-NLS-1$
								result = startOffset;
								break;
							}
						}
					}
					++editorLineNumber;
				} while ((!phpPartitionVisited || PHPStructuredTextPartitioner
						.isPHPPartitionType(partitionType)) && result == -1);
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
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) {
		// not used
	}

	public void setSourceEditingTextTools(ISourceEditingTextTools tools) {
		// not used
	}

}

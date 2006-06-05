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
package org.eclipse.php.core.project.options.includepath;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.operations.LocalizedMessage;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;

public class IncludePathValidator implements IValidator {

	private class OptionsFileVisitor implements IResourceProxyVisitor {

		private List fFiles = new ArrayList();
		//	    private IContentType fContentTypeJSP = null;
		private IReporter fReporter = null;

		public OptionsFileVisitor(IReporter reporter) {
			fReporter = reporter;
		}

		public boolean visit(IResourceProxy proxy) throws CoreException {

			// check validation
			if (fReporter.isCancelled())
				return false;

			if (proxy.getType() == IResource.FILE) {
				IFile file = (IFile) proxy.requestResource();
				if (file.exists()) {
					if (canHandle(file)) {
						fFiles.add(file);
						// don't search deeper for files
						return false;
					}
				}
				return false;
			} else if (proxy.getType() == IResource.FOLDER)
				return false;
			return true;
		}

		// Simple check for php file. When create PHP file wizard exist should be able 
		// to do a context check on file. 

		private boolean canHandle(IFile file) {
			boolean result = false;
			if (file != null) {
				if (file.getName().equals(PHPProjectOptions.FILE_NAME))
					return true;
			}
			return false;
		}

		public final IFile[] getFiles() {
			return (IFile[]) fFiles.toArray(new IFile[fFiles.size()]);
		}
	}

	public void cleanup(IReporter reporter) {

	}

	public void validate(IValidationContext helper, IReporter reporter) throws ValidationException {
		String[] uris = helper.getURIs();
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		if (uris.length > 0) {
			IFile currentFile = null;
			for (int i = 0; i < uris.length && !reporter.isCancelled(); i++) {
				currentFile = wsRoot.getFile(new Path(uris[i]));
				if (currentFile != null && currentFile.exists()) {
					validateIncludePath(currentFile, reporter);
				}
			}
		} else {
			// it's an entire workspace "clean"
			OptionsFileVisitor visitor = new OptionsFileVisitor(reporter);
			try {
				//  collect all jsp files
				ResourcesPlugin.getWorkspace().getRoot().accept(visitor, IResource.DEPTH_INFINITE);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			IFile[] files = visitor.getFiles();
			for (int i = 0; i < files.length && !reporter.isCancelled(); i++) {
				validateIncludePath(files[i], reporter);
			}
		}

	}

	private void clearMarkers(IReporter reporter, IProject project) {

		reporter.removeAllMessages(this, project);
		//		try {
		//			project.deleteMarkers(IMarker.TASK, false, IResource.DEPTH_INFINITE);
		//		} catch (CoreException e1) {
		//		}

	}

	private void validateIncludePath(IFile optionsFile, IReporter reporter) {
		IProject project = optionsFile.getProject();
		PHPProjectOptions projectOptions = PHPProjectOptions.forProject(project);
		IIncludePathEntry[] entries = projectOptions.readRawIncludePath();
		clearMarkers(reporter, project);
		for (int i = 0; i < entries.length; i++) {
			String message = entries[i].validate();
			if (message != null)
				addError(project, message, reporter, IMarker.PRIORITY_HIGH);
		}
	}

	private void addError(IProject project, String message, IReporter reporter, int priority) {
		try {
			IMarker marker = project.createMarker(IMarker.TASK);
			marker.setAttribute(IMarker.LINE_NUMBER, 0);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.PRIORITY, priority);
		} catch (CoreException e) {
		}
		Message mess = new LocalizedMessage(IMessage.HIGH_SEVERITY, message, project);
		reporter.addMessage(this, mess);
	}
}

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
package org.eclipse.php.internal.core.project.build;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.part.FileEditorInput;

public class PHPProjectBuildJobWrapper {
	private IProject project;
	
	public PHPProjectBuildJobWrapper(IProject project) {
		this.project = project;
	}

	public void runJob() {
    	WorkspaceJob cleanJob = new WorkspaceJob("Building after php version changed ...") {
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				try {
					project.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
//					IWorkbenchWindow[] workbenchWindows = PHPCorePlugin.getDefault().getWorkbench().getWorkbenchWindows();
//					IWorkbenchPage[] workbenchPages = workbenchWindows[0].getPages();
//					IEditorReference[] editorReferences = workbenchPages[0].getEditorReferences();
//					handleEditorReferences(editorReferences);
		        } finally {
		            monitor.done();
		        }
				return Status.OK_STATUS;
			}
			private void handleEditorReferences(IEditorReference[] editorReferences) {
				for (int i = 0; i < editorReferences.length; i++) {
					IEditorReference reference = editorReferences[i];
					IEditorPart editorPart = reference.getEditor(false);
					IEditorInput editorInput = editorPart.getEditorInput();

					if (!(editorInput instanceof FileEditorInput)) {
						return;
					}
					final IFile file = (IFile) ((FileEditorInput) editorInput).getFile();
					IContentDescription contentDescription;
					try {
						contentDescription = file.getContentDescription();
					} catch (CoreException e) {
						Logger.logException(e);
						return;
					}
					if (contentDescription == null) {
						return;
					}
					if (!ContentTypeIdForPHP.ContentTypeID_PHP.equals(contentDescription.getContentType().getId())) {
						return;
					}
//					final ITextEditor textEditor = (ITextEditor) editorPart;
//					final StructuredTextEditor structuredTextEditor = (StructuredTextEditor) textEditor;
//					structuredTextEditor
					
//					structuredTextEditor
//					Display.getDefault().syncExec(new Runnable() {
//						public void run() {
//							structuredTextEditor.update();
//							textEditor.resetHighlightRange();
//							try {
//								textEditor.setHighlightRange(0, file.getContents().toString().length(), false);
//							} catch (CoreException e) {
//								Logger.logException(e);
//							}
//						}
//					});
					
				}
			}
		};
        cleanJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
        cleanJob.setUser(false);
        cleanJob.schedule();		
	}

}

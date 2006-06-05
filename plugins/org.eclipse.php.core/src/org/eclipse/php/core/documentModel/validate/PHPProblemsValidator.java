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
package org.eclipse.php.core.documentModel.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.php.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.IPHPMarker;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPTask;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.core.preferences.TaskTagsProvider;
import org.eclipse.wst.sse.core.internal.provisional.tasks.TaskTag;
import org.eclipse.wst.validation.internal.core.Message;
import org.eclipse.wst.validation.internal.core.ValidationException;
import org.eclipse.wst.validation.internal.operations.LocalizedMessage;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidationContext;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;

public class PHPProblemsValidator implements IValidator {

	static boolean shouldValidate(IFile file) {
		IResource resource = file;
		do {
			if (resource.isDerived() || resource.isTeamPrivateMember() || !resource.isAccessible() || (resource.getName().charAt(0) == '.' && resource.getType() == IResource.FOLDER)) {
				return false;
			}
			resource = resource.getParent();
		} while ((resource.getType() & IResource.PROJECT) == 0);
		return true;
	}

	private class PHPFileVisitor implements IResourceProxyVisitor {

		private List fFiles = new ArrayList();
		//	    private IContentType fContentTypeJSP = null;
		private IReporter fReporter = null;

		public PHPFileVisitor(IReporter reporter) {
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
			}
			return true;
		}

		// Simple check for php file. When create PHP file wizard exist should be able 
		// to do a context check on file. 

		private boolean canHandle(IFile file) {
			boolean result = false;
			if (file != null) {
				try {
					IContentTypeManager contentTypeManager = Platform.getContentTypeManager();

					IContentDescription contentDescription = file.getContentDescription();
					IContentType phpContentType = contentTypeManager.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
					if (contentDescription != null) {
						IContentType fileContentType = contentDescription.getContentType();

						if (phpContentType != null) {
							if (fileContentType.isKindOf(phpContentType)) {
								result = true;
							}
						}
					} else if (phpContentType != null) {
						result = phpContentType.isAssociatedWith(file.getName());
					}
				} catch (CoreException e) {
					// should be rare, but will ignore to avoid logging "encoding
					// exceptions" and the like here.
					// Logger.logException(e);
				}
			}
			return result;
		}

		public final IFile[] getFiles() {
			return (IFile[]) fFiles.toArray(new IFile[fFiles.size()]);
		}
	}

	public void cleanup(IReporter reporter) {
		// TODO Auto-generated method stub

	}

	public void validate(IValidationContext helper, IReporter reporter) throws ValidationException {
		String[] uris = helper.getURIs();
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
		if (uris.length > 0) {
			IFile currentFile = null;
			for (int i = 0; i < uris.length && !reporter.isCancelled(); i++) {
				currentFile = wsRoot.getFile(new Path(uris[i]));
				if (currentFile != null && currentFile.exists()) {
					validateFile(currentFile, reporter);
				}
			}
		} else {
			// it's an entire workspace "clean"
			PHPFileVisitor visitor = new PHPFileVisitor(reporter);
			try {
				//  collect all jsp files
				ResourcesPlugin.getWorkspace().getRoot().accept(visitor, IResource.DEPTH_INFINITE);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			IFile[] files = visitor.getFiles();
			for (int i = 0; i < files.length && !reporter.isCancelled(); i++) {
				validateFile(files[i], reporter);
			}
		}

	}

	private TaskTagsProvider taskTagsProvider = TaskTagsProvider.getInstance();

	private void validateFile(IFile phpFile, IReporter reporter) {

		PHPFileData fileData = PHPWorkspaceModelManager.getInstance().getModelForFile(phpFile.getFullPath().toString(), true);
		if (fileData == null) {
			return;
		}
		IPHPMarker[] markers = fileData.getMarkers();
		IMarker[] rullerAddedMarkers = null; // We have to get all the markers, so we wont delete the ruler-added ones (Fix Bug #95)
		Map[] rullerMarkersAttributes = null;
		reporter.removeAllMessages(this, phpFile);
		try {
			rullerAddedMarkers = phpFile.findMarkers(IMarker.TASK, false, IResource.DEPTH_INFINITE);
			rullerMarkersAttributes = new Map[rullerAddedMarkers.length];
			// Get the attributes before we delete the marker.
			for (int i = 0; i < rullerMarkersAttributes.length; i++) {
				rullerMarkersAttributes[i] = rullerAddedMarkers[i].getAttributes();
			}
			phpFile.deleteMarkers(IMarker.TASK, false, IResource.DEPTH_INFINITE);
		} catch (CoreException e1) {
		}
		if (markers != null) {
			TaskTag[] tags = taskTagsProvider.getProjectTaskTags(phpFile.getProject());
			if (tags == null) {
				tags = taskTagsProvider.getWorkspaceTaskTags();
			}
			boolean caseSensitive = taskTagsProvider.getProjectTagsCaseSensitive(phpFile.getProject());

			for (int i = 0; markers.length > i; i++) {
				String type = markers[i].getType();
				if (type.equals(IPHPMarker.TASK)) {
					PHPTask task = (PHPTask) markers[i];
					String descr = task.getTaskName() + " " + task.getDescription();
					UserData userData = task.getUserData();
					int prio = getPriority(task.getTaskName(), tags, caseSensitive);
					try {
						IMarker marker = phpFile.createMarker(IMarker.TASK);
						marker.setAttribute(IMarker.LINE_NUMBER, userData.getStopLine() + 1);
						marker.setAttribute(IMarker.CHAR_START, userData.getStartPosition());
						marker.setAttribute(IMarker.CHAR_END, userData.getEndPosition() + 1);
						marker.setAttribute(IMarker.MESSAGE, descr);
						marker.setAttribute(IMarker.PRIORITY, prio);
					} catch (CoreException e) {
						// Logger.logException(e);
					}
					continue;
				}
				if (!type.equals(IPHPMarker.ERROR) && !type.equals(IPHPMarker.WARNING) && !type.equals(IPHPMarker.INFO)) {
					continue;
				}
				String descr = markers[i].getDescription();
				UserData userData = markers[i].getUserData();
				Message mess = new LocalizedMessage(IMessage.HIGH_SEVERITY, descr, phpFile);
				int lineNo = userData.getStopLine() + 1;
				mess.setLineNo(lineNo);
				reporter.addMessage(this, mess);
			}

			// Add the non-IPHPMarkers (ruler-added markers).
			if (rullerAddedMarkers != null) {
				for (int i = 0; i < rullerAddedMarkers.length; i++) {
					try {
						boolean exists = rullerAddedMarkers[i].exists();
						// Take just the Task markers that were added through the ruler.
						if (!exists && rullerMarkersAttributes[i].get(PHPTask.RULER_PHP_TASK) != null) {
							IMarker marker = phpFile.createMarker(IMarker.TASK);
							marker.setAttributes(rullerMarkersAttributes[i]);
						}
					} catch (CoreException ce) {
					}
				}
			}
		}
	}

	private int getPriority(String tagName, TaskTag[] tags, boolean caseSensitive) {
		for (int i = 0; i < tags.length; i++) {
			boolean tagFound;
			if (caseSensitive) {
				tagFound = tags[i].getTag().equals(tagName);
			} else {
				tagFound = tags[i].getTag().equalsIgnoreCase(tagName);
			}
			if (tagFound) {
				return tags[i].getPriority();
			}
		}
		return IMarker.PRIORITY_NORMAL;
	}
}

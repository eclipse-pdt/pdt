/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.documentModel.markers;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.IPHPMarker;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPTask;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.core.preferences.TaskTagsProvider;
import org.eclipse.wst.sse.core.internal.provisional.tasks.TaskTag;
import org.eclipse.wst.validation.internal.TaskListUtility;

public class MarkerContributor {

	private static MarkerContributor instance;

	public static MarkerContributor getInstance() {
		if (instance == null)
			instance = new MarkerContributor();
		return instance;
	}

	private MarkerContributor() {
	}

	private static String ID = "org.eclipse.php.core.documentModel.validate.PHPProblemsMarker"; //$NON-NLS-1$
	private static String PHP_PROBLEM_MARKER_TYPE = PHPCorePlugin.ID + ".phpproblemmarker"; //$NON-NLS-1$

	private static String[] owners = new String[] { ID };

	private final TaskTagsProvider taskTagsProvider = TaskTagsProvider.getInstance();

	public void markFileProblems(IFile phpFile, boolean markTasks) {
		PHPFileData fileData = getFileModel(phpFile);
		if (fileData == null) {
			return;
		}
		IPHPMarker[] markers = fileData.getMarkers();
		try {
			phpFile.deleteMarkers(PHP_PROBLEM_MARKER_TYPE, false, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
		}
		if (markTasks) {
			markTasks(phpFile, markers);
		}
		markErrors(phpFile, markers);
	}

	/**
	 * @param phpFile
	 * @param markers
	 */
	private void markErrors(IFile phpFile, IPHPMarker[] markers) {
		if (markers != null) {
			try {
				for (int i = 0; markers.length > i; i++) {
					String type = markers[i].getType();
					if (!type.equals(IPHPMarker.ERROR) && !type.equals(IPHPMarker.WARNING) && !type.equals(IPHPMarker.INFO)) {
						continue;
					}
					String descr = markers[i].getDescription();
					UserData userData = markers[i].getUserData();

					int prio = IMarker.PRIORITY_HIGH;
					createMarker(phpFile, userData, PHP_PROBLEM_MARKER_TYPE, descr, prio);
				}
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}
	}

	/**
	 * @param phpFile
	 * @param markers
	 */
	private void markTasks(IFile phpFile, IPHPMarker[] markers) {
		if (phpFile == null || !phpFile.exists()) {
			return;
		}

		IMarker[] rullerAddedMarkers = null; // We have to get all the markers, so we wont delete the ruler-added ones (Fix Bug #95)
		Map[] rullerMarkersAttributes = null;
		try {
			/* use this function they way it is used 'normally' by validators, send null instead of file name.
			 * fix bug# 160976
			 */
			TaskListUtility.removeAllTasks(phpFile, owners, null);
			//TaskListUtility.removeAllTasks(phpFile, owners, phpFile.getFullPath().toString());
		} catch (CoreException e) {
			Logger.logException(e);
		}

		try {
			rullerAddedMarkers = phpFile.findMarkers(IMarker.TASK, false, IResource.DEPTH_INFINITE);
			rullerMarkersAttributes = new Map[rullerAddedMarkers.length];
			// Get the attributes before we delete the marker.
			for (int i = 0; i < rullerMarkersAttributes.length; i++) {
				rullerMarkersAttributes[i] = rullerAddedMarkers[i].getAttributes();
			}
			phpFile.deleteMarkers(IMarker.TASK, false, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
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
					String descr = task.getTaskName() + " " + task.getDescription(); //$NON-NLS-1$
					UserData userData = task.getUserData();
					int prio = getPriority(task.getTaskName(), tags, caseSensitive);
					try {
						createMarker(phpFile, userData, IMarker.TASK, descr, prio);
					} catch (CoreException e) {
					}
					continue;
				}
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

	public void markFile(IFile phpFile) {
		markFileProblems(phpFile, true);
	}

	private PHPFileData getFileModel(IFile phpFile) {
		return PHPWorkspaceModelManager.getInstance().getModelForFile(phpFile.getFullPath().toString(), false);
	}

	private void createMarker(IFile phpFile, UserData userData, String markerType, String descr, int prio) throws CoreException {
		IMarker marker = phpFile.createMarker(markerType);
		marker.setAttribute(IMarker.LINE_NUMBER, userData.getStopLine() + 1);
		marker.setAttribute(IMarker.MESSAGE, descr);
		marker.setAttribute(IMarker.PRIORITY, prio);
		if (markerType == PHP_PROBLEM_MARKER_TYPE) {
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
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

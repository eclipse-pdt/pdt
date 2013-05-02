/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.core.zend.debugger.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.php.debug.core.debugger.handlers.IDebugMessageHandler;
import org.eclipse.php.debug.core.debugger.messages.IDebugMessage;
import org.eclipse.php.internal.debug.core.zend.debugger.RemoteDebugger;
import org.eclipse.php.internal.debug.core.zend.debugger.messages.IniAlteredNotification;
import org.eclipse.php.internal.debug.core.zend.model.PHPDebugTarget;

/**
 * Handler for iniAltered message.
 * 
 * @author Wojciech Galanciak, 2012
 * 
 */
public class IniAlteredNotificationHandler implements IDebugMessageHandler {

	private static final String INCLUDE_PATH = "include_path"; //$NON-NLS-1$

	public void handle(IDebugMessage message, PHPDebugTarget debugTarget) {
		IniAlteredNotification iniMessage = (IniAlteredNotification) message;
		if (INCLUDE_PATH.equals(iniMessage.getName())) {
			List<String> oldValues = parseEntries(iniMessage.getOldValue());
			List<String> newValues = parseEntries(iniMessage.getNewValue());
			for (String oldValue : oldValues) {
				if (newValues.contains(oldValue)) {
					newValues.remove(oldValue);
				}
			}
			if (newValues.size() > 0) {
				RemoteDebugger debugger = (RemoteDebugger) debugTarget
						.getRemoteDebugger();
				List<IProject> toAddFiles = new ArrayList<IProject>();
				for (String newValue : newValues) {
					String localPath = debugger.convertToLocalFilename(
							newValue, debugger.getCurrentWorkingDirectory(),
							debugTarget.getLastFileName());
					IResource member = ResourcesPlugin.getWorkspace().getRoot()
							.findMember(localPath);
					if (member != null) {
						IProject project = member.getProject();
						if (project != null
								&& !project.equals(debugTarget.getProject())) {
							toAddFiles.add(project);
						}
					}
				}
				if (toAddFiles.size() > 0) {
					debugTarget.addBreakpointFiles(toAddFiles
							.toArray(new IProject[toAddFiles.size()]));
				}
			}
		}
	}

	private List<String> parseEntries(String oldValue) {
		if (oldValue == null || oldValue.trim().length() == 0) {
			return new ArrayList<String>();
		}
		String separator = ":"; //$NON-NLS-1$
		int semicolonIndex = oldValue.indexOf(';');
		if (semicolonIndex != -1) {
			separator = ";"; //$NON-NLS-1$
		}
		return new ArrayList<String>(Arrays.asList(oldValue.split(separator)));
	}

}

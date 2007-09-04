/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial implementation
 *******************************************************************************/

package org.eclipse.php.internal.debug.core.xdebug.dbgp;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.php.internal.debug.core.xdebug.dbgp.extensionpoints.IFileMapper;

public class AutoPathMapper implements IFileMapper {

	private boolean workspaceRemap = false;
	private String externalLocPreAppend = "";
	private String launchProject = "";
	private boolean mappingRequired = false;

	/**
	 * 
	 * @param launchScript
	 *            the launch script, with project info
	 * @param initScript
	 *            the initial script executed
	 */
	public void init(String launchScript, String initScript) {
		if (launchScript == null || initScript == null) {
			return;
		}

		IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFileForLocation(new Path(initScript));
		if (file == null) {
			
			mappingRequired = true;

			// determine the difference between the 2, ie project
			// to external location.
			launchScript = launchScript.replace("\\", "/").trim();
			initScript = initScript.replace("\\", "/").trim();

			// try with the project in it
			if (initScript.endsWith(launchScript)) {
				workspaceRemap = true;
				int end = initScript.indexOf(launchScript);
				externalLocPreAppend = initScript.substring(0, end);
			} else {
				// remove project and try again
				// IPath path = new Path(launchScript);
				int projEnd;
				if (launchScript.startsWith("/")) {
					projEnd = launchScript.indexOf('/', 1);
				} else {
					projEnd = launchScript.indexOf('/');
				}
				String newTest = launchScript.substring(projEnd);
				if (initScript.endsWith(newTest)) {
					workspaceRemap = false;
					launchProject = launchScript.substring(0, projEnd);
					int end = initScript.indexOf(newTest);
					externalLocPreAppend = initScript.substring(0, end);
				}
			}
		}
	}

	public String mapWorkspaceFileToExternal(IFile workspaceFile,
			ILaunchConfiguration configuration) {
		String result = null;
		
		if (workspaceFile != null) {
			result = workspaceFile.getRawLocation().toString();
			String relPath = workspaceFile.getProjectRelativePath().toString();
			String project = workspaceFile.getProject().getName();
			if (externalLocPreAppend != null) {
				StringBuffer sb = new StringBuffer(externalLocPreAppend);
				sb.append('/');

				// if no specific project remapping then we include the project
				// to distinguish between other projects that may not have a
				// specific
				// remapping.
				if (workspaceRemap) {
					sb.append(project);
					sb.append('/');
				}
				sb.append(relPath);
				result = sb.toString();
			}
		}
		return result;
	}

	/**
	 * can assume that the externalFile has been properly decoded need to check
	 * on the slash format though, we will assume it is "/" for now
	 */
	public String mapExternalFileToWorkspace(String externalFile,
			ILaunchConfiguration configuration) {

		String result = externalFile;
		if (externalLocPreAppend != null) {
			externalFile = externalFile.replace('\\', '/');

			if (startsWith(externalFile, externalLocPreAppend)) {
				// remove the base dir from the external file
				String projectFile = externalFile
						.substring(externalLocPreAppend.length());
				IFile file = null;
				if (!workspaceRemap) {

					// we are mapping to a specific project so we need to
					// include the project. a workspace mapping already includes
					// the project.
					projectFile = launchProject + projectFile;
				}
				file = ResourcesPlugin.getWorkspace().getRoot().getFile(
						new Path(projectFile));

				if (file != null) {
					result = file.getRawLocation().toString();
				}
			}
		}

		// even if we cannot remap it, there is still a chance the source
		// locater will find something
		return result;
	}

	/**
	 * handles case sensitivity differences between platforms, eg c:\x is the
	 * same as C:\x
	 * 
	 * @param externalFile
	 * @param preAppend
	 * @return
	 */
	private boolean startsWith(String externalFile, String preAppend) {
		// are we running on windows or mac os ?
		// yes i do know about the optional file system for mac os that is case
		// sensitive
		String os = Platform.getOS();
		if (os.equals(Platform.OS_WIN32) || os.equals(Platform.OS_MACOSX)) {
			externalFile = externalFile.toLowerCase();
			preAppend = preAppend.toLowerCase();
		}
		return externalFile.startsWith(preAppend);
	}

	public boolean isMappingRequired() {
		return mappingRequired;
	}	
}

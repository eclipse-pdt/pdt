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
package org.eclipse.php.internal.debug.ui.console;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.ui.console.FileLink;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.ui.ide.IDE;

import com.ibm.icu.text.MessageFormat;

/**
 * 
 * @author seva
 * 
 *         A version of {@link FileLink} which also supports external resources
 * 
 */
public class PHPFileLink implements IHyperlink {

	protected String fileName;
	protected int lineNumber;

	/**
	 * Constructs a hyperlink to the specified file.
	 * 
	 * @param fileName
	 *            The file name to open
	 * @param lineNumber
	 *            The line number to select
	 */
	public PHPFileLink(String fileName, int lineNumber) {
		this.fileName = fileName;
		this.lineNumber = lineNumber;
	}

	public void linkActivated() {
		try {
			Object element = findSourceModule(fileName);
			if (element != null) {
				openElementInEditor(element);
				return;
			}
			if (EditorUtility.openLocalFile(fileName, lineNumber) != null) {
				return;
			}
		} catch (CoreException e) {
			PHPDebugUIPlugin.log(e);
		}
		// did not find source
		MessageDialog.openInformation(PHPDebugUIPlugin
				.getActiveWorkbenchShell(), Messages.PHPFileLink_0, MessageFormat
				.format(Messages.PHPFileLink_1, new Object[] { fileName }));
	}

	protected void openElementInEditor(Object element) throws CoreException {
		Assert.isNotNull(element);

		IEditorInput input = null;
		if (element instanceof IModelElement) {
			input = EditorUtility.getEditorInput((IModelElement) element);
		} else {
			input = org.eclipse.dltk.internal.ui.editor.EditorUtility
					.getEditorInput(element);
		}
		if (input == null) {
			return;
		}
		IEditorDescriptor descriptor = IDE.getEditorDescriptor(input.getName());
		IWorkbenchPage page = PHPDebugUIPlugin.getActivePage();
		IEditorPart editor = page.openEditor(input, descriptor.getId());
		org.eclipse.dltk.internal.ui.editor.EditorUtility.revealInEditor(
				editor, lineNumber - 1);
	}

	/**
	 * Finds {@link IFile} or {@link ISourceModule} matching the specified file
	 * name
	 * 
	 * @param fileName
	 * @return
	 */
	public static Object findSourceModule(String fileName) {
		IPath path = Path.fromOSString(fileName);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile f = root.getFileForLocation(path);
		if (f != null) {
			return f;
		}
		IDLTKLanguageToolkit toolkit = PHPLanguageToolkit.getDefault();
		PHPConsoleSourceModuleLookup lookup = new PHPConsoleSourceModuleLookup(
				toolkit);
		return lookup.findSourceModuleByLocalPath(path);
	}

	public void linkEntered() {
	}

	public void linkExited() {
	}
}
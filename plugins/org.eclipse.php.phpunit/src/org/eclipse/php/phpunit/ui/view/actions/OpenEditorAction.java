/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.phpunit.ui.view.actions;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.php.internal.core.model.PHPModelAccess;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.php.phpunit.PHPUnitMessages;
import org.eclipse.php.phpunit.PHPUnitPlugin;
import org.eclipse.php.phpunit.ui.view.PHPUnitView;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Abstract Action for opening a Java editor.
 */
public abstract class OpenEditorAction extends Action {

	public static final String GOTO_CALL = PHPUnitMessages.OpenEditorAction_Go_Call;
	public static final String GOTO_CLASS = PHPUnitMessages.OpenEditorAction_Go_Class;

	public static final String GOTO_FILE = PHPUnitMessages.OpenEditorAction_Go_File;
	public static final String GOTO_FUNCTION = PHPUnitMessages.OpenEditorAction_Go_Func;
	public static final String GOTO_METHOD = PHPUnitMessages.OpenEditorAction_Go_Method;
	public static final String GOTO_OCCURANCE = PHPUnitMessages.OpenEditorAction_Go_Occurence;

	protected String fClassName;
	protected String fFileName;
	protected int fLineNumber;
	protected PHPUnitView fTestRunner;

	protected OpenEditorAction(final String label, final PHPUnitView testRunner, final String className,
			final String fileName, final int lineNumber) {
		super(label == null ? GOTO_FILE : label);
		fClassName = className;
		fFileName = fileName;
		fLineNumber = lineNumber;
		fTestRunner = testRunner;
	}

	protected IType findClass(final IProject project, String className, final String fileName) {
		ISourceModule element = null;

		if (fileName != null && !fileName.isEmpty()) {
			Path path = new Path(fileName);
			IFile iFile;
			if (path.isAbsolute()) {
				iFile = project.getWorkspace().getRoot().getFileForLocation(path);
			} else {
				iFile = project.getWorkspace().getRoot().getFile(path);
			}

			element = DLTKCore.createSourceModuleFrom(iFile);
		}
		if (element == null) {
			IType[] classes = PHPModelAccess.getDefault().findTypes(className, MatchRule.EXACT, 0, 0,
					SearchEngine.createSearchScope(DLTKCore.create(project)), null);

			if (classes != null && classes.length > 0) {
				element = classes[0].getSourceModule();
			}
		}

		if (element != null) {
			if (className.contains("\\")) { //$NON-NLS-1$
				className = className.substring(1 + className.lastIndexOf("\\")); //$NON-NLS-1$
				try {
					final IType[] allTypes = element.getAllTypes();
					for (IType t : allTypes) {
						if (t.getElementName().equals(className)) {
							return t;
						}
					}
				} catch (ModelException e) {
					// will happen when data provider test case is given
				}
			}
			return element.getType(className);
		}

		return null;
	}

	protected abstract IModelElement findElement(IProject project, String elementName, String filename)
			throws CoreException;

	protected IMethod findFunction(final IProject project, final String functionName, final String fileName) {
		IModelElement element = null;
		if (fileName != null && !fileName.isEmpty()) {
			Path path = new Path(fileName);
			IFile iFile;
			if (path.isAbsolute() && path.getDevice() != null) {
				iFile = project.getWorkspace().getRoot().getFileForLocation(path);
			} else {
				iFile = project.getWorkspace().getRoot().getFile(path);
			}
			element = DLTKCore.createSourceModuleFrom(iFile);
		} else {
			IMethod[] methods = PHPModelAccess.getDefault().findMethods(functionName, MatchRule.EXACT, 0, 0,
					SearchEngine.createSearchScope(DLTKCore.create(project)), null);

			if (methods != null && methods.length > 0) {
				element = methods[0].getSourceModule();
			}
		}

		if (element != null && element.getElementType() == IModelElement.SOURCE_MODULE) {
			ISourceModule module = (ISourceModule) element;
			return module.getMethod(functionName);
		}
		return null;
	}

	protected String getClassName() {
		return fClassName;
	}

	protected String getFileName() {
		return fFileName;
	}

	protected IProject getLaunchedProject() {
		return fTestRunner.getProject();
	}

	protected Shell getShell() {
		return fTestRunner.getSite().getShell();
	}

	protected abstract void reveal(ITextEditor editor);

	@Override
	public void run() {
		ITextEditor textEditor = null;
		try {
			final IModelElement element = findElement(getLaunchedProject(), fClassName, fFileName);
			if (element != null) {
				textEditor = (ITextEditor) org.eclipse.dltk.internal.ui.editor.EditorUtility.openInEditor(element);
			} else if (fFileName != null) {
				textEditor = (ITextEditor) EditorUtility.openLocalFile(fFileName, fLineNumber);
			}

			if (textEditor != null) {
				reveal(textEditor);
			}
		} catch (ModelException e) {
			PHPUnitPlugin.log(e);
			new OpenEditorAtLineAction(StringUtils.EMPTY, fTestRunner, fFileName, fLineNumber, null).run();
		} catch (final CoreException e) {
			PHPUnitPlugin.log(e);
			ErrorDialog.openError(getShell(), PHPUnitMessages.OpenEditorAction_Error,
					PHPUnitMessages.OpenEditorAction_Cant_Open, e.getStatus());
			return;
		}

	}
}

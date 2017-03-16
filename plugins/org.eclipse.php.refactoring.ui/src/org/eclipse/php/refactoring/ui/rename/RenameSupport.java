/*******************************************************************************
 * Copyright (c) 2006, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.rename;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.internal.ui.refactoring.RefactoringExecutionHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.refactoring.core.rename.*;
import org.eclipse.php.refactoring.ui.PHPRefactoringUIMessages;
import org.eclipse.php.refactoring.ui.RefactoringUIPlugin;
import org.eclipse.swt.widgets.Shell;

/**
 * Central access point to execute rename refactorings.
 * <p>
 * Note: this class is not intended to be subclassed.
 * </p>
 * 
 * @inspiredby JDT
 */
public class RenameSupport {

	private RenameRefactoring fRefactoring;
	private RefactoringStatus fPreCheckStatus;

	/**
	 * Executes some light weight precondition checking. If the returned status
	 * is an error then the refactoring can't be executed at all. However,
	 * returning an OK status doesn't guarantee that the refactoring can be
	 * executed. It may still fail while performing the exhaustive precondition
	 * checking done inside the methods <code>openDialog</code> or
	 * <code>perform</code>.
	 * 
	 * The method is mainly used to determine enable/disablement of actions.
	 * 
	 * @return the result of the light weight precondition checking.
	 * 
	 * @throws CoreException
	 *             if an unexpected exception occurs while performing the
	 *             checking.
	 * 
	 * @see #openDialog(Shell)
	 * @see #perform(Shell, IRunnableContext)
	 */
	public IStatus preCheck() throws CoreException {
		ensureChecked();
		if (fPreCheckStatus.hasFatalError())
			return fPreCheckStatus.getEntryMatchingSeverity(RefactoringStatus.FATAL).toStatus();
		else
			return new Status(IStatus.OK, RefactoringUIPlugin.PLUGIN_ID, 0, "", null); //$NON-NLS-1$
	}

	/**
	 * Opens the refactoring dialog for this rename support.
	 * 
	 * @param parent
	 *            a shell used as a parent for the refactoring dialog.
	 * @throws CoreException
	 *             if an unexpected exception occurs while opening the dialog.
	 */
	public boolean openDialog(Shell parent) throws CoreException {
		ensureChecked();
		if (fPreCheckStatus.hasFatalError()) {
			showInformation(parent, fPreCheckStatus);
			return false;
		}
		UserInterfaceStarter starter = RenameUserInterfaceManager.getDefault().getStarter(fRefactoring);
		return starter.activate(fRefactoring, parent, true);
	}

	/**
	 * Executes the rename refactoring without showing a dialog to gather
	 * additional user input. Only an error dialog is shown (if necessary) to
	 * present the result of the refactoring's full precondition checking.
	 * <p>
	 * The method has to be called from within the UI thread.
	 * </p>
	 * 
	 * @param parent
	 *            a shell used as a parent for the error dialog.
	 * @param context
	 *            a {@link IRunnableContext} to execute the operation.
	 * 
	 * @throws InterruptedException
	 *             if the operation has been canceled by the user.
	 * @throws InvocationTargetException
	 *             if an error occurred while executing the operation.
	 * 
	 * @see #openDialog(Shell)
	 * @see IRunnableContext#run(boolean, boolean,
	 *      org.eclipse.jface.operation.IRunnableWithProgress)
	 */
	public void perform(Shell parent, IRunnableContext context) throws InterruptedException, InvocationTargetException {
		try {
			ensureChecked();
			if (fPreCheckStatus.hasFatalError()) {
				showInformation(parent, fPreCheckStatus);
				return;
			}
		} catch (CoreException e) {
			throw new InvocationTargetException(e);
		}

		// TODO: helpers here
		RefactoringExecutionHelper helper = new RefactoringExecutionHelper(fRefactoring,
				RefactoringCore.getConditionCheckingFailedSeverity(), false, parent, context);
		helper.perform(false);
	}

	/** Flag indication that no additional update is to be performed. */
	public static final int NONE = 0;

	/** Flag indicating that references are to be updated as well. */
	public static final int UPDATE_REFERENCES = 1 << 0;

	/**
	 * Flag indicating that textual matches in comments and in string literals
	 * are to be updated as well.
	 * 
	 * @since 3.0
	 */
	public static final int UPDATE_TEXTUAL_MATCHES = 1 << 6;

	/** Flag indicating that the getter method is to be updated as well. */
	public static final int UPDATE_GETTER_METHOD = 1 << 4;

	/** Flag indicating that the setter method is to be updated as well. */
	public static final int UPDATE_SETTER_METHOD = 1 << 5;

	private RenameSupport(AbstractRenameProcessor<?> processor, String newName, int flags) throws CoreException {
		fRefactoring = new RenameRefactoring(processor);
		initialize(fRefactoring, newName, flags);
	}

	/**
	 * Creates a new rename support for the given {@link }.
	 * 
	 * @param operatedFile
	 * 
	 * @param project
	 *            the {@link IJavaProject} to be renamed.
	 * @param newName
	 *            the project's new name. <code>null</code> is a valid value
	 *            indicating that no new name is provided.
	 * @param flags
	 *            flags controlling additional parameters. Valid flags are
	 *            <code>UPDATE_REFERENCES</code> or <code>NONE</code>.
	 * @return the {@link RenameSupport}.
	 * @throws CoreException
	 *             if an unexpected error occurred while creating the
	 *             {@link RenameSupport}.
	 */
	public static RenameSupport create(IResource operatedFile, int element, ASTNode locateNode, String newName,
			int flags) throws CoreException {
		AbstractRenameProcessor<?> processor = null;
		if (operatedFile instanceof IContainer) {
			processor = new RenameFolderProcessor((IContainer) operatedFile);
		} else {
			switch (element) {
			case PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE:
				processor = new RenameGlobalVariableProcessor((IFile) operatedFile, locateNode);
				break;
			case PhpElementConciliator.CONCILIATOR_FUNCTION:
				processor = new RenameFunctionProcessor((IFile) operatedFile, locateNode);
				break;
			case PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE:
				processor = new RenameLocalVariableProcessor((IFile) operatedFile, locateNode);
				break;
			case PhpElementConciliator.CONCILIATOR_CLASSNAME:
				processor = new RenameClassProcessor((IFile) operatedFile, locateNode);
				break;
			case PhpElementConciliator.CONCILIATOR_TRAITNAME:
				processor = new RenameTraitProcessor((IFile) operatedFile, locateNode);
				break;
			case PhpElementConciliator.CONCILIATOR_CONSTANT:
				processor = new RenameGlobalConstantProcessor((IFile) operatedFile, locateNode);
				break;
			case PhpElementConciliator.CONCILIATOR_CLASS_MEMBER:
				processor = new RenameClassMemberProcessor((IFile) operatedFile, locateNode);
				break;
			case PhpElementConciliator.CONCILIATOR_PROGRAM:
				processor = new RenameFileProcessor(operatedFile, (Program) locateNode);
				break;
			}
		}
		return processor == null ? null : new RenameSupport(processor, newName, flags);
	}

	private static void initialize(RenameRefactoring refactoring, String newName, int flags) {
		if (refactoring.getProcessor() == null)
			return;
		setNewName((INameUpdating) refactoring.getAdapter(INameUpdating.class), newName);

		ITextUpdating text = (ITextUpdating) refactoring.getAdapter(ITextUpdating.class);
		if (text != null) {
			text.setUpdateTextualMatches(updateTextualMatches(flags));
		}
	}

	private static void setNewName(INameUpdating refactoring, String newName) {
		if (newName != null)
			refactoring.setNewElementName(newName);
	}

	private static boolean updateTextualMatches(int flags) {
		int TEXT_UPDATES = UPDATE_TEXTUAL_MATCHES;
		return (flags & TEXT_UPDATES) != 0;
	}

	private void ensureChecked() throws CoreException {
		if (fPreCheckStatus == null) {
			if (!fRefactoring.isApplicable()) {
				fPreCheckStatus = RefactoringStatus
						.createFatalErrorStatus(PHPRefactoringUIMessages.getString("RenameSupport_not_available")); //$NON-NLS-1$
			} else {
				fPreCheckStatus = new RefactoringStatus();
			}
		}
	}

	private void showInformation(Shell parent, RefactoringStatus status) {
		String message = status.getMessageMatchingSeverity(RefactoringStatus.FATAL);
		MessageDialog.openInformation(parent, PHPRefactoringUIMessages.getString("RenameSupport_dialog_title"), //$NON-NLS-1$
				message);
	}

}

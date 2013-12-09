package org.eclipse.php.internal.ui.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.action.Action;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.LibraryFolderHelper;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class UseAsLibraryFolderAction extends Action {

	private IModelElement[] fElements;

	public UseAsLibraryFolderAction(IWorkbenchSite site,
			IModelElement[] elements) {
		if (elements.length == 0)
			throw new IllegalArgumentException("empty elements array");

		fElements = elements;

		setText(Messages.LibraryFolderAction_UseAsLibraryFolder_label);
		setImageDescriptor(PHPPluginImages.DESC_OBJS_PHP_LIBFOLDER);
	}

	@Override
	public void run() {
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException,
					InterruptedException {
				LibraryFolderHelper.useAsLibraryFolder(fElements, monitor);
			}
		};

		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(op);
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}
	}

}

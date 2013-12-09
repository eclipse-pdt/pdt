package org.eclipse.php.internal.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.LibraryFolderHelper;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.wst.validation.internal.DisabledResourceManager;

public class UseAsSourceFolderAction extends Action {

	private IWorkbenchSite fSite;
	private IModelElement[] fElements;

	public UseAsSourceFolderAction(IWorkbenchSite site, IModelElement[] elements) {
		if (elements.length == 0)
			throw new IllegalArgumentException("empty elements array");

		fSite = site;
		fElements = elements;

		setText(Messages.LibraryFolderAction_UseAsSourceFolder_label);
		setImageDescriptor(PHPPluginImages.DESC_OBJS_PHPFOLDER_ROOT);
	}

	@Override
	public void run() {
		boolean askForConfirmation = false;
		Collection<IModelElement> topmostElements = new HashSet<IModelElement>();

		for (IModelElement element : fElements) {
			IModelElement topmostLibraryFolder = getTopmostLibraryFolder(element);
			topmostElements.add(topmostLibraryFolder);
			if (!element.equals(topmostLibraryFolder)) {
				askForConfirmation = true;
			}
		}

		final IModelElement[] elements = topmostElements
				.toArray(new IModelElement[topmostElements.size()]);

		if (askForConfirmation) {
			String title = Messages.LibraryFolderAction_Dialog_title;

			String message = NLS.bind(
					Messages.LibraryFolderAction_Dialog_description,
					StringUtils.join(getSortedElementNames(elements), ",\n\t"));

			if (!MessageDialog.openConfirm(fSite.getShell(), title, message))
				return;
		}

		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException,
					InterruptedException {
				LibraryFolderHelper.useAsSourceFolder(elements, monitor);
			}
		};

		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(op);
		} catch (Exception e) {
			PHPUiPlugin.log(e);
		}

	}

	private String[] getSortedElementNames(IModelElement[] elements) {
		String[] names = new String[elements.length];

		for (int i = 0; i < elements.length; i++) {
			names[i] = elements[i].getElementName();
		}

		Arrays.sort(names);

		return names;
	}

	public IModelElement getTopmostLibraryFolder(IModelElement element) {
		IResource resource = element.getResource();
		if (resource == null)
			return null;

		if (resource.getType() == IResource.FILE) {
			resource = resource.getParent();
		}

		while (resource.getType() == IResource.FOLDER) {
			if (DisabledResourceManager.getDefault().isDisabled(resource)) {
				return DLTKCore.create(resource);
			}
			resource = resource.getParent();
		}

		return null;
	}

}

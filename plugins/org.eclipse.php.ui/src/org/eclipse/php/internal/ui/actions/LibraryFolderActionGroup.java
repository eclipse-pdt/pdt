package org.eclipse.php.internal.ui.actions;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.ui.IContextMenuConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.core.util.LibraryFolderUtil;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;

public class LibraryFolderActionGroup extends ActionGroup {

	private IWorkbenchSite fSite;

	public LibraryFolderActionGroup(IViewPart part) {
		fSite = part.getSite();
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);

		IAction action = getActionFromSelection();
		if (action != null) {
			menu.appendToGroup(IContextMenuConstants.GROUP_REORGANIZE, action);
		}
	}

	private IAction getActionFromSelection() {
		ISelection sel = fSite.getSelectionProvider().getSelection();
		if (!(sel instanceof IStructuredSelection))
			return null;

		IStructuredSelection selection = (IStructuredSelection) sel;
		Iterator<?> iterator = selection.iterator();

		Collection<IModelElement> selected = new HashSet<IModelElement>();

		while (iterator.hasNext()) {
			Object obj = iterator.next();

			if (!(obj instanceof IModelElement))
				return null;

			IModelElement element = (IModelElement) obj;
			IResource resource = element.getResource();

			if (resource == null)
				return null;

			if (resource.getType() != IResource.FOLDER)
				return null;

			selected.add(element);
		}

		IModelElement[] elements = selected.toArray(new IModelElement[selected
				.size()]);

		if (elements.length == 0)
			return null;

		if (!allOfSameKind(elements))
			return null;

		if (LibraryFolderUtil.inLibraryFolder(elements[0])) {
			return new UseAsSourceFolderAction(fSite, elements);
		} else {
			return new UseAsLibraryFolderAction(fSite, elements);
		}
	}

	private boolean allOfSameKind(IModelElement[] elements) {
		int libraryFolderCount = 0;

		for (IModelElement element : elements) {
			if (LibraryFolderUtil.inLibraryFolder(element)) {
				libraryFolderCount++;
			}
		}

		return libraryFolderCount == 0 || libraryFolderCount == elements.length;
	}
}

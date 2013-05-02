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
package org.eclipse.php.internal.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.actions.ActionMessages;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.buildpath.AddSourceFolderWizard;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.newsourcepage.ConfigureBuildPathAction;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.IContextMenuConstants;
import org.eclipse.dltk.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.texteditor.IUpdate;

/**
 * Action group that adds the source and generate actions to a part's context
 * menu and installs handlers for the corresponding global menu actions.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * 
 */
public class GenerateIncludePathActionGroup extends ActionGroup /*
																 * org.eclipse.dltk
																 * .internal.ui.
																 * wizards
																 * .buildpath
																 * .newsourcepage
																 * .
																 * GenerateBuildPathActionGroup
																 */{
	/**
	 * Pop-up menu: id of the source sub menu (value
	 * <code>org.eclipse.dltk.ui.buildpath.menu</code>).
	 * 
	 * 
	 */
	public static final String MENU_ID = "org.eclipse.php.ui.includepath.menu"; //$NON-NLS-1$

	/**
	 * Pop-up menu: id of the build path (add /remove) group of the build path
	 * sub menu (value <code>buildpathGroup</code>).
	 * 
	 * 
	 */
	public static final String GROUP_INCLUDEPATH = "includepathGroup"; //$NON-NLS-1$

	/**
	 * Pop-up menu: id of the filter (include / exclude) group of the build path
	 * sub menu (value <code>filterGroup</code>).
	 * 
	 * 
	 */
	public static final String GROUP_FILTER = "filterGroup"; //$NON-NLS-1$

	/**
	 * Pop-up menu: id of the customize (filters / output folder) group of the
	 * build path sub menu (value <code>customizeGroup</code>).
	 * 
	 * 
	 */
	public static final String GROUP_CUSTOMIZE = "customizeGroup"; //$NON-NLS-1$

	private static class NoActionAvailable extends Action {
		public NoActionAvailable() {
			setEnabled(false);
			setText(NewWizardMessages.GenerateBuildPathActionGroup_no_action_available);
		}
	}

	private Action fNoActionAvailable = new NoActionAvailable();

	private static abstract class OpenPHPIncludePathWizardAction extends
			AbstractOpenWizardAction implements ISelectionChangedListener {
		/**
		 * {@inheritDoc}
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			ISelection selection = event.getSelection();
			if (selection instanceof IStructuredSelection) {
				setEnabled(selectionChanged((IStructuredSelection) selection));
			} else {
				setEnabled(selectionChanged(StructuredSelection.EMPTY));
			}
		}

		// Needs to be public for the operation, will be protected later.
		public abstract boolean selectionChanged(IStructuredSelection selection);
	}

	private abstract static class CreateSourceFolderAction extends
			OpenPHPIncludePathWizardAction {

		private AddSourceFolderWizard fAddSourceFolderWizard;
		private IScriptProject fSelectedProject;
		private final boolean fIsLinked;

		public CreateSourceFolderAction(boolean isLinked) {
			fIsLinked = isLinked;
		}

		/**
		 * {@inheritDoc}
		 */
		protected INewWizard createWizard() throws CoreException {
			BPListElement newEntrie = new BPListElement(fSelectedProject,
					IBuildpathEntry.BPE_SOURCE, false);
			BPListElement[] existing = BPListElement
					.createFromExisting(fSelectedProject);
			boolean isProjectSrcFolder = BPListElement.isProjectSourceFolder(
					existing, fSelectedProject);
			fAddSourceFolderWizard = new AddSourceFolderWizard(existing,
					newEntrie, fIsLinked, false, false, isProjectSrcFolder,
					isProjectSrcFolder);
			return fAddSourceFolderWizard;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean selectionChanged(IStructuredSelection selection) {
			if (selection.size() == 1
					&& selection.getFirstElement() instanceof IScriptProject) {
				fSelectedProject = (IScriptProject) selection.getFirstElement();
				return true;
			}
			return false;
		}

		public List getBPListElements() {
			return fAddSourceFolderWizard.getExistingEntries();
		}

	}

	public static class CreateLocalSourceFolderAction extends
			CreateSourceFolderAction {

		public CreateLocalSourceFolderAction() {
			super(false);
			setText(ActionMessages.OpenNewSourceFolderWizardAction_text2); //$NON-NLS-1$
			setDescription(ActionMessages.OpenNewSourceFolderWizardAction_description); //$NON-NLS-1$
			setToolTipText(ActionMessages.OpenNewSourceFolderWizardAction_tooltip); //$NON-NLS-1$
			setImageDescriptor(DLTKPluginImages.DESC_TOOL_NEWPACKROOT);
			if (DLTKCore.DEBUG) {
				System.err.println(Messages.GenerateIncludePathActionGroup_0);
			}
			// PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
			// IDLTKHelpContextIds.OPEN_SOURCEFOLDER_WIZARD_ACTION);
		}
	}

	public static class CreateLinkedSourceFolderAction extends
			CreateSourceFolderAction {

		public CreateLinkedSourceFolderAction() {
			super(true);
			setText(NewWizardMessages.NewSourceContainerWorkbookPage_ToolBar_Link_label);
			setToolTipText(NewWizardMessages.NewSourceContainerWorkbookPage_ToolBar_Link_tooltip);
			setImageDescriptor(DLTKPluginImages.DESC_ELCL_ADD_LINKED_SOURCE_TO_BUILDPATH);
			setDescription(NewWizardMessages.PackageExplorerActionGroup_FormText_createLinkedFolder);
		}
	}

	private IWorkbenchSite fSite;
	private List/* <Action> */fActions;

	private String fGroupName = IContextMenuConstants.GROUP_REORGANIZE;

	/**
	 * Creates a new <code>GenerateActionGroup</code>. The group requires that
	 * the selection provided by the page's selection provider is of type
	 * <code>org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param page
	 *            the page that owns this action group
	 */
	public GenerateIncludePathActionGroup(Page page) {
		this(page.getSite());
	}

	/**
	 * Creates a new <code>GenerateActionGroup</code>. The group requires that
	 * the selection provided by the part's selection provider is of type
	 * <code>org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param part
	 *            the view part that owns this action group
	 */
	public GenerateIncludePathActionGroup(IViewPart part) {
		this(part.getSite());
	}

	private GenerateIncludePathActionGroup(IWorkbenchSite site) {
		fSite = site;
		fActions = new ArrayList();

		final RemoveFromIncludepathAction remove = new RemoveFromIncludepathAction(
				site);
		fActions.add(remove);

		final ConfigurePHPIncludePathAction configure = new ConfigurePHPIncludePathAction(
				site);
		fActions.add(configure);

		final ISelectionProvider provider = fSite.getSelectionProvider();
		for (Iterator iter = fActions.iterator(); iter.hasNext();) {
			Action action = (Action) iter.next();
			if (action instanceof ISelectionChangedListener) {
				provider.addSelectionChangedListener((ISelectionChangedListener) action);
			}
		}

	}

	/*
	 * (non-Javadoc) Method declared in ActionGroup
	 */
	public void fillActionBars(IActionBars actionBar) {
		super.fillActionBars(actionBar);
		setGlobalActionHandlers(actionBar);
	}

	/*
	 * (non-Javadoc) Method declared in ActionGroup
	 */
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		if (!canOperateOnSelection())
			return;
		String menuText = PHPUIMessages.IncludePathActionGroup_label;
		IMenuManager subMenu = new MenuManager(menuText, MENU_ID);
		subMenu.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				fillViewSubMenu(manager);
			}
		});
		subMenu.setRemoveAllWhenShown(true);
		subMenu.add(new ConfigureBuildPathAction(fSite));
		menu.appendToGroup(fGroupName, subMenu);
	}

	private void fillViewSubMenu(IMenuManager source) {
		int added = 0;
		int i = 0;
		for (Iterator iter = fActions.iterator(); iter.hasNext();) {
			Action action = (Action) iter.next();
			if (action instanceof IUpdate)
				((IUpdate) action).update();

			if (i == 2)
				source.add(new Separator(GROUP_INCLUDEPATH));
			else if (i == 8)
				source.add(new Separator(GROUP_FILTER));
			else if (i == 10)
				source.add(new Separator(GROUP_CUSTOMIZE));
			added += addAction(source, action);
			i++;
		}

		if (added == 0) {
			source.add(fNoActionAvailable);
		}
	}

	private void setGlobalActionHandlers(IActionBars actionBar) {
		// TODO implement
	}

	private int addAction(IMenuManager menu, IAction action) {
		if (action != null && action.isEnabled()) {
			menu.add(action);
			return 1;
		}
		return 0;
	}

	private boolean canOperateOnSelection() {
		ISelection sel = fSite.getSelectionProvider().getSelection();
		if (!(sel instanceof IStructuredSelection))
			return false;
		IStructuredSelection selection = (IStructuredSelection) sel;

		if (selection.isEmpty()) {
			return false;
		}

		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			Object element = iter.next();

			if (element instanceof IProject) {
				IProject project = (IProject) element;
				if (!project.isAccessible()) {
					return false;
				}
			}

			if (element instanceof IWorkingSet)
				return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		if (fActions != null) {
			final ISelectionProvider provider = fSite.getSelectionProvider();
			for (Iterator iter = fActions.iterator(); iter.hasNext();) {
				Action action = (Action) iter.next();
				if (action instanceof ISelectionChangedListener)
					provider.removeSelectionChangedListener((ISelectionChangedListener) action);
			}
		}
		fActions = null;
		super.dispose();
	}
}

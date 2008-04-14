/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.CollectionUtils;
import org.eclipse.php.internal.ui.util.ParentChecker;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.CopyFilesAndFoldersOperation;
import org.eclipse.ui.actions.CopyProjectOperation;
import org.eclipse.ui.part.ResourceTransfer;

public class PasteAction extends SelectionDispatchAction {

	private final Clipboard fClipboard;
	private Text fSelectedText;

	private abstract static class Paster {
		private final Shell fShell;
		private final Clipboard fClipboard2;

		protected Paster(Shell shell, Clipboard clipboard) {
			fShell = shell;
			fClipboard2 = clipboard;
		}

		protected final Shell getShell() {
			return fShell;
		}

		protected final Clipboard getClipboard() {
			return fClipboard2;
		}

		protected final IResource[] getClipboardResources(TransferData[] availableDataTypes) {
			Transfer transfer = ResourceTransfer.getInstance();
			if (isAvailable(transfer, availableDataTypes)) {
				return (IResource[]) getContents(fClipboard2, transfer, getShell());
			}
			return null;
		}

		static final PHPCodeData[] EMPTY_PHPELEMENTS = new PHPCodeData[0];

		protected final PHPCodeData[] getClipboardPHPElements(TransferData[] availableDataTypes) {
			if (PHPUiConstants.DISABLE_ELEMENT_REFACTORING)
				return EMPTY_PHPELEMENTS;
			throw new RuntimeException(PHPUIMessages.getString("PasteAction.0")); //$NON-NLS-1$
			//			Transfer transfer= PHPElementTransfer.getInstance();
			//			if (isAvailable(transfer, availableDataTypes)) {
			//				return (Object[])getContents(fClipboard2, transfer, getShell());
			//			}
			//			return null;
		}

		public abstract void paste(Object[] selectedPHPElements, IResource[] selectedResources, TransferData[] availableTypes) throws CoreException, InterruptedException, InvocationTargetException;

		public abstract boolean canEnable(TransferData[] availableTypes) throws CoreException;

		public abstract boolean canPasteOn(Object[] selectedPHPElements, IResource[] selectedResources) throws CoreException;
	}

	private static class ProjectPaster extends Paster {

		protected ProjectPaster(Shell shell, Clipboard clipboard) {
			super(shell, clipboard);
		}

		public boolean canEnable(TransferData[] availableDataTypes) {
			boolean resourceTransfer = isAvailable(ResourceTransfer.getInstance(), availableDataTypes);
			boolean phpElementTransfer = false;//isAvailable(PHPElementTransfer.getInstance(), availableDataTypes);
			if (!phpElementTransfer)
				return canPasteSimpleProjects(availableDataTypes);
			if (!resourceTransfer)
				return false;
			return canPasteSimpleProjects(availableDataTypes);
		}

		public void paste(Object[] phpElements, IResource[] resources, TransferData[] availableTypes) {
			pasteProjects(availableTypes);
		}

		private void pasteProjects(TransferData[] availableTypes) {
			pasteProjects(getProjectsToPaste(availableTypes));
		}

		private void pasteProjects(IProject[] projects) {
			Shell shell = getShell();
			for (int i = 0; i < projects.length; i++) {
				new CopyProjectOperation(shell).copyProject(projects[i]);
			}
		}

		private IProject[] getProjectsToPaste(TransferData[] availableTypes) {
			IResource[] resources = getClipboardResources(availableTypes);
			PHPCodeData[] phpElements = getClipboardPHPElements(availableTypes);
			Set result = new HashSet();
			if (resources != null)
				result.addAll(Arrays.asList(resources));
			if (phpElements != null)
				result.addAll(Arrays.asList(CollectionUtils.getNotNulls(CollectionUtils.getResources(phpElements))));
			Assert.isTrue(result.size() > 0);
			return (IProject[]) result.toArray(new IProject[result.size()]);
		}

		public boolean canPasteOn(Object[] phpElements, IResource[] resources) {
			return true;
		}

		private boolean canPasteSimpleProjects(TransferData[] availableDataTypes) {
			IResource[] resources = getClipboardResources(availableDataTypes);
			if (resources == null || resources.length == 0)
				return false;
			for (int i = 0; i < resources.length; i++) {
				if (resources[i].getType() != IResource.PROJECT || !((IProject) resources[i]).isOpen())
					return false;
			}
			return true;
		}
	}

	private static class FilePaster extends Paster {
		protected FilePaster(Shell shell, Clipboard clipboard) {
			super(shell, clipboard);
		}

		public void paste(Object[] phpElements, IResource[] resources, TransferData[] availableTypes) throws CoreException {

			// try a resource transfer
			ResourceTransfer resTransfer = ResourceTransfer.getInstance();
			IResource[] resourceData = getClipboardResources(availableTypes);

			if (resourceData != null && resourceData.length > 0) {
				if (resourceData[0].getType() == IResource.PROJECT) {
					// enablement checks for all projects
					for (int i = 0; i < resourceData.length; i++) {
						CopyProjectOperation operation = new CopyProjectOperation(getShell());
						operation.copyProject((IProject) resourceData[i]);
					}
				} else {
					// enablement should ensure that we always have access to a container
					IContainer container = getAsContainer(getTarget(phpElements, resources));

					CopyFilesAndFoldersOperation operation = new CopyFilesAndFoldersOperation(getShell());
					operation.copyResources(resourceData, container);
				}
				return;
			}

			// try a file transfer
			FileTransfer fileTransfer = FileTransfer.getInstance();
			String[] fileData = getClipboardFiles(availableTypes);

			if (fileData != null) {
				// enablement should ensure that we always have access to a container
				IContainer container = getAsContainer(getTarget(phpElements, resources));

				CopyFilesAndFoldersOperation operation = new CopyFilesAndFoldersOperation(getShell());
				operation.copyFiles(fileData, container);
			}

		}

		private String[] getClipboardFiles(TransferData[] availableDataTypes) {
			Transfer transfer = FileTransfer.getInstance();
			if (isAvailable(transfer, availableDataTypes)) {
				return (String[]) getContents(getClipboard(), transfer, getShell());
			}
			return null;
		}

		private Object getTarget(Object[] phpElements, IResource[] resources) {
			if (phpElements.length + resources.length == 1) {
				if (phpElements.length == 1)
					return phpElements[0];
				else
					return resources[0];
			} else
				return getCommonParent(phpElements, resources);
		}

		public boolean canPasteOn(Object[] phpElements, IResource[] resources) throws CoreException {
			Object target = getTarget(phpElements, resources);
			return target != null && canPasteFilesOn(getAsContainer(target));
		}

		public boolean canEnable(TransferData[] availableDataTypes) throws CoreException {
			return isAvailable(FileTransfer.getInstance(), availableDataTypes);
		}

		private boolean canPasteFilesOn(Object target) {
			return target instanceof IContainer;
		}

		private IContainer getAsContainer(Object target) throws CoreException {
			if (target == null)
				return null;
			if (target instanceof IContainer)
				return (IContainer) target;
			if (target instanceof IFile)
				return ((IFile) target).getParent();
			return null;
		}

		private Object getCommonParent(Object[] phpElements, IResource[] resources) {
			return new ParentChecker(resources, (PHPCodeData[]) phpElements).getCommonParent();
		}
	}

	private static class ResourceNameTextPaster extends Paster {
		private final Text textWidget;

		protected ResourceNameTextPaster(Shell shell, Clipboard clipboard, Text textWidget) {
			super(shell, clipboard);
			this.textWidget = textWidget;
		}

		public void paste(Object[] phpElements, IResource[] resources, TransferData[] availableTypes) throws CoreException {
			textWidget.paste();
		}

		public boolean canPasteOn(Object[] phpElements, IResource[] resources) throws CoreException {
			return true;
		}

		public boolean canEnable(TransferData[] availableDataTypes) throws CoreException {
			return isAvailable(TextTransfer.getInstance(), availableDataTypes);
		}
	}

	private static class PHPElementAndResourcePaster extends Paster {

		protected PHPElementAndResourcePaster(Shell shell, Clipboard clipboard) {
			super(shell, clipboard);
		}

		private TransferData[] fAvailableTypes;

		public void paste(Object[] phpElements, IResource[] resources, TransferData[] availableTypes) throws CoreException, InterruptedException, InvocationTargetException {
			IResource[] clipboardResources = getClipboardResources(availableTypes);
			if (clipboardResources == null)
				clipboardResources = new IResource[0];
			Object[] clipboardPHPElements = getClipboardPHPElements(availableTypes);
			if (clipboardPHPElements == null)
				clipboardPHPElements = new Object[0];

			Object destination = getTarget(phpElements, resources);
			if (destination instanceof PHPCodeData)
				pasteToPHP(clipboardPHPElements, clipboardResources, (PHPCodeData) destination);
			else if (destination instanceof IResource)
				pasteToResource(clipboardPHPElements, clipboardResources, (IResource) destination);
		}

		private void pasteToResource(Object[] clipboardPHPElements, IResource[] clipboardResources, IResource resource) {
			new CopyFilesAndFoldersOperation(getShell()).copyResources(clipboardResources, (IContainer) resource);
		}

		private void pasteToPHP(Object[] clipboardPHPElements, IResource[] clipboardResources, PHPCodeData data) {
			// TODO Auto-generated method stub
		}

		private Object getTarget(Object[] phpElements, IResource[] resources) {
			if (phpElements.length + resources.length == 1) {
				if (phpElements.length == 1)
					return phpElements[0];
				else
					return resources[0];
			} else
				return getCommonParent(phpElements, resources);
		}

		private Object getCommonParent(Object[] phpElements, IResource[] resources) {
			return new ParentChecker(resources, (PHPCodeData[]) phpElements).getCommonParent();
		}

		public boolean canPasteOn(Object[] phpElements, IResource[] resources) throws CoreException {
			IResource[] clipboardResources = getClipboardResources(fAvailableTypes);
			if (clipboardResources == null)
				clipboardResources = new IResource[0];
			Object[] clipboardPHPElements = getClipboardPHPElements(fAvailableTypes);
			if (clipboardPHPElements == null)
				clipboardPHPElements = new Object[0];
			Object destination = getTarget(phpElements, resources);
			if (destination instanceof PHPCodeData)
				return canPasteElement(clipboardPHPElements, clipboardResources, (PHPCodeData) destination);
			if (destination instanceof IResource)
				return canPasteResource(clipboardPHPElements, clipboardResources, (IResource) destination);
			return false;
		}

		public boolean canPasteResource(Object[] clipboardPHPElements, IResource[] clipboardResources, IResource resource) {
			if (!(resource instanceof IContainer))
				return false;
			for (int i = 0; i < clipboardResources.length; i++) {
				if (!(clipboardResources[i] instanceof IFile) && !(clipboardResources[i] instanceof IFolder))
					return false;
			}
			return true;
		}

		private boolean canPasteElement(Object[] clipboardPHPElements, IResource[] clipboardResources, PHPCodeData data) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean canEnable(TransferData[] availableTypes) {
			fAvailableTypes = availableTypes;
			return
			//isAvailable(PHPElementTransfer.getInstance(), availableTypes) || 
			isAvailable(ResourceTransfer.getInstance(), availableTypes);
		}
	}

	public PasteAction(IWorkbenchSite site, Clipboard clipboard) {
		super(site);
		Assert.isNotNull(clipboard);
		fClipboard = clipboard;

		setText(PHPUIMessages.getString("PasteAction_text"));
		setDescription(PHPUIMessages.getString("PasteAction_desc"));

		ISharedImages workbenchImages = PHPUiPlugin.getDefault().getWorkbench().getSharedImages();
		setDisabledImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
		setImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setHoverImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));

		update(getSelection());
		// HELP - PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.PASTE_ACTION);
	}

	public void selectionChanged(IStructuredSelection selection) {

		if (PHPUiConstants.DISABLE_ELEMENT_REFACTORING) {
			boolean enabled = true;
			Object[] sels = selection.toArray();
			for (int i = 0; i < sels.length; i++) {
				if (sels[i] instanceof PHPCodeData && !(sels[i] instanceof PHPFileData))
					enabled = false;
			}
			setEnabled(enabled);
		}
	}

	private static Object getContents(final Clipboard clipboard, final Transfer transfer, Shell shell) {
		final Object[] result = new Object[1];
		shell.getDisplay().syncExec(new Runnable() {
			public void run() {
				result[0] = clipboard.getContents(transfer);
			}
		});
		return result[0];
	}

	private static boolean isAvailable(Transfer transfer, TransferData[] availableDataTypes) {
		for (int i = 0; i < availableDataTypes.length; i++) {
			if (transfer.isSupportedType(availableDataTypes[i]))
				return true;
		}
		return false;
	}

	public void run(IStructuredSelection selection) {

		try {
			TransferData[] availableTypes = fClipboard.getAvailableTypes();
			List elements = selection.toList();
			IResource[] resources = ActionUtils.getResources(elements, true);
			Object[] phpElements = ActionUtils.getPHPElements(elements, true);
			Paster[] pasters = createEnabledPasters(availableTypes);
			for (int i = 0; i < pasters.length; i++) {
				if (pasters[i].canPasteOn(phpElements, resources)) {
					pasters[i].paste(phpElements, resources, availableTypes);
					return;// one is enough
				}
			}
			MessageDialog.openError(PHPUiPlugin.getActiveWorkbenchShell(), PHPUIMessages.getString("RefactoringAction_refactoring"), PHPUIMessages.getString("RefactoringAction_disabled"));
		} catch (CoreException e) {
			PHPUiPlugin.log(e);
		} catch (InvocationTargetException e) {
			PHPUiPlugin.log(e);
		} catch (InterruptedException e) {
			// OK
		}
	}

	private Paster[] createEnabledPasters(TransferData[] availableDataTypes) throws CoreException {
		Paster paster;
		Shell shell = getShell();
		List result = new ArrayList(2);
		paster = new ProjectPaster(shell, fClipboard);
		if (paster.canEnable(availableDataTypes))
			result.add(paster);

		paster = new PHPElementAndResourcePaster(shell, fClipboard);
		if (paster.canEnable(availableDataTypes))
			result.add(paster);

		paster = new FilePaster(shell, fClipboard);
		if (paster.canEnable(availableDataTypes))
			result.add(paster);

		if (fSelectedText != null) {
			paster = new ResourceNameTextPaster(shell, fClipboard, fSelectedText);
			if (paster.canEnable(availableDataTypes)) {
				result.add(paster);
			}
		}
		return (Paster[]) result.toArray(new Paster[result.size()]);
	}

	/**
	 * Override to handle text selections in Tree
	 */
	public void runWithEvent(Event event) {
		if (event.widget instanceof Text) {
			Text text = (Text) event.widget;
			fSelectedText = text;
		}
		run();
	}
}

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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPElementLabelProvider;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ResourceTransfer;

public class CopyToClipboardAction extends SelectionDispatchAction {

	private final Clipboard fClipboard;
	private SelectionDispatchAction fPasteAction;//may be null
	private boolean fAutoRepeatOnFailure = false;

	private static class ClipboardCopier {
		private final boolean fAutoRepeatOnFailure;
		private final IResource[] fResources;
		private final Object[] fPHPElements;
		private final Clipboard fClipboard;
		private final Shell fShell;
		private final ILabelProvider fLabelProvider;

		private ClipboardCopier(IResource[] resources, Object[] phpElements, Clipboard clipboard, Shell shell, boolean autoRepeatOnFailure) {

			fResources = resources;
			fPHPElements = phpElements;
			fClipboard = clipboard;
			fShell = shell;
			fLabelProvider = createLabelProvider();
			fAutoRepeatOnFailure = autoRepeatOnFailure;

		}

		public void copyToClipboard() throws CoreException {
			//Set<String> fileNames
			Set fileNames = new HashSet(fResources.length + fPHPElements.length);
			StringBuffer namesBuf = new StringBuffer();
			processResources(fileNames, namesBuf);
			processPHPElements(fileNames, namesBuf);

			String[] fileNameArray = (String[]) fileNames.toArray(new String[fileNames.size()]);
			copyToClipboard(fResources, fileNameArray, namesBuf.toString(), fPHPElements, 0);
		}

		private void copyToClipboard(IResource[] resources, String[] fileNames, String names, Object[] phpElements, int repeat) {
			final int repeat_max_count = 10;
			try {
				fClipboard.setContents(createDataArray(resources, phpElements, fileNames, names), createDataTypeArray(resources, phpElements, fileNames));
			} catch (SWTError e) {
				if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD || repeat >= repeat_max_count)
					throw e;
				if (fAutoRepeatOnFailure) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// do nothing.
					}
				}
				if (fAutoRepeatOnFailure || MessageDialog.openQuestion(fShell, PHPUIMessages.CopyToClipboardAction_4, PHPUIMessages.CopyToClipboardAction_5))
					copyToClipboard(resources, fileNames, names, phpElements, repeat + 1);
			}
		}

		private void processResources(Set fileNames, StringBuffer namesBuf) {
			for (int i = 0; i < fResources.length; i++) {
				IResource resource = fResources[i];
				addFileName(fileNames, resource);

				if (i > 0)
					namesBuf.append('\n');
				namesBuf.append(getName(resource));
			}
		}

		private void processPHPElements(Set fileNames, StringBuffer namesBuf) {
			for (int i = 0; i < fPHPElements.length; i++) {

				if (fPHPElements[i] instanceof PHPFileData) {
					addFileName(fileNames, PHPModelUtil.getResource(fPHPElements[i]));

				}
				if (fResources.length > 0 || i > 0)
					namesBuf.append('\n');
				namesBuf.append(getName(fPHPElements[i]));
			}
		}

		private static Transfer[] createDataTypeArray(IResource[] resources, Object[] phpElements, String[] fileNames) {
			List result = new ArrayList(4);
			if (resources.length != 0)
				result.add(ResourceTransfer.getInstance());
			if (phpElements.length != 0) {
				throw new RuntimeException("implement me");
				//				result.add(PHPElementTransfer.getInstance());
			}
			if (fileNames.length != 0)
				result.add(FileTransfer.getInstance());
			result.add(TextTransfer.getInstance());
			return (Transfer[]) result.toArray(new Transfer[result.size()]);
		}

		private static Object[] createDataArray(IResource[] resources, Object[] phpElements, String[] fileNames, String names) {
			List result = new ArrayList(4);
			if (resources.length != 0)
				result.add(resources);
			if (phpElements.length != 0)
				result.add(phpElements);
			if (fileNames.length != 0)
				result.add(fileNames);
			result.add(names);
			return result.toArray();
		}

		private static ILabelProvider createLabelProvider() {
			return new PHPElementLabelProvider(PHPElementLabelProvider.SHOW_VARIABLE + PHPElementLabelProvider.SHOW_PARAMETERS + PHPElementLabelProvider.SHOW_TYPE);
		}

		private String getName(IResource resource) {
			return fLabelProvider.getText(resource);
		}

		private String getName(Object phpElement) {
			return fLabelProvider.getText(phpElement);
		}

		private static void addFileNames(Set fileName, IResource[] resources) {
			for (int i = 0; i < resources.length; i++) {
				addFileName(fileName, resources[i]);
			}
		}

		private static void addFileName(Set fileName, IResource resource) {
			if (resource == null)
				return;
			IPath location = resource.getLocation();
			// location may be null. See bug 29491.
			if (location != null)
				fileName.add(location.toOSString());
		}
	}

	public CopyToClipboardAction(IWorkbenchSite site, Clipboard clipboard, SelectionDispatchAction pasteAction) {
		super(site);
		setText(PHPUIMessages.CopyToClipboardAction_text);
		setDescription(PHPUIMessages.CopyToClipboardAction_desc);
		org.eclipse.core.runtime.Assert.isNotNull(clipboard);
		fClipboard = clipboard;
		fPasteAction = pasteAction;
		ISharedImages workbenchImages = getWorkbenchSharedImages();
		setDisabledImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setHoverImageDescriptor(workbenchImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		update(getSelection());

		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.COPY_ACTION);
	}

	public void setAutoRepeatOnFailure(boolean autorepeatOnFailure) {
		fAutoRepeatOnFailure = autorepeatOnFailure;
	}

	private static ISharedImages getWorkbenchSharedImages() {
		return PHPUiPlugin.getDefault().getWorkbench().getSharedImages();
	}

	public void selectionChanged(IStructuredSelection selection) {
		List elements = selection.toList();
		IResource[] resources = ActionUtils.getResources(elements);
		Object[] phpElements = ActionUtils.getPHPElements(elements);
		if (elements.size() != resources.length + phpElements.length)
			setEnabled(false);
		else
			setEnabled(canEnable(resources, phpElements));
	}

	public void run(IStructuredSelection selection) {
		List elements = selection.toList();
		try {
			IResource[] resources = ActionUtils.getResources(elements, true);
			Object[] phpElements = ActionUtils.getPHPElements(elements, true);
			if (elements.size() == resources.length + phpElements.length && canEnable(resources, phpElements))
				doRun(resources, phpElements);
		} catch (CoreException e) {
			PHPUiPlugin.log(e);
		}
	}

	private void doRun(IResource[] resources, Object[] phpElements) throws CoreException {
		new ClipboardCopier(resources, phpElements, fClipboard, getShell(), fAutoRepeatOnFailure).copyToClipboard();
		if (fPasteAction != null && fPasteAction.getSelection() != null)
			fPasteAction.update(fPasteAction.getSelection());
	}

	private boolean canEnable(IResource[] resources, Object[] phpElements) {

		if (resources.length + phpElements.length == 0)
			return false;
		if (!canCopyAllToClipboard(resources, phpElements))
			return false;
		return true;
	}

	private boolean canCopyAllToClipboard(IResource[] resources, Object[] phpElements) {
		for (int i = 0; i < resources.length; i++) {
			if (!canCopyToClipboard(resources[i]))
				return false;
		}
		for (int i = 0; i < phpElements.length; i++) {
			if (!canCopyToClipboard(phpElements[i]))
				return false;
		}
		return true;
	}

	private static boolean canCopyToClipboard(Object element) {
		if (element == null)
			return false;

		if (!(element instanceof PHPCodeData || element instanceof PHPProjectModel))
			return false;
		if (PHPUiConstants.DISABLE_ELEMENT_REFACTORING && !(element instanceof PHPFileData))
			return false;
		return true;
	}

	private static boolean canCopyToClipboard(IResource resource) {
		return resource != null && resource.exists() && !resource.isPhantom() && resource.getType() != IResource.ROOT;
	}

}

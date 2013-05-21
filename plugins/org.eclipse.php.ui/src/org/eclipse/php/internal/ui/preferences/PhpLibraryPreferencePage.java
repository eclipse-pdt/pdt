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
package org.eclipse.php.internal.ui.preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.internal.core.UserLibraryManager;
import org.eclipse.dltk.internal.ui.IUIConstants;
import org.eclipse.dltk.internal.ui.wizards.BuildpathAttributeConfiguration;
import org.eclipse.dltk.internal.ui.wizards.buildpath.*;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.ITreeListAdapter;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.TreeListDialogField;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.preferences.PreferencesMessages;
import org.eclipse.dltk.ui.preferences.UserLibraryPreferencePage;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.ui.preferences.IPHPLibraryButtonHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;

/**
 * Preference page for user libraries
 */
public class PhpLibraryPreferencePage extends UserLibraryPreferencePage {

	private static final String BTN_HANDLERS_EXTENSION_POINT = "libraryButtonHandlers";

	private static final String ID_TEMPLATE = "org.eclipse.dltk.{0}.preferences.UserLibraryPreferencePage"; //$NON-NLS-1$

	private IDialogSettings fDialogSettings;
	private IScriptProject fDummyProject;
	private BuildpathAttributeConfigurationDescriptors fAttributeDescriptors;

	private static List<IPHPLibraryButtonHandler> handlers;

	private class NewButtonHandler implements IPHPLibraryButtonHandler {

		public boolean selectionChanged(TreeListDialogField field) {
			return true;
		}

		public void handleSelection(TreeListDialogField field) {
			editUserLibraryElement(null);
		}

		public String getLabel() {
			return PreferencesMessages.UserLibraryPreferencePage_libraries_new_button;
		}

		public int getPosition() {
			return 0;
		}
	};

	private class EditButtonHandler implements IPHPLibraryButtonHandler {

		public boolean selectionChanged(TreeListDialogField field) {
			return canEdit(field.getSelectedElements());
		}

		public void handleSelection(TreeListDialogField field) {
			doEdit(field.getSelectedElements());
		}

		public String getLabel() {
			return PreferencesMessages.UserLibraryPreferencePage_libraries_edit_button;
		}

		public int getPosition() {
			return 1;
		}
	}

	private class AddZipButtonHandler implements IPHPLibraryButtonHandler {

		public boolean selectionChanged(TreeListDialogField field) {
			return canAdd(field.getSelectedElements())
					&& getLanguageToolkit().languageSupportZIPBuildpath();
		}

		public void handleSelection(TreeListDialogField field) {
			doAdd(field.getSelectedElements());
		}

		public String getLabel() {
			return PreferencesMessages.UserLibraryPreferencePage_libraries_addzip_button;
		}

		public int getPosition() {
			return 2;
		}
	}

	private class AddExternalButtonHandler implements IPHPLibraryButtonHandler {

		public boolean selectionChanged(TreeListDialogField field) {
			return canAdd(field.getSelectedElements());
		}

		public void handleSelection(TreeListDialogField field) {
			doAddExternal(field.getSelectedElements());
		}

		public String getLabel() {
			return PreferencesMessages.UserLibraryPreferencePage_libraries_addext_button;
		}

		public int getPosition() {
			return 3;
		}
	}

	private class RemoveButtonHandler implements IPHPLibraryButtonHandler {

		public boolean selectionChanged(TreeListDialogField field) {
			return canRemove(field.getSelectedElements());
		}

		public void handleSelection(TreeListDialogField field) {
			doRemove(field.getSelectedElements());
		}

		public String getLabel() {
			return PreferencesMessages.UserLibraryPreferencePage_libraries_remove_button;
		}

		public int getPosition() {
			return 4;
		}
	}

	private class UpButtonHandler implements IPHPLibraryButtonHandler {

		public boolean selectionChanged(TreeListDialogField field) {
			return canMoveUp(field.getSelectedElements());
		}

		public void handleSelection(TreeListDialogField field) {
			doMoveUp(field.getSelectedElements());
		}

		public String getLabel() {
			return PreferencesMessages.UserLibraryPreferencePage_UserLibraryPreferencePage_libraries_up_button;
		}

		public int getPosition() {
			return 5;
		}
	}

	private class DownButtonHandler implements IPHPLibraryButtonHandler {

		public boolean selectionChanged(TreeListDialogField field) {
			return canMoveDown(field.getSelectedElements());
		}

		public void handleSelection(TreeListDialogField field) {
			doMoveDown(field.getSelectedElements());
		}

		public String getLabel() {
			return PreferencesMessages.UserLibraryPreferencePage_UserLibraryPreferencePage_libraries_down_button;
		}

		public int getPosition() {
			return 6;
		}
	}

	private class LoadButtonHandler implements IPHPLibraryButtonHandler {

		public boolean selectionChanged(TreeListDialogField field) {
			return true;
		}

		public void handleSelection(TreeListDialogField field) {
			doLoad();
		}

		public String getLabel() {
			return PreferencesMessages.UserLibraryPreferencePage_libraries_load_button;
		}

		public int getPosition() {
			return 7;
		}
	}

	private class SaveButtonHandler implements IPHPLibraryButtonHandler {

		public boolean selectionChanged(TreeListDialogField field) {
			return field.getSize() > 0;
		}

		public void handleSelection(TreeListDialogField field) {
			doSave();
		}

		public String getLabel() {
			return PreferencesMessages.UserLibraryPreferencePage_libraries_save_button;
		}

		public int getPosition() {
			return 8;
		}
	}

	private class EmptyButtonHandler implements IPHPLibraryButtonHandler {

		public boolean selectionChanged(TreeListDialogField field) {
			return false;
		}

		public void handleSelection(TreeListDialogField field) {
		}

		public String getLabel() {
			return null;
		}

		public int getPosition() {
			return 9;
		}
	}

	/**
	 * Constructor for ClasspathVariablesPreferencePage
	 */
	public PhpLibraryPreferencePage() {
		setPreferenceStore(DLTKUIPlugin.getDefault().getPreferenceStore());
		fDummyProject = createPlaceholderProject();

		fAttributeDescriptors = DLTKUIPlugin.getDefault()
				.getClasspathAttributeConfigurationDescriptors();

		// title only used when page is shown programatically
		setTitle(PreferencesMessages.UserLibraryPreferencePage_title);
		setDescription(PreferencesMessages.UserLibraryPreferencePage_description);
		noDefaultAndApplyButton();

		fDialogSettings = DLTKUIPlugin.getDefault().getDialogSettings();

		UserLibraryAdapter adapter = new UserLibraryAdapter();

		handlers = new ArrayList<IPHPLibraryButtonHandler>();
		// NEW
		handlers.add(new NewButtonHandler());
		// EDIT
		handlers.add(new EditButtonHandler());
		// ADD
		handlers.add(new AddZipButtonHandler());
		// ADD EXTERNAL
		handlers.add(new AddExternalButtonHandler());
		// REMOVE
		handlers.add(new RemoveButtonHandler());
		// SEPARATOR
		handlers.add(new EmptyButtonHandler());
		// UP
		handlers.add(new UpButtonHandler());
		// DOWN
		handlers.add(new DownButtonHandler());
		// SEPARATOR
		handlers.add(new EmptyButtonHandler());
		// LOAD
		handlers.add(new LoadButtonHandler());
		// SAVE
		handlers.add(new SaveButtonHandler());

		List<IPHPLibraryButtonHandler> additionalHandlers = getHandlers();
		for (IPHPLibraryButtonHandler handler : additionalHandlers) {
			int position = handler.getPosition();
			if (position < 0) {
				position = 0;
			}
			if (position > handlers.size()) {
				position = handlers.size();
			}
			handlers.add(handler.getPosition(), handler);
		}

		String[] buttonLabels = new String[handlers.size()];
		int size = handlers.size();
		for (int i = 0; i < size; i++) {
			buttonLabels[i] = handlers.get(i).getLabel();
		}

		fLibraryList = new TreeListDialogField(adapter, buttonLabels,
				new BPListLabelProvider());
		fLibraryList
				.setLabelText(PreferencesMessages.UserLibraryPreferencePage_libraries_label);

		String[] names = DLTKCore.getUserLibraryNames(getLanguageToolkit());
		ArrayList elements = new ArrayList();

		for (int i = 0; i < names.length; i++) {
			IPath path = new Path(DLTKCore.USER_LIBRARY_CONTAINER_ID)
					.append(UserLibraryManager.makeLibraryName(names[i],
							getLanguageToolkit()));
			try {
				IBuildpathContainer container = DLTKCore.getBuildpathContainer(
						path, fDummyProject);
				elements.add(new BPUserLibraryElement(names[i], container,
						fDummyProject));
			} catch (ModelException e) {
				DLTKUIPlugin.log(e);
				// ignore
			}
		}
		fLibraryList.setElements(elements);
		fLibraryList.setViewerSorter(new BPListElementSorter());

		doSelectionChanged(fLibraryList); // update button enable state
	}

	/**
	 * @param toolkit
	 * @return
	 */
	public static String getPreferencePageId(IDLTKLanguageToolkit toolkit) {
		return NLS.bind(ID_TEMPLATE, toolkit.getLanguageName().toLowerCase());
	}

	protected void doSelectionChanged(TreeListDialogField field) {
		if (handlers != null) {
			int size = handlers.size();
			for (int i = 0; i < size; i++) {
				field.enableButton(i, handlers.get(i).selectionChanged(field));
			}
		}
	}

	protected void doCustomButtonPressed(TreeListDialogField field, int index) {
		if (handlers != null) {
			handlers.get(index).handleSelection(field);
		}
	}

	protected IDLTKLanguageToolkit getLanguageToolkit() {
		return PHPLanguageToolkit.getDefault();
	}

	private static IScriptProject createPlaceholderProject() {
		String name = "####internal"; //$NON-NLS-1$
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		while (true) {
			IProject project = root.getProject(name);
			if (!project.exists()) {
				return DLTKCore.create(project);
			}
			name += '1';
		}
	}

	private BPUserLibraryElement getSingleSelectedLibrary(List selected) {
		if (selected.size() == 1
				&& selected.get(0) instanceof BPUserLibraryElement) {
			return (BPUserLibraryElement) selected.get(0);
		}
		return null;
	}

	private void editAttributeEntry(BPListElementAttribute elem) {
		String key = elem.getKey();
		BPListElement selElement = elem.getParent();
		if (key.equals(BPListElement.ACCESSRULES)) {
			AccessRulesDialog dialog = new AccessRulesDialog(getShell(),
					selElement, null, false);
			if (dialog.open() == Window.OK) {
				selElement.setAttribute(BPListElement.ACCESSRULES,
						dialog.getAccessRules());
				fLibraryList.refresh(elem);
				fLibraryList.expandElement(elem, 2);
			}
		} else if (!elem.isBuiltIn()) {
			BuildpathAttributeConfiguration config = fAttributeDescriptors
					.get(key);
			if (config != null) {
				IBuildpathAttribute result = config.performEdit(getShell(),
						elem.getBuildpathAttributeAccess());
				if (result != null) {
					elem.setValue(result.getValue());
					fLibraryList.refresh(elem);
				}
			}
		}
	}

	private void doEdit(List selected) {
		if (selected.size() == 1) {
			Object curr = selected.get(0);
			if (curr instanceof BPListElementAttribute) {
				editAttributeEntry((BPListElementAttribute) curr);
			} else if (curr instanceof BPUserLibraryElement) {
				editUserLibraryElement((BPUserLibraryElement) curr);
			} else if (curr instanceof BPListElement) {
				BPListElement elem = (BPListElement) curr;
				if (((BPListElement) curr).isExternalFolder()) {
					editExternalElement(elem,
							(BPUserLibraryElement) elem.getParentContainer());
				} else {
					editArchiveElement(elem,
							(BPUserLibraryElement) elem.getParentContainer());
				}
			}
			doSelectionChanged(fLibraryList);
		}
	}

	private void editUserLibraryElement(BPUserLibraryElement element) {
		LibraryNameDialog dialog = new LibraryNameDialog(getShell(), element,
				fLibraryList.getElements());
		if (dialog.open() == Window.OK) {
			BPUserLibraryElement newLibrary = dialog.getNewLibrary();
			if (element != null) {
				fLibraryList.replaceElement(element, newLibrary);
			} else {
				fLibraryList.addElement(newLibrary);
			}
			fLibraryList.expandElement(newLibrary,
					AbstractTreeViewer.ALL_LEVELS);
			fLibraryList.selectElements(new StructuredSelection(newLibrary));
		}
	}

	private void editArchiveElement(BPListElement existingElement,
			BPUserLibraryElement parent) {
		BPListElement[] elements = openExtZipFileDialog(existingElement, parent);
		if (elements != null) {
			for (int i = 0; i < elements.length; i++) {
				if (existingElement != null) {
					parent.replace(existingElement, elements[i]);
				} else {
					parent.add(elements[i]);
				}
			}
			fLibraryList.refresh(parent);
			fLibraryList.selectElements(new StructuredSelection(Arrays
					.asList(elements)));
			fLibraryList.expandElement(parent, 2);
		}
	}

	private void editExternalElement(BPListElement existingElement,
			BPUserLibraryElement parent) {
		BPListElement[] elements = openExtSourceFolderDialog(existingElement,
				parent);
		if (elements != null) {
			for (int i = 0; i < elements.length; i++) {
				if (existingElement != null) {
					parent.replace(existingElement, elements[i]);
				} else {
					parent.add(elements[i]);
				}
			}
			fLibraryList.refresh(parent);
			fLibraryList.selectElements(new StructuredSelection(Arrays
					.asList(elements)));
			fLibraryList.expandElement(parent, 2);
		}
	}

	private void doRemove(List selected) {
		Object selectionAfter = null;
		for (int i = 0; i < selected.size(); i++) {
			Object curr = selected.get(i);
			if (curr instanceof BPUserLibraryElement) {
				fLibraryList.removeElement(curr);
			} else if (curr instanceof BPListElement) {
				Object parent = ((BPListElement) curr).getParentContainer();
				if (parent instanceof BPUserLibraryElement) {
					BPUserLibraryElement elem = (BPUserLibraryElement) parent;
					elem.remove((BPListElement) curr);
					fLibraryList.refresh(elem);
					selectionAfter = parent;
				}
			} else if (curr instanceof BPListElementAttribute) {
				BPListElementAttribute attrib = (BPListElementAttribute) curr;
				if (attrib.isBuiltIn()) {
					Object value = null;
					String key = attrib.getKey();
					if (key.equals(BPListElement.ACCESSRULES)) {
						value = new IAccessRule[0];
					}
					attrib.getParent().setAttribute(key, value);
					fLibraryList.refresh(attrib);
				} else {
					BuildpathAttributeConfiguration config = fAttributeDescriptors
							.get(attrib.getKey());
					if (config != null) {
						IBuildpathAttribute result = config
								.performRemove(attrib
										.getBuildpathAttributeAccess());
						if (result != null) {
							attrib.setValue(result.getValue());
							fLibraryList.refresh(attrib);
						}
					}
				}
			}
		}
		if (fLibraryList.getSelectedElements().isEmpty()) {
			if (selectionAfter != null) {
				fLibraryList.selectElements(new StructuredSelection(
						selectionAfter));
			} else {
				fLibraryList.selectFirstElement();
			}
		} else {
			doSelectionChanged(fLibraryList);
		}
	}

	private void doAdd(List list) {
		if (canAdd(list)) {
			BPUserLibraryElement element = getSingleSelectedLibrary(list);
			editArchiveElement(null, element);
		}
	}

	private void doAddExternal(List list) {
		if (canAdd(list)) {
			BPUserLibraryElement element = getSingleSelectedLibrary(list);
			editExternalElement(null, element);
		}
	}

	private void doLoad() {
		List existing = fLibraryList.getElements();
		LoadSaveDialog dialog = new LoadSaveDialog(getShell(), false, existing,
				fDialogSettings);
		if (dialog.open() == Window.OK) {
			HashMap map = new HashMap(existing.size());
			for (int k = 0; k < existing.size(); k++) {
				BPUserLibraryElement elem = (BPUserLibraryElement) existing
						.get(k);
				map.put(elem.getName(), elem);
			}

			List list = dialog.getLoadedLibraries();
			for (int i = 0; i < list.size(); i++) {
				BPUserLibraryElement elem = (BPUserLibraryElement) list.get(i);
				BPUserLibraryElement found = (BPUserLibraryElement) map
						.get(elem.getName());
				if (found == null) {
					existing.add(elem);
					map.put(elem.getName(), elem);
				} else {
					existing.set(existing.indexOf(found), elem); // replace
				}
			}
			fLibraryList.setElements(existing);
			fLibraryList.selectElements(new StructuredSelection(list));
		}
	}

	private void doSave() {
		LoadSaveDialog dialog = new LoadSaveDialog(getShell(), true,
				fLibraryList.getElements(), fDialogSettings);
		dialog.open();
	}

	private boolean canEdit(List list) {
		if (list.size() != 1)
			return false;

		Object firstElement = list.get(0);
		if (firstElement instanceof IAccessRule) {
			return false;
		}
		if (firstElement instanceof BPListElementAttribute) {
			BPListElementAttribute attrib = (BPListElementAttribute) firstElement;
			if (!attrib.isBuiltIn()) {
				BuildpathAttributeConfiguration config = fAttributeDescriptors
						.get(attrib.getKey());
				return config != null
						&& config.canEdit(attrib.getBuildpathAttributeAccess());
			}
		}
		return true;
	}

	private boolean canRemove(List list) {
		if (list.size() == 0) {
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			Object elem = list.get(i);
			if (elem instanceof BPListElementAttribute) {
				BPListElementAttribute attrib = (BPListElementAttribute) elem;
				// if (attrib.isNonModifiable()) {
				// return false;
				// }
				if (attrib.isBuiltIn()) {
					if (attrib.getKey().equals(BPListElement.ACCESSRULES)) {
						return ((IAccessRule[]) attrib.getValue()).length > 0;
					}
					if (attrib.getValue() == null) {
						return false;
					}
				} else {
					BuildpathAttributeConfiguration config = fAttributeDescriptors
							.get(attrib.getKey());
					if (config == null
							|| !config.canRemove(attrib
									.getBuildpathAttributeAccess())) {
						return false;
					}
				}
			} else if (elem instanceof BPListElement) {
				// ok to remove
			} else if (elem instanceof BPUserLibraryElement) {
				// ok to remove
			} else { // unknown element
				return false;
			}
		}
		return true;
	}

	private BPUserLibraryElement getCommonParent(List list) {
		BPUserLibraryElement parent = null;
		for (int i = 0, len = list.size(); i < len; i++) {
			Object curr = list.get(i);
			if (curr instanceof BPListElement) {
				Object elemParent = ((BPListElement) curr).getParentContainer();
				if (parent == null) {
					if (elemParent instanceof BPUserLibraryElement) {
						parent = (BPUserLibraryElement) elemParent;
					} else {
						return null;
					}
				} else if (parent != elemParent) {
					return null;
				}
			} else {
				return null;
			}
		}
		return parent;
	}

	private void doMoveUp(List list) {
		BPUserLibraryElement parent = getCommonParent(list);
		if (parent != null) {
			parent.moveUp(list);
			fLibraryList.refresh(parent);
			doSelectionChanged(fLibraryList);
		}
	}

	private void doMoveDown(List list) {
		BPUserLibraryElement parent = getCommonParent(list);
		if (parent != null) {
			parent.moveDown(list);
			fLibraryList.refresh(parent);
			doSelectionChanged(fLibraryList);
		}
	}

	private boolean canMoveUp(List list) {
		BPUserLibraryElement parent = getCommonParent(list);
		if (parent != null) {
			BPListElement[] children = parent.getChildren();
			for (int i = 0, len = Math.min(list.size(), children.length); i < len; i++) {
				if (!list.contains(children[i])) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean canMoveDown(List list) {
		BPUserLibraryElement parent = getCommonParent(list);
		if (parent != null) {
			BPListElement[] children = parent.getChildren();
			for (int i = children.length - 1, end = Math.max(0, children.length
					- list.size()); i >= end; i--) {
				if (!list.contains(children[i])) {
					return true;
				}
			}
		}
		return false;
	}

	private BPListElement[] openExtZipFileDialog(BPListElement existing,
			Object parent) {
		String lastUsedPath;
		if (existing != null) {
			lastUsedPath = existing.getPath().removeLastSegments(1)
					.toOSString();
		} else {
			lastUsedPath = fDialogSettings
					.get(IUIConstants.DIALOGSTORE_LASTEXTZIP);
			if (lastUsedPath == null) {
				lastUsedPath = ""; //$NON-NLS-1$
			}
		}
		String title = (existing == null) ? PreferencesMessages.UserLibraryPreferencePage_browsejar_new_title
				: PreferencesMessages.UserLibraryPreferencePage_browsejar_edit_title;

		FileDialog dialog = new FileDialog(getShell(),
				existing == null ? SWT.MULTI : SWT.SINGLE);
		dialog.setText(title);
		dialog.setFilterExtensions(new String[] { "*.zip" }); //$NON-NLS-1$
		dialog.setFilterPath(lastUsedPath);
		if (existing != null) {
			dialog.setFileName(existing.getPath().lastSegment());
		}

		String res = dialog.open();
		if (res == null) {
			return null;
		}
		String[] fileNames = dialog.getFileNames();
		int nChosen = fileNames.length;

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		IPath filterPath = Path.fromOSString(dialog.getFilterPath());
		BPListElement[] elems = new BPListElement[nChosen];
		for (int i = 0; i < nChosen; i++) {
			IPath path = filterPath.append(fileNames[i]).makeAbsolute();

			IFile file = root.getFileForLocation(path);
			// support internal JARs: bug 133191
			if (file != null) {
				path = file.getFullPath();
			}

			path = EnvironmentPathUtils.getFullPath(
					EnvironmentManager.getLocalEnvironment(), path);
			BPListElement curr = new BPListElement(parent, null,
					IBuildpathEntry.BPE_LIBRARY, path, file, true);
			elems[i] = curr;
		}
		fDialogSettings.put(IUIConstants.DIALOGSTORE_LASTEXTZIP,
				dialog.getFilterPath());

		return elems;
	}

	private BPListElement[] openExtSourceFolderDialog(BPListElement existing,
			Object parent) {
		String lastUsedPath;
		if (existing != null) {
			lastUsedPath = EnvironmentPathUtils.getLocalPath(
					existing.getPath().removeLastSegments(1)).toOSString();
		} else {
			lastUsedPath = fDialogSettings
					.get(IUIConstants.DIALOGSTORE_LASTEXTSOURCE);
			if (lastUsedPath == null) {
				lastUsedPath = ""; //$NON-NLS-1$
			}
		}
		String title = (existing == null) ? PreferencesMessages.UserLibraryPreferencePage_browseext_new_title
				: PreferencesMessages.UserLibraryPreferencePage_browseext_edit_title;

		DirectoryDialog dialog = new DirectoryDialog(getShell(),
				existing == null ? SWT.MULTI : SWT.SINGLE);
		dialog.setText(title);
		// dialog.setFilterExtensions(new String[] { "*.zip" }); //$NON-NLS-1$
		dialog.setFilterPath(lastUsedPath);
		// dialog.set
		// if (existing != null) {
		// dialog.setFileName(existing.getPath().lastSegment());
		// }

		String res = dialog.open();
		if (res == null) {
			return null;
		}

		// IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		// IPath filterPath = Path.fromOSString(dialog.getFilterPath());
		BPListElement[] elems = new BPListElement[1];
		IPath path = Path.fromOSString(res).makeAbsolute();

		BPListElement curr = new BPListElement(parent, null,
				IBuildpathEntry.BPE_LIBRARY, EnvironmentPathUtils.getFullPath(
						EnvironmentManager.getLocalEnvironment(), path), null,
				true);
		elems[0] = curr;

		fDialogSettings.put(IUIConstants.DIALOGSTORE_LASTEXTSOURCE,
				dialog.getFilterPath());

		return elems;
	}

	private List<IPHPLibraryButtonHandler> getHandlers() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(PHPUiPlugin.ID,
						BTN_HANDLERS_EXTENSION_POINT);
		List<IPHPLibraryButtonHandler> result = new ArrayList<IPHPLibraryButtonHandler>();
		for (IConfigurationElement element : elements) {
			if ("handler".equals(element.getName())) { //$NON-NLS-1$
				try {
					Object handler = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (handler instanceof IPHPLibraryButtonHandler) {
						result.add((IPHPLibraryButtonHandler) handler);
					}
				} catch (CoreException e) {
					PHPUiPlugin.log(e);
				}
			}
		}
		return result;
	}

	private class UserLibraryAdapter implements ITreeListAdapter {

		private final Object[] EMPTY = new Object[0];

		public void customButtonPressed(TreeListDialogField field, int index) {
			doCustomButtonPressed(field, index);
		}

		public void selectionChanged(TreeListDialogField field) {
			doSelectionChanged(field);
		}

		public void doubleClicked(TreeListDialogField field) {
			doDoubleClicked(field);
		}

		public void keyPressed(TreeListDialogField field, KeyEvent event) {
			doKeyPressed(field, event);
		}

		public Object[] getChildren(TreeListDialogField field, Object element) {
			if (element instanceof BPUserLibraryElement) {
				BPUserLibraryElement elem = (BPUserLibraryElement) element;
				return elem.getChildren();
			} else if (element instanceof BPListElement) {
				return ((BPListElement) element).getChildren();
			} else if (element instanceof BPListElementAttribute) {
				BPListElementAttribute attribute = (BPListElementAttribute) element;
				if (BPListElement.ACCESSRULES.equals(attribute.getKey())) {
					return (IAccessRule[]) attribute.getValue();
				}
			}
			return EMPTY;
		}

		public Object getParent(TreeListDialogField field, Object element) {
			if (element instanceof BPListElementAttribute) {
				return ((BPListElementAttribute) element).getParent();
			} else if (element instanceof BPListElement) {
				return ((BPListElement) element).getParentContainer();
			}
			return null;
		}

		public boolean hasChildren(TreeListDialogField field, Object element) {
			return getChildren(field, element).length > 0;
		}

	}

}

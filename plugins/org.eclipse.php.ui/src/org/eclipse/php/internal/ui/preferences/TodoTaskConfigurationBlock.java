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
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentAdapter;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.editor.PHPStructuredTextViewer;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.util.StatusInfo;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.osgi.service.prefs.BackingStoreException;

import com.ibm.icu.text.MessageFormat;

/**
 */
public class TodoTaskConfigurationBlock extends
		PHPCoreOptionsConfigurationBlock {

	private static final Key PREF_TASK_TAGS = getPHPCoreKey(PHPCoreConstants.TASK_TAGS);
	private static final Key PREF_TASK_PRIORITIES = getPHPCoreKey(PHPCoreConstants.TASK_PRIORITIES);

	private static final Key PREF_TASK_CASE_SENSITIVE = getPHPCoreKey(PHPCoreConstants.TASK_CASE_SENSITIVE);

	private static final String PRIORITY_HIGH = PHPCoreConstants.TASK_PRIORITY_HIGH;
	private static final String PRIORITY_NORMAL = PHPCoreConstants.TASK_PRIORITY_NORMAL;
	private static final String PRIORITY_LOW = PHPCoreConstants.TASK_PRIORITY_LOW;

	private static final String ENABLED = PHPCoreConstants.ENABLED;
	private static final String DISABLED = PHPCoreConstants.DISABLED;

	public static class TodoTask {
		public String name;
		public String priority;
	}

	private class TodoTaskLabelProvider extends LabelProvider implements
			ITableLabelProvider, IFontProvider {

		public TodoTaskLabelProvider() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
		 */
		public Image getImage(Object element) {
			return null; // JavaPluginImages.get(JavaPluginImages.IMG_OBJS_REFACTORING_INFO);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
		 */
		public String getText(Object element) {
			return getColumnText(element, 0);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java
		 * .lang.Object, int)
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.
		 * lang.Object, int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			TodoTask task = (TodoTask) element;
			if (columnIndex == 0) {
				String name = task.name;
				if (isDefaultTask(task)) {
					name = MessageFormat
							.format(
									PHPUIMessages.TodoTaskConfigurationBlock_tasks_default,
									new Object[] { name });
				}
				return name;
			} else {
				if (PRIORITY_HIGH.equals(task.priority)) {
					return PHPUIMessages.TodoTaskConfigurationBlock_markers_tasks_high_priority;
				} else if (PRIORITY_NORMAL.equals(task.priority)) {
					return PHPUIMessages.TodoTaskConfigurationBlock_markers_tasks_normal_priority;
				} else if (PRIORITY_LOW.equals(task.priority)) {
					return PHPUIMessages.TodoTaskConfigurationBlock_markers_tasks_low_priority;
				}
				return ""; //$NON-NLS-1$
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.IFontProvider#getFont(java.lang.Object)
		 */
		public Font getFont(Object element) {
			if (isDefaultTask((TodoTask) element)) {
				return JFaceResources.getFontRegistry().getBold(
						JFaceResources.DIALOG_FONT);
			}
			return null;
		}
	}

	private static class TodoTaskSorter extends ViewerSorter {
		public int compare(Viewer viewer, Object e1, Object e2) {
			return collator.compare(((TodoTask) e1).name, ((TodoTask) e2).name);
		}
	}

	private static final int IDX_ADD = 0;
	private static final int IDX_EDIT = 1;
	private static final int IDX_REMOVE = 2;
	private static final int IDX_DEFAULT = 4;

	private IStatus fTaskTagsStatus;
	private ListDialogField fTodoTasksList;
	private SelectionButtonDialogField fCaseSensitiveCheckBox;

	public TodoTaskConfigurationBlock(IStatusChangeListener context,
			IProject project, IWorkbenchPreferenceContainer container) {
		super(context, project, getKeys(), container);

		TaskTagAdapter adapter = new TaskTagAdapter();
		String[] buttons = new String[] {
				PHPUIMessages.TodoTaskConfigurationBlock_markers_tasks_add_button,
				PHPUIMessages.TodoTaskConfigurationBlock_markers_tasks_edit_button,
				PHPUIMessages.TodoTaskConfigurationBlock_markers_tasks_remove_button,
				null,
				PHPUIMessages.TodoTaskConfigurationBlock_markers_tasks_setdefault_button, };
		fTodoTasksList = new ListDialogField(adapter, buttons,
				new TodoTaskLabelProvider());
		fTodoTasksList.setDialogFieldListener(adapter);
		fTodoTasksList.setRemoveButtonIndex(IDX_REMOVE);

		String[] columnsHeaders = new String[] {
				PHPUIMessages.TodoTaskConfigurationBlock_markers_tasks_name_column,
				PHPUIMessages.TodoTaskConfigurationBlock_markers_tasks_priority_column, };

		fTodoTasksList.setTableColumns(new ListDialogField.ColumnsDescription(
				columnsHeaders, true));
		fTodoTasksList.setViewerSorter(new TodoTaskSorter());

		fCaseSensitiveCheckBox = new SelectionButtonDialogField(SWT.CHECK);
		fCaseSensitiveCheckBox
				.setLabelText(PHPUIMessages.TodoTaskConfigurationBlock_casesensitive_label);
		fCaseSensitiveCheckBox.setDialogFieldListener(adapter);

		unpackTodoTasks();
		if (fTodoTasksList.getSize() > 0) {
			fTodoTasksList.selectFirstElement();
		} else {
			fTodoTasksList.enableButton(IDX_EDIT, false);
			fTodoTasksList.enableButton(IDX_DEFAULT, false);
		}

		fTaskTagsStatus = new StatusInfo();
	}

	public void setEnabled(boolean isEnabled) {
		fTodoTasksList.setEnabled(isEnabled);
		fCaseSensitiveCheckBox.setEnabled(isEnabled);
	}

	final boolean isDefaultTask(TodoTask task) {
		return fTodoTasksList.getIndexOfElement(task) == 0;
	}

	private void setToDefaultTask(TodoTask task) {
		List elements = fTodoTasksList.getElements();
		elements.remove(task);
		elements.add(0, task);
		fTodoTasksList.setElements(elements);
		fTodoTasksList.enableButton(IDX_DEFAULT, false);
	}

	private static Key[] getKeys() {
		return new Key[] { PREF_TASK_TAGS, PREF_TASK_PRIORITIES,
				PREF_TASK_CASE_SENSITIVE };
	}

	public class TaskTagAdapter implements IListAdapter, IDialogFieldListener {

		private boolean canEdit(List selectedElements) {
			return selectedElements.size() == 1;
		}

		private boolean canSetToDefault(List selectedElements) {
			return selectedElements.size() == 1
					&& !isDefaultTask((TodoTask) selectedElements.get(0));
		}

		public void customButtonPressed(ListDialogField field, int index) {
			doTodoButtonPressed(index);
		}

		public void selectionChanged(ListDialogField field) {
			List selectedElements = field.getSelectedElements();
			field.enableButton(IDX_EDIT, canEdit(selectedElements));
			field.enableButton(IDX_DEFAULT, canSetToDefault(selectedElements));
		}

		public void doubleClicked(ListDialogField field) {
			if (canEdit(field.getSelectedElements())) {
				doTodoButtonPressed(IDX_EDIT);
			}
		}

		public void dialogFieldChanged(DialogField field) {
			updateModel(field);
		}

	}

	protected Control createContents(Composite parent) {
		setShell(parent.getShell());

		Composite markersComposite = createMarkersTabContent(parent);

		validateSettings(null, null, null);

		return markersComposite;
	}

	private Composite createMarkersTabContent(Composite folder) {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;

		PixelConverter conv = new PixelConverter(folder);

		Composite markersComposite = new Composite(folder, SWT.NULL);
		markersComposite.setLayout(layout);
		markersComposite.setFont(folder.getFont());

		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = conv.convertWidthInCharsToPixels(50);
		Control listControl = fTodoTasksList.getListControl(markersComposite);
		listControl.setLayoutData(data);

		Control buttonsControl = fTodoTasksList.getButtonBox(markersComposite);
		buttonsControl.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL
						| GridData.VERTICAL_ALIGN_BEGINNING));

		fCaseSensitiveCheckBox.doFillIntoGrid(markersComposite, 2);

		return markersComposite;
	}

	protected void validateSettings(Key changedKey, String oldValue,
			String newValue) {
		if (changedKey != null) {
			if (PREF_TASK_TAGS.equals(changedKey)) {
				fTaskTagsStatus = validateTaskTags();
			} else {
				return;
			}
		} else {
			fTaskTagsStatus = validateTaskTags();
		}
		IStatus status = fTaskTagsStatus; // StatusUtil.getMostSevere(new
		// IStatus[] { fTaskTagsStatus });
		fContext.statusChanged(status);
	}

	private IStatus validateTaskTags() {
		return new StatusInfo();
	}

	protected final void updateModel(DialogField field) {
		if (field == fTodoTasksList) {
			StringBuffer tags = new StringBuffer();
			StringBuffer prios = new StringBuffer();
			List list = fTodoTasksList.getElements();
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					tags.append(',');
					prios.append(',');
				}
				TodoTask elem = (TodoTask) list.get(i);
				tags.append(elem.name);
				prios.append(elem.priority);
			}
			setValue(PREF_TASK_TAGS, tags.toString());
			setValue(PREF_TASK_PRIORITIES, prios.toString());
			validateSettings(PREF_TASK_TAGS, null, null);
		} else if (field == fCaseSensitiveCheckBox) {
			String state = fCaseSensitiveCheckBox.isSelected() ? ENABLED
					: DISABLED;
			setValue(PREF_TASK_CASE_SENSITIVE, state);
		}
	}

	public void useProjectSpecificSettings(boolean enable) {
		super.useProjectSpecificSettings(enable);
		fTodoTasksList.setEnabled(enable);
	}

	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		String title = PHPUIMessages.TodoTaskConfigurationBlock_needsbuild_title;
		String message;
		if (fProject == null) {
			message = PHPUIMessages.TodoTaskConfigurationBlock_needsfullbuild_message;
		} else {
			message = PHPUIMessages.TodoTaskConfigurationBlock_needsprojectbuild_message;
		}
		return new String[] { title, message };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.internal.ui.preferences.OptionsConfigurationBlock#
	 * updateControls()
	 */
	protected void updateControls() {
		unpackTodoTasks();
	}

	private void unpackTodoTasks() {
		String currTags = getValue(PREF_TASK_TAGS);
		String currPrios = getValue(PREF_TASK_PRIORITIES);
		String[] tags = getTokens(currTags, ","); //$NON-NLS-1$
		String[] prios = getTokens(currPrios, ","); //$NON-NLS-1$
		ArrayList elements = new ArrayList(tags.length);
		for (int i = 0; i < tags.length; i++) {
			TodoTask task = new TodoTask();
			task.name = tags[i].trim();
			task.priority = (i < prios.length) ? prios[i] : PRIORITY_NORMAL;
			elements.add(task);
		}
		fTodoTasksList.setElements(elements);

		boolean isCaseSensitive = checkValue(PREF_TASK_CASE_SENSITIVE, ENABLED);
		fCaseSensitiveCheckBox.setSelection(isCaseSensitive);
	}

	private void doTodoButtonPressed(int index) {
		TodoTask edited = null;
		if (index != IDX_ADD) {
			edited = (TodoTask) fTodoTasksList.getSelectedElements().get(0);
		}
		if (index == IDX_ADD || index == IDX_EDIT) {
			TodoTaskInputDialog dialog = new TodoTaskInputDialog(getShell(),
					edited, fTodoTasksList.getElements());
			if (dialog.open() == Window.OK) {
				if (edited != null) {
					fTodoTasksList.replaceElement(edited, dialog.getResult());
				} else {
					fTodoTasksList.addElement(dialog.getResult());
				}
			}
		} else if (index == IDX_DEFAULT) {
			setToDefaultTask(edited);
		}
	}

	@Override
	protected void prepareForBuild() {
		try {
			fManager.applyChanges();
		} catch (BackingStoreException e) {
			PHPUiPlugin.log(e);
			return;
		}
		IEditorReference[] editors = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < editors.length; i++) {
			IEditorReference editorReference = editors[i];
			IEditorPart editor = editorReference.getEditor(false);
			if (editor instanceof PHPStructuredEditor) {
				PHPStructuredEditor phpEditor = (PHPStructuredEditor) editor;
				boolean isDirty = phpEditor.isDirty();
				if (phpEditor.getTextViewer() instanceof PHPStructuredTextViewer) {
					PHPStructuredTextViewer textViewer = (PHPStructuredTextViewer) phpEditor
							.getTextViewer();
					IDocumentAdapter documentAdapter = textViewer
							.getDocumentAdapter();
					IDocument document = phpEditor.getDocument();
					String content = document.get();
					if (documentAdapter != null) {
						documentAdapter.replaceTextRange(0, content.length(),
								""); //$NON-NLS-1$
						documentAdapter.replaceTextRange(0, 0, content);
					}

				}
				if (!isDirty) {
					phpEditor.doSave(null);
				}
			}
		}
	}
}

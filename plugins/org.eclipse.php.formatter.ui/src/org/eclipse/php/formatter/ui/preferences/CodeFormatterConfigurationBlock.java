/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.ui.preferences;

import java.io.File;
import java.util.*;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.php.formatter.core.CodeFormatterPreferences;
import org.eclipse.php.formatter.core.FormatterCorePlugin;
import org.eclipse.php.formatter.ui.FormatterMessages;
import org.eclipse.php.formatter.ui.FormatterUIPlugin;
import org.eclipse.php.formatter.ui.Logger;
import org.eclipse.php.formatter.ui.preferences.ProfileManager.CustomProfile;
import org.eclipse.php.formatter.ui.preferences.ProfileManager.Profile;
import org.eclipse.php.internal.ui.preferences.IStatusChangeListener;
import org.eclipse.php.internal.ui.preferences.PHPCoreOptionsConfigurationBlock;
import org.eclipse.php.internal.ui.preferences.PHPFormatterConfigurationBlock;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.ExceptionHandler;
import org.eclipse.php.internal.ui.util.Messages;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

/**
 * 
 * @author moshe
 * 
 */
public class CodeFormatterConfigurationBlock extends
		PHPCoreOptionsConfigurationBlock implements SelectionListener {
	private static final Key PROFILE_KEY = new Key(
			FormatterCorePlugin.PLUGIN_ID,
			PreferenceConstants.FORMATTER_PROFILE);
	private static final String DIALOGSTORE_LASTLOADPATH = FormatterUIPlugin.PLUGIN_ID
			+ ".loadpath"; //$NON-NLS-1$
	private static final String DIALOGSTORE_LASTSAVEPATH = FormatterUIPlugin.PLUGIN_ID
			+ ".savepath"; //$NON-NLS-1$

	private class StoreUpdater implements Observer {

		public StoreUpdater() {
			fProfileManager.addObserver(this);
		}

		public void update(Observable o, Object arg) {
			final int value = ((Integer) arg).intValue();
			switch (value) {
			case ProfileManager.PROFILE_DELETED_EVENT:
			case ProfileManager.PROFILE_RENAMED_EVENT:
			case ProfileManager.PROFILE_CREATED_EVENT:
			case ProfileManager.SETTINGS_CHANGED_EVENT:
				try {
					ProfileStore
							.writeProfiles(fProfileManager.getSortedProfiles(),
									fInstanceScope); // update
														// profile
														// store
					fProfileManager.commitChanges(fCurrContext); // update
																	// formatter
																	// settings
																	// with
																	// curently
																	// selected
																	// profile
				} catch (CoreException x) {
					Logger.logException(x);
				}
				break;
			case ProfileManager.SELECTION_CHANGED_EVENT:
				fProfileManager.commitChanges(fCurrContext); // update formatter
																// settings with
																// curently
																// selected
																// profile
				break;
			}
		}
	}

	private class ProfileComboController implements Observer, SelectionListener {

		private final List<Profile> fSortedProfiles;

		public ProfileComboController() {
			fSortedProfiles = fProfileManager.getSortedProfiles();
			fProfileCombo.addSelectionListener(this);
			fProfileManager.addObserver(this);
			updateProfiles();
			updateSelection();
		}

		public void widgetSelected(SelectionEvent e) {
			final int index = fProfileCombo.getSelectionIndex();
			Profile selectedProfile = fSortedProfiles.get(index);
			fProfileManager.setSelected(selectedProfile);
		}

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void update(Observable o, Object arg) {
			if (arg == null)
				return;
			final int value = ((Integer) arg).intValue();
			switch (value) {
			case ProfileManager.PROFILE_CREATED_EVENT:
			case ProfileManager.PROFILE_DELETED_EVENT:
			case ProfileManager.PROFILE_RENAMED_EVENT:
				updateProfiles();
				updateSelection();
				break;
			case ProfileManager.SELECTION_CHANGED_EVENT:
				updateSelection();
				break;
			}
		}

		private void updateProfiles() {
			fProfileCombo.setItems(fProfileManager.getSortedDisplayNames());
		}

		private void updateSelection() {
			fProfileCombo.setText(fProfileManager.getSelected().getName());
		}
	}

	private class ButtonController implements Observer, SelectionListener {
		public ButtonController() {
			fProfileManager.addObserver(this);
			fNewButton.addSelectionListener(this);
			fRenameButton.addSelectionListener(this);
			fEditButton.addSelectionListener(this);
			fDeleteButton.addSelectionListener(this);
			fSaveButton.addSelectionListener(this);
			fLoadButton.addSelectionListener(this);
			update(fProfileManager, null);
		}

		public void update(Observable o, Object arg) {
			Profile selected = ((ProfileManager) o).getSelected();
			final boolean notBuiltIn = !selected.isBuiltInProfile();
			fEditButton
					.setText(notBuiltIn ? FormatterMessages.CodingStyleConfigurationBlock_edit_button_desc
							: FormatterMessages.CodingStyleConfigurationBlock_show_button_desc);
			fDeleteButton.setEnabled(notBuiltIn);
			fSaveButton.setEnabled(notBuiltIn);
			fRenameButton.setEnabled(notBuiltIn);
		}

		public void widgetSelected(SelectionEvent e) {
			final Button button = (Button) e.widget;
			if (button == fSaveButton)
				saveButtonPressed();
			else if (button == fEditButton)
				modifyButtonPressed();
			else if (button == fDeleteButton)
				deleteButtonPressed();
			else if (button == fNewButton)
				newButtonPressed();
			else if (button == fLoadButton)
				loadButtonPressed();
			else if (button == fRenameButton)
				renameButtonPressed();
		}

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		private void renameButtonPressed() {
			if (fProfileManager.getSelected().isBuiltInProfile())
				return;
			final CustomProfile profile = (CustomProfile) fProfileManager
					.getSelected();
			final RenameProfileDialog renameDialog = new RenameProfileDialog(
					fComposite.getShell(), profile, fProfileManager);
			if (renameDialog.open() == Window.OK) {
				fProfileManager.setSelected(renameDialog.getRenamedProfile());
			}
		}

		private void modifyButtonPressed() {
			final ModifyDialog modifyDialog = new ModifyDialog(
					CodeFormatterConfigurationBlock.this,
					fComposite.getShell(), fProfileManager.getSelected(),
					fProfileManager, false);
			modifyDialog.open();
		}

		private void deleteButtonPressed() {
			if (MessageDialog
					.openQuestion(
							fComposite.getShell(),
							FormatterMessages.CodingStyleConfigurationBlock_delete_confirmation_title,
							Messages.format(
									FormatterMessages.CodingStyleConfigurationBlock_delete_confirmation_question,
									fProfileManager.getSelected().getName()))) {
				fProfileManager.deleteSelected();
			}
		}

		private void newButtonPressed() {
			final CreateProfileDialog p = new CreateProfileDialog(
					fComposite.getShell(), fProfileManager);
			if (p.open() != Window.OK)
				return;
			if (!p.openEditDialog())
				return;
			final ModifyDialog modifyDialog = new ModifyDialog(
					CodeFormatterConfigurationBlock.this,
					fComposite.getShell(), p.getCreatedProfile(),
					fProfileManager, true);
			modifyDialog.open();
		}

		private void saveButtonPressed() {
			Profile selected = fProfileManager.getSelected();

			final FileDialog dialog = new FileDialog(fComposite.getShell(),
					SWT.SAVE);
			dialog.setText(FormatterMessages.CodingStyleConfigurationBlock_save_profile_dialog_title);
			dialog.setFilterExtensions(new String[] { "*.xml" }); //$NON-NLS-1$
			// set default file name to profile name
			dialog.setFileName(selected.getName() + ".xml");

			final String lastPath = FormatterUIPlugin.getDefault()
					.getDialogSettings().get(DIALOGSTORE_LASTSAVEPATH);
			if (lastPath != null) {
				dialog.setFilterPath(lastPath);
			}
			final String path = dialog.open();
			if (path == null) {
				return;
			}

			if (!(path.endsWith(".xml"))) {
				path.concat(".xml");
			}

			FormatterUIPlugin.getDefault().getDialogSettings()
					.put(DIALOGSTORE_LASTSAVEPATH, dialog.getFilterPath());

			final File file = new File(path);
			if (file.exists()
					&& !MessageDialog
							.openQuestion(
									fComposite.getShell(),
									FormatterMessages.CodingStyleConfigurationBlock_save_profile_overwrite_title,
									Messages.format(
											FormatterMessages.CodingStyleConfigurationBlock_save_profile_overwrite_message,
											path))) {
				return;
			}

			final Collection<Profile> profiles = new ArrayList<Profile>();

			profiles.add(selected);
			try {
				ProfileStore.writeProfilesToFile(profiles, file);
			} catch (CoreException e) {
				final String title = FormatterMessages.CodingStyleConfigurationBlock_save_profile_error_title;
				final String message = FormatterMessages.CodingStyleConfigurationBlock_save_profile_error_message;
				ExceptionHandler.handle(e, fComposite.getShell(), title,
						message);
			}
		}

		private void loadButtonPressed() {
			final FileDialog dialog = new FileDialog(fComposite.getShell(),
					SWT.OPEN);
			dialog.setText(FormatterMessages.CodingStyleConfigurationBlock_load_profile_dialog_title);
			dialog.setFilterExtensions(new String[] { "*.xml" }); //$NON-NLS-1$
			final String lastPath = FormatterUIPlugin.getDefault()
					.getDialogSettings().get(DIALOGSTORE_LASTLOADPATH);
			if (lastPath != null) {
				dialog.setFilterPath(lastPath);
			}
			final String path = dialog.open();
			if (path == null)
				return;
			FormatterUIPlugin.getDefault().getDialogSettings()
					.put(DIALOGSTORE_LASTLOADPATH, dialog.getFilterPath());

			final File file = new File(path);
			Collection<Profile> profiles = null;
			try {
				profiles = ProfileStore.readProfilesFromFile(file);
			} catch (CoreException e) {
				final String title = FormatterMessages.CodingStyleConfigurationBlock_load_profile_error_title;
				final String message = FormatterMessages.CodingStyleConfigurationBlock_load_profile_error_message;
				ExceptionHandler.handle(e, fComposite.getShell(), title,
						message);
			}
			if (profiles == null || profiles.isEmpty())
				return;

			final CustomProfile profile = (CustomProfile) profiles.iterator()
					.next();

			if (fProfileManager.containsName(profile.getName())) {
				final AlreadyExistsDialog aeDialog = new AlreadyExistsDialog(
						fComposite.getShell(), profile, fProfileManager);
				if (aeDialog.open() != Window.OK)
					return;
			}
			fProfileManager.addProfile(profile);
		}
	}

	private class PreviewController implements Observer {

		public PreviewController() {
			fProfileManager.addObserver(this);
			fPhpPreview.setPreferences(new CodeFormatterPreferences(
					fProfileManager.getSelected().getSettings()));
			fPhpPreview.update();
		}

		public void update(Observable o, Object arg) {
			final int value = ((Integer) arg).intValue();
			switch (value) {
			case ProfileManager.PROFILE_CREATED_EVENT:
			case ProfileManager.PROFILE_DELETED_EVENT:
			case ProfileManager.SELECTION_CHANGED_EVENT:
			case ProfileManager.SETTINGS_CHANGED_EVENT:
				fPhpPreview.setPreferences(new CodeFormatterPreferences(
						((ProfileManager) o).getSelected().getSettings()));
				fPhpPreview.update();
			}
		}

	}

	/**
	 * Some PHP source code used for preview.
	 */
	private final String PREVIEW = "<?php\n class Example {" + //$NON-NLS-1$
			"  var $theInt= 1;" + //$NON-NLS-1$
			"  function foo($a, $b) {" + //$NON-NLS-1$
			"    switch($a) {" + //$NON-NLS-1$
			"    case 0: " + //$NON-NLS-1$
			"      $Other->doFoo();" + //$NON-NLS-1$
			"      break;" + //$NON-NLS-1$
			"    default:" + //$NON-NLS-1$
			"      $Other->doBaz();" + //$NON-NLS-1$
			"    }" + //$NON-NLS-1$
			"  }" + //$NON-NLS-1$
			"  function bar($v) {" + //$NON-NLS-1$
			"    for ($i= 0; $i < 10; $i++) {" + //$NON-NLS-1$
			"      $v->add($i);" + //$NON-NLS-1$
			"    }" + //$NON-NLS-1$
			"  }" + //$NON-NLS-1$
			"} \n?>"; //$NON-NLS-1$

	/**
	 * The GUI controls
	 */
	protected Composite fComposite;
	protected Combo fProfileCombo;
	protected Button fEditButton;
	protected Button fRenameButton;
	protected Button fDeleteButton;
	protected Button fNewButton;
	protected Button fLoadButton;
	protected Button fSaveButton;

	/**
	 * The ProfileManager, the model of this page.
	 */
	protected final ProfileManager fProfileManager;

	/**
	 * The PhpPreview.
	 */
	protected CodeFormatterPreview fPhpPreview;
	private PixelConverter fPixConv;

	private IScopeContext fCurrContext;
	private IScopeContext fInstanceScope;

	public CodeFormatterConfigurationBlock(IStatusChangeListener context,
			IProject project, IWorkbenchPreferenceContainer container) {
		super(context, project, new Key[] { PROFILE_KEY }, container);

		fInstanceScope = new InstanceScope();
		List<Profile> profiles = null;
		try {
			profiles = ProfileStore.readProfiles(fInstanceScope);
		} catch (CoreException e) {
			Logger.logException(e);
		}
		if (profiles == null) {
			try {
				profiles = ProfileStore
						.readProfilesFromPreferences(new DefaultScope());
			} catch (CoreException e) {
				Logger.logException(e);
			}
		}

		if (profiles == null)
			profiles = new ArrayList<Profile>();

		if (project != null) {
			fCurrContext = new ProjectScope(project);
		} else {
			fCurrContext = fInstanceScope;
		}

		fProfileManager = new ProfileManager(profiles, fCurrContext);

		new StoreUpdater();
	}

	protected Control createContents(Composite parent) {
		final int numColumns = 5;

		fPixConv = new PixelConverter(parent);
		fComposite = createComposite(parent, numColumns);

		fProfileCombo = createProfileCombo(fComposite, numColumns - 3,
				fPixConv.convertWidthInCharsToPixels(20));
		fEditButton = createButton(
				fComposite,
				FormatterMessages.CodingStyleConfigurationBlock_edit_button_desc,
				GridData.HORIZONTAL_ALIGN_BEGINNING);
		fRenameButton = createButton(
				fComposite,
				FormatterMessages.CodingStyleConfigurationBlock_rename_button_desc,
				GridData.HORIZONTAL_ALIGN_BEGINNING);
		fDeleteButton = createButton(
				fComposite,
				FormatterMessages.CodingStyleConfigurationBlock_remove_button_desc,
				GridData.HORIZONTAL_ALIGN_BEGINNING);

		final Composite group = createComposite(fComposite, 4);
		final GridData groupData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		groupData.horizontalSpan = numColumns;
		group.setLayoutData(groupData);

		fNewButton = createButton(
				group,
				FormatterMessages.CodingStyleConfigurationBlock_new_button_desc,
				GridData.HORIZONTAL_ALIGN_BEGINNING);
		((GridData) createLabel(group, "", 1).getLayoutData()).grabExcessHorizontalSpace = true; //$NON-NLS-1$
		fLoadButton = createButton(
				group,
				FormatterMessages.CodingStyleConfigurationBlock_load_button_desc,
				GridData.HORIZONTAL_ALIGN_END);
		fSaveButton = createButton(
				group,
				FormatterMessages.CodingStyleConfigurationBlock_save_button_desc,
				GridData.HORIZONTAL_ALIGN_END);

		createLabel(
				fComposite,
				FormatterMessages.CodingStyleConfigurationBlock_preview_label_text,
				numColumns);
		configurePreview(fComposite, numColumns);

		new ButtonController();
		new ProfileComboController();
		new PreviewController();

		return fComposite;
	}

	protected String[] getFullBuildDialogStrings(boolean workspaceSettings) {
		return null;
	}

	@Override
	public void performDefaults() {
		String[] items = fProfileCombo.getItems();
		int index = 0;
		for (int i = 0; i < items.length; i++) {
			if (items[i].equalsIgnoreCase("PHP Conventions [built-in]")) {
				index = i;
				break;
			}
		}
		fProfileCombo.select(index);
		fProfileManager.setSelected(fProfileManager
				.getProfile(ProfileManager.PHP_PROFILE));

		super.performDefaults();
	}

	@Override
	public boolean performOk() {
		if (this.fComposite.isEnabled()) {
			final int index = fProfileCombo.getSelectionIndex();
			Profile selectedProfile = fProfileManager.getSortedProfiles().get(
					index);
			fProfileManager.setSelected(selectedProfile);
		}
		return super.performOk();
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	protected void validateSettings(Key changedKey, String oldValue,
			String newValue) {
		// TODO Auto-generated method stub
	}

	private static Button createButton(Composite composite, String text,
			final int style) {
		final Button button = new Button(composite, SWT.PUSH);
		button.setFont(composite.getFont());
		button.setText(text);

		final GridData gd = new GridData(style);
		gd.widthHint = SWTUtil.getButtonWidthHint(button);
		button.setLayoutData(gd);
		return button;
	}

	private static Combo createProfileCombo(Composite composite, int span,
			int widthHint) {
		final GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = span;
		gd.widthHint = widthHint;

		final Combo combo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setFont(composite.getFont());
		combo.setLayoutData(gd);
		return combo;
	}

	private Label createLabel(Composite composite, String text, int numColumns) {
		final GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = numColumns;
		gd.widthHint = 0;

		final Label label = new Label(composite, SWT.WRAP);
		label.setFont(composite.getFont());
		label.setText(text);
		label.setLayoutData(gd);
		return label;
	}

	private Composite createComposite(Composite parent, int numColumns) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());

		final GridLayout layout = new GridLayout(numColumns, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		return composite;
	}

	/**
	 * This method was added to update required PDT preferences from Neon
	 * Settings
	 * 
	 * @deprecated
	 * @param preferences
	 */
	public void updatePDTPreferences(CodeFormatterPreferences preferences) {
		Key indentKey = PHPFormatterConfigurationBlock.PREF_FORMATTER_INDENTATION_SIZE;
		Key tabSizeKey = PHPFormatterConfigurationBlock.PREF_FORMATTER_TAB_SIZE;
		Key useTabKey = PHPFormatterConfigurationBlock.PREF_FORMATTER_USE_TABS;

		setValue(indentKey, new Integer(preferences.indentationSize).toString());
		setValue(tabSizeKey, new Integer(preferences.tabSize).toString());

		if (preferences.indentationChar == CodeFormatterPreferences.TAB_CHAR) {
			setValue(useTabKey, "true");
		} else {
			setValue(useTabKey, "false");
		}

	}

	private void configurePreview(Composite composite, int numColumns) {
		fPhpPreview = new CodeFormatterPreview(
				CodeFormatterPreferences.getDefaultPreferences(), composite);
		fPhpPreview.setPreviewText(PREVIEW);

		final GridData gd = new GridData(GridData.FILL_VERTICAL
				| GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = numColumns;
		gd.verticalSpan = 7;
		gd.widthHint = 0;
		gd.heightHint = 0;
		fPhpPreview.getControl().setLayoutData(gd);
	}
}

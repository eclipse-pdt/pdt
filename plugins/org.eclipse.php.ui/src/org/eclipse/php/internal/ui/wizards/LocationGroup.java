package org.eclipse.php.internal.ui.wizards;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.*;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.environment.IEnvironmentUI;
import org.eclipse.php.internal.server.core.Server;
import org.eclipse.php.internal.server.core.manager.ServersManager;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * Request a location. Fires an event whenever the checkbox or the location
 * field is changed, regardless of whether the change originates from the user
 * or has been invoked programmatically.
 */
public class LocationGroup extends Observable implements Observer,
		IStringButtonAdapter, IDialogFieldListener {
	protected final SelectionButtonDialogField fWorkspaceRadio;
	protected final SelectionButtonDialogField fExternalRadio;
	protected final StringButtonDialogField fLocation;
	// protected final ComboDialogField fEnvironment;
	private IEnvironment[] environments;

	private String fPreviousExternalLocation;
	private int localEnv;
	protected SelectionButtonDialogField fLocalServerRadio;
	protected ComboDialogField fSeverLocationList;
	private String[] docRootArray;
	private NameGroup fNameGroup;
	private Shell shell;

	private static final String DIALOGSTORE_LAST_EXTERNAL_LOC = DLTKUIPlugin.PLUGIN_ID
			+ ".last.external.project"; //$NON-NLS-1$ 

	public LocationGroup(Composite composite, NameGroup nameGroup, Shell shell) {
		this.fNameGroup = nameGroup;
		this.shell = shell;

		final int numColumns = 3;
		final Group group = new Group(composite, SWT.None);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setLayout(new GridLayout(numColumns, false));
		group.setText(NewWizardMessages.ScriptProjectWizardFirstPage_LocationGroup_title);
		fWorkspaceRadio = new SelectionButtonDialogField(SWT.RADIO);
		fWorkspaceRadio.setDialogFieldListener(this);
		fWorkspaceRadio
				.setLabelText(NewWizardMessages.ScriptProjectWizardFirstPage_LocationGroup_workspace_desc);
		fExternalRadio = new SelectionButtonDialogField(SWT.RADIO);
		fExternalRadio.setDialogFieldListener(this);
		fExternalRadio
				.setLabelText(NewWizardMessages.ScriptProjectWizardFirstPage_LocationGroup_external_desc);
		fLocation = new StringButtonDialogField(this);
		fLocation.setDialogFieldListener(this);
		fLocation
				.setLabelText(NewWizardMessages.ScriptProjectWizardFirstPage_LocationGroup_locationLabel_desc);
		fLocation
				.setButtonLabel(NewWizardMessages.ScriptProjectWizardFirstPage_LocationGroup_browseButton_desc);
		// fExternalRadio.attachDialogField(fLocation);
		fWorkspaceRadio.setSelection(true);
		fExternalRadio.setSelection(false);
		fPreviousExternalLocation = ""; //$NON-NLS-1$ 
		fWorkspaceRadio.doFillIntoGrid(group, numColumns);
		fExternalRadio.doFillIntoGrid(group, numColumns);
		fLocation.doFillIntoGrid(group, numColumns);
		LayoutUtil.setHorizontalGrabbing(fLocation.getTextControl(null));
		fExternalRadio.attachDialogFields(new DialogField[] { fLocation });

		environments = EnvironmentManager.getEnvironments();
		String[] items = new String[environments.length];
		localEnv = 0;
		for (int i = 0; i < items.length; i++) {
			items[i] = environments[i].getName();
			if (environments[i].isLocal()) {
				localEnv = i;
			}
		}

		createLocalServersGroup(group, numColumns);

	}

	public boolean isExistingLocation() {
		return fExternalRadio.isSelected();
	}

	/**
	 * check if any of the server can provide local doc root.
	 * 
	 * @param group
	 * @param numColumns
	 */
	protected void createLocalServersGroup(final Group group,
			final int numColumns) {
		Server[] servers = ServersManager.getServers();
		List<String> docRoots = new ArrayList<String>();
		for (int i = 0; i < servers.length; i++) {
			String docRoot = servers[i].getDocumentRoot();
			if (docRoot != null && !"".equals(docRoot.trim())) { //$NON-NLS-1$ 
				docRoots.add(docRoot);
			}
		}

		if (docRoots.size() > 0) {
			fLocalServerRadio = new SelectionButtonDialogField(SWT.RADIO);
			fLocalServerRadio.setDialogFieldListener(this);
			fLocalServerRadio
					.setLabelText(PHPUIMessages.PHPProjectWizardFirstPage_localServerLabel);
			fLocalServerRadio.setSelection(false);
			fLocalServerRadio.doFillIntoGrid(group, numColumns);
			fSeverLocationList = new ComboDialogField(SWT.READ_ONLY);
			fSeverLocationList
					.setLabelText(NewWizardMessages.ScriptProjectWizardFirstPage_LocationGroup_locationLabel_desc);
			fSeverLocationList.doFillIntoGrid(group, numColumns);
			fSeverLocationList.setEnabled(false);
			docRootArray = new String[docRoots.size()];
			docRoots.toArray(docRootArray);
			fSeverLocationList.setItems(docRootArray);
			fSeverLocationList.selectItem(0);
			fLocalServerRadio.attachDialogField(fSeverLocationList);
			fWorkspaceRadio.setSelection(false);
			fLocalServerRadio.setSelection(true);
		} else {
			createNoLocalServersFound(group, numColumns);
		}
	}

	/**
	 * add proper GUI if there's no preconfigured local server
	 * 
	 * @param group
	 * @param numColumns
	 */
	protected void createNoLocalServersFound(Group group, int numColumns) {
		// empty
	}

	public boolean isInLocalServer() {
		return fLocalServerRadio != null && fLocalServerRadio.isSelected();
	}

	protected void fireEvent() {
		setChanged();
		notifyObservers();
	}

	protected String getDefaultPath(String name) {
		IEnvironment environment = this.getEnvironment();
		if (environment != null && environment.isLocal()) {
			final IPath path = Platform.getLocation().append(name);
			return path.toOSString();
		} else {
			return ""; //$NON-NLS-1$ 
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if (isInWorkspace()) {
			fLocation.setText(getDefaultPath(fNameGroup.getName()));
		}
		if (docRootArray != null && docRootArray.length > 0) {
			int index = fSeverLocationList.getSelectionIndex();
			String[] items = getDocItems(docRootArray);
			fSeverLocationList.setItems(items);
			fSeverLocationList.selectItem(index);
		}

		fireEvent();
	}

	private String[] getDocItems(String[] docRootArray) {
		String[] items = new String[docRootArray.length];
		for (int i = 0; i < docRootArray.length; i++) {
			items[i] = docRootArray[i] + File.separator + fNameGroup.getName();
		}
		return items;
	}

	public IPath getLocation() {
		if (isInWorkspace()) {
			return Platform.getLocation();
		}
		if (isInLocalServer()) {
			return new Path(fSeverLocationList.getText());
		}
		return new Path(fLocation.getText().trim());
	}

	public boolean isInWorkspace() {
		return fWorkspaceRadio.isSelected();
	}

	public IEnvironment getEnvironment() {
		if (fWorkspaceRadio.isSelected()) {
			return EnvironmentManager
					.getEnvironmentById(LocalEnvironment.ENVIRONMENT_ID);
		}
		// return environments[fEnvironment.getSelectionIndex()];
		return environments[localEnv];
	}

	public void changeControlPressed(DialogField field) {
		IEnvironment environment = getEnvironment();
		IEnvironmentUI environmentUI = (IEnvironmentUI) environment
				.getAdapter(IEnvironmentUI.class);
		if (environmentUI != null) {
			String selectedDirectory = environmentUI.selectFolder(shell);

			if (selectedDirectory != null) {
				fLocation.setText(selectedDirectory);
				DLTKUIPlugin.getDefault().getDialogSettings()
						.put(DIALOGSTORE_LAST_EXTERNAL_LOC, selectedDirectory);
			}
		}
	}

	public void dialogFieldChanged(DialogField field) {
		if (field == fWorkspaceRadio) {
			final boolean checked = fWorkspaceRadio.isSelected();
			if (checked) {
				fPreviousExternalLocation = fLocation.getText();
				fLocation.setText(getDefaultPath(fNameGroup.getName()));
			} else {
				IEnvironment environment = this.getEnvironment();
				if (environment != null && environment.isLocal()) {
					fLocation.setText(fPreviousExternalLocation);
				} else {
					fLocation.setText(""); //$NON-NLS-1$ 
				}
			}
		}

		fireEvent();
	}

}
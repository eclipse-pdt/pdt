package org.eclipse.php.internal.ui.wizards;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

/**
 * Show a warning when the project location contains files.
 */
public class DetectGroup extends Observable implements Observer,
		SelectionListener {
	private final Link fHintText;
	private Label fIcon;
	private boolean fDetect;
	private LocationGroup fPHPLocationGroup;
	private NameGroup fGroupName;

	public DetectGroup(Composite parent, LocationGroup locationGroup,
			NameGroup nameGroup) {
		this.fPHPLocationGroup = locationGroup;
		this.fGroupName = nameGroup;

		Composite composite = new Composite(parent, SWT.None);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 10;
		composite.setLayout(layout);

		fIcon = new Label(composite, SWT.LEFT);
		fIcon.setImage(Dialog.getImage(Dialog.DLG_IMG_MESSAGE_WARNING));
		GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		fIcon.setLayoutData(gridData);
		fIcon.setVisible(false);

		fHintText = new Link(composite, SWT.WRAP);
		fHintText.setFont(composite.getFont());
		gridData = new GridData(GridData.FILL, SWT.FILL, true, true);
		gridData.widthHint = 500;
		fHintText.setLayoutData(gridData);
		fHintText
				.setText(NewWizardMessages.ScriptProjectWizardFirstPage_DetectGroup_message);
		fHintText.setVisible(false);
	}

	private boolean isValidProjectName(String name) {
		if (name.length() == 0) {
			return false;
		}
		final IWorkspace workspace = DLTKUIPlugin.getWorkspace();
		return workspace.validateName(name, IResource.PROJECT).isOK()
				&& workspace.getRoot().findMember(name) == null;
	}

	public void update(Observable o, Object arg) {
		if (o instanceof LocationGroup) {
			boolean oldDetectState = fDetect;
			IPath location = fPHPLocationGroup.getLocation();
			if (fPHPLocationGroup.isInWorkspace()) {
				if (!isValidProjectName(fGroupName.getName())) {
					fDetect = false;
				} else {
					IEnvironment environment = fPHPLocationGroup
							.getEnvironment();
					final IFileHandle directory = environment.getFile(location
							.append(fGroupName.getName()));
					fDetect = directory.isDirectory();
				}
			} else {
				IEnvironment environment = fPHPLocationGroup.getEnvironment();
				if (location.toPortableString().length() > 0) {
					final IFileHandle directory = environment.getFile(location);
					fDetect = directory.isDirectory()
							&& directory.getPath().toFile().exists();
				}
			}
			if (oldDetectState != fDetect) {
				setChanged();
				notifyObservers();
				if (fDetect) {
					fHintText.setVisible(true);
					fIcon.setVisible(true);
				} else {
					fHintText.setVisible(false);
					fIcon.setVisible(false);
				}

			}
		}
	}

	public boolean mustDetect() {
		return fDetect;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
	 * .swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		widgetDefaultSelected(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org
	 * .eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		if (DLTKCore.DEBUG) {
			System.err.println("DetectGroup show compilancePreferencePage..."); //$NON-NLS-1$ 
		}

	}
}
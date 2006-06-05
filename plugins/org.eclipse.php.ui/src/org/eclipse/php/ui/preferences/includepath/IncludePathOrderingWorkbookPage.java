package org.eclipse.php.ui.preferences.includepath;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.ui.wizards.fields.DialogField;
import org.eclipse.php.ui.wizards.fields.LayoutUtil;
import org.eclipse.php.ui.wizards.fields.ListDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class IncludePathOrderingWorkbookPage extends IncludePathBasePage {

	private ListDialogField fIncludePathList;

	public IncludePathOrderingWorkbookPage(ListDialogField includePathList) {
		fIncludePathList = includePathList;
	}

	public Control getControl(Composite parent) {
		PixelConverter converter = new PixelConverter(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());

		LayoutUtil.doDefaultLayout(composite, new DialogField[] { fIncludePathList }, true, SWT.DEFAULT, SWT.DEFAULT);
		LayoutUtil.setHorizontalGrabbing(fIncludePathList.getListControl(null));

		int buttonBarWidth = converter.convertWidthInCharsToPixels(24);
		fIncludePathList.setButtonsMinWidth(buttonBarWidth);

		return composite;
	}

	/*
	 * @see BuildPathBasePage#getSelection
	 */
	public List getSelection() {
		return fIncludePathList.getSelectedElements();
	}

	/*
	 * @see BuildPathBasePage#setSelection
	 */
	public void setSelection(List selElements, boolean expand) {
		fIncludePathList.selectElements(new StructuredSelection(selElements));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.wizards.buildpaths.BuildPathBasePage#isEntryKind(int)
	 */
	public boolean isEntryKind(int kind) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.wizards.buildpaths.BuildPathBasePage#init(org.eclipse.jdt.core.IJavaProject)
	 */
	public void init(IProject phpProject) {
	}

}

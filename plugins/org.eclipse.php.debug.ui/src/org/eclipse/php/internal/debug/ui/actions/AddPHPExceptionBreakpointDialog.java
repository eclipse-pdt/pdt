/*******************************************************************************
 * Copyright (c) 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.actions;

import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ITypeHierarchy;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.internal.core.hierarchy.FakeType;
import org.eclipse.dltk.ui.util.PixelConverter;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.model.PHPModelAccess;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIPlugin;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Dialog for adding PHP exception breakpoints.
 * 
 * @author Bartlomiej Laczkowski
 */
public class AddPHPExceptionBreakpointDialog extends SelectionDialog {

	private class LabelProvider extends StyledCellLabelProvider {

		@Override
		public void update(ViewerCell cell) {
			Object element = cell.getElement();
			cell.setImage(getImage(element));
			String text = getText(element);
			cell.setText(text);
			int separatorIndex = text.indexOf('-');
			StyleRange greyRange = new StyleRange(separatorIndex, text.length() - separatorIndex,
					Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY), null);
			cell.setStyleRanges(new StyleRange[] { greyRange });
			super.update(cell);
		}

		private String getText(Object element) {
			IType exception = (IType) element;
			if (exception instanceof ErrorType) {
				return MessageFormat.format(Messages.AddPHPExceptionBreakpointDialog_Exception_description,
						exception.getElementName(), ((ErrorType) exception).getDescription());
			}
			return MessageFormat.format(Messages.AddPHPExceptionBreakpointDialog_Exception_description,
					exception.getElementName(), exception.getSourceModule().getElementName());
		}

		private Image getImage(Object element) {
			IType exception = (IType) element;
			if (exception instanceof ErrorType) {
				return PHPDebugUIImages.get(PHPDebugUIImages.IMG_ELCL_ERROR_BREAKPOINT);
			}
			return PHPDebugUIImages.get(PHPDebugUIImages.IMG_ELCL_EXCEPTION_BREAKPOINT);
		}

	};

	static class ErrorType extends FakeType {

		private String description;

		/**
		 * @param sourceModule
		 * @param name
		 */
		public ErrorType(String name, String description) {
			super(null, name);
			this.description = description;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

	}

	public static final IType[] ERROR_TYPES = {
			new ErrorType("Notice", Messages.AddPHPExceptionBreakpointDialog_Suspend_on_notice), //$NON-NLS-1$
			new ErrorType("Warning", Messages.AddPHPExceptionBreakpointDialog_Suspend_on_error), //$NON-NLS-1$
			new ErrorType("Deprecated", Messages.AddPHPExceptionBreakpointDialog_Suspend_on_deprecated) }; //$NON-NLS-1$

	private static final String EXCEPTION_BASE_CLASS_NAME = "Exception"; //$NON-NLS-1$

	private static final String DIALOG_SETTINGS = "org.eclipse.php.debug.ui.dialogs.AddPHPExceptionDialog"; //$NON-NLS-1$
	private static final String WIDTH = "width"; //$NON-NLS-1$
	private static final String HEIGHT = "height"; //$NON-NLS-1$

	private Set<IType> fExceptionTypesCache;
	private Text fExceptionFilter;
	private TableViewer fExceptionViewer;

	private IDialogSettings fSettings;
	private Point fLocation;
	private Point fSize;

	/**
	 * Creates new selection dialog.
	 * 
	 * @param parent
	 *            parent shell
	 */
	public AddPHPExceptionBreakpointDialog() {
		super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		initializeSettings();
		BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), new Runnable() {
			@Override
			public void run() {
				fetchExceptionTypes();
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 */
	@Override
	public boolean close() {
		writeSettings();
		return super.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#getInitialSize()
	 */
	@Override
	protected Point getInitialSize() {
		Point result = super.getInitialSize();
		if (fSize != null) {
			result.x = Math.max(result.x, fSize.x);
			result.y = Math.max(result.y, fSize.y);
			Rectangle display = getShell().getDisplay().getClientArea();
			result.x = Math.min(result.x, display.width);
			result.y = Math.min(result.y, display.height);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#getInitialLocation(org.eclipse.swt.
	 * graphics.Point)
	 */
	@Override
	protected Point getInitialLocation(Point initialSize) {
		Point result = super.getInitialLocation(initialSize);
		if (fLocation != null) {
			result.x = fLocation.x;
			result.y = fLocation.y;
			Rectangle display = getShell().getDisplay().getClientArea();
			int xe = result.x + initialSize.x;
			if (xe > display.width) {
				result.x -= xe - display.width;
			}
			int ye = result.y + initialSize.y;
			if (ye > display.height) {
				result.y -= ye - display.height;
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		readSettings();
		Composite area = (Composite) super.createDialogArea(parent);
		Label filterLabel = new Label(area, SWT.NONE);
		filterLabel.setText(Messages.AddPHPExceptionBreakpointDialog_Choose_an_exception_filter_message);
		fExceptionFilter = new Text(area, SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		fExceptionFilter.setLayoutData(gridData);
		fExceptionFilter.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				filterExceptionTypes(fExceptionFilter.getText());
			}
		});
		fExceptionFilter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN) {
					fExceptionViewer.getTable().setFocus();
				}
			}
		});
		Label itemsLabel = new Label(area, SWT.NONE);
		itemsLabel.setText(Messages.AddPHPExceptionBreakpointDialog_Matching_items);
		fExceptionViewer = new TableViewer(area, SWT.BORDER | SWT.MULTI);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		PixelConverter converter = new PixelConverter(fExceptionViewer.getTable());
		gridData.widthHint = converter.convertWidthInCharsToPixels(60);
		gridData.heightHint = SWTUtil.getTableHeightHint(fExceptionViewer.getTable(), 10);
		fExceptionViewer.getTable().setLayoutData(gridData);
		fExceptionViewer.getTable().setFont(parent.getFont());
		fExceptionViewer.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				okPressed();
			}
		});
		fExceptionViewer.setContentProvider(new ArrayContentProvider());
		fExceptionViewer.setLabelProvider(new LabelProvider());
		Collection<IType> input = fExceptionTypesCache;
		fExceptionViewer.setInput(fExceptionTypesCache);
		if (!input.isEmpty()) {
			fExceptionViewer.setSelection(new StructuredSelection(input.iterator().next()));
		}
		CLabel noteIcon = new CLabel(area, SWT.NONE);
		noteIcon.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		noteIcon.setImage(Dialog.getImage(Dialog.DLG_IMG_MESSAGE_INFO));
		noteIcon.setText(Messages.AddPHPExceptionBreakpointDialog_Note);
		return area;
	}

	/*
	 * @see Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		computeResult();
		super.okPressed();
	}

	private void computeResult() {
		List<Object> results = new ArrayList<>();
		IStructuredSelection selection = fExceptionViewer.getStructuredSelection();
		for (Object element : selection.toArray()) {
			results.add(element);
		}
		setResult(results);
	}

	private void initializeSettings() {
		IDialogSettings settings = PHPDebugUIPlugin.getDefault().getDialogSettings();
		fSettings = settings.getSection(DIALOG_SETTINGS);
		if (fSettings == null) {
			fSettings = new DialogSettings(DIALOG_SETTINGS);
			settings.addSection(fSettings);
			fSettings.put(WIDTH, 480);
			fSettings.put(HEIGHT, 320);
		}
	}

	private void readSettings() {
		try {
			int x = fSettings.getInt("x"); //$NON-NLS-1$
			int y = fSettings.getInt("y"); //$NON-NLS-1$
			fLocation = new Point(x, y);
		} catch (NumberFormatException e) {
			fLocation = null;
		}
		try {
			int width = fSettings.getInt("width"); //$NON-NLS-1$
			int height = fSettings.getInt("height"); //$NON-NLS-1$
			fSize = new Point(width, height);

		} catch (NumberFormatException e) {
			fSize = null;
		}
	}

	private void writeSettings() {
		Point location = getShell().getLocation();
		fSettings.put("x", location.x); //$NON-NLS-1$
		fSettings.put("y", location.y); //$NON-NLS-1$
		Point size = getShell().getSize();
		fSettings.put("width", size.x); //$NON-NLS-1$
		fSettings.put("height", size.y); //$NON-NLS-1$
	}

	private void filterExceptionTypes(String text) {
		String patternBase = text.replaceAll("\\*", ".*").replaceAll("\\?", ".").concat(".*"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		Pattern pattern = Pattern.compile(patternBase, Pattern.CASE_INSENSITIVE);
		List<IType> filtered = new LinkedList<>();
		for (IType type : fExceptionTypesCache) {
			if (pattern.matcher(type.getElementName()).matches()) {
				filtered.add(type);
			}
		}
		fExceptionViewer.setInput(filtered);
		if (!filtered.isEmpty()) {
			fExceptionViewer.setSelection(new StructuredSelection(filtered.iterator().next()));
		}
	}

	private void fetchExceptionTypes() {
		if (fExceptionTypesCache == null) {
			fExceptionTypesCache = new TreeSet<>(new Comparator<IType>() {
				@Override
				public int compare(IType o1, IType o2) {
					if (o1 instanceof ErrorType && !(o2 instanceof ErrorType)) {
						return -1;
					}
					return o1.getElementName().toLowerCase().compareTo(o2.getElementName().toLowerCase());
				}
			});
			final PHPModelAccess modelAccess = PHPModelAccess.getDefault();
			final IDLTKSearchScope scope = SearchEngine.createWorkspaceScope(PHPLanguageToolkit.getDefault());
			// Make sure that scope is up-to-date
			scope.enclosingProjectsAndZips();
			IType[] exceptionBaseTypes = modelAccess.findTypes(EXCEPTION_BASE_CLASS_NAME, MatchRule.EXACT, 0, 0, scope,
					null);
			for (IType exceptionBaseType : exceptionBaseTypes) {
				try {
					fExceptionTypesCache.add(exceptionBaseType);
					ITypeHierarchy hierarchy = exceptionBaseType.newTypeHierarchy(new NullProgressMonitor());
					IType[] subtypes = hierarchy.getAllSubtypes(exceptionBaseType);
					for (IType subType : subtypes) {
						fExceptionTypesCache.add(subType);
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
			for (IType mockType : ERROR_TYPES) {
				fExceptionTypesCache.add(mockType);
			}
		}
	}

}

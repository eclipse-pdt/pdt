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
/**
 * 
 */
package org.eclipse.php.internal.debug.ui.preferences;

import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.debug.core.debugger.AbstractDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.debugger.IDebuggerConfiguration;
import org.eclipse.php.internal.debug.core.preferences.PHPDebuggersRegistry;
import org.eclipse.php.internal.debug.ui.PHPDebugUIImages;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.php.internal.ui.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * A PHP debuggers table.
 * 
 * @author Shalom Gibly
 */
public class PHPDebuggersTable {

	/**
	 * The main list control
	 */
	private TableViewer fPHPDebuggers;
	private Button fSettingsButton;
	// column weights
	private float fWeight1 = 4.9F / 5F;
	// ignore column re-sizing when the table is being resized
	private boolean fResizingTable = false;

	/**
	 * Creates this control inside the given control.
	 * 
	 * @param ancestor
	 *            containing control
	 */
	public void createControl(final Composite ancestor) {
		// Create a 'cosmetic' composite to fit the dialog style of margins.
		Composite comp = new Composite(ancestor, SWT.NULL);
		GridLayout layout = new GridLayout();
		comp.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		comp.setLayoutData(data);

		final Group parent = new Group(comp, SWT.NULL);
		parent.setText(PHPDebugUIMessages.PHPDebuggersTable_installedDebuggers);
		layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		parent.setLayout(layout);
		final Font font = ancestor.getFont();
		parent.setFont(font);
		data = new GridData(GridData.FILL_BOTH);
		parent.setLayoutData(data);

		final Table table = new Table(parent, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);

		data = new GridData(GridData.FILL_BOTH);
		data.heightHint = 35;
		table.setLayoutData(data);
		table.setFont(font);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		final TableLayout tableLayout = new TableLayout();
		table.setLayout(tableLayout);

		final TableColumn debuggerTypeColumn = new TableColumn(table, SWT.NULL);
		debuggerTypeColumn.setText(PHPDebugUIMessages.PHPDebuggersTable_debuggerType);

		fPHPDebuggers = new CheckboxTableViewer(table);
		fPHPDebuggers.setLabelProvider(new PHPDebuggersLabelProvider());
		fPHPDebuggers.setContentProvider(new PHPDebuggersContentProvider());
		fPHPDebuggers.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(final SelectionChangedEvent evt) {
				enableButtons();
			}
		});

		fPHPDebuggers.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(final DoubleClickEvent e) {
				if (!fPHPDebuggers.getSelection().isEmpty()) {
					editSettings();
				}
			}
		});

		final Composite buttons = new Composite(parent, SWT.NULL);
		buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		buttons.setLayout(layout);
		buttons.setFont(font);

		fSettingsButton = createPushButton(buttons, PHPDebugUIMessages.PHPDebuggersTable_configure);
		fSettingsButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(final Event evt) {
				editSettings();
			}
		});

		fPHPDebuggers.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if (e1 instanceof IDebuggerConfiguration && e2 instanceof IDebuggerConfiguration) {
					return ((IDebuggerConfiguration) e2).getName().compareTo(((IDebuggerConfiguration) e1).getName());
				}
				return super.compare(viewer, e1, e2);
			}
		});
		configureTableResizing(parent, buttons, table, debuggerTypeColumn);

		fillWithWorkspaceDebuggers();
		enableButtons();
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				resizeTable(parent, buttons, table, debuggerTypeColumn);
			}
		});
	}

	/**
	 * Set the debuggers to their default values (ports etc.)
	 */
	public void performDefaults() {
		AbstractDebuggerConfiguration[] debuggersConfigurations = PHPDebuggersRegistry.getDebuggersConfigurations();
		if (debuggersConfigurations == null) {
			return;
		}
		for (AbstractDebuggerConfiguration conf : debuggersConfigurations) {
			conf.applyDefaults();
		}
		fPHPDebuggers.refresh();
	}

	private void configureTableResizing(final Composite parent, final Composite buttons, final Table table,
			final TableColumn debuggerTypeColumn) {
		parent.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				resizeTable(parent, buttons, table, debuggerTypeColumn);
			}
		});
		table.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(final Event event) {
				table.removeListener(SWT.Paint, this);
				resizeTable(parent, buttons, table, debuggerTypeColumn);
			}
		});
		debuggerTypeColumn.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(final ControlEvent e) {
				if (debuggerTypeColumn.getWidth() > 0 && !fResizingTable) {
					fWeight1 = getColumnWeight(0);
				}
			}
		});
	}

	private void resizeTable(final Composite parent, final Composite buttons, final Table table,
			final TableColumn column1) {
		fResizingTable = true;
		int parentWidth = -1;
		int parentHeight = -1;
		if (parent.isVisible()) {
			final Rectangle area = parent.getClientArea();
			parentWidth = area.width;
			parentHeight = area.height;
		} else {
			final Point parentSize = parent.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			parentWidth = parentSize.x;
			parentHeight = parentSize.y;
		}
		final Point preferredSize = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		int width = parentWidth - 2 * table.getBorderWidth();
		if (preferredSize.y > parentHeight) {
			// Subtract the scrollbar width from the total column width
			// if a vertical scrollbar will be required
			final Point vBarSize = table.getVerticalBar().getSize();
			width -= vBarSize.x;
		}
		width -= buttons.getSize().x;
		final Point oldSize = table.getSize();
		if (oldSize.x > width) {
			// table is getting smaller so make the columns
			// smaller first and then resize the table to
			// match the client area width
			column1.setWidth(Math.round(width * fWeight1));
			table.setSize(width, parentHeight);
		} else {
			// table is getting bigger so make the table
			// bigger first and then make the columns wider
			// to match the client area width
			table.setSize(width, parentHeight);
			column1.setWidth(Math.round(width * fWeight1));
		}
		fResizingTable = false;
	}

	private float getColumnWeight(final int col) {
		final Table table = fPHPDebuggers.getTable();
		final int tableWidth = table.getSize().x;
		final int columnWidth = table.getColumn(col).getWidth();
		if (tableWidth > columnWidth) {
			return (float) columnWidth / tableWidth;
		}
		switch (col) {
		case 0:
			return 4.9F / 5F;
		default:
			return 4.9F / 5F;
		}
	}

	/*
	 * Set the debuggers input to the table.
	 */
	private void fillWithWorkspaceDebuggers() {
		AbstractDebuggerConfiguration[] debuggersConfigurations = PHPDebuggersRegistry.getDebuggersConfigurations();
		fPHPDebuggers.setInput(debuggersConfigurations);
		fPHPDebuggers.refresh();

	}

	// Enable / Disable the setting button
	private void enableButtons() {
		IStructuredSelection selection = (IStructuredSelection) fPHPDebuggers.getSelection();
		fSettingsButton.setEnabled(selection.size() == 1);
	}

	/**
	 * Creates a push button.
	 * 
	 * @param parent
	 * @param label
	 * @return
	 */
	protected Button createPushButton(final Composite parent, final String label) {
		return SWTUtil.createPushButton(parent, label, null);
	}

	/*
	 * Edit the settings for the selected PHP debugger.
	 */
	private void editSettings() {
		final IStructuredSelection selection = (IStructuredSelection) fPHPDebuggers.getSelection();
		final AbstractDebuggerConfiguration phpDebuggerConfiguration = (AbstractDebuggerConfiguration) selection
				.getFirstElement();
		if (phpDebuggerConfiguration == null) {
			return;
		}
		// Open the edit dialog after creating it's content by calling the
		// AbstractDebuggerConfiguration#openConfigurationDialog
		phpDebuggerConfiguration.openConfigurationDialog(Display.getDefault().getActiveShell());
		fPHPDebuggers.refresh(phpDebuggerConfiguration);
		commitChanges();
	}

	/*
	 * Save the changes to the preferences.
	 */
	protected void commitChanges() {
		// TODO - Save the changes to the preferences (if needed).
	}

	/**
	 * Content provider to show a list of PHP debuggers
	 */
	class PHPDebuggersContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(final Object input) {
			return PHPDebuggersRegistry.getDebuggersConfigurations();
		}

		@Override
		public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		}
	}

	/**
	 * Label provider for installed PHPs table.
	 */
	class PHPDebuggersLabelProvider extends LabelProvider implements ITableLabelProvider {

		/**
		 * @see ITableLabelProvider#getColumnImage(Object, int)
		 */
		@Override
		public Image getColumnImage(final Object element, final int columnIndex) {
			return PHPDebugUIImages.get(PHPDebugUIImages.IMG_OBJ_DEBUG_CONF);
		}

		/**
		 * @see ITableLabelProvider#getColumnText(Object, int)
		 */
		@Override
		public String getColumnText(final Object element, final int columnIndex) {
			if (element instanceof IDebuggerConfiguration) {
				IDebuggerConfiguration configuration = (IDebuggerConfiguration) element;
				switch (columnIndex) {
				case 0:
					return configuration.getName();
				}
			}
			return element.toString();
		}
	}
}

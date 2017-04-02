/*******************************************************************************
 * Copyright (c) 2000, 2012, 2016 IBM Corporation and others.
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

import java.util.*;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.dltk.internal.ui.util.CoreUtility;
import org.eclipse.dltk.internal.ui.util.SWTUtil;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.Util;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.util.Key;
import org.eclipse.php.internal.ui.util.ScrolledPageContent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.ui.preferences.IWorkingCopyManager;
import org.eclipse.ui.preferences.WorkingCopyManager;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Abstract options configuration block providing a general implementation for
 * setting up an options configuration page.
 *
 * @since 2.1
 */
public abstract class OptionsConfigurationBlock {

	/**
	 * Key that is only managed locally and not part of preference store.
	 */
	private static class LocalKey extends Key {
		private final HashMap<IScopeContext, String> fValues;

		private LocalKey(String key) {
			super("local", key); //$NON-NLS-1$
			fValues = new HashMap<IScopeContext, String>();
		}

		@Override
		public String getStoredValue(IScopeContext context, IWorkingCopyManager manager) {
			return fValues.get(context);
		}

		@Override
		public void setStoredValue(IScopeContext context, String value, IWorkingCopyManager manager) {
			if (value != null) {
				fValues.put(context, value);
			} else {
				fValues.remove(context);
			}
		}
	}

	protected static class ControlData {
		private final Key fKey;
		private final String[] fValues;

		public ControlData(Key key, String[] values) {
			fKey = key;
			fValues = values;
		}

		public Key getKey() {
			return fKey;
		}

		public String getValue(boolean selection) {
			int index = selection ? 0 : 1;
			return fValues[index];
		}

		public String getValue(int index) {
			return fValues[index];
		}

		public int getSelection(String value) {
			if (value != null) {
				for (int i = 0; i < fValues.length; i++) {
					if (value.equals(fValues[i])) {
						return i;
					}
				}
			}
			return fValues.length - 1; // assume the last option is the least
										// severe
		}
	}

	protected static class LinkControlData extends ControlData {
		private Link fLink;

		public LinkControlData(Key key, String[] values) {
			super(key, values);
		}

		public void setLink(Link link) {
			fLink = link;
		}

		public Link getLink() {
			return fLink;
		}
	}

	private static class HighlightPainter implements PaintListener {

		private final Composite fParent;
		private final Label fLabelControl;
		private final Combo fComboBox;
		int fColor;

		public HighlightPainter(Composite parent, Label labelControl, Combo comboBox, int color) {
			fParent = parent;
			fLabelControl = labelControl;
			fComboBox = comboBox;
			fColor = color;
		}

		@Override
		public void paintControl(PaintEvent e) {
			if (((GridData) fLabelControl.getLayoutData()).exclude) {
				fParent.removePaintListener(this);
				fLabelControl.setData(null);
				return;
			}

			int GAP = 7;
			int ARROW = 3;
			Rectangle l = fLabelControl.getBounds();
			Point c = fComboBox.getLocation();

			e.gc.setForeground(e.display.getSystemColor(fColor));
			int x2 = c.x - GAP;
			int y = l.y + l.height / 2 + 1;

			e.gc.drawLine(l.x + l.width + GAP, y, x2, y);
			e.gc.drawLine(x2 - ARROW, y - ARROW, x2, y);
			e.gc.drawLine(x2 - ARROW, y + ARROW, x2, y);
		}
	}

	private static final String REBUILD_COUNT_KEY = "preferences_build_requested"; //$NON-NLS-1$

	private static final String SETTINGS_EXPANDED = "expanded"; //$NON-NLS-1$

	protected final ArrayList<Button> fCheckBoxes;
	protected final ArrayList<Combo> fComboBoxes;
	protected final ArrayList<Text> fTextBoxes;
	protected final ArrayList<Link> fLinks;
	protected final HashMap<Control, Label> fLabels;
	protected final ArrayList<ExpandableComposite> fExpandableComposites;

	private SelectionListener fSelectionListener;
	private ModifyListener fTextModifyListener;

	protected IStatusChangeListener fContext;
	protected final IProject fProject; // project or null
	protected final Key[] fAllKeys;

	private IScopeContext[] fLookupOrder;

	private Shell fShell;

	protected final IWorkingCopyManager fManager;
	private final IWorkbenchPreferenceContainer fContainer;

	private Map<Key, String> fDisabledProjectSettings; // null when project
														// specific settings are
														// turned off

	private int fRebuildCount; /// used to prevent multiple dialogs that ask for
								/// a rebuild

	public OptionsConfigurationBlock(IStatusChangeListener context, IProject project, Key[] allKeys,
			IWorkbenchPreferenceContainer container) {
		fContext = context;
		fProject = project;
		fAllKeys = allKeys;
		fContainer = container;
		if (container == null) {
			fManager = new WorkingCopyManager();
		} else {
			fManager = container.getWorkingCopyManager();
		}

		if (fProject != null) {
			fLookupOrder = new IScopeContext[] { new ProjectScope(fProject), InstanceScope.INSTANCE,
					DefaultScope.INSTANCE };
		} else {
			fLookupOrder = new IScopeContext[] { InstanceScope.INSTANCE, DefaultScope.INSTANCE };
		}
		testIfOptionsComplete(allKeys);
		if (fProject == null || hasProjectSpecificOptions(fProject)) {
			fDisabledProjectSettings = null;
		} else {
			fDisabledProjectSettings = new IdentityHashMap<Key, String>();
			for (int i = 0; i < allKeys.length; i++) {
				Key curr = allKeys[i];
				fDisabledProjectSettings.put(curr, curr.getStoredValue(fLookupOrder, false, fManager));
			}
		}

		settingsUpdated();

		fCheckBoxes = new ArrayList<Button>();
		fComboBoxes = new ArrayList<Combo>();
		fTextBoxes = new ArrayList<Text>(2);
		fLinks = new ArrayList<Link>(2);
		fLabels = new HashMap<Control, Label>();
		fExpandableComposites = new ArrayList<ExpandableComposite>();

		fRebuildCount = getRebuildCount();
	}

	protected final IWorkbenchPreferenceContainer getPreferenceContainer() {
		return fContainer;
	}

	protected static Key getKey(String plugin, String key) {
		return new Key(plugin, key);
	}

	protected final static Key getLocalKey(String key) {
		return new LocalKey(key);
	}

	private void testIfOptionsComplete(Key[] allKeys) {
		for (int i = 0; i < allKeys.length; i++) {
			Key key = allKeys[i];
			if (!(key instanceof LocalKey)) {
				if (key.getStoredValue(fLookupOrder, false, fManager) == null) {
					PHPUiPlugin.logErrorMessage(
							"preference option missing: " + key + " (" + this.getClass().getName() + ')'); //$NON-NLS-1$//$NON-NLS-2$
				}
			}
		}
	}

	private int getRebuildCount() {
		return fManager.getWorkingCopy(DefaultScope.INSTANCE.getNode(PHPUiPlugin.ID)).getInt(REBUILD_COUNT_KEY, 0);
	}

	private void incrementRebuildCount() {
		fRebuildCount++;
		fManager.getWorkingCopy(DefaultScope.INSTANCE.getNode(PHPUiPlugin.ID)).putInt(REBUILD_COUNT_KEY, fRebuildCount);
	}

	protected void settingsUpdated() {
		// hook for subclasses
	}

	public void selectOption(String key, String qualifier) {
		for (int i = 0; i < fAllKeys.length; i++) {
			Key curr = fAllKeys[i];
			if (curr.getName().equals(key) && curr.getQualifier().equals(qualifier)) {
				selectOption(curr);
			}
		}
	}

	public void selectOption(Key key) {
		Control control = findControl(key);
		if (control != null) {
			if (!fExpandableComposites.isEmpty()) {
				ExpandableComposite expandable = getParentExpandableComposite(control);
				if (expandable != null) {
					for (int i = 0; i < fExpandableComposites.size(); i++) {
						ExpandableComposite curr = fExpandableComposites.get(i);
						curr.setExpanded(curr == expandable);
					}
					expandedStateChanged(expandable);
				}
			}
			control.setFocus();
		}
	}

	public boolean hasProjectSpecificOptions(IProject project) {
		return hasProjectSpecificOptions(project, fAllKeys, fManager);
	}

	public static boolean hasProjectSpecificOptions(IProject project, Key[] allKeys, IWorkingCopyManager manager) {
		if (project != null) {
			IScopeContext projectContext = new ProjectScope(project);
			for (int i = 0; i < allKeys.length; i++) {
				if (allKeys[i].getStoredValue(projectContext, manager) != null) {
					return true;
				}
			}
		}
		return false;
	}

	protected Shell getShell() {
		return fShell;
	}

	protected void setShell(Shell shell) {
		fShell = shell;
	}

	protected abstract Control createContents(Composite parent);

	protected Button addCheckBox(Composite parent, String label, Key key, String[] values, int indent) {
		ControlData data = new ControlData(key, values);

		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 3;
		gd.horizontalIndent = indent;

		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setFont(JFaceResources.getDialogFont());
		checkBox.setText(label);
		checkBox.setData(data);
		checkBox.setLayoutData(gd);
		checkBox.addSelectionListener(getSelectionListener());

		makeScrollableCompositeAware(checkBox);

		updateCheckBox(checkBox);

		fCheckBoxes.add(checkBox);

		return checkBox;
	}

	protected Button addCheckBoxWithLink(Composite parent, final String label, Key key, String[] values, int indent,
			int widthHint, final SelectionListener listener) {
		LinkControlData data = new LinkControlData(key, values);

		GridData gd = new GridData(GridData.FILL, GridData.FILL, true, false);
		gd.horizontalSpan = 3;
		gd.horizontalIndent = indent;

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(gd);

		final Button checkBox = new Button(composite, SWT.CHECK);
		checkBox.setFont(JFaceResources.getDialogFont());
		gd = new GridData(GridData.FILL, GridData.CENTER, false, false);
		int offset = Util.isMac() ? -4
				: Util.isLinux() ? -2 : /* Windows et al. */ 3;
		gd.widthHint = checkBox.computeSize(SWT.DEFAULT, SWT.DEFAULT).x + offset;
		checkBox.setLayoutData(gd);
		checkBox.setData(data);
		checkBox.addSelectionListener(getSelectionListener());
		checkBox.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			@Override
			public void getName(AccessibleEvent e) {
				e.result = LegacyActionTools.removeMnemonics(label.replaceAll("</?[aA][^>]*>", "")); //$NON-NLS-1$ //$NON-NLS-2$
			}
		});

		gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gd.widthHint = widthHint;

		Link link = new Link(composite, SWT.NONE);
		link.setText(label);
		link.setLayoutData(gd);
		link.setData(key);
		data.setLink(link);

		// toggle checkbox when user clicks unlinked text in link:
		final boolean[] linkSelected = { false };
		link.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				linkSelected[0] = true;
				if (listener != null) {
					listener.widgetSelected(e);
				}
			}
		});
		link.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				linkSelected[0] = false;
			}

			@Override
			public void mouseUp(MouseEvent e) {
				if (!linkSelected[0]) {
					checkBox.setSelection(!checkBox.getSelection());
					checkBox.setFocus();
					linkSelected[0] = false;
					controlChanged(checkBox);
				}
			}
		});
		link.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_MNEMONIC && e.doit == true) {
					e.detail = SWT.TRAVERSE_NONE;
					checkBox.setSelection(!checkBox.getSelection());
					checkBox.setFocus();
					linkSelected[0] = false;
					controlChanged(checkBox);
				}
			}
		});

		makeScrollableCompositeAware(link);
		makeScrollableCompositeAware(checkBox);

		updateCheckBox(checkBox);

		fCheckBoxes.add(checkBox);
		fLinks.add(link);

		return checkBox;
	}

	protected Combo addComboBox(Composite parent, String label, Key key, String[] values, String[] valueLabels,
			int indent) {
		GridData gd = new GridData(GridData.BEGINNING, GridData.CENTER, true, false, 2, 1);
		gd.horizontalIndent = indent;

		Label labelControl = new Label(parent, SWT.LEFT);
		labelControl.setFont(JFaceResources.getDialogFont());
		labelControl.setText(label);
		labelControl.setLayoutData(gd);

		Combo comboBox = newComboControl(parent, key, values, valueLabels);
		comboBox.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		fLabels.put(comboBox, labelControl);

		addHighlight(parent, labelControl, comboBox);

		return comboBox;
	}

	protected static final int HIGHLIGHT_FOCUS = SWT.COLOR_WIDGET_DARK_SHADOW;
	protected static final int HIGHLIGHT_MOUSE = SWT.COLOR_WIDGET_NORMAL_SHADOW;
	protected static final int HIGHLIGHT_NONE = SWT.NONE;

	private void addHighlight(final Composite parent, final Label labelControl, final Combo comboBox) {
		comboBox.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				highlight(parent, labelControl, comboBox, HIGHLIGHT_NONE);
			}

			@Override
			public void focusGained(FocusEvent e) {
				highlight(parent, labelControl, comboBox, HIGHLIGHT_FOCUS);
			}
		});

		MouseTrackAdapter labelComboListener = new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				highlight(parent, labelControl, comboBox, comboBox.isEnabled()
						? comboBox.isFocusControl() ? HIGHLIGHT_FOCUS : HIGHLIGHT_MOUSE : HIGHLIGHT_NONE);
			}

			@Override
			public void mouseExit(MouseEvent e) {
				if (!comboBox.isFocusControl())
					highlight(parent, labelControl, comboBox, HIGHLIGHT_NONE);
			}
		};
		comboBox.addMouseTrackListener(labelComboListener);
		labelControl.addMouseTrackListener(labelComboListener);

		class MouseMoveTrackListener extends MouseTrackAdapter implements MouseMoveListener, MouseListener {
			@Override
			public void mouseExit(MouseEvent e) {
				if (!comboBox.isFocusControl())
					highlight(parent, labelControl, comboBox, HIGHLIGHT_NONE);
			}

			@Override
			public void mouseMove(MouseEvent e) {
				int color = comboBox.isEnabled() ? comboBox.isFocusControl() ? HIGHLIGHT_FOCUS
						: isAroundLabel(e) ? HIGHLIGHT_MOUSE : HIGHLIGHT_NONE : HIGHLIGHT_NONE;
				highlight(parent, labelControl, comboBox, color);
			}

			@Override
			public void mouseDown(MouseEvent e) {
				if (isAroundLabel(e))
					comboBox.setFocus();
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// not used
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// not used
			}

			private boolean isAroundLabel(MouseEvent e) {
				int lx = labelControl.getLocation().x;
				Rectangle c = comboBox.getBounds();
				int x = e.x;
				int y = e.y;
				boolean isAroundLabel = lx - 5 < x && x < c.x && c.y - 2 < y && y < c.y + c.height + 2;
				return isAroundLabel;
			}
		}
		MouseMoveTrackListener parentListener = new MouseMoveTrackListener();
		parent.addMouseMoveListener(parentListener);
		parent.addMouseTrackListener(parentListener);
		parent.addMouseListener(parentListener);

		MouseAdapter labelClickListener = new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				comboBox.setFocus();
			}
		};
		labelControl.addMouseListener(labelClickListener);
	}

	protected void highlight(final Composite parent, final Label labelControl, final Combo comboBox, final int color) {
		Object data = labelControl.getData();
		if (data == null) {
			if (color != HIGHLIGHT_NONE) {
				PaintListener painter = new HighlightPainter(parent, labelControl, comboBox, color);
				parent.addPaintListener(painter);
				labelControl.setData(painter);
			} else {
				return;
			}
		} else {
			if (color == HIGHLIGHT_NONE) {
				parent.removePaintListener((PaintListener) data);
				labelControl.setData(null);
			} else if (color != ((HighlightPainter) data).fColor) {
				((HighlightPainter) data).fColor = color;
			} else {
				return;
			}
		}

		parent.redraw();
	}

	protected int getHighlight(Label labelControl) {
		Object data = labelControl.getData();
		if (data == null)
			return HIGHLIGHT_NONE;
		return ((HighlightPainter) data).fColor;
	}

	protected Combo addInversedComboBox(Composite parent, String label, Key key, String[] values, String[] valueLabels,
			int indent) {
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.horizontalIndent = indent;
		gd.horizontalSpan = 3;

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(gd);

		Combo comboBox = newComboControl(composite, key, values, valueLabels);
		comboBox.setFont(JFaceResources.getDialogFont());
		comboBox.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		Label labelControl = new Label(composite, SWT.LEFT | SWT.WRAP);
		labelControl.setText(label);
		labelControl.setLayoutData(new GridData());

		fLabels.put(comboBox, labelControl);
		return comboBox;
	}

	protected Combo newComboControl(Composite composite, Key key, String[] values, String[] valueLabels) {
		ControlData data = new ControlData(key, values);

		Combo comboBox = new Combo(composite, SWT.READ_ONLY);
		comboBox.setItems(valueLabels);
		comboBox.setData(data);
		comboBox.addSelectionListener(getSelectionListener());
		comboBox.setFont(JFaceResources.getDialogFont());
		SWTUtil.setDefaultVisibleItemCount(comboBox);

		makeScrollableCompositeAware(comboBox);

		updateCombo(comboBox);

		fComboBoxes.add(comboBox);
		return comboBox;
	}

	protected Text addTextField(Composite parent, String label, Key key, int indent, int widthHint) {
		Label labelControl = new Label(parent, SWT.WRAP);
		labelControl.setText(label);
		labelControl.setFont(JFaceResources.getDialogFont());
		GridData gd = new GridData();
		gd.horizontalIndent = indent;
		labelControl.setLayoutData(gd);

		Text textBox = new Text(parent, SWT.BORDER | SWT.SINGLE);
		textBox.setData(key);

		makeScrollableCompositeAware(textBox);

		fLabels.put(textBox, labelControl);

		updateText(textBox);

		textBox.addModifyListener(getTextModifyListener());

		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		if (widthHint != 0) {
			data.widthHint = widthHint;
		}
		data.horizontalSpan = 2;
		textBox.setLayoutData(data);

		fTextBoxes.add(textBox);
		return textBox;
	}

	protected Link addLink(Composite parent, String label, Key key, SelectionListener linkListener, int indent,
			int widthHint) {
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.horizontalSpan = 3;
		gd.horizontalIndent = indent;
		gd.widthHint = widthHint;

		Link link = new Link(parent, SWT.NONE);
		link.setFont(JFaceResources.getDialogFont());
		link.setText(label);
		link.setData(key);
		link.setLayoutData(gd);
		link.addSelectionListener(linkListener);

		makeScrollableCompositeAware(link);

		fLinks.add(link);

		return link;
	}

	protected ScrolledPageContent getParentScrolledComposite(Control control) {
		Control parent = control.getParent();
		while (!(parent instanceof ScrolledPageContent) && parent != null) {
			parent = parent.getParent();
		}
		if (parent instanceof ScrolledPageContent) {
			return (ScrolledPageContent) parent;
		}
		return null;
	}

	protected ExpandableComposite getParentExpandableComposite(Control control) {
		Control parent = control.getParent();
		while (!(parent instanceof ExpandableComposite) && parent != null) {
			parent = parent.getParent();
		}
		if (parent instanceof ExpandableComposite) {
			return (ExpandableComposite) parent;
		}
		return null;
	}

	private void makeScrollableCompositeAware(Control control) {
		ScrolledPageContent parentScrolledComposite = getParentScrolledComposite(control);
		if (parentScrolledComposite != null) {
			parentScrolledComposite.adaptChild(control);
		}
	}

	protected ExpandableComposite createStyleSection(Composite parent, String label, int nColumns) {
		return createStyleSection(parent, label, nColumns, null);
	}

	protected ExpandableComposite createStyleSection(Composite parent, String label, int nColumns, Key key) {
		ExpandableComposite excomposite = new ExpandableComposite(parent, SWT.NONE,
				ExpandableComposite.TWISTIE | ExpandableComposite.CLIENT_INDENT);
		excomposite.setText(label);
		if (key != null) {
			excomposite.setData(key);
		}
		excomposite.setExpanded(false);
		excomposite.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		excomposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, nColumns, 1));
		excomposite.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				expandedStateChanged((ExpandableComposite) e.getSource());
			}
		});
		fExpandableComposites.add(excomposite);
		makeScrollableCompositeAware(excomposite);
		return excomposite;
	}

	protected final void expandedStateChanged(ExpandableComposite expandable) {
		ScrolledPageContent parentScrolledComposite = getParentScrolledComposite(expandable);
		if (parentScrolledComposite != null) {
			parentScrolledComposite.reflow(true);
		}
	}

	protected void restoreSectionExpansionStates(IDialogSettings settings) {
		for (int i = 0; i < fExpandableComposites.size(); i++) {
			ExpandableComposite excomposite = fExpandableComposites.get(i);
			if (settings == null) {
				excomposite.setExpanded(i == 0); // only expand the first node
													// by default
			} else {
				excomposite.setExpanded(settings.getBoolean(SETTINGS_EXPANDED + String.valueOf(i)));
			}
		}
	}

	protected void storeSectionExpansionStates(IDialogSettings settings) {
		for (int i = 0; i < fExpandableComposites.size(); i++) {
			ExpandableComposite curr = fExpandableComposites.get(i);
			settings.put(SETTINGS_EXPANDED + String.valueOf(i), curr.isExpanded());
		}
	}

	protected SelectionListener getSelectionListener() {
		if (fSelectionListener == null) {
			fSelectionListener = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					controlChanged(e.widget);
				}
			};
		}
		return fSelectionListener;
	}

	protected ModifyListener getTextModifyListener() {
		if (fTextModifyListener == null) {
			fTextModifyListener = new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					textChanged((Text) e.widget);
				}
			};
		}
		return fTextModifyListener;
	}

	protected void controlChanged(Widget widget) {
		ControlData data = (ControlData) widget.getData();
		String newValue = null;
		if (widget instanceof Button) {
			newValue = data.getValue(((Button) widget).getSelection());
		} else if (widget instanceof Combo) {
			newValue = data.getValue(((Combo) widget).getSelectionIndex());
		} else {
			return;
		}
		String oldValue = setValue(data.getKey(), newValue);
		validateSettings(data.getKey(), oldValue, newValue);
	}

	protected void textChanged(Text textControl) {
		Key key = (Key) textControl.getData();
		String number = textControl.getText();
		String oldValue = setValue(key, number);
		validateSettings(key, oldValue, number);
	}

	/**
	 * Checks a setting.
	 * 
	 * @param key
	 *            a key
	 * @param value
	 *            an assumed value for the key
	 * @return <code>true</code> iff the given key's value is equal to the given
	 *         value
	 */
	protected boolean checkValue(Key key, String value) {
		return value.equals(getValue(key));
	}

	/**
	 * Returns the value for the key.
	 * 
	 * @param key
	 *            the key
	 * @return the stored value
	 */
	protected String getValue(Key key) {
		if (fDisabledProjectSettings != null && fDisabledProjectSettings.get(key) != null) {
			return fDisabledProjectSettings.get(key);
		}
		return key.getStoredValue(fLookupOrder, false, fManager);
	}

	protected boolean getBooleanValue(Key key) {
		return Boolean.valueOf(getValue(key)).booleanValue();
	}

	/**
	 * Sets the option <code>key</code> to the value <code>value</code>. Note
	 * that callers have to make sure the corresponding controls are updated
	 * afterwards.
	 * 
	 * @param key
	 *            the option key
	 * @param value
	 *            the new value
	 * @return the old value
	 * 
	 * @see #updateControls()
	 * @see #updateCheckBox(Button)
	 * @see #updateCombo(Combo)
	 * @see #updateText(Text)
	 */
	protected String setValue(Key key, String value) {
		if (fDisabledProjectSettings != null) {
			return fDisabledProjectSettings.put(key, value);
		}
		String oldValue = getValue(key);
		key.setStoredValue(fLookupOrder[0], value, fManager);
		return oldValue;
	}

	/**
	 * Sets the option <code>key</code> to the value <code>value</code>. Note
	 * that callers have to make sure the corresponding controls are updated
	 * afterwards.
	 * 
	 * @param key
	 *            the option key
	 * @param value
	 *            the new value
	 * @return the old value
	 * 
	 * @see #updateControls()
	 * @see #updateCheckBox(Button)
	 * @see #updateCombo(Combo)
	 * @see #updateText(Text)
	 */
	protected String setValue(Key key, boolean value) {
		return setValue(key, String.valueOf(value));
	}

	protected final void setDefaultValue(Key key, String value) {
		IScopeContext instanceScope = fLookupOrder[fLookupOrder.length - 1];
		key.setStoredValue(instanceScope, value, fManager);
	}

	/**
	 * Returns the value as stored in the preference store.
	 *
	 * @param key
	 *            the key
	 * @return the value
	 */
	protected String getStoredValue(Key key) {
		return key.getStoredValue(fLookupOrder, false, fManager);
	}

	/**
	 * Returns the value as actually stored in the preference store, without
	 * considering the working copy store.
	 *
	 * @param key
	 *            the key
	 * @return the value as actually stored in the preference store
	 */
	protected String getOriginalStoredValue(Key key) {
		return key.getStoredValue(fLookupOrder, false, null);
	}

	/**
	 * Reverts the given options to the stored values.
	 * 
	 * @param keys
	 *            the options to revert
	 * @since 3.5
	 */
	protected void revertValues(Key[] keys) {
		for (int i = 0; i < keys.length; i++) {
			Key curr = keys[i];
			String origValue = curr.getStoredValue(fLookupOrder, false, null);
			setValue(curr, origValue);
		}
	}

	/**
	 * Updates fields and validates settings.
	 * 
	 * @param changedKey
	 *            key that changed, or <code>null</code>, if all changed.
	 * @param oldValue
	 *            old value or <code>null</code>
	 * @param newValue
	 *            new value or <code>null</code>
	 */
	protected abstract void validateSettings(Key changedKey, String oldValue, String newValue);

	protected String[] getTokens(String text, String separator) {
		StringTokenizer tok = new StringTokenizer(text, separator);
		int nTokens = tok.countTokens();
		String[] res = new String[nTokens];
		for (int i = 0; i < res.length; i++) {
			res[i] = tok.nextToken().trim();
		}
		return res;
	}

	private boolean getChanges(IScopeContext currContext, List<Key> changedSettings) {
		boolean completeSettings = fProject != null && fDisabledProjectSettings == null; // complete
																							// when
																							// project
																							// settings
																							// are
																							// enabled
		boolean needsBuild = false;
		for (int i = 0; i < fAllKeys.length; i++) {
			Key key = fAllKeys[i];
			String oldVal = key.getStoredValue(currContext, null);
			String val = key.getStoredValue(currContext, fManager);
			if (val == null) {
				if (oldVal != null) {
					changedSettings.add(key);
					needsBuild |= !oldVal.equals(key.getStoredValue(fLookupOrder, true, fManager));
				} else if (completeSettings) {
					key.setStoredValue(currContext, key.getStoredValue(fLookupOrder, true, fManager), fManager);
					changedSettings.add(key);
					// no build needed
				}
			} else if (!val.equals(oldVal)) {
				changedSettings.add(key);
				needsBuild |= oldVal != null || !val.equals(key.getStoredValue(fLookupOrder, true, fManager));
			}
		}
		return needsBuild;
	}

	public void useProjectSpecificSettings(boolean enable) {
		boolean hasProjectSpecificOption = fDisabledProjectSettings == null;
		if (enable != hasProjectSpecificOption && fProject != null) {
			if (enable) {
				for (int i = 0; i < fAllKeys.length; i++) {
					Key curr = fAllKeys[i];
					String val = fDisabledProjectSettings.get(curr);
					curr.setStoredValue(fLookupOrder[0], val, fManager);
				}
				fDisabledProjectSettings = null;
				updateControls();
				validateSettings(null, null, null);
			} else {
				fDisabledProjectSettings = new IdentityHashMap<Key, String>();
				for (int i = 0; i < fAllKeys.length; i++) {
					Key curr = fAllKeys[i];
					String oldSetting = curr.getStoredValue(fLookupOrder, false, fManager);
					fDisabledProjectSettings.put(curr, oldSetting);
					curr.setStoredValue(fLookupOrder[0], null, fManager); // clear
																			// project
																			// settings
				}
			}
		}
	}

	public boolean areSettingsEnabled() {
		return fDisabledProjectSettings == null || fProject == null;
	}

	public boolean performOk() {
		return processChanges(fContainer);
	}

	public boolean performApply() {
		return processChanges(null); // apply directly
	}

	protected boolean processChanges(IWorkbenchPreferenceContainer container) {
		IScopeContext currContext = fLookupOrder[0];

		List<Key> changedOptions = new ArrayList<Key>();
		boolean needsBuild = getChanges(currContext, changedOptions);
		if (changedOptions.isEmpty()) {
			return true;
		}

		if (!this.checkChanges(currContext)) {
			// check failed
			return false;
		}

		if (needsBuild) {
			int count = getRebuildCount();
			if (count > fRebuildCount) {
				needsBuild = false; // build already requested
				fRebuildCount = count;
			}
		}

		boolean doBuild = false;
		if (needsBuild) {
			String[] strings = getFullBuildDialogStrings(fProject == null);
			if (strings != null) {
				if (ResourcesPlugin.getWorkspace().getRoot().getProjects().length == 0) {
					doBuild = true; // don't bother the user
				} else {
					MessageDialog dialog = new MessageDialog(getShell(), strings[0], null, strings[1],
							MessageDialog.QUESTION, new String[] { IDialogConstants.YES_LABEL,
									IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL },
							2);
					int res = dialog.open();
					if (res == 0) {
						doBuild = true;
					} else if (res != 1) {
						return false; // cancel pressed
					}
				}
			}
		}
		if (container != null) {
			// no need to apply the changes to the original store: will be done
			// by the page container
			if (doBuild) { // post build
				incrementRebuildCount();
				container.registerUpdateJob(CoreUtility.getBuildJob(fProject));
			}
		} else {
			// apply changes right away
			try {
				fManager.applyChanges();
			} catch (BackingStoreException e) {
				PHPUiPlugin.log(e);
				return false;
			}
			if (doBuild) {
				CoreUtility.getBuildJob(fProject).schedule();
			}

		}
		return true;
	}

	/**
	 * Default implementation returns true. Override and return false if the
	 * changes are incompatible to the underlying project
	 * 
	 * @param currContext
	 * @return false to deny the changes.
	 */
	protected boolean checkChanges(IScopeContext currContext) {
		return true;
	}

	protected abstract String[] getFullBuildDialogStrings(boolean workspaceSettings);

	public void performDefaults() {
		for (int i = 0; i < fAllKeys.length; i++) {
			Key curr = fAllKeys[i];
			String defValue = curr.getStoredValue(fLookupOrder, true, fManager);
			setValue(curr, defValue);
		}

		settingsUpdated();
		updateControls();
		validateSettings(null, null, null);
	}

	/**
	 * @since 3.1
	 */
	public void performRevert() {
		revertValues(fAllKeys);

		settingsUpdated();
		updateControls();
		validateSettings(null, null, null);
	}

	public void dispose() {
		// hook for subclasses
	}

	/**
	 * Updates the UI from the current settings. Must be called whenever a
	 * setting has been changed by code.
	 */
	protected void updateControls() {
		for (int i = fCheckBoxes.size() - 1; i >= 0; i--) {
			updateCheckBox(fCheckBoxes.get(i));
		}
		for (int i = fComboBoxes.size() - 1; i >= 0; i--) {
			updateCombo(fComboBoxes.get(i));
		}
		for (int i = fTextBoxes.size() - 1; i >= 0; i--) {
			updateText(fTextBoxes.get(i));
		}
	}

	protected void updateCombo(Combo curr) {
		ControlData data = (ControlData) curr.getData();

		String currValue = getValue(data.getKey());
		curr.select(data.getSelection(currValue));
	}

	protected void updateCheckBox(Button curr) {
		ControlData data = (ControlData) curr.getData();

		String currValue = getValue(data.getKey());
		curr.setSelection(data.getSelection(currValue) == 0);
	}

	protected void updateText(Text curr) {
		Key key = (Key) curr.getData();

		String currValue = getValue(key);
		if (currValue != null) {
			curr.setText(currValue);
		}
	}

	protected ExpandableComposite getExpandableComposite(Key key) {
		for (int i = fExpandableComposites.size() - 1; i >= 0; i--) {
			ExpandableComposite curr = fExpandableComposites.get(i);
			Key data = (Key) curr.getData();
			if (key.equals(data)) {
				return curr;
			}
		}
		return null;
	}

	protected Button getCheckBox(Key key) {
		for (int i = fCheckBoxes.size() - 1; i >= 0; i--) {
			Button curr = fCheckBoxes.get(i);
			ControlData data = (ControlData) curr.getData();
			if (key.equals(data.getKey())) {
				return curr;
			}
		}
		return null;
	}

	protected Link getCheckBoxLink(Key key) {
		if (fCheckBoxes == null)
			return null;

		for (int i = fCheckBoxes.size() - 1; i >= 0; i--) {
			Button curr = fCheckBoxes.get(i);
			ControlData data = (ControlData) curr.getData();
			if (key.equals(data.getKey()) && data instanceof LinkControlData) {
				return ((LinkControlData) data).getLink();
			}
		}
		return null;
	}

	protected Combo getComboBox(Key key) {
		for (int i = fComboBoxes.size() - 1; i >= 0; i--) {
			Combo curr = fComboBoxes.get(i);
			ControlData data = (ControlData) curr.getData();
			if (key.equals(data.getKey())) {
				return curr;
			}
		}
		return null;
	}

	protected Text getTextControl(Key key) {
		for (int i = fTextBoxes.size() - 1; i >= 0; i--) {
			Text curr = fTextBoxes.get(i);
			Key data = (Key) curr.getData();
			if (key.equals(data)) {
				return curr;
			}
		}
		return null;
	}

	protected Link getLink(Key key) {
		for (int i = fLinks.size() - 1; i >= 0; i--) {
			Link curr = fLinks.get(i);
			Key data = (Key) curr.getData();
			if (key.equals(data)) {
				return curr;
			}
		}
		return null;
	}

	protected Control findControl(Key key) {
		Combo comboBox = getComboBox(key);
		if (comboBox != null) {
			return comboBox;
		}
		Button checkBox = getCheckBox(key);
		if (checkBox != null) {
			return checkBox;
		}
		Text text = getTextControl(key);
		if (text != null) {
			return text;
		}
		Link link = getLink(key);
		if (link != null) {
			return link;
		}
		return null;
	}

	protected void setComboEnabled(Key key, boolean enabled) {
		Combo combo = getComboBox(key);
		Label label = fLabels.get(combo);
		combo.setEnabled(enabled);
		label.setEnabled(enabled);
		if (!enabled) {
			highlight(combo.getParent(), label, combo, HIGHLIGHT_NONE);
		}
	}

	protected void setTextFieldEnabled(Key key, boolean enabled) {
		Text text = getTextControl(key);
		Label label = fLabels.get(text);
		text.setEnabled(enabled);
		label.setEnabled(enabled);
	}
}

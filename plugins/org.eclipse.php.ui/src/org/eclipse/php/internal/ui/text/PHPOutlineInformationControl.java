/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.text;

import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.viewsupport.AppearanceAwareLabelProvider;
import org.eclipse.dltk.ui.viewsupport.DecoratingModelLabelProvider;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.outline.PHPOutlineContentProvider;
import org.eclipse.php.internal.ui.util.StringMatcher;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.keys.KeySequence;
import org.eclipse.ui.keys.SWTKeySupport;

public class PHPOutlineInformationControl extends AbstractInformationControl {
	
	private KeyAdapter fKeyAdapter;
	private PHPOutlineContentProvider fOutlineContentProvider;
	private Object fInput = null;
	private DecoratingModelLabelProvider fInnerLabelProvider;

	/**
	 * Creates a new PHP outline information control.
	 *
	 * @param parent
	 * @param shellStyle
	 * @param treeStyle
	 * @param commandId
	 */
	public PHPOutlineInformationControl(Shell parent, int shellStyle, int treeStyle, String commandId) {
		super(parent, shellStyle, treeStyle, commandId, true);
	}

	/**
	 * {@inheritDoc}
	 */
	protected Text createFilterText(Composite parent) {
		Text text= super.createFilterText(parent);
		text.addKeyListener(getKeyAdapter());
		return text;
	}

	/**
	 * {@inheritDoc}
	 */
	protected TreeViewer createTreeViewer(Composite parent, int style) {
		Tree tree= new Tree(parent, SWT.SINGLE | (style & ~SWT.MULTI));
		GridData gd= new GridData(GridData.FILL_BOTH);
		gd.heightHint= tree.getItemHeight() * 12;
		tree.setLayoutData(gd);

		final TreeViewer treeViewer= new TreeViewer(tree);

		// Hard-coded filters
		treeViewer.addFilter(new NamePatternFilter());

		IPreferenceStore store = PHPUiPlugin.getDefault().getPreferenceStore();
		AppearanceAwareLabelProvider lprovider = new AppearanceAwareLabelProvider(AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS | ScriptElementLabels.F_APP_TYPE_SIGNATURE | ScriptElementLabels.ALL_CATEGORY, AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS, store);
		fInnerLabelProvider = new DecoratingModelLabelProvider(lprovider);
		treeViewer.setLabelProvider(fInnerLabelProvider);

		fOutlineContentProvider= new PHPOutlineContentProvider(treeViewer);
		treeViewer.setContentProvider(fOutlineContentProvider);
		
		treeViewer.setAutoExpandLevel(AbstractTreeViewer.ALL_LEVELS);

		treeViewer.getTree().addKeyListener(getKeyAdapter());

		return treeViewer;
	}

	/**
	 * {@inheritDoc}
	 */
	protected String getStatusFieldText() {
		KeySequence[] sequences= getInvokingCommandKeySequences();
		if (sequences == null || sequences.length == 0)
			return ""; //$NON-NLS-1$

//		String keySequence= sequences[0].format();

		return ""; //$NON-NLS-1$
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.text.AbstractInformationControl#getId()
	 * @since 3.0
	 */
	protected String getId() {
		return "org.eclipse.php.internal.ui.text.QuickOutline"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInput(Object information) {
		if (information == null || information instanceof String) {
			inputChanged(null, null);
			return;
		}
		fInput = information;
		inputChanged(fInput, information);
	}

	private KeyAdapter getKeyAdapter() {
		if (fKeyAdapter == null) {
			fKeyAdapter= new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int accelerator = SWTKeySupport.convertEventToUnmodifiedAccelerator(e);
					KeySequence keySequence = KeySequence.getInstance(SWTKeySupport.convertAcceleratorToKeyStroke(accelerator));
					KeySequence[] sequences= getInvokingCommandKeySequences();
					if (sequences == null)
						return;
					for (int i= 0; i < sequences.length; i++) {
						if (sequences[i].equals(keySequence)) {
							e.doit= false;
							return;
						}
					}
				}
			};
		}
		return fKeyAdapter;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.text.AbstractInformationControl#fillViewMenu(org.eclipse.jface.action.IMenuManager)
	 */
	protected void fillViewMenu(IMenuManager viewMenu) {
		super.fillViewMenu(viewMenu);
	}
	
	/*
	 * @see org.eclipse.jdt.internal.ui.text.AbstractInformationControl#setMatcherString(java.lang.String, boolean)
	 * @since 3.2
	 */
	protected void setMatcherString(String pattern, boolean update) {
		if (pattern.length() == 0) {
			super.setMatcherString(pattern, update);
			return;
		}
		
		boolean ignoreCase= pattern.toLowerCase().equals(pattern);
		String pattern2= "*" + pattern; //$NON-NLS-1$
		fStringMatcher= new OrStringMatcher(pattern, pattern2, ignoreCase, false);

		if (update)
			stringMatcherUpdated();
		
	}

	/**
	 * String matcher that can match two patterns.
	 * 
	 * @since 3.2
	 */
	private static class OrStringMatcher extends StringMatcher {
		
		private StringMatcher fMatcher1;
		private StringMatcher fMatcher2;
		
		private OrStringMatcher(String pattern1, String pattern2, boolean ignoreCase, boolean foo) {
			super("", false, false); //$NON-NLS-1$
			fMatcher1= new StringMatcher(pattern1, ignoreCase, false);
			fMatcher2= new StringMatcher(pattern2, ignoreCase, false);
		}
		
		public boolean match(String text) {
			return fMatcher2.match(text) || fMatcher1.match(text);
		}
		
	}
}

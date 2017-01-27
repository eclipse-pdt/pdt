/*******************************************************************************
 * Copyright (c) 2012, 2016, 2017 PDT Extension Group and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     PDT Extension Group - initial API and implementation
 *     Kaloyan Raev - [501269] externalize strings
 *******************************************************************************/
package org.eclipse.php.composer.ui.editor.composer;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.collection.ComposerPackages;
import org.eclipse.php.composer.core.ComposerPlugin;
import org.eclipse.php.composer.core.log.Logger;
import org.eclipse.php.composer.core.resources.IComposerProject;
import org.eclipse.php.composer.ui.actions.ToggleDevAction;
import org.eclipse.php.composer.ui.controller.GraphController;
import org.eclipse.php.composer.ui.editor.ComposerFormPage;
import org.eclipse.php.composer.ui.editor.toolbar.SearchControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

/**
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class DependencyGraphPage extends ComposerFormPage implements ModifyListener {

	private static final String SEARCH_ID = "composer.dpg.search"; //$NON-NLS-1$
	private static final String SEPARATOR_ID = "composer.dpg.separator"; //$NON-NLS-1$
	public final static String ID = "org.eclipse.php.composer.ui.editor.composer.DependencyGraphPage"; //$NON-NLS-1$
	protected ComposerFormEditor editor;
	private GraphController graphController;
	private GraphViewer viewer;
	private IComposerProject composerProject;
	private IProject project;
	private SearchControl searchControl;

	private IToolBarManager manager;

	public DependencyGraphPage(ComposerFormEditor editor, String id, String title) {
		super(editor, id, title);
		this.editor = editor;
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);
		if (active) {
			editor.getHeaderForm().getForm().setText(Messages.DependencyGraphPage_Title);
		}

		active = active && editor.isValidJson();

		// set toolbar contributions visible
		manager.find(ToggleDevAction.ID).setVisible(active);
		manager.find(SEPARATOR_ID).setVisible(active);
		searchControl.setVisible(active);
		manager.update(true);

		viewer.getControl().setEnabled(active);

		if (active) {
			update();
		}
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		try {
			createGraph(managedForm);
		} catch (IOException e) {
			Logger.logException(e);
		}
	}

	private void createGraph(IManagedForm managedForm) throws IOException {

		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new FillLayout());

		project = getComposerEditor().getProject();
		composerProject = ComposerPlugin.getDefault().getComposerProject(project);
		graphController = new GraphController(composerProject);
		graphController.setComposerProject(composerProject);
		viewer = new GraphViewer(body, SWT.NO_REDRAW_RESIZE);
		viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		viewer.setContentProvider(graphController);
		viewer.setLabelProvider(graphController);
		viewer.setLayoutAlgorithm(setLayout());
		viewer.applyLayout();

		DevFilter filter = new DevFilter();
		ViewerFilter[] filters = new ViewerFilter[1];
		filters[0] = filter;
		viewer.setFilters(filters);

		update();
	}

	@Override
	public void contributeToToolbar(IToolBarManager manager, IManagedForm headerForm) {
		this.manager = manager;
		searchControl = new SearchControl(SEARCH_ID, headerForm);
		searchControl.setVisible(false);
		searchControl.addModifyListener(this);

		manager.add(searchControl);
		manager.add(new ToggleDevAction(this));
		manager.find(ToggleDevAction.ID).setVisible(false);

		Separator graphSeparator = new Separator();
		graphSeparator.setId(SEPARATOR_ID);
		graphSeparator.setVisible(false);
		manager.add(graphSeparator);
	}

	private LayoutAlgorithm setLayout() {
		LayoutAlgorithm layout;
		layout = new CompositeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING,
				new LayoutAlgorithm[] { new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),
						new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING) });
		return layout;
	}

	private class DevFilter extends ViewerFilter {
		protected boolean showDev = true;

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			if (showDev == true) {
				return true;
			}

			if (element instanceof ComposerPackage) {
				return !composerProject.getComposerPackage().getRequireDev().has((ComposerPackage) element);
			}

			return true;
		}

		public void hideDevPackages() {
			showDev = false;
		}
	}

	protected void update() {
		if (composerProject != null && viewer != null && !viewer.getControl().isDisposed() && editor.isValidJson()) {
			ComposerPackages packages = composerProject.getInstalledPackages();
			packages.add(composerProject.getComposerPackage());
			viewer.setInput(packages);
			applyFilter(true);
		}
	}

	public void applyFilter(boolean showDev) {
		DevFilter filter = new DevFilter();

		if (!showDev) {
			filter.hideDevPackages();
		}

		ViewerFilter[] filters = new ViewerFilter[1];
		filters[0] = filter;
		viewer.setFilters(filters);
		viewer.applyLayout();
	}

	@Override
	public void modifyText(ModifyEvent e) {
		graphController.setFilterText(searchControl.getText());
		viewer.refresh();
	}
}

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
package org.eclipse.php.composer.ui.controller;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.composer.api.ComposerPackage;
import org.eclipse.php.composer.api.collection.ComposerPackages;
import org.eclipse.php.composer.core.resources.IComposerProject;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

public class GraphController extends LabelProvider
		implements IStructuredContentProvider, IGraphEntityContentProvider, IEntityStyleProvider {

	private Color LIGHT_BLUE = new Color(Display.getDefault(), 216, 228, 248);
	private Color DARK_BLUE = new Color(Display.getDefault(), 1, 70, 122);
	private Color LIGHT_GREY = new Color(null, 192, 192, 192);
	private Color GREEN = new Color(Display.getDefault(), 166, 222, 168);

	private ComposerPackages packages;
	private IComposerProject composerProject;

	private String filterText;

	public GraphController(IComposerProject project) {
		composerProject = project;
	}

	public void setComposerProject(IComposerProject project) {
		composerProject = project;
	}

	@Override
	public Object[] getConnectedTo(Object entity) {
		if (!(entity instanceof ComposerPackage)) {
			return null;
		}

		ComposerPackages connections = new ComposerPackages();
		ComposerPackage pkg = (ComposerPackage) entity;

		for (ComposerPackage target : packages) {
			if (pkg.getRequire().has(target) || pkg.getRequireDev().has(target)) {
				connections.add(target);
			}
		}

		return connections.toArray();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof ComposerPackages) {
			packages = (ComposerPackages) newInput;
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return packages.toArray();
	}

	@Override
	public String getText(Object element) {
		if (element instanceof ComposerPackage) {
			return ((ComposerPackage) element).getName();
		}

		if (element instanceof EntityConnectionData) {
			return ""; //$NON-NLS-1$
		}

		return super.getText(element);
	}

	@Override
	public Color getNodeHighlightColor(Object entity) {
		return ColorConstants.yellow;
	}

	@Override
	public Color getBorderColor(Object entity) {
		return LIGHT_GREY;
	}

	@Override
	public Color getBorderHighlightColor(Object entity) {
		return LIGHT_GREY;
	}

	@Override
	public int getBorderWidth(Object entity) {
		return 0;
	}

	@Override
	public Color getBackgroundColour(Object entity) {

		if (entity instanceof ComposerPackage) {
			ComposerPackage pkg = (ComposerPackage) entity;

			if (filterText != null && filterText.length() > 0 && pkg.getName().contains(filterText)) {
				return GREEN;
			}

			if (composerProject.getComposerPackage().getRequireDev().has((ComposerPackage) entity)) {
				return ColorConstants.lightGray;
			}
		}

		return LIGHT_BLUE;
	}

	@Override
	public Color getForegroundColour(Object entity) {
		return DARK_BLUE;
	}

	@Override
	public IFigure getTooltip(Object entity) {
		return null;
	}

	@Override
	public boolean fisheyeNode(Object entity) {
		return false;
	}

	public String getFilterText() {
		return filterText;
	}

	public void setFilterText(String filterText) {
		this.filterText = filterText;
	}
}

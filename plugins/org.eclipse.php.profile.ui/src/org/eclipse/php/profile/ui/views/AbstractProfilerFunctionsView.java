/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.debug.ui.views.coverage.CodeCoverageView;
import org.eclipse.php.profile.core.data.ProfilerFileData;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.ui.ProfilerUIConstants;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

/**
 * Abstract profiler functions view.
 */
public abstract class AbstractProfilerFunctionsView extends AbstractProfilerView {

	/**
	 * Returns the associated tree viewer
	 * 
	 * @return tree viewer
	 */
	public abstract TreeViewer getViewer();

	public void openFunctionInvocationStatisticsView(ProfilerFunctionData function) {
		IWorkbenchPage page = getSite().getWorkbenchWindow().getActivePage();
		if (page != null) {
			try {
				IViewPart part = page.showView(ProfilerUIConstants.FUNCTION_INVOCATION_STATISTICS_VIEW,
						Integer.toString(function.getID()), IWorkbenchPage.VIEW_ACTIVATE);
				if (part != null && part instanceof FunctionInvocationStatisticsView) {
					((FunctionInvocationStatisticsView) part).setInput(getInput(), function);
				}
			} catch (PartInitException e) {
				ProfilerUiPlugin.log(e);
			}
		}
	}

	public void openCodeCoverageView(ProfilerFileData file) {
		IWorkbenchPage page = getSite().getWorkbenchWindow().getActivePage();
		if (page != null) {
			try {
				IViewPart part = page.showView(ProfilerUIConstants.CODE_COVERAGE_VIEW, file.getName().replace(':', '_'),
						IWorkbenchPage.VIEW_ACTIVATE);
				if (part != null && part instanceof CodeCoverageView) {
					((CodeCoverageView) part).setInput(file.getCodeCoverageData());
				}
			} catch (PartInitException e) {
				ProfilerUiPlugin.log(e);
			}
		}
	}

	/**
	 * Saves information about currently expanded elements, starting from the
	 * specified element.
	 * 
	 * @param root
	 *            element
	 */
	public void storeExpandedElements(Object rootElement) {
		if (rootElement != null) {
			((TreeElement) rootElement).setExpanded(getViewer().getExpandedState(rootElement));
			storeExpandedElements(((TreeElement) rootElement).getChildren());
		}
	}

	private void storeExpandedElements(Object[] elements) {
		for (int i = 0; i < elements.length; ++i) {
			storeExpandedElements(elements[i]);
		}
	}

	/**
	 * Saves information about currently expanded elements, starting from the root
	 * element.
	 */
	public void storeExpandedElements() {
		// getViewer().getControl().setRedraw(false);
		// storeExpandedElements(getViewer().getInput());
		// getViewer().getControl().setRedraw(true);
	}

	/**
	 * Restores previously stored expanded elements, starting from the specified
	 * element.
	 * 
	 * @param root
	 *            element
	 */
	public void restoreExpandedElements(Object rootElement) {
		if (rootElement != null) {
			getViewer().setExpandedState(rootElement, ((TreeElement) rootElement).getExpanded());
			restoreExpandedElements(((TreeElement) rootElement).getChildren());
		}
	}

	private void restoreExpandedElements(Object[] elements) {
		for (int i = 0; i < elements.length; ++i) {
			restoreExpandedElements(elements[i]);
		}
	}

	/**
	 * Restores previously stored expanded elements, starting from the root element.
	 */
	public void restoreExpandedElements() {
		// getViewer().getControl().setRedraw(false);
		// restoreExpandedElements(getViewer().getInput());
		// getViewer().getControl().setRedraw(true);
	}
}

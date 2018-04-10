/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.internal.ui.outline.PHPContentOutlineConfiguration;
import org.eclipse.ui.*;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.wst.sse.ui.internal.contentoutline.ConfigurableContentOutlinePage;

public class PHPContextActivator implements IWindowListener, IPartListener2 {
	private final static PHPContextActivator INSTANCE = new PHPContextActivator();

	private Map<CommonNavigator, NavigatorSelectionListener> navigatorListener = new HashMap<>();

	private Map<ContentOutline, IContextActivation> outlineContexts = new HashMap<>();

	private class NavigatorSelectionListener implements ISelectionChangedListener {
		private IContextService contextService;
		private IWorkbenchPartSite site;
		private IContextActivation activation;

		public NavigatorSelectionListener(IWorkbenchPartSite site) {
			this.site = site;
			this.contextService = site.getService(IContextService.class);
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			validateSelection(event.getSelection());
		}

		private void validateSelection(ISelection selection) {
			if (!selection.isEmpty() && selection instanceof IStructuredSelection
					&& isRelevant(((IStructuredSelection) selection).getFirstElement())) {
				if (activation == null) {
					activation = contextService.activateContext(PHPUiConstants.VIEW_SCOPE);
				}
			} else {
				if (isPHPPerspective(site.getPage())) {
					if (activation == null) {
						activation = contextService.activateContext(PHPUiConstants.VIEW_SCOPE);
					}
				} else if (activation != null) {
					contextService.deactivateContext(activation);
					activation = null;
				}
			}
		}

		private boolean isRelevant(Object selection) {
			if (selection instanceof IModelElement) {
				return true;
			} else if (selection instanceof IResource) {
				try {
					if (PHPToolkitUtil.isPHPProject(((IResource) selection).getProject())) {
						return true;
					}
				} catch (CoreException e) {
					PHPUiPlugin.log(e);
				}
			}
			return false;
		}

		public void install() {
			if (this.contextService == null) {
				return;
			}
			ISelectionProvider provider = site.getSelectionProvider();
			if (provider != null) {
				provider.addSelectionChangedListener(this);
				validateSelection(provider.getSelection());
			}
		}

		public void uninstall() {
			if (site.getSelectionProvider() != null) {
				site.getSelectionProvider().removeSelectionChangedListener(this);
			}
		}

	}

	private PHPContextActivator() {
	}

	public boolean isPHPPerspective(IWorkbenchPage page) {
		return page.getPerspective() != null
				&& page.getPerspective().getId().equals(PHPPerspectiveFactory.PERSPECTIVE_ID);
	}

	public static PHPContextActivator getInstance() {
		return INSTANCE;
	}

	public void install() {
		if (!PlatformUI.isWorkbenchRunning()) {
			return;
		}
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null) {
			return;
		}
		workbench.addWindowListener(this);
		for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
			window.getPartService().addPartListener(this);
		}
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		if (window == null) {
			return;
		}
		IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return;
		}
		IWorkbenchPartReference partReference = page.getActivePartReference();
		if (partReference == null) {
			return;
		}
		partActivated(partReference);
	}

	public void uninstall() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.removeWindowListener(this);
		for (IWorkbenchWindow window : workbench.getWorkbenchWindows()) {
			window.getPartService().removePartListener(this);
		}
	}

	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		IWorkbenchPart part = partRef.getPart(false);
		if (part instanceof CommonNavigator) {
			commonNavigatorActivated((CommonNavigator) part);
		} else if (part instanceof ContentOutline) {
			outlineActivated((ContentOutline) part);
		}
	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {

	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		IWorkbenchPart part = partRef.getPart(false);
		if (part instanceof CommonNavigator) {
			commonNavigatorClosed((CommonNavigator) part);
		} else if (part instanceof ContentOutline) {
			outlineClosed((ContentOutline) part);
		}
	}

	private void outlineActivated(ContentOutline part) {
		IContextService contextService = part.getViewSite().getService(IContextService.class);
		if (contextService == null) {
			return;
		}
		if (isPHPRelevantPage(part.getCurrentPage(), part.getViewSite().getPage())) {
			if (!outlineContexts.containsKey(part)) {
				outlineContexts.put(part, contextService.activateContext(PHPUiConstants.VIEW_SCOPE));
			}
		} else {
			if (outlineContexts.containsKey(part)) {
				contextService.deactivateContext(outlineContexts.remove(part));
			}
		}
	}

	private boolean isPHPRelevantPage(IPage outline, IWorkbenchPage page) {
		if (outline instanceof MessagePage && isPHPPerspective(page)) {
			return true;
		} else if (outline instanceof ConfigurableContentOutlinePage && ((ConfigurableContentOutlinePage) outline)
				.getConfiguration() instanceof PHPContentOutlineConfiguration) {
			return true;
		}
		return false;
	}

	private void outlineClosed(ContentOutline part) {
		outlineContexts.remove(part);
	}

	private void commonNavigatorActivated(CommonNavigator part) {
		if (!navigatorListener.containsKey(part)) {
			NavigatorSelectionListener listener = new NavigatorSelectionListener(part.getSite());
			listener.install();
			navigatorListener.put(part, listener);
		}
	}

	private void commonNavigatorClosed(CommonNavigator part) {
		NavigatorSelectionListener listener = navigatorListener.get(part);
		if (listener != null) {
			listener.uninstall();
			navigatorListener.remove(part);
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {

	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {

	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
		// TODO Auto-generated method stub

	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {

	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {

	}

	@Override
	public void windowActivated(IWorkbenchWindow window) {
	}

	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
	}

	@Override
	public void windowClosed(IWorkbenchWindow window) {
		window.getPartService().removePartListener(this);
	}

	@Override
	public void windowOpened(IWorkbenchWindow window) {
		window.getPartService().addPartListener(this);

	}

}

/*******************************************************************************
 * Copyright (c) 2004, 2015 Tasktop Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *     Dawid Pakuła - PDT port
 *******************************************************************************/

package org.eclipse.php.mylyn.internal.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IParent;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.mylyn.DLTKStructureBridge;
import org.eclipse.dltk.internal.mylyn.DLTKUiBridgePlugin;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.text.folding.IFoldingStructureProviderExtension;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.mylyn.commons.core.StatusHandler;
import org.eclipse.mylyn.context.core.AbstractContextListener;
import org.eclipse.mylyn.context.core.ContextChangeEvent;
import org.eclipse.mylyn.context.core.ContextCore;
import org.eclipse.mylyn.context.core.IInteractionElement;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.IEditorPart;

/**
 * @author Mik Kersten
 */
public class ActiveFoldingListener extends AbstractContextListener {

	private final IEditorPart editor;

	private static DLTKStructureBridge bridge = (DLTKStructureBridge) ContextCore
			.getStructureBridge(DLTKStructureBridge.CONTENT_TYPE);

	private boolean enabled = false;

	private final IPropertyChangeListener PREFERENCE_LISTENER = new IPropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(DLTKUiBridgePlugin.AUTO_FOLDING_ENABLED)) {
				if (event.getNewValue().equals(Boolean.TRUE.toString())) {
					enabled = true;
				} else {
					enabled = false;
				}
				updateFolding();
			}
		}
	};

	public ActiveFoldingListener(PHPStructuredEditor editor) {
		this.editor = editor;
		ContextCore.getContextManager().addListener(this);
		DLTKUiBridgePlugin.getDefault().getPreferenceStore().addPropertyChangeListener(PREFERENCE_LISTENER);
		enabled = DLTKUiBridgePlugin.getDefault().getPreferenceStore()
				.getBoolean(DLTKUiBridgePlugin.AUTO_FOLDING_ENABLED);

		updateFolding();
	}

	public void dispose() {
		ContextCore.getContextManager().removeListener(this);
		DLTKUiBridgePlugin.getDefault().getPreferenceStore().removePropertyChangeListener(PREFERENCE_LISTENER);
	}

	public static void resetProjection(IEditorPart javaEditor) {
		// XXX 3.2 ignore for 3.2, leave for 3.1?
	}

	public void updateFolding() {
		if (!enabled || !ContextCore.getContextManager().isContextActive()) {
			if (editor instanceof PHPStructuredEditor) {
				((PHPStructuredEditor) editor).resetProjection();
			}
		} else if (editor.getEditorInput() == null) {
			return;
		} else {
			try {
				List<IModelElement> toExpand = new ArrayList<>();
				List<IModelElement> toCollapse = new ArrayList<>();

				IModelElement element = DLTKUIPlugin.getEditorInputModelElement(editor.getEditorInput());
				if (element instanceof ISourceModule) {
					ISourceModule compilationUnit = (ISourceModule) element;
					List<IModelElement> allChildren = getAllChildren(compilationUnit);
					for (IModelElement child : allChildren) {
						IInteractionElement interactionElement = ContextCore.getContextManager()
								.getElement(bridge.getHandleIdentifier(child));
						if (interactionElement != null && interactionElement.getInterest().isInteresting()) {
							toExpand.add(child);
						} else {
							toCollapse.add(child);
						}
					}
				}
				IFoldingStructureProviderExtension updater = editor
						.getAdapter(IFoldingStructureProviderExtension.class);
				if (updater != null) {
					updater.collapseComments();
					updater.collapseMembers();
					updater.expandElements(toExpand.toArray(new IModelElement[toExpand.size()]));
				}
			} catch (Exception e) {
				StatusHandler
						.log(new Status(IStatus.ERROR, DLTKUiBridgePlugin.ID_PLUGIN, "Could not update folding", e)); //$NON-NLS-1$
			}
		}
	}

	private static List<IModelElement> getAllChildren(IParent parentElement) {
		List<IModelElement> allChildren = new ArrayList<>();
		try {
			for (IModelElement child : parentElement.getChildren()) {
				allChildren.add(child);
				if (child instanceof IParent) {
					allChildren.addAll(getAllChildren((IParent) child));
				}
			}
		} catch (ModelException e) {
			// ignore failures
		}
		return allChildren;
	}

	public void updateFolding(List<IInteractionElement> elements) {
		IFoldingStructureProviderExtension updater = editor.getAdapter(IFoldingStructureProviderExtension.class);
		for (IInteractionElement element : elements) {
			if (updater == null || !enabled) {
				return;
			} else {
				Object object = bridge.getObjectForHandle(element.getHandleIdentifier());
				if (object instanceof IMember) {
					IMember member = (IMember) object;
					if (element.getInterest().isInteresting()) {
						updater.expandElements(new IModelElement[] { member });
						// expand the next 2 children down (e.g. anonymous
						// types)
						try {
							IModelElement[] children = ((IParent) member).getChildren();
							if (children.length == 1) {
								updater.expandElements(new IModelElement[] { children[0] });
								if (children[0] instanceof IParent) {
									IModelElement[] childsChildren = ((IParent) children[0]).getChildren();
									if (childsChildren.length == 1) {
										updater.expandElements(new IModelElement[] { childsChildren[0] });
									}
								}
							}
						} catch (ModelException e) {
							// ignore
						}
					} else {
						updater.collapseElements(new IModelElement[] { member });
					}
				}
			}
		}
	}

	@Override
	public void contextChanged(ContextChangeEvent event) {
		switch (event.getEventKind()) {
		case ACTIVATED:
		case DEACTIVATED:
			if (DLTKUiBridgePlugin.getDefault().getPreferenceStore()
					.getBoolean(DLTKUiBridgePlugin.AUTO_FOLDING_ENABLED)) {
				updateFolding();
			}
			break;
		case CLEARED:
			if (event.isActiveContext()) {
				if (DLTKUiBridgePlugin.getDefault().getPreferenceStore()
						.getBoolean(DLTKUiBridgePlugin.AUTO_FOLDING_ENABLED)) {
					updateFolding();
				}
			}
			break;
		case INTEREST_CHANGED:
			updateFolding(event.getElements());
			break;
		default:
			break;
		}
	}
}
